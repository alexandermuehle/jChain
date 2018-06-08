package de.hpi.bclab.jchain.consensus.nakamoto;

import java.util.concurrent.LinkedBlockingQueue;

import org.apache.commons.configuration2.Configuration;

import de.hpi.bclab.jchain.consensus.ConsensusManager;
import de.hpi.bclab.jchain.messages.ConsensusMessage;
import de.hpi.bclab.jchain.statemachine.State;
import de.hpi.bclab.jchain.statemachine.Transaction;

public class NakamotoManager extends ConsensusManager {

	public NakamotoManager(Configuration config, State state, LinkedBlockingQueue<Transaction> txPool,
			LinkedBlockingQueue<ConsensusMessage> cnsPool) {
		super(config, state, txPool, cnsPool);
		// TODO Auto-generated constructor stub
	}

}
