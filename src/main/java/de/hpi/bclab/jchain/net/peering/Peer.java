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

	@Override
	public boolean equals(Object obj) {
		
		if (obj == this) return true;
		if (!(obj instanceof Peer)) return false;
		
		Peer test = (Peer) obj;
		return this.port == test.port && this.adr.equals(test.getAdr());
	}

}
