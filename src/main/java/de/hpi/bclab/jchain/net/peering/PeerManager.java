package de.hpi.bclab.jchain.net.peering;

import java.util.List;

import org.apache.commons.configuration2.Configuration;

import de.hpi.bclab.jchain.net.peering.multicast.MulticastPeerManager;

public class PeerManager implements Runnable{
	
	private List<Peer> peers;
	private Configuration config;
	
	public PeerManager(Configuration config, List<Peer> peers) {
		this.peers = peers;
		this.config = config;
	}
	
	public void run() {
		switch(config.getString("peermode")) {
			case "multicast":
				new MulticastPeerManager(config, peers).start();
		}
	}

	public List<Peer> getPeers() {
		return peers;
	}

	public Configuration getConfig() {
		return config;
	}

		
}
