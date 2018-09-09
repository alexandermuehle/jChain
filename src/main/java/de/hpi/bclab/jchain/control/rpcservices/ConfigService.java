package de.hpi.bclab.jchain.control.rpcservices;

import org.apache.commons.configuration2.Configuration;

import com.github.arteam.simplejsonrpc.core.annotation.JsonRpcMethod;
import com.github.arteam.simplejsonrpc.core.annotation.JsonRpcService;

public @JsonRpcService class ConfigService {
	
	private Configuration config;
	
	public ConfigService(Configuration config) {
		this.config = config;
	}
	
	public @JsonRpcMethod boolean config_mining(boolean mining){
		config.setProperty("mining", mining);
		return true;
	}

}
