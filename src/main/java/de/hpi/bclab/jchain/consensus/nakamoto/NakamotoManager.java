package de.hpi.bclab.jchain.consensus.nakamoto;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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
		ExecutorService executor = Executors.newFixedThreadPool(2);
		//could consider an interrupt when a new head is synced
		executor.execute(new Mining(config, txPool, blockchain, state, cnsOut));
		executor.execute(new Syncing(cnsIn, blockchain));
	}

}
