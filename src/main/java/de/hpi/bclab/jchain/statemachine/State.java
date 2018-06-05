package de.hpi.bclab.jchain.statemachine;

import de.hpi.bclab.jchain.message.Transaction;

public abstract class State {
	
	abstract public void applyTransaction(Transaction tx);
	
	abstract public boolean verifyTransaction(Transaction tx);

}
