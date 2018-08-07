package de.hpi.bclab.jchain.statemachine;

import java.io.Externalizable;

public abstract class Transaction implements Externalizable{
	
	public abstract boolean validateTransaction();

}
