package de.hpi.bclab.jchain.consensus.nakamoto;

import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;

import de.hpi.bclab.jchain.messages.ConsensusMessage;

public class Syncing implements Runnable{
	
	private static final Logger log = Logger.getLogger(Syncing.class.getName());
	
	private LinkedBlockingQueue<ConsensusMessage> cnsIn;
	private Blockchain blockchain;

	public Syncing(LinkedBlockingQueue<ConsensusMessage> cnsIn, Blockchain blockchain) {
		this.cnsIn = cnsIn;
		this.blockchain = blockchain;
	}

	@Override
	public void run() {
		
		log.info("Starting Syncing");
	
		ConsensusMessage cnsMsg;
		while(!Thread.interrupted()) {
			try {
				cnsMsg = cnsIn.take();
				blockchain.addBlock(cnsMsg.getBlock());
			} catch (InterruptedException e) {
				log.debug(e);
			}
		}

	}
}
