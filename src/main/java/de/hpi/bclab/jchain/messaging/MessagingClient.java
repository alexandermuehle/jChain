package de.hpi.bclab.jchain.messaging;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import de.hpi.bclab.jchain.peering.Peer;

public class MessagingClient implements Runnable {
	
	private static final Logger log = Logger.getLogger(MessagingClient.class.getName());
	
	private List<Peer> peers;
	private LinkedBlockingQueue<Message> msgOut;
	
	public MessagingClient(List<Peer> peers, LinkedBlockingQueue<Message> msgOut) {
		this.peers = peers;
		this.msgOut = msgOut;
	}

	@Override
	public void run() {
		
		log.info("Starting Messaging Client");
		
		//SEND MSG
		Message msg;
		String msgJson;
		Gson gson = new GsonBuilder().create();
		while(!Thread.interrupted()) {
			try {
				msg = msgOut.take();
				msgJson = gson.toJson(msg);
				Socket clientSocket = null;
					for (Peer peer : peers) {
						log.info("Sending " + msg + " to " + peer);
						clientSocket = new Socket(peer.getAdr(), peer.getPort());
						Writer out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream(), "UTF8"));
					    out.append(msgJson).append("\n");
					    out.flush(); 
						clientSocket.close();
						out.close();
					}
			} catch (InterruptedException e) {
				log.info("Failed to process msgOut");
				log.debug(e);
			} catch (IOException e) {
				log.error("Failed to open Messaging Client Socket");
				e.printStackTrace();
			}
		}

	}

}
