package de.hpi.bclab.jchain;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.sync.ReadWriteSynchronizer;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import de.hpi.bclab.jchain.consensus.ConsensusManager;
import de.hpi.bclab.jchain.consensus.ConsensusMessage;
import de.hpi.bclab.jchain.consensus.nakamoto.NakamotoManager;
import de.hpi.bclab.jchain.control.Cli;
import de.hpi.bclab.jchain.control.CommandManager;
import de.hpi.bclab.jchain.messaging.Message;
import de.hpi.bclab.jchain.messaging.MessagingClient;
import de.hpi.bclab.jchain.messaging.MessagingServer;
import de.hpi.bclab.jchain.peering.Peer;
import de.hpi.bclab.jchain.peering.PeerManager;
import de.hpi.bclab.jchain.peering.multicast.MulticastPeerManager;
import de.hpi.bclab.jchain.peering.unstructured.UnstructuredPeerManager;
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
        config.setSynchronizer(new ReadWriteSynchronizer());
    	
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
    	LinkedBlockingQueue<Transaction> txIn = new LinkedBlockingQueue<Transaction>();
    	LinkedBlockingQueue<ConsensusMessage> cnsIn = new LinkedBlockingQueue<ConsensusMessage>();
    	LinkedBlockingQueue<Message> msgOut = new LinkedBlockingQueue<Message>();
        
    	//THREAD POOL
		ExecutorService executor = Executors.newFixedThreadPool(5);
    	
    	//PEERING
		PeerManager peering;
		switch (config.getString("peermode")) {
		case "multicast":
	    	peering = new MulticastPeerManager(config, peers);
			break;
		case "unstructured":
	    	peering = new UnstructuredPeerManager(config, peers);
	    	break;
		default:
			peering = new UnstructuredPeerManager(config, peers);
			break;
		}
		executor.execute(peering);
        
        //MESSAGING
    	executor.execute(new MessagingServer(txIn, cnsIn, 7499));
    	executor.execute(new MessagingClient(peers, msgOut));

        //CONSENSUS
    	ConsensusManager consensus;
    	switch (config.getString("consensus")) {
		case "nakamoto":
			consensus = new NakamotoManager(config, state, txIn, cnsIn, msgOut);
			break;
		default:
			consensus = new NakamotoManager(config, state, txIn, cnsIn, msgOut);
			break;
		}
        executor.execute(consensus);
        
        //CLI/RPC CONTROL
        executor.execute(new CommandManager(config, state, txIn, msgOut, peers));
        
        executor.shutdown();
        
    }
}
