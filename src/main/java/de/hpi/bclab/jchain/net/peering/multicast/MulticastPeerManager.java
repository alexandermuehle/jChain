package de.hpi.bclab.jchain.net.peering.multicast;

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

import de.hpi.bclab.jchain.net.peering.Peer;
import de.hpi.bclab.jchain.net.peering.PeerManager;

public class MulticastPeerManager extends PeerManager{
	
	private static final Logger log = Logger.getLogger(MulticastPeerManager.class.getName());
	
	/**
	 * The delay before the first announcement is sent to the network. {@value #INITIAL_DELAY}
	 */
	private static final int INITIAL_DELAY = 0;
	/**
	 * The delay between announcements. {@value #REPEAT_DELAY}
	 */
	private static final int REPEAT_DELAY = 30;

	private InetAddress group; //https://www.iana.org/assignments/multicast-addresses/multicast-addresses.xhtml#multicast-addresses-2
	private int port; //https://www.iana.org/assignments/service-names-port-numbers/service-names-port-numbers.xhtml?&page=106
	
	/**
	 * 
	 * @param port The port used for PeerDiscovery
	 * @param group The InetAddress used for Multicast
	 * @throws UnknownHostException 
	 */
	public MulticastPeerManager(Configuration config, List<Peer> peers) {
		super(config, peers);
		this.port = config.getInt("port");
		try {
			this.group = InetAddress.getByName(config.getString("group"));
		} catch (UnknownHostException e) {
			log.error("Failed to recognise multicast group");
		}
	}
	
	
	public void start() {
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
		executor.execute(new MulticastDiscovery(port, group, super.getPeers(), socket));
		
	}
	
	

}
