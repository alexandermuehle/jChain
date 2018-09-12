package de.hpi.bclab.jchain.control.rpcservices;

import java.util.concurrent.LinkedBlockingQueue;

import com.github.arteam.simplejsonrpc.core.annotation.JsonRpcMethod;
import com.github.arteam.simplejsonrpc.core.annotation.JsonRpcParam;
import com.github.arteam.simplejsonrpc.core.annotation.JsonRpcService;

import de.hpi.bclab.jchain.messaging.Message;
import de.hpi.bclab.jchain.statemachine.State;
import de.hpi.bclab.jchain.statemachine.Transaction;
import de.hpi.bclab.jchain.statemachine.accountmodel.Account;
import de.hpi.bclab.jchain.statemachine.accountmodel.AccountState;
import de.hpi.bclab.jchain.statemachine.accountmodel.AccountTransaction;

public @JsonRpcService class AccountService{
	
	private LinkedBlockingQueue<Transaction> txPool;
	private LinkedBlockingQueue<Message> msgOut;
	private State state;
	
	public AccountService(LinkedBlockingQueue<Transaction> txPool, LinkedBlockingQueue<Message> msgOut, State state) {
		this.txPool = txPool;
		this.state = state;
	}
	
	public @JsonRpcMethod boolean acc_send(@JsonRpcParam("to") int receiver, @JsonRpcParam("value") int value) {
		try {
			txPool.put(new AccountTransaction(receiver, value));
//			msgOut.put(new AccountTransactionMessage());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public @JsonRpcMethod Long acc_balance(@JsonRpcParam("id") int id) {
		AccountState accState = (AccountState) state;
		Account acc = new Account(id);
		return accState.getValue(acc);
	}
}