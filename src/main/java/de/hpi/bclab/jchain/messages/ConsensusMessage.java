package de.hpi.bclab.jchain.messages;

import de.hpi.bclab.jchain.consensus.nakamoto.Block;

public class ConsensusMessage {
	
	private Block blocK;
	
	public ConsensusMessage(Block block) {
		this.blocK = block;
	}

}
