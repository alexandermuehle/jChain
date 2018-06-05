package de.hpi.bclab.jchain.net.peering.multicast;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import de.hpi.bclab.jchain.net.peering.Peer;

/**
 * PeerDiscovery is responsible for listening to the {@link MulticastAnnouncement} Multicast group and handing received packets to a new {@link AnnouncementHandler} Thread 
 * @author Alexander Mühle
 *
 */
public class MulticastDiscovery implements Runnable{
	
	private static final Logger log = Logger.getLogger(MulticastDiscovery.class.getName());
	

	MulticastSocket socket;
	InetAddress localHost;
	
	private List<Peer> peers;
	
	private boolean listening = true;
	
	public MulticastDiscovery(int port, InetAddress group, List<Peer> peers, MulticastSocket socket) {
		this.peers = peers;
		this.socket = socket;
		try {
			localHost = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
		  	log.fatal("Failed identifying local host");
		   	e.printStackTrace(); 
		   	System.exit(1);
		}
	}
	
	public void run() {
		
		DatagramPacket packet;
		byte[] buff;
		
		try {
			buff = new byte[100];
			packet = new DatagramPacket(buff, buff.length);
			ExecutorService executor = Executors.newCachedThreadPool();
			log.info("Listening to announcements");
			while(listening) {
				socket.receive(packet);
				//spawn handler for received announcement
		        //if (!(packet.getAddress().equals(localHost))) {
		        executor.execute(new AnnouncementHandler(peers, packet));
		        //}
		    }
		} catch (Exception e) {
			log.error("Failed to receive peer announcements");
			log.debug(e);
			e.printStackTrace();
		} 
	}

}