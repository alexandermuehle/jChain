package de.hpi.bclab.jchain.consensus;

import java.util.concurrent.LinkedBlockingQueue;

import org.apache.commons.configuration2.Configuration;
import org.apache.log4j.Logger;

import de.hpi.bclab.jchain.consensus.nakamoto.NakamotoManager;
import de.hpi.bclab.jchain.messages.ConsensusMessage;
import de.hpi.bclab.jchain.net.peering.multicast.MulticastPeerManager;
import de.hpi.bclab.jchain.statemachine.State;
import de.hpi.bclab.jchain.statemachine.Transaction;

public class ConsensusManager implements Runnable {
	
	private static final Logger log = Logger.getLogger(ConsensusManager.class.getName());

	private Configuration config;
	private State state;
	private LinkedBlockingQueue<Transaction> txPool;
	private LinkedBlockingQueue<ConsensusMessage> cnsPool;
	
	public ConsensusManager(Configuration config, State state, LinkedBlockingQueue<Transaction> txPool, LinkedBlockingQueue<ConsensusMessage> cnsPool) {
		this.config = config;
		this.state = state;
		this.txPool = txPool;
		this.cnsPool = cnsPool;
	}

	@Override
	public void run() {
		
		switch(config.getString("consensusmode")) {
		case "nakamoto":
			new NakamotoManager(config, state, txPool, cnsPool).start();
		}
		
	}
	
	public Configuration getConfig() {
		return config;
	}
	
	public State getState() {
		return state;
	}
	
	public LinkedBlockingQueue<Transaction> getTxPool(){
		return txPool;
	}
	
	public LinkedBlockingQueue<ConsensusMessage> getCnsPool(){
		return cnsPool;
	}

}
