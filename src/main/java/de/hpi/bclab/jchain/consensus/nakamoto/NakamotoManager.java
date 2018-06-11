package de.hpi.bclab.jchain.consensus.nakamoto;

import java.util.concurrent.LinkedBlockingQueue;

import org.apache.commons.configuration2.Configuration;
import org.apache.log4j.Logger;

import de.hpi.bclab.jchain.consensus.ConsensusManager;
import de.hpi.bclab.jchain.messages.ConsensusMessage;
import de.hpi.bclab.jchain.statemachine.State;
import de.hpi.bclab.jchain.statemachine.Transaction;

public class NakamotoManager extends ConsensusManager {
	
	private static final Logger log = Logger.getLogger(NakamotoManager.class.getName());

	public NakamotoManager(Configuration config, State state, LinkedBlockingQueue<Transaction> txPool,
			LinkedBlockingQueue<ConsensusMessage> cnsPool) {
		super(config, state, txPool, cnsPool);
	}
	
	public void start() {
		log.info("Starting Nakamoto ConsensusManager");
		while(true) {
			try {
				Transaction tx = super.getTxPool().take();
				log.debug("Processing " + tx);
				//TODO: get work, consensus etc
				super.getState().applyTransaction(tx);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
