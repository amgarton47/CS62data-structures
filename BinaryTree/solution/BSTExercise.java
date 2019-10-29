import java.util.Random;

/**
 * A Binary Search Tree implementation based on the implementation presented in
 * Sedgewick and Wayne's Algorithms textbook. Can have methods added if
 * necessary, as this implementation is meant as a teaching exercise and is
 * missing some potentially useful methods.
 * 
 * @author Aden Siebel
 * @author Alexandra Papoutsaki
 *
 * @param <Item>
 *            The type of item of the node. Must be Comparable
 */
public class BSTExercise<Item extends Comparable<Item>> {

	public static final int RAND_INT_BOUND = 300;
	public static Random rand = new Random();

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
		if (x == null)
			return 0;
		else
			return x.size;
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
		int cmp = item.compareTo(x.item);

		if (cmp < 0 && x.left != null)
			return locate(x.left, item);
		else if (cmp > 0 && x.right != null)
			return locate(x.right, item);
		else {
			return x;
		}
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
		// todo: fix me
		// return null;
		if (x == null)
			return new Node(item, 1);

		int cmp = item.compareTo(x.item);
		if (cmp < 0)
			x.left = insert(x.left, item);
		else if (cmp > 0)
			x.right = insert(x.right, item);
		else
			x.item = item;
		x.size = 1 + size(x.left) + size(x.right);
		return x;
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
		if (x == null)
			return -1;
		return 1 + Math.max(height(x.left), height(x.right));
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
			return toString(root.left) + root.item + toString(root.right);
	}

	public static BSTExercise<Integer> construct128intTree() {
		// Construct tree with 128 random ints
		BSTExercise<Integer> biggerTree = new BSTExercise<Integer>();
		for (int i = 0; i < 127; i++)
			biggerTree.insert(rand.nextInt(RAND_INT_BOUND));
		System.out.println(biggerTree);
		return biggerTree;
	}


	public static void randomTreeHeights() {
		ArrayList<BSTExercise<Integer>> randTrees = new ArrayList<BSTExercise<Integer>>();
		ArrayList<Integer> randTreesHeight = new ArrayList<Integer>();
		int sum = 0;
		for (int i = 0; i < 100; i++) {
			randTrees.add(construct128intTree());
			randTreesHeight.add(randTrees.get(i).height());
			sum += randTrees.get(i).height();
			System.out.print(randTrees.get(i).height() + " ");
		}

		// Theoretical min height = log(num nodes) (in our case = 7)
		// Theoretical max height = num nodes - 1 (in our case = 128)

		System.out.println("\navg height of 100: " + (sum / 100.0)); // avg = 13.09
		// Yes, the data supports this, the average hovers around 13, and is much, much
		// closer
		// to the min height than the max height
	}

	public static void main(String args[]) {
		BSTExercise<Integer> bt = new BSTExercise<Integer>();
		bt.insert(4);
		bt.insert(1);
		bt.insert(3);
		bt.insert(5);
		bt.insert(2);
		bt.insert(5);
		System.out.println(bt);
		System.out.println(bt.height());
		System.out.println(bt.locate(1));
		System.out.println(bt.locate(6));
		System.out.println(construct128intTree());
		randomTreeHeights();
	}

}