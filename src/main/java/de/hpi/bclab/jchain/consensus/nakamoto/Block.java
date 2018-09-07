package de.hpi.bclab.jchain.consensus.nakamoto;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import org.apache.log4j.Logger;

import de.hpi.bclab.jchain.statemachine.Transaction;
import de.hpi.bclab.jchain.util.HashUtil;

public class Block {
	
	private static final Logger log = Logger.getLogger(Block.class.getName());
	
	private String prevHash;
	private long timestamp;
	private String difficulty;
	private int nonce;
	private ArrayList<Transaction> txs;
	
	private String hash;
	
	public Block(String prevHash, String difficulty, ArrayList<Transaction> txs) {
		this.prevHash = prevHash;
		this.difficulty = difficulty;
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
			dos.write(nonce);
			//TODO: merkle root of txs
		} catch (IOException e) {
			log.error("Failed to hash Block");
			log.debug(e);
		}
		this.hash = HashUtil.shaHash(outputStream.toByteArray());
		return this.hash;
	}
	
	public String getHash() {
		return this.hash;
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
	
	public boolean mineBlock() {
		while(hash.compareTo(difficulty) > 0) {
			nonce++;
			calculateHash();
		}
		return true;
	}

}
