package de.hpi.bclab.jchain.statemachine;

import de.hpi.bclab.jchain.messaging.Message;
import de.hpi.bclab.jchain.statemachine.datamodel.DataTransaction;

public class TransactionMessage extends Message{

	private Transaction tx;
	
	public TransactionMessage(DataTransaction tx) {
		super("tx");
		this.tx = tx;
	}
	
	public Transaction getTx() {
		return tx;
	}
	
	

}
