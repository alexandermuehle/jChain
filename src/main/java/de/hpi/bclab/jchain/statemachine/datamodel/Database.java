package de.hpi.bclab.jchain.statemachine.datamodel;

import java.util.ArrayList;

import de.hpi.bclab.jchain.statemachine.State;
import de.hpi.bclab.jchain.statemachine.Transaction;

public class Database implements State{

	ArrayList<String> dataList = new ArrayList<String>();
	
	@Override
	public void applyTransaction(Transaction tx) {
		DataTransaction dtx = (DataTransaction) tx;
		dataList.add(dtx.getData());
	}

	@Override
	public boolean verifyTransaction(Transaction tx) {
		return true;
	}
	
	public ArrayList<String> getDataList(){
		return dataList;
	}

}
