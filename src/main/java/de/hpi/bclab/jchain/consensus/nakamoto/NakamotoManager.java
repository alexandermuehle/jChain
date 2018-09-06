package de.hpi.bclab.jchain.consensus.nakamoto;

import java.util.ArrayList;
import java.util.Random;
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
	private ArrayList<Block> blockchain;
	private LinkedBlockingQueue<Transaction> txPool;
	private LinkedBlockingQueue<ConsensusMessage> cnsIn;	
	private LinkedBlockingQueue<ConsensusMessage> cnsOut;

	public NakamotoManager(Configuration config, State state, LinkedBlockingQueue<Transaction> txPool, LinkedBlockingQueue<ConsensusMessage> cnsIn, LinkedBlockingQueue<ConsensusMessage> cnsOut) {
		this.config = config;
		this.state = state;
		this.txPool = txPool;
		this.cnsIn = cnsIn;
		this.cnsOut = cnsOut;
	}
	
	@Override
	public void run() {
		log.info("Starting Nakamoto ConsensusManager");
		while(!Thread.currentThread().isInterrupted()) {
			try {
				ArrayList<Transaction> txList = new ArrayList<>();
				//TODO: get work, consensus etc
				while(config.getInt("blks") > txList.size()) {
					txList.add(txPool.take());
				}
				Block newBlock = new Block(blockchain.get(blockchain.size()-1).getHash(), txList);
				newBlock.mineBlock(2); //TODO calculate difficulty from blockchain
				for(Transaction tx : txList) {
					state.applyTransaction(tx);	
				}
			} catch (InterruptedException e) {
				log.info("Shutting down Nakamoto Consensus Manager");
			}
		}
	}

}
