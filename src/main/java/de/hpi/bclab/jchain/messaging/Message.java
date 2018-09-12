package de.hpi.bclab.jchain.messaging;

public abstract class Message {
	
	private String id;
	
	public Message(String id) {
		this.id = id;
	}
	
	public String getID() {
		return id;
	}
	
	public void setID(String id) {
		this.id = id;
	}

}
