package de.hpi.bclab.jchain.consensus;

import java.util.concurrent.LinkedBlockingQueue;

import org.apache.commons.configuration2.Configuration;

import de.hpi.bclab.jchain.message.ConsensusMessage;
import de.hpi.bclab.jchain.message.Transaction;
import de.hpi.bclab.jchain.statemachine.State;

public class ConsensusManager implements Runnable {

	public ConsensusManager(Configuration config, State state, LinkedBlockingQueue<Transaction> txPool, LinkedBlockingQueue<ConsensusMessage> cnsPool) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
