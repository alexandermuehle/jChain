package de.hpi.bclab.jchain.consensus.nakamoto;

import java.util.ArrayList;
import java.util.List;

import de.hpi.bclab.jchain.statemachine.Transaction;

public class Blockchain {
	
	private Node<Block> root;
	private Node<Block> head;
	
	/**
	 * The Blockchain holds a tree structure of all received Blocks
	 */
	public Blockchain () {
		root = new Node<Block>();
		root.block = new Block("0", new ArrayList<Transaction>());
		root.depth = 0;
		root.children = new ArrayList<Node<Block>>();
		head = root;
	}
	
	/**
	 * The Block will be added according to the block.prevHash value
	 * 
	 * @param block The Block to be added to the Blockchain
	 */
	public void addBlock(Block block) {
		Node<Block> newNode = new Node<Block>();
		newNode.block = block;
		Node<Block> prev = findHash(root, block.getPrevHash());
		newNode.parent = prev;
		newNode.depth = prev.depth + 1;
		prev.children.add(newNode);
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
	
	public Block getHead() {
		return head.block;
	}
	
	public static class Node<T> {
		private Block block;
		private Node<Block> parent;
		private int depth;
		private List<Node<Block>> children;
	}

}
