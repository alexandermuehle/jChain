package de.hpi.bclab.jchain.consensus.nakamoto;

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
	private LinkedBlockingQueue<Transaction> txPool;
	private LinkedBlockingQueue<ConsensusMessage> cnsPool;
	
	public NakamotoManager(Configuration config, State state, LinkedBlockingQueue<Transaction> txPool, LinkedBlockingQueue<ConsensusMessage> cnsPool) {
		this.config = config;
		this.state = state;
		this.txPool = txPool;
		this.cnsPool = cnsPool;
	}
	
	@Override
	public void run() {
		log.info("Starting Nakamoto ConsensusManager");
		while(!Thread.currentThread().isInterrupted()) {
			try {
				Transaction tx = txPool.take();
				log.debug("Processing " + tx);
				//TODO: get work, consensus etc
				state.applyTransaction(tx);
			} catch (InterruptedException e) {
				log.info("Shutting down Nakamoto Consensus Manager");
			}
		}
	}

}
