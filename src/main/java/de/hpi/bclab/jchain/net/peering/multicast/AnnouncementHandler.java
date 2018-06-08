package de.hpi.bclab.jchain.net.peering.multicast;

import java.net.DatagramPacket;
import java.util.List;

import org.apache.log4j.Logger;

import de.hpi.bclab.jchain.net.peering.Peer;

public class AnnouncementHandler implements Runnable{
	
	private static final Logger log = Logger.getLogger(AnnouncementHandler.class.getName());
	
	private List<Peer> peers;
	private DatagramPacket packet;
	
	public AnnouncementHandler(List<Peer> peers, DatagramPacket packet) {
		this.peers = peers;
		this.packet = packet;
	}

	@Override
	public void run() {
		Peer peer = new Peer(packet.getPort(), packet.getAddress());
		if(!peers.contains(peer)) {
			peers.add(peer);
		}
		log.debug("Handler received Announcement from " + peer);
	}

}