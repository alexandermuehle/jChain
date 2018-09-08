package de.hpi.bclab.jchain.net.messaging;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;

import de.hpi.bclab.jchain.messages.ConsensusMessage;
import de.hpi.bclab.jchain.statemachine.Transaction;


public class MessagingServer implements Runnable {
	
	private static final Logger log = Logger.getLogger(MessagingServer.class.getName());

	private int msgPort;
	private LinkedBlockingQueue<Transaction> txPool;
	private LinkedBlockingQueue<ConsensusMessage> cnsPool;
	
	public MessagingServer(LinkedBlockingQueue<Transaction> txPool, LinkedBlockingQueue<ConsensusMessage> cnsPool, int msgPort) {
		this.txPool = txPool;
		this.cnsPool = cnsPool;
		this.msgPort = msgPort;
	}
	
	public void run() {
		
		ServerSocket socket = null;
		Socket client = null;
		
		try {
			socket = new ServerSocket(msgPort);
		} catch (IOException e1) {
			log.error("Failed to open Messaging Server Socket");
			return;
		}
		
		ExecutorService executor = Executors.newCachedThreadPool();
		while(!Thread.currentThread().isInterrupted()) {
			try {
				client = socket.accept();
				executor.execute(new ServerHandler(client, txPool, cnsPool));
			} catch (IOException e) {
				log.error("Failed to accept Messaging request");
				log.debug(e);
			}
		
		}
		
		try {
			log.info("Shutting down Messaging Server");
			socket.close();
		} catch (IOException e) {
			log.error("Failed to close Messaging Server Socket");
		}		
	}

}
