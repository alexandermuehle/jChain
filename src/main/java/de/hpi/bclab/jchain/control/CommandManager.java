package de.hpi.bclab.jchain.control;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.commons.configuration2.Configuration;
import org.apache.log4j.Logger;

import com.github.arteam.simplejsonrpc.server.JsonRpcServer;

import de.hpi.bclab.jchain.control.rpcservices.AccountService;
import de.hpi.bclab.jchain.control.rpcservices.NetworkService;
import de.hpi.bclab.jchain.messages.Command;
import de.hpi.bclab.jchain.statemachine.State;

/**
 * The CommandManager is responsible for running the RPC Server and calling the appropriate {@link de.hpi.bclab.jchain.control.rpcservices}
 * Incoming commands are put in the command pool for the messaging service to consume.
 * 
 * @author Alexander Mühle
 *
 */
public class CommandManager implements Runnable{
	
	private static final Logger log = Logger.getLogger(CommandManager.class.getName());
	
	private State state;
	private LinkedBlockingQueue<Command> cmdPool;
	private boolean listening;
	private int rpcPort;

	public CommandManager(Configuration config, State state, LinkedBlockingQueue<Command> cmdPool) {
		this.cmdPool = cmdPool;
		this.listening = config.getBoolean("rpc");
		this.rpcPort = config.getInt("rpcport");
	}

	@Override
	public void run() {
		
		log.info("Starting CommandManager");
		AccountService accountService = new AccountService(cmdPool, state);
		NetworkService  networkService = new NetworkService();
		JsonRpcServer rpcServer = new JsonRpcServer();		
		
		//RPC
		ServerSocket socket = null;
		try {
			socket = new ServerSocket(rpcPort);
		} catch (IOException e1) {
			log.error("Failed to open RPC Server Socket");
			return;
		}
		Socket client;
		int contentLength = 0;
		
		while(listening) {
			try {
				client = socket.accept();
				BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
				BufferedWriter out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
				//HEADER
				String req = in.readLine();
				if (!req.startsWith("POST")) throw new IOException();
				while (!req.isEmpty()) {
					req = in.readLine();
			        if (req.startsWith("Content-Length: ")) {
			        	contentLength = Integer.parseInt(req.substring("Content-Length: ".length()));
			        }
				}
				
				//BODY
				StringBuilder payload = new StringBuilder();
		        while(payload.length() < contentLength && in.ready()){
		            payload.append((char) in.read());
		        }
		        //TODO: switch this properly 
		        String answer = null; 
		        if (payload.toString().contains("acc_")) answer = rpcServer.handle(payload.toString(), accountService);
		        if (payload.toString().contains("net_")) answer = rpcServer.handle(payload.toString(), networkService);

				//RESPONSE
		        out.write("HTTP/1.0 200 OK\r\n");
		        out.write(new Date().toString() + "\r\n");
		        out.write("Server: jChain/0.0.1\r\n");
		        out.write("Content-Type: application/json\r\n");
		        out.write("Content-Length: "+ answer.length() + "\r\n");
		        out.write("\r\n");
				out.write(answer);
				out.flush();
				in.close();
				out.close();
				client.close();
			} catch (IOException e) {
				log.error("Failed to accept RPC request");
				e.printStackTrace();
			}
		}		
		try {
			socket.close();
		} catch (IOException e) {
			log.error("Failed to close RPC Server Socket");
		}		
	}

}
