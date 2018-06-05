package de.hpi.bclab.jchain.net.peering;

import java.net.DatagramPacket;
import java.util.List;

import org.apache.log4j.Logger;

public class PeerHandler implements Runnable{
	
	private static final Logger log = Logger.getLogger(PeerHandler.class.getName());
	
	private List<Peer> peers;
	private DatagramPacket packet;
	
	public PeerHandler(List<Peer> peers, DatagramPacket packet) {
		this.peers = peers;
		this.packet = packet;
	}

	@Override
	public void run() {
		log.info("Handler received Announcement from " + packet.getAddress());
		Peer peer = new Peer(packet.getPort(), packet.getAddress());
		if(!peers.contains(peer)) {
			peers.add(peer);
		}
	}

}