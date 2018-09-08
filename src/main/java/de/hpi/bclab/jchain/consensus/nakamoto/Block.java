package de.hpi.bclab.jchain.consensus.nakamoto;

import java.util.ArrayList;
import java.util.Date;

import de.hpi.bclab.jchain.statemachine.Transaction;

public class Block {
		
	private String prevHash;
	private long timestamp;
	private String difficulty;
	private long nonce;
	private ArrayList<Transaction> txs;
	
	private String hash;
	
	public Block(String prevHash, String difficulty, ArrayList<Transaction> txs) {
		this.prevHash = prevHash;
		this.difficulty = difficulty;
		this.nonce = 0;
		this.timestamp = new Date().getTime();
		this.txs = txs;
	}
	
	public long getTimestamp() {
		return this.timestamp;
	}
	
	public String getDifficulty() {
		return this.difficulty;
	}
	
	public String getPrevHash() {
		return this.prevHash;
	}
	
	public long getNonce() {
		return this.nonce;
	}
	
	public String getHash() {
		return this.hash;
	}
	
	public void setHash(String hash) {
		this.hash = hash;
	}

	public void setTimestamp(long time) {
		this.timestamp = time;
	}

	public ArrayList<Transaction> getTransactions() {
		return this.txs;
	}


}
