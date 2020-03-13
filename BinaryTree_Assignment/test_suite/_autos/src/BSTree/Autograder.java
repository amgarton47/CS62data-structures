package BSTree;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * <p>
 * Auto-grader unit tests for <strong>Binary Search Tree</strong>.
 * </p>
 *
 *
 * @author Mark Kampe
 */
public class Autograder {

	private static final int TIMEOUT_SECONDS = 10;
	private static final int DEFAULT_SLEEP = 500;

	private static int[] small_tree = {6, 5, 7, 4, 2, 2};
	private static String small_string = "< 2  4  5  6  7 >";
	private static int small_size = 5;			// 3 4 5 6 7
	private static int small_height = 3;		// 6.5.4.2
	private static int small_first_left = 5;	// 6.5
	private static int small_first_right = 7;	// 6.7
	private static int small_mid = 4;			// 6.5.4.2
	private static int small_leaf = 2;			// 6.5.4.2
	private static int small_add_left = 1;		// 6.5.4.2.1
	private static int small_add_right = 3;		// 6.5.4.2.3
	private static int small_not_there = 8;

	private static int[] large_tree = {5, 1, 2, 3, 4, 6, 7, 8, 9, 10};
	private static int large_size = 10;
	private static int large_height = 5;		// 5.6.7.8.9.10

	// Set global time out
	//@Rule
	//public Timeout globalTimeout = Timeout.seconds(TIMEOUT_SECONDS);


	/**
	 * create the small tree used in the distributed main tests
	 */
	private BSTExercise<Integer> makeSmallTree() {
		BSTExercise<Integer> bt = new BSTExercise<Integer>();
		for (int i = 0; i < small_tree.length; i++)
			bt.insert(small_tree[i]);

		return bt;
	}

	/**
	 * trivial test to see if we got this far
	 */
	@Test
	public void buildsAndRuns() {

		BSTExercise<Integer> bt = makeSmallTree();
		assertTrue(small_string.equals(bt.toString()), small_string + ".toString");
	}

	/**
	 * check correct return for the distributed size test
	 */
	@Test
	public void size_small() {
		BSTExercise<Integer> bt = makeSmallTree();

		assertEquals( small_size, bt.size(), small_string + ".size()");
	}

	/**
	 * check correct return for size in a larger tree
	 */
	@Test
	public void size_large() {
		BSTExercise<Integer> bt = new BSTExercise<Integer>();
		for (int i = 0; i < large_tree.length; i++)
			bt.insert(large_tree[i]);

		assertEquals( large_size, bt.size(), bt.toString() + ".size()");
	}

	/**
	 * check correct return for the distributed height test
	 */
	@Test
	public void height_small() {
		BSTExercise<Integer> bt = makeSmallTree();

		assertEquals( small_height, bt.height(), small_string + ".height()");
	}

	/**
	 * check correct return for height in a larger tree
	 */
	@Test
	public void height_large() {
		BSTExercise<Integer> bt = new BSTExercise<Integer>();
		for (int i = 0; i < large_tree.length; i++)
			bt.insert(large_tree[i]);

		assertEquals(large_height, bt.height(), bt.toString() + ".height()");
	}

	/**
	 * can locate find the value at top of tree
	 */
	@Test
	public void locate_top() {
		BSTExercise<Integer> bt = makeSmallTree();

		assertEquals(bt.root, bt.locate(small_tree[0]), small_string + ".locate(first_node)");
	}

	/**
	 * can locate find the value in mid-tree
	 */
	@Test
	public void locate_shallow() {
		BSTExercise<Integer> bt = makeSmallTree();

		assertEquals(small_first_left, (int) bt.locate(small_first_left).item,
				small_string + ".locate(root.left_child)");
		assertEquals(small_first_right, (int) bt.locate(small_first_right).item,
				small_string + ".locate(root.right_child)");
	}

	/**
	 * can locate find the value at a remote leaf
	 */
	@Test
	public void locate_leaf() {
		BSTExercise<Integer> bt = makeSmallTree();

		assertEquals(small_leaf, (int) bt.locate(small_leaf).item,
				small_string + ".locate(leaf)");
	}

	/**
	 * does locate properly report a value not in the tree
	 */
	@Test
	public void locate_none() {
		BSTExercise<Integer> bt = makeSmallTree();

		assertNull(bt.locate(small_not_there), small_string + ".locate(not-there)");
	}

