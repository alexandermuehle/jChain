package de.hpi.bclab.jchain;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.apache.commons.configuration2.Configuration;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import de.hpi.bclab.jchain.consensus.ConsensusManager;
import de.hpi.bclab.jchain.control.Cli;
import de.hpi.bclab.jchain.control.CommandManager;
import de.hpi.bclab.jchain.messages.Command;
import de.hpi.bclab.jchain.messages.ConsensusMessage;
import de.hpi.bclab.jchain.net.messaging.MessagingManager;
import de.hpi.bclab.jchain.net.peering.Peer;
import de.hpi.bclab.jchain.net.peering.PeerManager;
import de.hpi.bclab.jchain.statemachine.State;
import de.hpi.bclab.jchain.statemachine.Transaction;
import de.hpi.bclab.jchain.statemachine.accountmodel.AccountState;

/**
 *
 */
public class App 
{
	
	private static final Logger log = Logger.getLogger(App.class.getName());
	
    public static void main( String[] args )
    {
        System.out.println( "Hello jChain!" );
        log.setLevel(Level.DEBUG);
        
        
        //CONFIG
        Configuration config = new Cli(args).parse();
    	
        //STATE
        State state;
        switch (config.getString("statemodel")) {
        	case "accounts":
        		state = new AccountState();
        	default:
        		state = new AccountState();
        }
        		
        //SHARED RESOURCES for PRODUCER / CONSUMER DESIGN PATTERN
        List<Peer> peers = Collections.synchronizedList(new ArrayList<Peer>());
    	LinkedBlockingQueue<Transaction> txPool = new LinkedBlockingQueue<Transaction>();
    	LinkedBlockingQueue<ConsensusMessage> cnsPool = new LinkedBlockingQueue<ConsensusMessage>();
    	LinkedBlockingQueue<Command> cmdPool = new LinkedBlockingQueue<Command>();
        
    	//THREAD POOL
		ExecutorService executor = Executors.newCachedThreadPool();
    	
    	//PEERING
    	executor.execute(new PeerManager(config, peers));
        
        //MESSAGING
    	executor.execute(new MessagingManager(config, peers, txPool, cnsPool, cmdPool));
        
        //CONSENSUS
        executor.execute(new ConsensusManager(config, state, txPool, cnsPool));
        
        //CLI/RPC CONTROL
        executor.execute(new CommandManager(config, state, cmdPool, peers));
        
//        Runtime.getRuntime().addShutdownHook(new Thread() {
//        	public void run() {
//        		log.info("Shutting down...");
//        		executor.shutdownNow();
//        		try {
//					System.out.println(executor.awaitTermination(1, TimeUnit.MINUTES));
//				} catch (InterruptedException e) {
//					log.error("Couldn't wait for termination of all threads");
//				}
//        	}
//        });
        
    }
}
