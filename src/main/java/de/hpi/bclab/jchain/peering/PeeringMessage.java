package de.hpi.bclab.jchain.peering;

import de.hpi.bclab.jchain.messaging.Message;

public abstract class PeeringMessage extends Message{
	
	public PeeringMessage() {
		super("p2p");
	}

}
