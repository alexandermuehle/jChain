package de.hpi.bclab.jchain.consensus;

import de.hpi.bclab.jchain.messaging.Message;

public abstract class ConsensusMessage extends Message{
	
	private String cnsAlg;

	public ConsensusMessage(String cnsAlg) {
		super("cns");
		this.cnsAlg = cnsAlg;
	}
	
	public String getCnsAlg() {
		return cnsAlg;
	}

}
