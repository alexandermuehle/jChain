package de.hpi.bclab.jchain.peering;

import java.util.List;

public interface PeerManager extends Runnable{
	
	public List<Peer> getPeers();
	
	public List<Peer> getNeighbours();
}
