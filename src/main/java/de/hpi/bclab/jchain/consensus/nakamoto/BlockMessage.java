package de.hpi.bclab.jchain.consensus.nakamoto;

import de.hpi.bclab.jchain.consensus.ConsensusMessage;

public class BlockMessage extends ConsensusMessage {
	
	private Block block;

	public BlockMessage(Block block) {
		super("nakamoto");
		this.block = block;
	}

	public Block getBlock() {
		return this.block;
	}

}
