package de.hpi.bclab.jchain.net.messaging;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import org.apache.commons.configuration2.Configuration;
import org.apache.log4j.Logger;

import de.hpi.bclab.jchain.messages.Command;
import de.hpi.bclab.jchain.messages.ConsensusMessage;
import de.hpi.bclab.jchain.messages.TransactionCommand;
import de.hpi.bclab.jchain.net.peering.Peer;
import de.hpi.bclab.jchain.statemachine.Transaction;

public class MessagingManager implements Runnable {
	
	private static final Logger log = Logger.getLogger(MessagingManager.class.getName());
	
	private Configuration config;
	private List<Peer> peers;
	private LinkedBlockingQueue<Transaction> txPool;
	private LinkedBlockingQueue<ConsensusMessage> cnsPool;
	private LinkedBlockingQueue<Command> cmdPool;

	public MessagingManager(Configuration config, List<Peer> peers, LinkedBlockingQueue<Transaction> txPool,
			LinkedBlockingQueue<ConsensusMessage> cnsPool, LinkedBlockingQueue<Command> cmdPool) {
		this.config = config;
		this.peers = peers;
		this.txPool = txPool;
		this.cnsPool = cnsPool;
		this.cmdPool = cmdPool;
	}

	@Override
	public void run() {
		
		log.info("Starting Messaging Manager");
			
		//SERVER THREAD
		
		
		//CMD 
		Command cmd;
		try {
			while((cmd = cmdPool.take()) != null && !Thread.interrupted()) {
				if (cmd instanceof TransactionCommand) {
					Transaction tx = ((TransactionCommand) cmd).getTx();
					txPool.put(tx);
					send(tx);
				}
			}
		} catch (InterruptedException e) {
			log.info("Shutting down Messaging Manager");
		}
		
	}
	
	private void send(Transaction tx) {
		
	}

}
