package de.hpi.bclab.jchain.net.messaging;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;

import de.hpi.bclab.jchain.messages.ConsensusMessage;
import de.hpi.bclab.jchain.net.peering.Peer;
import de.hpi.bclab.jchain.statemachine.Transaction;

public class MessagingClient implements Runnable {
	
	private static final Logger log = Logger.getLogger(MessagingClient.class.getName());
	
	private List<Peer> peers;
	private LinkedBlockingQueue<Transaction> txOut;
	private LinkedBlockingQueue<ConsensusMessage> cnsOut;
	
	public MessagingClient(List<Peer> peers, LinkedBlockingQueue<Transaction> txOut, LinkedBlockingQueue<ConsensusMessage> cnsOut) {
		this.peers = peers;
		this.txOut = txOut;
		this.cnsOut = cnsOut;
	}

	@Override
	public void run() {
		ExecutorService executor = Executors.newFixedThreadPool(2);	
		//SEND TX
		Runnable txTask = () -> {
			Transaction tx;
			while(!Thread.interrupted()) {
				try {
					tx = txOut.take();
					Socket clientSocket = null;
						for (Peer peer : peers) {
							log.info("Sending " + tx + " to " + peer);
							clientSocket = new Socket(peer.getAdr(), peer.getPort());
							ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
//							out.writeObject(tx); //TODO: replace with gson
							clientSocket.close();
							out.close();
						}
				} catch (InterruptedException e) {
					log.info("Failed to process txOut");
					log.debug(e);
				} catch (IOException e) {
					log.error("Failed to open TX Messaging Client Socket");
					e.printStackTrace();
				}
			}
		};
			
		//SEND CNS
		Runnable cnsTask = () -> {
			while(!Thread.interrupted()) {
				try {
					ConsensusMessage cnsMsg = cnsOut.take();
					Socket clientSocket = null;
					for (Peer peer : peers) {
						log.info("Sending " + cnsMsg + " to " + peer);
						clientSocket = new Socket(peer.getAdr(), peer.getPort());
						ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
//						out.writeObject(tx); //TODO: replace with gson
						clientSocket.close();
						out.close();
					}
				} catch (InterruptedException e) {
					log.info("Failed to process cnsOut");
					log.debug(e);
				} catch (IOException e) {
					log.error("Failed to open CNS Messaging Client Socket");
					e.printStackTrace();
				}
			}
		};
		
		//EXECUTE
		executor.execute(txTask);
		executor.execute(cnsTask);
	}

}
