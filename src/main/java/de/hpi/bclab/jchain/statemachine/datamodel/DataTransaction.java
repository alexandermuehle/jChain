package de.hpi.bclab.jchain.statemachine.datamodel;

import de.hpi.bclab.jchain.statemachine.Transaction;

public class DataTransaction extends Transaction {

	String data;
	
	public DataTransaction(String data) {
		this.data = data;
	}
	
	@Override
	public boolean validateTransaction() {
		return true;
	}

	public String getData() {
		return data;
	}

}
