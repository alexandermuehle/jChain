package de.hpi.bclab.jchain.statemachine.accountmodel;

import org.apache.commons.lang.builder.HashCodeBuilder;

public class Account {
	
	private int id;
	
	public Account(int id) {
		this.id = id;
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if (obj == this) return true;
		if (!(obj instanceof Account)) return false;
		
		Account test = (Account) obj;
		return this.id == test.id;
	}
	
	@Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31). // two randomly chosen prime numbers
            // if deriving: appendSuper(super.hashCode()).
            append(id).
            toHashCode();
    }
	
	@Override
	public String toString() {
		return "ID " + id;
	}

}
