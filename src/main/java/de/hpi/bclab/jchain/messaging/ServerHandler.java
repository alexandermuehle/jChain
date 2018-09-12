package de.hpi.bclab.jchain.messaging;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import de.hpi.bclab.jchain.consensus.ConsensusMessage;
import de.hpi.bclab.jchain.consensus.nakamoto.BlockMessage;
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
			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			String inString = in.readLine();
			Gson gson = new GsonBuilder().create();
			if(inString.contains("\"id\":\"cns\"")) {
				if(inString.contains("\"cnsAlg\":\"nakamoto\"")) {
					cnsPool.put(gson.fromJson(inString, BlockMessage.class));
				}
			}
			client.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonSyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
