package de.hpi.bclab.jchain.statemachine.accountmodel;

import de.hpi.bclab.jchain.statemachine.Transaction;

public class AccountTransaction extends Transaction{
	
	int receiver; //TODO: ID
	int value; //TODO: value

	public AccountTransaction(int receiver, int value) {
		this.receiver = receiver;
		this.value = value;
	}
	
	@Override
	public boolean validateTransaction() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public String toString() {
		return "Transaction to: " + receiver + " with value: " + value;
	}

}