	/**
	 * does the first insertion work correctly
	 */
	@Test
	public void insert_root() {
		BSTExercise<Integer> bt = new BSTExercise<Integer>();

		assertNull(bt.root, "newly created B-Tree has no root");
		assertEquals(0, bt.size(), "newly created B-Tree has size 0");

		bt.insert(666);
		assertNotNull(bt.root, "first insert creates root node");
		assertEquals(666, (int) bt.root.item, "first inserted item is root node");
		assertEquals(1, bt.size(), "after root insertion, size=1");
		assertNull(bt.root.left, "newly added root has no left child");
		assertNull(bt.root.right, "newly added root has no right child");
	}

	/**
	 * does insertion immediately under the root work
	 */
	@Test
	public void insert_root_child() {
		BSTExercise<Integer> bt = new BSTExercise<Integer>();

		bt.insert(666);

		bt.insert(333);
		assertNotNull(bt.root.left, "insert to left of root creates node");
		assertEquals(333, (int) bt.root.left.item, "insert to left of root adds to tree");
		assertEquals(2, bt.size(), "after first child insertion, size=2");
		assertNull(bt.root.left.left, "newly inserted node has no left child");
		assertNull(bt.root.left.right, "newly inserted node has no right child");

		bt.insert(999);
		assertNotNull(bt.root.right, "insert to right of root creates node");
		assertEquals(999, (int) bt.root.right.item, "insert to right of root adds to tree");
		assertEquals(3, bt.size(), "after second child insertion, size=3");
		assertNull(bt.root.right.left, "newly inserted node has no left child");
		assertNull(bt.root.right.right, "newly inserted node has no right child");
	}

	/**
	 * does insertion of a deep left child work
	 */
	@Test
	public void insert_deep_left() {
		BSTExercise<Integer> bt = makeSmallTree();

		assertEquals(small_size, bt.size(), small_string + ".size()");

		bt.insert(small_add_left);
		assertEquals(small_size+1, bt.size(), "add left leaf increases size by one");
		assertEquals(small_add_left, (int) bt.locate(small_add_left).item,
				"left leaf node is added to tree");
	}

	/**
	 * does insertion of a deep right child work
	 */
	@Test
	public void insert_deep_right() {
		BSTExercise<Integer> bt = makeSmallTree();

		assertEquals(small_size, bt.size(), small_string + ".size()");

		bt.insert(small_add_right);
		assertEquals(small_size+1, bt.size(), "add lright leaf increases size by one");
		assertEquals(small_add_right, (int) bt.locate(small_add_right).item,
			"right leaf node is added to tree");
	}

	/**
	 * does insertion recognize a reinsertion of the root
	 */
	@Test
	public void insert_same_root() {
		BSTExercise<Integer> bt = makeSmallTree();

		assertEquals(small_size, bt.size(), small_string + ".size()");
		bt.insert(small_tree[0]);
		assertEquals(small_size, bt.size(), "reinsertion of root does not change size");
	}

	/**
	 * does insertion recognize a reinsertion mid-tree
	 */
	@Test
	public void insert_same_deep() {
		BSTExercise<Integer> bt = makeSmallTree();

		assertEquals(small_size, bt.size(), small_string + ".size()");
		bt.insert(small_tree[small_mid]);
		assertEquals(small_size, bt.size(), "reinsertion of mid-tree node does not change size");
	}

	/**
	 * does insertion recognize an insertion of a deep leaf
	 */
	@Test
	public void insert_same_leaf() {
		BSTExercise<Integer> bt = makeSmallTree();

		assertEquals(small_size, bt.size(), small_string + ".size()");
		bt.insert(small_tree[small_leaf]);
		assertEquals(small_size, bt.size(), "reinsertion of leaf does not change size");
	}

	/**
	 * does constructIntTree produce a tree with a plausible height
	 */
	@Test
	public void construct_height() {
		int target = 128;
		int min_height = 6;		// log2(128) - 1
		int max_height = min_height * 3;

		BSTExercise<Integer> bt = BSTExercise.constructIntTree(128);

		assertTrue(bt.height() > min_height, "constructIntTree -> height > " + min_height);
		assertTrue(bt.height() < max_height, "constructIntTree -> height < " + max_height);
	}

	/**
	 * does constructIntTree produce a tree with a plausible size
	 */
	@Test
	public void construct_size() {
		int target = 128;
		BSTExercise<Integer> bt = BSTExercise.constructIntTree(target);

		int min_size = 2 * target / 3;
		assertTrue(bt.size() > min_size, "constructIntTree -> size > " + min_size);
		assertTrue(bt.height() <= target, "constructIntTree -> size <= " + target);
	}
}
