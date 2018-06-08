package de.hpi.bclab.jchain.net.peering.multicast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import org.apache.log4j.Logger;

public class MulticastAnnouncement implements Runnable{
	
	private static final Logger log = Logger.getLogger(MulticastAnnouncement.class.getName());
	
	private MulticastSocket socket;
	private InetAddress group; //https://www.iana.org/assignments/multicast-addresses/multicast-addresses.xhtml#multicast-addresses-2
	private int port; //https://www.iana.org/assignments/service-names-port-numbers/service-names-port-numbers.xhtml?&page=106
	
	public MulticastAnnouncement(int port, InetAddress group, MulticastSocket socket) {
		this.port = port;
		this.group = group;
		this.socket = socket;
	}

	@Override
	public void run() {
		byte[] msg = new byte[100]; //TODO: actual announcement
		try {
			DatagramPacket packet = new DatagramPacket(msg, msg.length, group, port);
			socket.send(packet);
			log.debug("Sending PeerAnnouncement: " + packet);
		} catch (IOException e) {
			log.error("Failed to send PeerAnnouncement Multicast");
		} 
	}

}
