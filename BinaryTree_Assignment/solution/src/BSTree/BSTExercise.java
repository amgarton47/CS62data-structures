package BSTree;

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

	public static final int RAND_INT_BOUND = 300;	// bound for random items generated
	public static Random rand = new Random();		// random number generator

	private Node root;		// root of BST ... null until first insert

	/**
	 * Nested subclass defining the nodes in the tree, with an item, left and right
	 * subtrees, and number of nodes in subtree
	 *
	 * Notes:
	 *	1. if the Item in a node has value x
	 *	   All items in the left sub-tree have values < x
	 *     All items in the right sub-tree have values > x
	 *  2. If there are no items less than (or greater than) x,
	 *     the x-node will have a left (or right) field of null
	 */
	private class Node {
		private Item item;			// sorted by item
		private Node left;			// left sub-tree (or null)
		private Node right;			// right sub-tree (or null)

		public Node(Item item) {
			this.item = item;
			this.left = null;
			this.right = null;
		}

		public String toString() {
			return this.item.toString();
		}
	}	// end of class Node

	/**
	 * Returns the size of the tree
	 * 
	 * @return the number of nodes in the entire tree
	 */
	public int size() {
		return size(root);
	}

	/**
	 * Size of the sub-tree rooted at x
	 * 
	 * @param x  root of the tree to be sized
	 * @return number of nodes in the sub-tree
	 */
	private int size(Node x) {
		if (x == null)
			return 0;
		else
			return 1 + size(x.left)+ size(x.right); 
	}

	/**
	 * search the tree for a desired node
	 *
	 * @param Node  node to compare its item with given item
	 * @param item  item to search for
	 * @return  existing tree node with desired item (or null)
	 */
	public Node locate(Item item) {
		return locate(root, item);
	}

	/**
	 * search a sub-tree tree for a desired node
	 *
	 * @param Node  node to compare its item with given item
	 * @param item  item to search for
	 * @return  existing tree node with desired item (or null)
	 */
	private Node locate(Node x, Item item) {
		int result = item.compareTo(x.item);
		if (result == 0)	// match
			return x;
		if (result < 0)		// recurse left
			return (x.left == null) ? null : locate(x.left, item);
		else				// recurse right
			return (x.right == null) ? null : locate(x.right, item);
	}

	/**
	 * Insert an item into the tree
	 * 
	 * @param item  item to add
	 */
	public void insert(Item item) {
		Node n = insert(root, item);
		if (root == null)
			root = n;
	}

	/**
	 * insert an item into its proper place in a sub-tree
	 * 
	 * @param x  node below which item should be inserted
	 * 			 (for first insert (x==null) return new node
	 * 			 which will become the root of the tree)
	 * @param item to be inserted insert
	 * @return node for the new (or preexisting) item
	 *
	 * NOTE: attempting to insert an item already in the tree
	 *       will simply return the already-existing node
	 */
	private Node insert(Node x, Item item) {
		// insert into an empty tree
        if (x == null)		// insert into an empty tree
            return new Node(item);

        int comparison = item.compareTo(x.item);
        if (comparison == 0)    // already in tree
            return x;
        if (comparison < 0)
            if (x.left != null)
                return insert(x.left, item);
            else {
                x.left = new Node(item);
                return x.left;
            }
        else
            if (x.right != null)
                return insert(x.right, item);
            else {
                x.right = new Node(item);
                return x.right;
            }
	}

	/**
	 * Returns the height of the BT.
	 *
	 * @return the height of the BT (a 1-node tree has height 0)
	 */
	public int height() {
		return height(root);
	}

	/**
	 * return the height of a specified sub-tree
	 * 
	 * @param x root of sub-tree to be measured
	 * @return height (a 1-node tree has a height of 0)
	 */
	private int height(Node x) {
		if (x == null)
			return 0;	// called on empty sub-tree
		if (x.left == null && x.right == null)
			return 0;	// height of a single node is defined to be zero
		
		// figure out my tallest sub-tree
		int l_height = height(x.left);
		int r_height = height(x.right);
		return 1 + ((l_height >= r_height) ? l_height : r_height);
	}

	/**
	 * @return string representation of the BST
	 */
	public String toString() {
		return "<" + toString(root) + ">";
	}

	/**
	 * @param root of the sub-tree to be represented
	 * @return string representation of sub-tree
	 * 
	 * @return
	 */
	public String toString(Node root) {
		if (root == null)
			return "";
		else
			return toString(root.left) + " " + root.item + " " + toString(root.right);
	}

	/**
	 * test method ... construct a tree with specified number of nodes
	 *
	 * @param nodes desired number of (random value) nodes
	 * @return root node of the constructed tree
	 */
	public static BSTExercise<Integer> constructIntTree(int nodes) {
		// Construct tree with #nodes random ints
		BSTExercise<Integer> bt = new BSTExercise<Integer>();
		for(int i = 0; i < nodes; i++)
			bt.insert(rand.nextInt(RAND_INT_BOUND));
		return bt;
	}

	/**
	 * measurement method ... height of random trees
	 *
	 * @param number of trees to construct
	 * @param number of nodes in each tree
	 */
	public static void randomTreeHeights(int nodes, int trees) {
		int min_height = RAND_INT_BOUND + 1;
		int max_height = 0;
		int sum_of_heights = 0;
		
		// create & measure heights of specified #trees w/#nodes each
		for(int i = 0; i < trees; i++) {
			int height = constructIntTree(nodes).height();
			if (height < min_height)
				min_height = height;
			if (height > max_height)
				max_height = height;
			sum_of_heights += height;
		}
		
		// report on max, min and mean heights
		int mean_height = sum_of_heights/trees;
		System.out.println(trees + " " + nodes + "-node trees: " +
						   "min_height=" + min_height +
						   ", max_height=" + max_height +
						   ", avg_height=" + mean_height);
		
		// report the theoretical best- and worst-case heights
		int worst_case = nodes - 1;		// if they are inserted in order
		int best_case = -1;				// Log2(1) == 0
		for(int n = nodes; n > 1; n /= 2)
			best_case++;
		System.out.println("Theoretical: best_case=" + best_case +
						   ", worst_case=" + worst_case);
		
		// construct and measure a worst case tree
		BSTExercise<Integer> bt = new BSTExercise<Integer>();
		for(int i = 0; i < nodes; i++)
			bt.insert(i);
		System.out.println("measured height of worst-case " + nodes + "-node tree: " + bt.height());

		// construct and measure a best-case tree
		bt = new BSTExercise<Integer>();
		for(int divisor = nodes/2; divisor >= 1; divisor /= 2) {
			// add numbers in declining powers of 2
			for(int value = divisor; value < nodes; value += divisor)
				bt.insert(value);
		}
		int best = bt.height();
		System.out.println("measured height of best-case " + nodes + "-node tree: " + best);
		
		System.out.println("CONCLUSION:");
		if (best == best_case)
			System.out.println("    Measured best-case == theoretical best-case: height=Log2(N) - 1");
		else
			System.out.println("    Measured best-case != theoretical best-case");
	}

	/**
	 * test suite orchestration
	 */
	public static void main(String args[]) {
		// create a tree, inserting nodes out of order
		BSTExercise<Integer> bt = new BSTExercise<Integer>();
		bt.insert(4);
		bt.insert(1);
		bt.insert(3);
		bt.insert(5);
		bt.insert(2);
		bt.insert(2);

		// extract and check information from the nodes
		String contents = bt.toString();
		if (contents.equals("< 1  2  3  4  5 >"))
			System.out.println(contents + " ... CORRECT");
		else
			System.out.println(contents + " ... ERROR, expected < 1  2  3  4  5 >");

		int size = bt.size();
		if (size == 5)
			System.out.println("size: " + size + " ... CORRECT");
		else
			System.out.println("size: " + size + " ... ERROR, expected 5");

		int height = bt.height();
		if (height == 3)
			System.out.println("height: " + height + " ... CORRECT");
		else
			System.out.println("height: " + height + " ... ERROR, expected 3");

		BSTExercise<Integer>.Node one = bt.locate(1);
		if (one.item == 1) 
			System.out.println("locate(1) finds node " + one.item + " ... CORRECT");
		else
			System.out.println("locate(1) returns " + one + " ... ERROR, expected 1");

		BSTExercise<Integer>.Node six = bt.locate(6);
		if (six == null) 
			System.out.println("locate(6) returns " + "null" + " ... CORRECT");
		else
			System.out.println("locate(6) returns " + six + " ... ERROR, expected null");


		// construct a 128 node tree
		BSTExercise<Integer> r = constructIntTree(128);
		System.out.println(r);

		// construct, measure and report on 100 128-node trees
		randomTreeHeights(128, 100);
	}
}
