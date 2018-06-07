package de.hpi.bclab.jchain.statemachine.accountmodel;

import java.util.HashMap;

import de.hpi.bclab.jchain.statemachine.State;
import de.hpi.bclab.jchain.statemachine.Transaction;
import de.hpi.bclab.jchain.statemachine.Value;

public class AccountState extends State{
	
	private HashMap<Account, Value> state;

	public void applyTransaction(Transaction tx) {
		// TODO Auto-generated method stub
	}
	
	public boolean verifyTransaction(Transaction tx) {
		return false;
		// TODO Auto-generated method stub
	}

}
