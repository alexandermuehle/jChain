package de.hpi.bclab.jchain.statemachine.accountmodel;

import de.hpi.bclab.jchain.statemachine.Transaction;

public class AccountTransaction extends Transaction{
	
	Account receiver; //TODO: ID
	Long value; //TODO: value

	public AccountTransaction(int receiver, int value) {
		this.receiver = new Account(receiver);
		this.value = (long) value;
	}
	
	@Override
	public boolean validateTransaction() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public Account getReceiver() {
		return receiver;
	}
	
	public Long getValue() {
		return value;
	}
	
	@Override
	public String toString() {
		return "Transaction to: " + receiver + " with value: " + value;
	}

}