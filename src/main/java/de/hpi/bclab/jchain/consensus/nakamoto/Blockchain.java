package de.hpi.bclab.jchain.consensus.nakamoto;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import de.hpi.bclab.jchain.statemachine.Transaction;
import de.hpi.bclab.jchain.util.HashUtil;

public class Blockchain {
	
	private static final Logger log = Logger.getLogger(Blockchain.class.getName());
	
	private static final int DIFFICULTY_WINDOW = 50;
	private static final int BLOCKTIME = 10000;
	
	private String difficulty;
	private Node<Block> root;
	private Node<Block> head;
	
	/**
	 * The Blockchain holds a tree structure of all received Blocks
	 */
	public Blockchain () {
		difficulty = "00000d8f5cd7a11e914eb19410540d1e59647eb08f391ef615ced7be08971a05";
		root = new Node<Block>();
		root.block = new Block("0", difficulty, 0,new ArrayList<Transaction>());
		root.block.setHash(HashUtil.blockHash("0", root.block.getNonce(), root.block.getTimestamp(), root.block.getTransactions()));
		root.depth = 0;
		head = root;
	}
	
	public String calculateDifficulty () {
		//find time difference in the difficulty window
		Node<Block> runner = head;
		for( int i = 0; i < DIFFICULTY_WINDOW; i++) {
			if (runner.parent == null) break;
			else {
				runner = runner.parent;
			}
		}
		long timeDif = head.block.getTimestamp() - runner.block.getTimestamp();
		//calculate divisor for current difficulty
		double multiDiff = (DIFFICULTY_WINDOW * BLOCKTIME) / (double) timeDif; 
		if(multiDiff > 1) multiDiff = Math.log(multiDiff);
		log.info("Calculated difficulty difference " + multiDiff);
		//new difficulty BigDecimal for accuracy
		BigInteger bigDiff = new BigInteger(difficulty, 16);
		BigDecimal newDiff = new BigDecimal(bigDiff).divide(new BigDecimal(multiDiff), 0, BigDecimal.ROUND_DOWN);
		//get hex string from calculated new difficulty and pad it with 0s in front to have same length
		String strDiff = newDiff.toBigInteger().toString(16);
		while(difficulty.length() > strDiff.length()) {
			strDiff = "0" + strDiff;
		}
		log.info("New difficulty calculated: " + strDiff);
		return strDiff;
	}
	
	
	/**
	 * The Block will be added according to the block.prevHash value
	 * 
	 * @param block The Block to be added to the Blockchain
	 * @return Returns true if the added block becomes the new head other returns false
	 */
	public synchronized boolean addBlock(Block block) {
		Node<Block> node = new Node<Block>();
		node.block = block;
		Node<Block> prev = findHash(root, block.getPrevHash());
		if(prev == null) {
			log.info("Couldn't add block " + block.getHash() + " to the Blockchain");
			log.info("Block.prevHash " + block.getPrevHash());
			return false;
		}
		node.parent = prev;
		prev.children.add(node);
		node.depth = prev.depth + 1;
		//update difficulty if needed
		if (node.depth % DIFFICULTY_WINDOW == 0) {
			this.difficulty = calculateDifficulty();
		}
		//update head if needed
		if (node.depth > head.depth) {
			log.info("New Head for Blockchain: " + node.block.getHash());
			head = node;
		}
		return true;
	}
	
	/**
	 * A recursive search for the given Hash
	 * 
	 * @param last Starting point for the search
	 * @param hash 
	 * @return
	 */
	private Node<Block> findHash(Node<Block> last, String hash){
		if (last.block.getHash().equals(hash)) {
			return last;
		}
		Node<Block> res = null;
		for(Node<Block> child : last.children) {
			res = findHash(child, hash);	
		}
		return res;
	}
	
	public String getDifficulty() {
		return this.difficulty;
	}
	
	public Block getHead() {
		return head.block;
	}
	
	public static class Node<T> {
		private Block block;
		private Node<Block> parent;
		private int depth;
		private ArrayList<Node<Block>> children = new ArrayList<Node<Block>>();
	}

}
