package de.hpi.bclab.jchain.consensus.nakamoto;

import java.util.ArrayList;
import java.util.List;

import de.hpi.bclab.jchain.statemachine.Transaction;

public class Block {
	
	private BlockHeader header;
	private ArrayList<Transaction> txs;
	
	public Block(byte[] prevHash, byte[] nonce, List<Transaction> txs) {

	}

}
