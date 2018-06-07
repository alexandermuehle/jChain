package de.hpi.bclab.jchain.consensus;

import java.util.concurrent.LinkedBlockingQueue;

import org.apache.commons.configuration2.Configuration;
import org.apache.log4j.Logger;

import de.hpi.bclab.jchain.message.ConsensusMessage;
import de.hpi.bclab.jchain.statemachine.State;
import de.hpi.bclab.jchain.statemachine.Transaction;

public class ConsensusManager implements Runnable {
	
	private static final Logger log = Logger.getLogger(ConsensusManager.class.getName());

	public ConsensusManager(Configuration config, State state, LinkedBlockingQueue<Transaction> txPool, LinkedBlockingQueue<ConsensusMessage> cnsPool) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {
		log.info("Starting ConsensusManager");
		
	}

}
