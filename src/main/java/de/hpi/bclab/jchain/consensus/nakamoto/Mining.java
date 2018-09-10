package de.hpi.bclab.jchain.consensus.nakamoto;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.commons.configuration2.Configuration;
import org.apache.log4j.Logger;

import de.hpi.bclab.jchain.messages.ConsensusMessage;
import de.hpi.bclab.jchain.statemachine.State;
import de.hpi.bclab.jchain.statemachine.Transaction;
import de.hpi.bclab.jchain.util.HashUtil;

public class Mining implements Runnable{
	
	private static final Logger log = Logger.getLogger(Mining.class.getName());
	
	private Configuration config;
	private LinkedBlockingQueue<Transaction> txPool;
	private Blockchain blockchain;
	private State state;
	private LinkedBlockingQueue<ConsensusMessage> cnsOut;
	
	public Mining(Configuration config, LinkedBlockingQueue<Transaction> txPool, Blockchain blockchain, State state, LinkedBlockingQueue<ConsensusMessage> cnsOut) {
		this.config = config;
		this.txPool = txPool;
		this.blockchain = blockchain;
		this.state = state;
		this.cnsOut = cnsOut;
	}

	@Override
	public void run() {
		
		log.info("Starting Mining");
		
		try {
			ArrayList<Transaction> txList = new ArrayList<>();
			while(!Thread.interrupted()) {
				while(config.getBoolean("mining")) {
					//fill txlist until the block is full or no tx are in the pool
					while(config.getInt("blocksize") > txList.size() && !txPool.isEmpty()) {
						txList.add(txPool.take());
					}
					//create new block and find nonce to fit difficulty
					Block newBlock = new Block(blockchain.getHead().getHash(), blockchain.getDifficulty(), txList);
					mine(newBlock);
					log.info("Found new Block: " + newBlock.getHash());
					//add it to the blockchain
					if (blockchain.addBlock(newBlock)) {
						//apply to state
						for(Transaction tx : txList) {
							state.applyTransaction(tx);	
						}
					}

					//broadcast block
					cnsOut.put(new BlockMessage(newBlock));	
				}
			}
		} catch (InterruptedException e) {
			log.info("Stopping Mining");
		}
		log.info("Stopping Mining");
	}
	
	private long mine(Block block) {
		long nonce = 0;
		String hash = HashUtil.blockHash(block.getPrevHash(), block.getTimestamp(), nonce, block.getTransactions());
		while(hash.compareTo(block.getDifficulty()) > 0) {
			nonce++;
			//reset timestamp and nonce if no block is found
			if(nonce == Long.MAX_VALUE) {
				block.setTimestamp(new Date().getTime());
				nonce = 0;
			}
			hash = HashUtil.blockHash(block.getPrevHash(), block.getTimestamp(), nonce, block.getTransactions());
		}
		block.setHash(hash);
		log.info("nonce found: " + nonce);
		return nonce;
	}
	
}
