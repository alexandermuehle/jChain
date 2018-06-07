package de.hpi.bclab.jchain.statemachine;

public abstract class State {
	
	abstract public void applyTransaction(Transaction tx);
	
	abstract public boolean verifyTransaction(Transaction tx);

}
