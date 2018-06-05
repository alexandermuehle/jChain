package de.hpi.bclab.jchain.net.messaging;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.commons.configuration2.Configuration;

import de.hpi.bclab.jchain.cli.Command;
import de.hpi.bclab.jchain.message.ConsensusMessage;
import de.hpi.bclab.jchain.message.Transaction;
import de.hpi.bclab.jchain.net.peering.Peer;

public class MessagingManager implements Runnable {

	public MessagingManager(Configuration config, List<Peer> peers, LinkedBlockingQueue<Transaction> txPool,
			LinkedBlockingQueue<ConsensusMessage> cnsPool, LinkedBlockingQueue<Command> cmdPool) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
