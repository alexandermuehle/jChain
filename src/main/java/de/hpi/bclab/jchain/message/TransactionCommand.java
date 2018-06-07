package de.hpi.bclab.jchain.message;

import de.hpi.bclab.jchain.statemachine.Transaction;

public class TransactionCommand extends Command{
	
	private Transaction tx;

	public TransactionCommand(Transaction tx) {
		this.tx = tx;
	}

	public Transaction getTx() {
		return tx;
	}


}
