package de.hpi.bclab.jchain.consensus.nakamoto;

import java.util.List;

import de.hpi.bclab.jchain.message.Transaction;

public class Genesis extends Block{

	public Genesis(byte[] prevHash, byte[] difficulty, byte[] nonce, List<Transaction> txs) {
		super(prevHash, difficulty, nonce, txs);
		// TODO Auto-generated constructor stub
	}

}
