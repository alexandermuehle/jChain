package de.hpi.bclab.jchain.consensus.nakamoto;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.commons.configuration2.Configuration;
import org.apache.log4j.Logger;

import de.hpi.bclab.jchain.consensus.ConsensusManager;
import de.hpi.bclab.jchain.messages.ConsensusMessage;
import de.hpi.bclab.jchain.statemachine.State;
import de.hpi.bclab.jchain.statemachine.Transaction;

public class NakamotoManager implements ConsensusManager {
	
	private static final Logger log = Logger.getLogger(NakamotoManager.class.getName());

	private Configuration config;
	private State state;
	private Blockchain blockchain;
	private LinkedBlockingQueue<Transaction> txPool;
	private LinkedBlockingQueue<ConsensusMessage> cnsIn;	
	private LinkedBlockingQueue<ConsensusMessage> cnsOut;

	public NakamotoManager(Configuration config, State state, LinkedBlockingQueue<Transaction> txPool, LinkedBlockingQueue<ConsensusMessage> cnsIn, LinkedBlockingQueue<ConsensusMessage> cnsOut) {
		this.config = config;
		this.state = state;
		this.txPool = txPool;
		this.cnsIn = cnsIn;
		this.cnsOut = cnsOut;
		this.blockchain = new Blockchain();
	}
	
	@Override
	public void run() {
		log.info("Starting Nakamoto ConsensusManager");
		while(!Thread.currentThread().isInterrupted()) {
			try {
				ArrayList<Transaction> txList = new ArrayList<>();

				//fill txlist until the block is full or no tx are in the pool
				while(config.getInt("blocksize") > txList.size() && !txPool.isEmpty()) {
					txList.add(txPool.take());
				}
				//create new block and find nonce to fit difficulty
				Block newBlock = new Block(blockchain.getHead().getHash(), blockchain.getDifficulty(), txList);
				newBlock.mineBlock(); 
				log.info("Found new Block: " + newBlock.getHash());
				//add it to the blockchain
				if (blockchain.addBlock(newBlock)) {
					//apply to state
					for(Transaction tx : txList) {
						state.applyTransaction(tx);	
					}
				}
				
				//broadcast block
				cnsOut.put(new ConsensusMessage(newBlock));

				
			} catch (InterruptedException e) {
				log.info("Shutting down Nakamoto Consensus Manager");
			}
		}
	}

}
