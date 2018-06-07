package de.hpi.bclab.jchain.consensus.nakamoto;

import java.util.List;

import de.hpi.bclab.jchain.statemachine.Transaction;

public class Block {
	
	private BlockHeader header;
	
	public Block(byte[] prevHash, byte[] difficulty, byte[] nonce, List<Transaction> txs) {
		
	}

}
