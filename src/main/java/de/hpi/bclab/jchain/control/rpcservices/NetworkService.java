package de.hpi.bclab.jchain.control.rpcservices;

import java.util.List;

import com.github.arteam.simplejsonrpc.core.annotation.JsonRpcMethod;
import com.github.arteam.simplejsonrpc.core.annotation.JsonRpcService;

import de.hpi.bclab.jchain.net.peering.Peer;

public @JsonRpcService class NetworkService {
	
	private List<Peer> peers;
	
	public NetworkService(List<Peer> peers) {
		this.peers = peers;
	}
	
	public @JsonRpcMethod List<Peer> net_peers() {
		return peers;
	}

}
