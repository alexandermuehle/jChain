package de.hpi.bclab.jchain.consensus.nakamoto;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import de.hpi.bclab.jchain.statemachine.Transaction;

public class Blockchain {
	
	private static final Logger log = Logger.getLogger(Blockchain.class.getName());
	
	private static final int DIFFICULTY_WINDOW = 5;
	private static final int BLOCKTIME = 15000;
	private int difficulty;
	private Node<Block> root;
	private Node<Block> head;
	
	/**
	 * The Blockchain holds a tree structure of all received Blocks
	 */
	public Blockchain () {
		difficulty = 2;
		root = new Node<Block>();
		root.block = new Block("0", difficulty, new ArrayList<Transaction>());
		root.cumDepth = 0;
		root.depth = 0;
		root.children = new ArrayList<Node<Block>>();
		head = root;
	}
	
	public int calculateDifficulty () {
		Node<Block> runner = head;
		for( int i = 0; i < DIFFICULTY_WINDOW; i++) {
			if (runner.parent == null) break;
			else {
				runner = runner.parent;
			}
		}
		long timeDif = head.block.getTimestamp() - runner.block.getTimestamp();
		double dif = (DIFFICULTY_WINDOW * BLOCKTIME) / timeDif; 
		log.info("Calculated difficulty difference " + dif);
		return (int) (difficulty * dif);
	}
	
	
	/**
	 * The Block will be added according to the block.prevHash value
	 * 
	 * @param block The Block to be added to the Blockchain
	 * @return Returns true if the added block becomes the new head other returns false
	 */
	public boolean addBlock(Block block) {
		Node<Block> node = new Node<Block>();
		node.block = block;
		Node<Block> prev = findHash(root, block.getPrevHash());
		node.parent = prev;
		prev.children.add(node);
		node.depth = prev.depth + 1;
		//update difficulty if needed
		if (node.depth % DIFFICULTY_WINDOW == 0) {
			this.difficulty = calculateDifficulty();
		}
		node.cumDepth = prev.block.getDifficulty() + block.getDifficulty();
		//update head if needed
		if (node.cumDepth > head.cumDepth) {
			head = node;
			return true;
		}
		return false;
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
	
	public int getDifficulty() {
		return this.difficulty;
	}
	
	public Block getHead() {
		return head.block;
	}
	
	public static class Node<T> {
		private Block block;
		private Node<Block> parent;
		private int cumDepth;
		private int depth;
		private List<Node<Block>> children = new ArrayList<Node<Block>>();
	}

}
