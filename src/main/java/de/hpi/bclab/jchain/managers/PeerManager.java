package de.hpi.bclab.jchain.managers;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import de.hpi.bclab.jchain.net.peering.Peer;
import de.hpi.bclab.jchain.net.peering.PeerAnnouncement;
import de.hpi.bclab.jchain.net.peering.PeerDiscovery;

public class PeerManager implements Runnable{
	
	private static final Logger log = Logger.getLogger(PeerManager.class.getName());
	
	/**
	 * The delay before the first announcement is sent to the network. {@value #INITIAL_DELAY}
	 */
	private static final int INITIAL_DELAY = 0;
	/**
	 * The delay between announcements. {@value #REPEAT_DELAY}
	 */
	private static final int REPEAT_DELAY = 30;

	private List<Peer> peers;
	private InetAddress group; //https://www.iana.org/assignments/multicast-addresses/multicast-addresses.xhtml#multicast-addresses-2
	private int port; //https://www.iana.org/assignments/service-names-port-numbers/service-names-port-numbers.xhtml?&page=106
	
	/**
	 * 
	 * @param port The port used for PeerDiscovery
	 * @param group The InetAddress used for Multicast
	 * @throws UnknownHostException 
	 */
	public PeerManager(int port, String group, List<Peer> peers) throws UnknownHostException {
		this.port = port;
		this.group = InetAddress.getByName(group);
		this.peers = peers;
	}
	
	
	public void run() {
		log.info("Starting NetManager");
		
		MulticastSocket socket = null;
		try {
			socket = new MulticastSocket(port);
			socket.joinGroup(group);
		} catch (IOException e) {
			log.fatal("Failed to join Multicast group");
			log.debug(e);
			System.exit(0);
		}
		
		//Schedule multicast announcement
		ScheduledExecutorService scheduledExecutor = Executors.newScheduledThreadPool(1);
		scheduledExecutor.scheduleWithFixedDelay(new PeerAnnouncement(port, group, socket), INITIAL_DELAY, REPEAT_DELAY, TimeUnit.SECONDS);

		//Start peer discovery thread
		ExecutorService executor = Executors.newSingleThreadExecutor();
		executor.execute(new PeerDiscovery(port, group, peers, socket));
		
	}
	
	

}
