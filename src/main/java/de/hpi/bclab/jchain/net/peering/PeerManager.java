package de.hpi.bclab.jchain.net.peering;

import java.util.List;

import org.apache.commons.configuration2.Configuration;

import de.hpi.bclab.jchain.net.peering.multicast.MulticastPeerManager;
import de.hpi.bclab.jchain.net.peering.unstructured.UnstructuredPeerManager;

public interface PeerManager extends Runnable{
	
	public List<Peer> getPeers();
	
	public List<Peer> getNeighbours();
}
