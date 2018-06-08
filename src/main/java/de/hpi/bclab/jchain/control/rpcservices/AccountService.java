package de.hpi.bclab.jchain.control.rpcservices;

import java.util.concurrent.LinkedBlockingQueue;

import com.github.arteam.simplejsonrpc.core.annotation.JsonRpcMethod;
import com.github.arteam.simplejsonrpc.core.annotation.JsonRpcParam;
import com.github.arteam.simplejsonrpc.core.annotation.JsonRpcService;

import de.hpi.bclab.jchain.messages.Command;
import de.hpi.bclab.jchain.messages.TransactionCommand;
import de.hpi.bclab.jchain.statemachine.accountmodel.AccountTransaction;

public @JsonRpcService class AccountService{
	
	LinkedBlockingQueue<Command> cmdPool;
	
	public AccountService(LinkedBlockingQueue<Command> cmdPool) {
		this.cmdPool = cmdPool;
	}
	
	public @JsonRpcMethod boolean send(@JsonRpcParam("to") int receiver, @JsonRpcParam("value") int value) {
		try {
			cmdPool.put(new TransactionCommand(new AccountTransaction(receiver, value)));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return true;
	}
}