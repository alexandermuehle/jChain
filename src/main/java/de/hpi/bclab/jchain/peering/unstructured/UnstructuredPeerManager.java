package de.hpi.bclab.jchain.peering.unstructured;

import java.util.List;

import org.apache.commons.configuration2.Configuration;

import de.hpi.bclab.jchain.peering.Peer;
import de.hpi.bclab.jchain.peering.PeerManager;

public class UnstructuredPeerManager implements PeerManager{
	
	private List<Peer> peers;
	private Configuration config;

	public UnstructuredPeerManager(Configuration config, List<Peer> peers) {
		this.peers = peers;
	}

	@Override
	public void run() {
		Peer bootstrap = bootstrap();
		List<Peer> gossip = getAddr(bootstrap);
	}

	@Override
	public List<Peer> getPeers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Peer> getNeighbours() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	private Peer bootstrap() {
		config.getString("bootstrap");
		return null;
	}
	
	private List<Peer> getAddr(Peer bootstrap){
		return null;
	}

}
