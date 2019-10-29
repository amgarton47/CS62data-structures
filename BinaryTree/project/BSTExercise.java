import java.util.Random;

/**
 * A basic Binary Search Tree class.
 * 
 * @author 
 *
 * @param <Item>
 *            The type of item of the node. Must be Comparable
 */
public class BSTExercise<Item extends Comparable<Item>> {

	public static final int RAND_INT_BOUND = 300;// bound for random items generated
	public static Random rand = new Random(); // random number generator

	private Node root; // root of BST

	/**
	 * Nested subclass defining the nodes in the tree, with an item, left and right
	 * subtrees, and number of nodes in subtree
	 */
	private class Node {
		private Item item; // sorted by item
		private Node left, right; // left and right subtrees
		private int size; // number of nodes in BST

		public Node(Item item, int size) {
			this.item = item;
			this.size = size;
		}

		public String toString() {
			return this.item.toString();
		}
	}// end of class Node

	/**
	 * Returns the size of the tree
	 * 
	 * @return the size of the tree
	 */
	public int size() {
		return size(root);
	}

	/**
	 * Size of the tree rooted at x
	 * 
	 * @param x
	 *            root of the tree
	 * @return size of the tree
	 */
	private int size(Node x) {
		// todo: fix me (what are the two cases)?
		return 0;
	}

	/**
	 * @param item
	 *            item to search for
	 * @return 1 - existing tree node with the desired item, or 2 - the node to
	 *         which item should be added
	 */
	public Node locate(Item item) {
		return locate(root, item);
	}

	/**
	 * @param Node
	 *            node to compare its item with given item
	 * @param item
	 *            item to search for
	 * @return 1 - existing tree node with the desired item, or 2 - the node to
	 *         which item should be added
	 */
	private Node locate(Node x, Item item) {
		// todo: recursively call locate on left or right child
		// based on the comparison between x.item and item
		return null;
	}

	/**
	 * Item to be inserted.
	 * 
	 * @param item
	 *            item to add
	 */
	public void insert(Item item) {
		root = insert(root, item);
	}

	/**
	 * Helper function to insert item in tree
	 * 
	 * @param x
	 *            root of the subtree to insert item
	 * @param item
	 *            item to insert
	 * @return the node where the item was inserted
	 */
	private Node insert(Node x, Item item) {
		// todo: recursively call insert on left or right child
	    //we will ignore duplicates
		return null;
	}

	/**
	 * Returns the height of the BT.
	 *
	 * @return the height of the BT (a 1-node tree has height 0)
	 */
	public int height() {
		return height(root);
	}

	// helper function that calculates the height of a specific node
	private int height(Node x) {
		//todo: What are the two cases? Use recursion
			return -1;
	}

	public String toString() {
		return "<" + toString(root) + ">";
	}

	/**
	 * Recursively prints the subtree starting at root based on an in-order
	 * traversal
	 * 
	 * @param root
	 * @return
	 */
	public String toString(Node root) {
		if (root == null)
			return "";
		else
			return toString(root.left) + " " + root.item + " " + toString(root.right);
	}

	public static BSTExercise<Integer> construct128intTree() {
		// todo: Construct tree with 128 random ints and return it.
		return null;
	}


	public static void randomTreeHeights() {
		//todo: create 100 BST trees with 128 nodes each and calculate their average height. 
		
		
		
		//todo: what are the theoretical min and max heights?
		
		
		//todo: do your findings support that the average height is O(logn)?	
	}

	public static void main(String args[]) {
		BSTExercise<Integer> bt = new BSTExercise<Integer>();
		bt.insert(4);
		bt.insert(1);
		bt.insert(3);
		bt.insert(5);
		bt.insert(2);
		bt.insert(2);
		System.out.println(bt);
		System.out.println(bt.height());
		System.out.println(bt.locate(1));
		System.out.println(bt.locate(6));
		System.out.println(construct128intTree());
		randomTreeHeights();
	}

}