package de.hpi.bclab.jchain.statemachine.accountmodel;

import java.util.HashMap;

import de.hpi.bclab.jchain.statemachine.State;
import de.hpi.bclab.jchain.statemachine.Transaction;

public class AccountState extends State{
	
	private HashMap<Account, Long> state;
	
	public AccountState() {
		this.state = new HashMap<Account, Long>();
	}
	
	public Long getValue(Account account) {
		return state.get(account);
	}

	public void applyTransaction(Transaction tx) {
		AccountTransaction atx = (AccountTransaction) tx;
		state.put(atx.getReceiver(), state.getOrDefault(atx.getReceiver(), (long) 0) + atx.getValue());
	}
	
	public boolean verifyTransaction(Transaction tx) {
		return false;
		// TODO Auto-generated method stub
	}

}
