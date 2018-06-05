package de.hpi.bclab.jchain;


import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.commons.configuration2.Configuration;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import de.hpi.bclab.jchain.cli.Cli;
import de.hpi.bclab.jchain.core.Block;
import de.hpi.bclab.jchain.core.Transaction;
import de.hpi.bclab.jchain.managers.PeerManager;
import de.hpi.bclab.jchain.managers.StateManager;
import de.hpi.bclab.jchain.net.peering.Peer;
import de.hpi.bclab.jchain.net.peering.PeerDiscovery;

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
    	
        //SHARED RESOURCES
        List<Peer> peers = Collections.synchronizedList(new ArrayList<Peer>());
    	LinkedBlockingQueue<Block> blockPool = new LinkedBlockingQueue<Block>();
    	LinkedBlockingQueue<Transaction> txPool = new LinkedBlockingQueue<Transaction>();
        
    	//THREADING
		ExecutorService executor = Executors.newCachedThreadPool();
    	
    	//PEERING
        PeerManager net;
        try {
    		executor.execute(new PeerManager(config.getInt("port"), config.getString("group"), peers));
		} catch (UnknownHostException e) {
			log.error("Failed to start NetManager");
			log.debug(e);
		}
        
        //STATE
        StateManager state;
        try {
        	executor.execute(new StateManager(config.getString("db"), peers));
        } catch (Exception e) {
        	log.error("Failed to start StateManager");
        	log.debug(e);
        }
        
        //RPC CONTROL
        
    }
}
