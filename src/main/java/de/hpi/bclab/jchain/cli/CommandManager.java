package de.hpi.bclab.jchain.cli;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;

import org.apache.commons.configuration2.Configuration;
import org.apache.log4j.Logger;

import com.github.arteam.simplejsonrpc.server.JsonRpcServer;

import de.hpi.bclab.jchain.message.Command;
import de.hpi.bclab.jchain.statemachine.State;

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
		CommandService controlService = new CommandService(cmdPool);
		JsonRpcServer rpcServer = new JsonRpcServer();		
		
		ServerSocket socket = null;
		try {
			socket = new ServerSocket(rpcPort);
		} catch (IOException e1) {
			log.error("Failed to open RPC Server Socket");
			return;
		}
		Socket client;
		
		while(listening) {
			try {
				client = socket.accept();
				BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
				BufferedWriter out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
				String req;
				while ((req = in.readLine()) != null) {
					if (req.isEmpty())break;
				}
				req = in.readLine();
				System.out.println(req);
				String answer = rpcServer.handle(req, controlService);
				System.out.println(answer);
				out.write(answer);
				//in.close();
				//out.close();
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
