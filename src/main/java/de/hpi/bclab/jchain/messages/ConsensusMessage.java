package de.hpi.bclab.jchain.messages;

import de.hpi.bclab.jchain.consensus.nakamoto.Block;

public class ConsensusMessage {
	
	private Block block;
	
	public ConsensusMessage(Block block) {
		this.block = block;
	}
	
	public Block getBlock() {
		return this.block;
	}

}
