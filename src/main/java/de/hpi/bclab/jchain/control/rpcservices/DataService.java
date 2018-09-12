package de.hpi.bclab.jchain.control.rpcservices;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

import com.github.arteam.simplejsonrpc.core.annotation.JsonRpcMethod;
import com.github.arteam.simplejsonrpc.core.annotation.JsonRpcParam;
import com.github.arteam.simplejsonrpc.core.annotation.JsonRpcService;

import de.hpi.bclab.jchain.messaging.Message;
import de.hpi.bclab.jchain.statemachine.State;
import de.hpi.bclab.jchain.statemachine.Transaction;
import de.hpi.bclab.jchain.statemachine.TransactionMessage;
import de.hpi.bclab.jchain.statemachine.datamodel.DataTransaction;
import de.hpi.bclab.jchain.statemachine.datamodel.Database;

public @JsonRpcService class DataService {
	
	private LinkedBlockingQueue<Transaction> txPool;
	private LinkedBlockingQueue<Message> msgOut;
	private State state;
	
	public DataService(LinkedBlockingQueue<Transaction> txPool, LinkedBlockingQueue<Message> msgOut, State state) {
		this.txPool = txPool;
		this.state = state;
	}
	
	public @JsonRpcMethod boolean data_submit(@JsonRpcParam("data") String data) {
		try {
			DataTransaction tx = new DataTransaction(data);
			txPool.put(tx);
			msgOut.put(new TransactionMessage(tx));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public @JsonRpcMethod ArrayList<String> data_list() {
		return ((Database) state).getDataList();
	}

}
