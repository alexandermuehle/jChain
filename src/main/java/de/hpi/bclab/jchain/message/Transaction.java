package de.hpi.bclab.jchain.message;

import java.io.Serializable;

public abstract class Transaction implements Serializable{
	
	abstract boolean validateTransaction();

}
