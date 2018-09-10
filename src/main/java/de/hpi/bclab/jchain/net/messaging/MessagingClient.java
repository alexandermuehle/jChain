package de.hpi.bclab.jchain.net.messaging;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
		
		log.info("Starting Messaging Client");
		
		ExecutorService executor = Executors.newFixedThreadPool(2);	
		//SEND TX
		Runnable txTask = () -> {
			Transaction tx;
			String txJson;
			Gson gson = new GsonBuilder().create();
			while(!Thread.interrupted()) {
				try {
					tx = txOut.take();
					txJson = gson.toJson(tx);
					Socket clientSocket = null;
						for (Peer peer : peers) {
							log.info("Sending " + tx + " to " + peer);
							clientSocket = new Socket(peer.getAdr(), peer.getPort());
							ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
							out.writeObject(txJson);
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
			ConsensusMessage cnsMsg;
			String cnsJson;
			Gson gson = new GsonBuilder().create();
			while(!Thread.interrupted()) {
				try {
					cnsMsg = cnsOut.take();
					cnsJson = gson.toJson(cnsMsg);
					Socket clientSocket = null;
					for (Peer peer : peers) {
						log.info("Sending " + cnsMsg + " to " + peer);
						clientSocket = new Socket(peer.getAdr(), peer.getPort());
						ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
						out.writeObject(cnsJson);
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
