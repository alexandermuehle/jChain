package de.hpi.bclab.jchain.net.peering;

import java.net.InetAddress;

/**
 * Peer is the representation of a neighbour with a specific port and address
 * @author Alexander Mühle
 *
 */
public class Peer {
	
	private int port;
	private InetAddress adr;
	
	public Peer(int port, InetAddress adr) {
		this.port = port;
		this.adr = adr;
	}

	public int getPort() {
		return port;
	}
	
	public InetAddress getAdr() {
		return adr;
	}


}
