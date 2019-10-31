package BSTree;

import static org.junit.Assert.*;
import org.junit.Test;

//import org.junit.Rule;
//import org.junit.Before;
//import org.junit.rules.Timeout;

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
		assertTrue(small_string + ".toString", small_string.equals(bt.toString()));
	}

	/**
	 * check correct return for the distributed size test
	 */
	@Test
	public void size_small() {
		BSTExercise<Integer> bt = makeSmallTree();

		assertEquals(small_string + ".size()", small_size, bt.size());
	}

	/**
	 * check correct return for size in a larger tree
	 */
	@Test
	public void size_large() {
		BSTExercise<Integer> bt = new BSTExercise<Integer>();
		for (int i = 0; i < large_tree.length; i++)
			bt.insert(large_tree[i]);

		assertEquals(bt.toString() + ".size()", large_size, bt.size());
	}

	/**
	 * check correct return for the distributed height test
	 */
	@Test
	public void height_small() {
		BSTExercise<Integer> bt = makeSmallTree();

		assertEquals(small_string + ".height()", small_height, bt.height());
	}

	/**
	 * check correct return for height in a larger tree
	 */
	@Test
	public void height_large() {
		BSTExercise<Integer> bt = new BSTExercise<Integer>();
		for (int i = 0; i < large_tree.length; i++)
			bt.insert(large_tree[i]);

		assertEquals(bt.toString() + ".height()", large_height, bt.height());
	}

	/**
	 * can locate find the value at top of tree
	 */
	@Test
	public void locate_top() {
		BSTExercise<Integer> bt = makeSmallTree();

		assertEquals(small_string + ".locate(first_node)", bt.root , bt.locate(small_tree[0]));
	}

	/**
	 * can locate find the value in mid-tree
	 */
	@Test
	public void locate_shallow() {
		BSTExercise<Integer> bt = makeSmallTree();

		assertEquals(small_string + ".locate(root.left_child)", 
					small_first_left, (int) bt.locate(small_first_left).item);
		assertEquals(small_string + ".locate(root.right_child)", 
					small_first_right, (int) bt.locate(small_first_right).item);
	}

	/**
	 * can locate find the value at a remote leaf
	 */
	@Test
	public void locate_leaf() {
		BSTExercise<Integer> bt = makeSmallTree();

		assertEquals(small_string + ".locate(leaf)", 
					small_leaf, (int) bt.locate(small_leaf).item);
	}

	/**
	 * does locate properly report a value not in the tree
	 */
	@Test
	public void locate_none() {
		BSTExercise<Integer> bt = makeSmallTree();

		assertNull(small_string + ".locate(not-there)", bt.locate(small_not_there));
	}

	/**
	 * does the first insertion work correctly
	 */
	@Test
	public void insert_root() {
		BSTExercise<Integer> bt = new BSTExercise<Integer>();

		assertNull("newly created B-Tree has no root", bt.root);
		assertEquals("newly created B-Tree has size 0", 0, bt.size());

		bt.insert(666);
		assertNotNull("first insert creates root node", bt.root);
		assertEquals("first inserted item is root node", 666, (int) bt.root.item);
		assertEquals("after root insertion, size=1", 1, bt.size());
		assertNull("newly added root has no left child", bt.root.left);
		assertNull("newly added root has no right child", bt.root.right);
	}

	/**
	 * does insertion immediately under the root work
	 */
	@Test
	public void insert_root_child() {
		BSTExercise<Integer> bt = new BSTExercise<Integer>();

		bt.insert(666);

		bt.insert(333);
		assertNotNull("insert to left of root creates node", bt.root.left);
		assertEquals("insert to left of root adds to tree", 333, (int) bt.root.left.item);
		assertEquals("after first child insertion, size=2", 2, bt.size());
		assertNull("newly inserted node has no left child", bt.root.left.left);
		assertNull("newly inserted node has no right child", bt.root.left.right);

		bt.insert(999);
		assertNotNull("insert to right of root creates node", bt.root.right);
		assertEquals("insert to right of root adds to tree", 999, (int) bt.root.right.item);
		assertEquals("after second child insertion, size=3", 3, bt.size());
		assertNull("newly inserted node has no left child", bt.root.right.left);
		assertNull("newly inserted node has no right child", bt.root.right.right);
	}

	/**
	 * does insertion of a deep left child work
	 */
	@Test
	public void insert_deep_left() {
		BSTExercise<Integer> bt = makeSmallTree();

		assertEquals(small_string + ".size()", small_size, bt.size());

		bt.insert(small_add_left);
		assertEquals("add left leaf increases size by one", small_size+1, bt.size());
		assertEquals("left leaf node is added to tree", 
					small_add_left, (int) bt.locate(small_add_left).item);
	}

	/**
	 * does insertion of a deep right child work
	 */
	@Test
	public void insert_deep_right() {
		BSTExercise<Integer> bt = makeSmallTree();

		assertEquals(small_string + ".size()", small_size, bt.size());

		bt.insert(small_add_right);
		assertEquals("add lright leaf increases size by one", small_size+1, bt.size());
		assertEquals("right leaf node is added to tree", 
					small_add_right, (int) bt.locate(small_add_right).item);
	}

	/**
	 * does insertion recognize a reinsertion of the root
	 */
	@Test
	public void insert_same_root() {
		BSTExercise<Integer> bt = makeSmallTree();

		assertEquals(small_string + ".size()", small_size, bt.size());
		bt.insert(small_tree[0]);
		assertEquals("reinsertion of root does not change size", small_size, bt.size());
	}

	/**
	 * does insertion recognize a reinsertion mid-tree
	 */
	@Test
	public void insert_same_deep() {
		BSTExercise<Integer> bt = makeSmallTree();

		assertEquals(small_string + ".size()", small_size, bt.size());
		bt.insert(small_tree[small_mid]);
		assertEquals("reinsertion of mid-tree node does not change size", small_size, bt.size());
	}

	/**
	 * does insertion recognize an insertion of a deep leaf
	 */
	@Test
	public void insert_same_leaf() {
		BSTExercise<Integer> bt = makeSmallTree();

		assertEquals(small_string + ".size()", small_size, bt.size());
		bt.insert(small_tree[small_leaf]);
		assertEquals("reinsertion of leaf does not change size", small_size, bt.size());
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

		assertTrue("constructIntTree -> height > " + min_height, bt.height() > min_height);
		assertTrue("constructIntTree -> height < " + max_height, bt.height() < max_height);
	}

	/**
	 * does constructIntTree produce a tree with a plausible size
	 */
	@Test
	public void construct_size() {
		int target = 128;
		BSTExercise<Integer> bt = BSTExercise.constructIntTree(target);

		int min_size = 2 * target / 3;
		assertTrue("constructIntTree -> size > " + min_size, bt.size() > min_size);
		assertTrue("constructIntTree -> size <= " + target, bt.height() <= target);
	}
}
