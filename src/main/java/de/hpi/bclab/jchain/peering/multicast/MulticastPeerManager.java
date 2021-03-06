package de.hpi.bclab.jchain.peering.multicast;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.configuration2.Configuration;
import org.apache.log4j.Logger;

import de.hpi.bclab.jchain.peering.Peer;
import de.hpi.bclab.jchain.peering.PeerManager;

/**
 * Vulnerable to eclipse attacks through connection monopolisation because there are only incoming announcments
 * Vulnerable to DoS because we handle unsolicited announcments
 *  	TODO: ping pong with hash of ping to only receive answers to own announcment
 *  
 *  Improve on this maybe http://www.bittorrent.org/beps/bep_0014.html
 * 
 * @author Alexander Mühle
 *
 */
public class MulticastPeerManager implements PeerManager{
	
	private static final Logger log = Logger.getLogger(MulticastPeerManager.class.getName());
	
	/**
	 * The delay before the first announcement is sent to the network.
	 */
	private static final int INITIAL_DELAY = 0;
	/**
	 * The delay between announcements. 
	 */
	private static final int REPEAT_DELAY = 30;
	
	/**
	 * Maximum number of neighbours allowed in this mode 
	 */
	private static final int MAX_NEIGHBOURS = 7;

	private InetAddress group; //https://www.iana.org/assignments/multicast-addresses/multicast-addresses.xhtml#multicast-addresses-2
	private int port; //https://www.iana.org/assignments/service-names-port-numbers/service-names-port-numbers.xhtml?&page=106
	
	private List<Peer> peers;
	
	/**
	 * 
	 * @param port The port used for PeerDiscovery
	 * @param group The InetAddress used for Multicast
	 * @throws UnknownHostException 
	 */
	public MulticastPeerManager(Configuration config, List<Peer> peers) {
		this.peers = peers;
		this.port = config.getInt("port");
		try {
			this.group = InetAddress.getByName(config.getString("group"));
		} catch (UnknownHostException e) {
			log.error("Failed to recognise multicast group");
		}
	}

	@Override
	public void run() {
		log.info("Starting Multicast PeerManager");
		MulticastSocket socket = null;
		try {
			socket = new MulticastSocket(port);
			socket.joinGroup(group);
		} catch (IOException e) {
			log.fatal("Failed to join Multicast group");
			System.exit(0);
		}
		
		//Schedule multicast announcement
		ScheduledExecutorService scheduledExecutor = Executors.newSingleThreadScheduledExecutor();
		scheduledExecutor.scheduleWithFixedDelay(new MulticastAnnouncement(port, group, socket), INITIAL_DELAY, REPEAT_DELAY, TimeUnit.SECONDS);

		//Start multicast peer discovery thread
		ExecutorService executor = Executors.newSingleThreadExecutor();
		executor.execute(new MulticastDiscovery(port, group, peers, socket));
		
		while(!Thread.currentThread().isInterrupted()) {
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				break;
			}
		}
		log.info("Shutting down Multicast Announcmenet and Discovery");
		scheduledExecutor.shutdownNow();
		executor.shutdownNow();
		try {
			scheduledExecutor.awaitTermination(1, TimeUnit.MINUTES);
			executor.awaitTermination(1, TimeUnit.MINUTES);
		} catch (InterruptedException ex) {
			log.error("Failed to shutdown Multicast Announcment and Discovery");
		}
	}


	@Override
	public List<Peer> getPeers() {
		return peers;
	}


	@Override
	public List<Peer> getNeighbours() {
		return peers.subList(0, MAX_NEIGHBOURS);
	}
	
	

}
