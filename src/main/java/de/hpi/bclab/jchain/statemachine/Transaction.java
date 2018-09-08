package de.hpi.bclab.jchain.statemachine;

import java.io.Serializable;

public abstract class Transaction implements Serializable{
	
	public abstract boolean validateTransaction();

}
