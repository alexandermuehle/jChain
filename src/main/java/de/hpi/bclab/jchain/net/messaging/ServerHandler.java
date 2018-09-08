package de.hpi.bclab.jchain.net.messaging;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

import de.hpi.bclab.jchain.messages.ConsensusMessage;
import de.hpi.bclab.jchain.statemachine.Transaction;

public class ServerHandler implements Runnable {
	
	private LinkedBlockingQueue<Transaction> txPool;
	private LinkedBlockingQueue<ConsensusMessage> cnsPool;
	private Socket client;
	
	public ServerHandler(Socket client, LinkedBlockingQueue<Transaction> txPool, LinkedBlockingQueue<ConsensusMessage> cnsPool) {
		this.client = client;
		this.txPool = txPool;
		this.cnsPool = cnsPool;
	}

	@Override
	public void run() {
		try {
			client.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
