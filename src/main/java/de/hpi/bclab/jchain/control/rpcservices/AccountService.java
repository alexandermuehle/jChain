package de.hpi.bclab.jchain.control.rpcservices;

import java.util.concurrent.LinkedBlockingQueue;

import com.github.arteam.simplejsonrpc.core.annotation.JsonRpcMethod;
import com.github.arteam.simplejsonrpc.core.annotation.JsonRpcParam;
import com.github.arteam.simplejsonrpc.core.annotation.JsonRpcService;

import de.hpi.bclab.jchain.messages.Command;
import de.hpi.bclab.jchain.messages.TransactionCommand;
import de.hpi.bclab.jchain.statemachine.State;
import de.hpi.bclab.jchain.statemachine.accountmodel.Account;
import de.hpi.bclab.jchain.statemachine.accountmodel.AccountState;
import de.hpi.bclab.jchain.statemachine.accountmodel.AccountTransaction;

public @JsonRpcService class AccountService{
	
	private LinkedBlockingQueue<Command> cmdPool;
	private State state;
	
	public AccountService(LinkedBlockingQueue<Command> cmdPool, State state) {
		this.cmdPool = cmdPool;
	}
	
	public @JsonRpcMethod boolean acc_send(@JsonRpcParam("to") int receiver, @JsonRpcParam("value") int value) {
		try {
			cmdPool.put(new TransactionCommand(new AccountTransaction(receiver, value)));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public @JsonRpcMethod int acc_balance(@JsonRpcParam("id") int id) {
		AccountState accState = (AccountState) state;
		return accState.getValue(new Account(id)).getValue();
	}
}