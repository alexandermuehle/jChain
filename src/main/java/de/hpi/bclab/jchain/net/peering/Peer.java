package de.hpi.bclab.jchain.net.peering;

import java.io.IOException;
import java.net.InetAddress;

import org.apache.log4j.Logger;


/**
 * Peer is the representation of a neighbour with a specific address, port, latency distance
 * TODO: public key for secure communication
 * 
 * @author Alexander Mühle
 *
 */
public class Peer {
	
	private static final Logger log = Logger.getLogger(Peer.class.getName());
	
	private int port;
	private InetAddress adr;
	private int distance;
	
	public Peer(int port, InetAddress adr) {
		
		this.port = port;
		this.adr = adr;
		
		//Check reachability and distance
		//TODO: use landmark- or simulation-based Network Coordinates to determine topology
		long finish = 0;
		long start = System.currentTimeMillis();
		try {
			if(adr.isReachable(8000)) {
				finish = System.currentTimeMillis();
				this.distance = (int) (finish - start);
			}
		} catch (IllegalArgumentException | IOException e) {
			log.error("Peer at " + adr + " is unreachable");
		}
		
	}

	public int getPort() {
		return port;
	}
	
	public InetAddress getAdr() {
		return adr;
	}
	
	public int getDistance() {
		return distance;
	}

	@Override
	public String toString() {
		return "Peer at " + this.adr + ":" + this.port + " RTT: " + this.distance;
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if (obj == this) return true;
		if (!(obj instanceof Peer)) return false;
		
		Peer test = (Peer) obj;
		return this.port == test.port && this.adr.equals(test.getAdr());
	}

}
