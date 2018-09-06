package de.hpi.bclab.jchain.consensus.nakamoto;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import org.apache.log4j.Logger;

import de.hpi.bclab.jchain.statemachine.Transaction;
import de.hpi.bclab.jchain.util.HashUtil;

public class Block {
	
	private static final Logger log = Logger.getLogger(Block.class.getName());
	
	private String prevHash;
	private long timestamp;
	private int nonce;
	private ArrayList<Transaction> txs;
	
	private String hash;
	
	public Block(String prevHash, ArrayList<Transaction> txs) {
		this.prevHash = prevHash;
		this.timestamp = new Date().getTime();
		this.txs = txs;
		this.hash = calculateHash();
	}

	public String calculateHash() {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );
        DataOutputStream dos = new DataOutputStream(outputStream);
		try {
			dos.writeUTF(prevHash);
			dos.writeLong(timestamp);
			outputStream.write(nonce);
			
		} catch (IOException e) {
			log.error("Failed to hash Block");
			log.debug(e);
		}
		this.hash = HashUtil.sha3Hash(outputStream.toByteArray());
		return this.hash;
	}
	
	public String getHash() {
		return this.hash;
	}
	
	public boolean mineBlock(int difficulty) {
		while(!this.getHash().substring(0, difficulty).matches("0*")) {
			this.nonce++; 
		}
		return true;
	}

}
