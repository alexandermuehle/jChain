package de.hpi.bclab.jchain.cli;

import java.util.concurrent.LinkedBlockingQueue;

import com.github.arteam.simplejsonrpc.core.annotation.JsonRpcMethod;
import com.github.arteam.simplejsonrpc.core.annotation.JsonRpcParam;
import com.github.arteam.simplejsonrpc.core.annotation.JsonRpcService;

import de.hpi.bclab.jchain.message.Command;
import de.hpi.bclab.jchain.message.TransactionCommand;
import de.hpi.bclab.jchain.statemachine.accountmodel.AccountTransaction;

public @JsonRpcService class CommandService{
	
	LinkedBlockingQueue<Command> cmdPool;
	
	public CommandService(LinkedBlockingQueue<Command> cmdPool) {
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