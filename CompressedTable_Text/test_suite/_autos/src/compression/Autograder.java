package compression;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for CurDoublyLinkedList and CompressedTable classes
 *
 * @author cs62
 */
public class Autograder {
	private CurDoublyLinkedList<Integer> list;
	protected CompressedTable<String> table;

	int rows = 5;
	int cols = 5;
	String defaultValue = "r";
	String newValue1 = "g";
	String newValue2 = "k";

	/**
	 * Sets up each test from scratch by creating a new empty list.
	 */
	@BeforeEach
	public void setUp() throws Exception {
		list = new CurDoublyLinkedList<Integer>();
		table = new CompressedTable<String>(rows, cols, defaultValue);
	}

	/**
	 * Check the initial conditions of a new list
	 */
	@Test
	public void testCurDoublyLinkedList() {
		assertNull(list.current);
		assertEquals(0, list.size());
		assertFalse(list.isOffLeft());
		assertFalse(list.isOffRight());
	}

	/**
	 * When adding new nodes using .add(), first should
	 */
	@Test
	public void testFirst() {
		// 47
		// one element
		list.add(47);
		list.first();
		assertNotNull(list.current, "current not null after calling first().");
		assertEquals(47, (int) list.current.item, "list is: 47, first is 47.");
		// off right

		// 83 <=> 47

		list.add(83);
		list.next();
		list.first();
		assertNotNull(list.current, "current not null after insert.");
		assertEquals(83, (int) list.current.item, "list is: 83 <=> 47, called next(), first(), then get current:");
	}

	@Test
	public void testFirstOff() {
		// 47
		list.add(47);
		// off right
		list.next();
		assertTrue(list.isOffRight(), "list should be off right after calling next()");
		// bring it back to first
		list.first();
		assertNotNull(list.current, "current not null after calling first() on non-empty list.");
		assertEquals(47, (int) list.current.item, "list is: 47, called next(), first(), then get current:");
		assertFalse(list.isOffRight(), "list should not be off right after calling first()");

		// off left
		list.back();
		assertTrue(list.isOffLeft(), "list should be off left after calling back()");

		// bring it back to first
		list.first();
		assertFalse(list.isOffLeft(), "list should not be off left after calling first()");
	}

	/**
	 * create an empty list, confirm first() throws IllegalStateException
	 */
	@Test
	public void testFirstEmpty() {
		assertThrows(IllegalStateException.class, () -> {
			list.first();
		});
	}

	@Test
	public void testLast() {
		// 47
		list.add(47);
		list.first();
		assertNotNull(list.current, "current not null after calling last().");
		assertEquals(47, (int) list.current.item, "list is: 47, last is 47.");

		// 83 <=> 47
		list.add(83);
		list.last();

		assertNotNull(list.current);
		assertEquals(47, (int) list.current.item, "list is: 83 <=> 47, called last(), then get current:");
	}

	@Test
	public void testLastOff() {
		// 47
		list.add(47);
		// off right
		list.next();
		assertTrue(list.isOffRight(), "list should be off right after calling next()");
		// bring back to last
		list.last();
		assertNotNull(list.current, "current not null after calling last() on non-empty list.");
		assertEquals(47, (int) list.current.item, "list is: 47, called next(), lst(), then get current:");
		assertFalse(list.isOffRight(), "list should not be off right after calling last()");

		// off left
		list.back();
		assertTrue(list.isOffLeft(), "list should be off right after calling back()");
		// bring back to last
		list.last();
		assertFalse(list.isOffLeft(), "list should not be off left after calling last()");
	}

	// create an empty list, confirm last() throws IllegalStateException
	@Test
	public void testLastEmpty() {
		assertThrows(IllegalStateException.class, () -> {
			list.last();
		});
	}

	// create an empty list, confirm next() throws IllegalStateException
	@Test
	public void testNextEmptyList() {
		assertThrows(IllegalStateException.class, () -> {
			list.next();
		});

	}

	// Calls next() on a non-empty list where current is off the right side
	// Replace with whatever exception you throw when the precondition is
	// violated
	@Test
	public void testNextOffRightSide() {
		list.addFirst(47);
		list.next(); // this moves current off right
		// this should trigger an exception
		assertThrows(IllegalStateException.class, () -> {
			list.next();
		});

	}

	// Calls next() on non-empty list where current is head
	@Test
	public void testNext() {
		// 83 <=> 47
		list.add(47);
		list.add(83);
		list.next();
		assertEquals(47, (int) list.current.item, "list is: 83 <=> 47, called next() after add().");
		assertFalse(list.isOffRight(), "list is: 83 <=> 47, called next() after add(). list is not off right.");
		list.next();
		assertTrue(list.isOffRight(),
				"list is: 83 <=> 47, called next() and next() after add(). list should be off right.");

	}

	// create an empty list, confirm back() throws IllegalStateException
	@Test
	public void testBackEmptyList() {
		assertThrows(IllegalStateException.class, () -> {
			list.back();
		});

	}

	// Calls back() on a non-empty list where current is off the left side
	@Test
	public void testBackOffLeftSide() {
		list.addFirst(47);
		list.back(); // this moves current off left
		assertThrows(IllegalStateException.class, () -> {
			list.back();
		});
	}

	@Test
	public void testBack() {
		list.add(47);
		list.back();
		assertTrue(list.isOffLeft(), "list is: 47, called back() after add(). list should be off left.");

		list.add(83);
		list.next();
		list.back();
		assertEquals(83, (int) list.current.item,
				"list is: 83 <=> 47, called add(), back(), add(), next(), and back(). current at 83.");
		assertFalse(list.isOffLeft(),
				"list is: 83 <=> 47, called add(), back(), add(), next(), and back(). list is not off left.");
	}

	@Test
	public void testIsOffRight() {
		list.add(47);
		list.next();
		assertNull(list.current);
		assertTrue(list.isOffRight(), "list is: 47, called next() after add(). list should be off right.");
		assertFalse(list.isOffLeft());
	}

	@Test
	public void testIsOffLeft() {
		list.add(47);
		list.back();
		assertNull(list.current);
		assertTrue(list.isOffLeft(), "list is: 47, called back() after add(). list should be off left.");
		assertFalse(list.isOffRight());

	}

	@Test
	public void testIsOff() {
		list.add(47);
		assertFalse(list.isOff());

		list.first();
		list.back();
		assertTrue(list.isOff());

		list.last();
		list.next();
		assertTrue(list.isOff());
	}

	// Calls currentValue() on an empty list.
	@Test
	public void testCurrentValueEmptyList() {
		assertThrows(IllegalStateException.class, () -> {
			list.currentValue();
		});
	}

	// Calls currentValue() on non-empty list where current is off right.
	@Test
	public void testCurrentValueIsOff() {
		list.addFirst(47);
		list.next(); // this moves current off right
		assertThrows(IllegalStateException.class, () -> {
			list.currentValue();
		});
	}

	@Test
	public void testCurrentValue() {
		// 134 <=> 84 <=> 47
		list.add(47);
		list.add(84);
		list.add(134);

		list.first(); // current points at 134
		assertEquals(134, (int) list.currentValue(), "list is 134 <=> 84 <=> 47, called first().");

		list.next(); // current points at 84
		assertEquals(84, (int) list.currentValue(), "list is 134 <=> 84 <=> 47, called first(), next().");

		list.next(); // current points at 47
		assertEquals(47, (int) list.currentValue(), "list is 134 <=> 84 <=> 47, called first(), next(), next().");
	}

	@Test
	public void testAddAfterCurrent() {
		// 47
		list.add(47);
		list.first();
		assertEquals(1, (int) list.size(), "list is 47, called add(), first(). Size should be 1.");
		assertEquals(47, (int) list.current.item, "list is 47, called add(), first(). current should be 47.");

		// 47 <=> 83
		list.addAfterCurrent(83);

		assertEquals(2, (int) list.size(),
				"list is 47 <=> 83, called add(), first(), addAfterCurrent(). Size should be 2.");
		assertEquals(83, (int) list.current.item,
				"list is 47 <=> 83, called add(), first(), addAfterCurrent(). Current should be 83.");
		// new node
		// 47 <=> 134 <=> 83
		list.back();
		list.addAfterCurrent(134);

		assertEquals(3, (int) list.size(),
				"list is 47 <=> 83, called add(), first(), addAfterCurrent(), back(), addAfterCurrent(). Size should be 3.");
		assertEquals(134, (int) list.current.item,
				"list is 47 <=> 83, called add(), first(), addAfterCurrent(), back(), addAfterCurrent(). Current should be 134.");

	}

	@Test
	public void testAddAfterCurrentNull() {
		list.add(47);
		assertThrows(IllegalArgumentException.class, () -> {
			list.addAfterCurrent(null);
		});
	}

	@Test
	public void testAddAfterCurrentIsOff() {
		list.add(47);
		list.next();
		assertThrows(IllegalStateException.class, () -> {
			list.addAfterCurrent(83);
		});
	}

	@Test
	public void testRemoveCurrent() {
		list.add(47);
		list.add(83);
		list.last();
		list.removeCurrent();
		// when removing the last value, check that current becomes null
		// and the size of the list is 1 (having started with 2 elts)
		assertEquals(1, (int) list.size(), 
				"list is 83, called add(47), add(83), last(), removeCurrent(). Size should be 1.");
		assertNull(list.current,
				"list is 83, called add(47), add(83), last(), removeCurrent(). current should be null.");
		assertTrue(list.isOff(),
				"list is 83, called add(47), add(83), last(), removeCurrent(). current should be off.");

		list.first();
		list.removeCurrent();
		// when removing the the only value, check that the
		// current value becomes the next one
		assertEquals(0, (int) list.size(),
				"list should be empty. called add(47), add(83), last(), removeCurrent(), first(), removeCurrent().");
		assertNull(list.current,
				"current should be null. called add(47), add(83), last(), removeCurrent(), first(), removeCurrent().");
		// assertTrue(list.isOff());

		// check that we can remove a value in the middle of a list
		// 47 <=> 134 <=> 83
		list.add(83);
		list.add(134);
		list.add(47);
		list.first();
		list.next();
		list.removeCurrent();

		assertEquals(83, (int) list.current.item, "list was 47 <=> 134 <=> 83 and removed 134. current at 83.");
		list.first();
		assertEquals(47, (int) list.current.item,
				"list was 47 <=> 134 <=> 83 and removed 134 and called first(). current at 47.");
	}

	@Test
	public void testRemoveCurrentEmpty() {
		assertThrows(IllegalStateException.class, () -> {
			list.removeCurrent();
		});
	}

	@Test
	public void testRemoveCurrentIsOff() {
		list.add(47);
		list.next();
		assertThrows(IllegalStateException.class, () -> {
			list.removeCurrent();
		});
	}

	@Test
	public void testAddFirst() {
		list.addFirst(47);
		assertEquals(47, (int) list.currentValue(), "current should be 47 after addFirst(47).");

		list.addFirst(83);
		assertEquals(83, (int) list.currentValue(), "current should be (83) <=> 47 after addFirst(47), addFirst(83).");
		list.first();
		assertEquals(83, (int) list.currentValue(),
				"current should be (83) <=> 47 after addFirst(47), addFirst(83), first().");
	}

	@Test
	public void testAddFirstAfterOff() {
		list.addFirst(47);
		list.back();
		list.addFirst(83);
		assertFalse(list.isOffLeft(), "list should change to not off left after addFirst()");
		list.next();
		list.next();
		list.addFirst(134);
		assertFalse(list.isOffRight(), "list should change to not off right after addFirst()");
	}

	@Test
	public void testAddFirstNull() {
		list.add(47);
		assertThrows(IllegalArgumentException.class, () -> {
			list.addFirst(null);
		});
	}

	@Test
	public void testRemoveFirst() {
		list.add(47);
		list.add(83);
		list.removeFirst();
		assertEquals(47, (int) list.current.item, "list was 83 <=> 47, and removed first");
	}

	@Test
	public void testRemoveFirstAfterOffLeft() {
		list.add(47);
		list.add(83);
		list.back();
		list.removeFirst();
		assertFalse(list.isOffLeft(), "list was 83 <=> 47. list should change to not off left after removeFirst()");
	}

	@Test
	public void testAddLast() {
		// 84 <=> 47 <=> 134
		list.add(47);
		list.add(84);

		assertEquals(84, (int) list.current.item);

		list.addLast(134);
		assertEquals(3, list.size());
		assertEquals(134, (int) list.current.item);

		list.first();
		assertEquals(84, (int) list.current.item);
		list.next();
		assertEquals(47, (int) list.current.item);
		list.next();
		assertEquals(134, (int) list.current.item);
	}

	@Test
	public void testAddLastOff() {
		list.addLast(47);
		list.next();
		list.addLast(83);
		assertFalse(list.isOffRight(), "list should change to not off right after addLast()");

		list.back();
		list.back();
		list.addLast(134);
		assertFalse(list.isOffLeft(), "list should change to not off left after addLast()");
	}

	@Test
	public void testAddLastNull() {
		assertThrows(IllegalArgumentException.class, () -> {
			list.addLast(null);
		});
	}

	@Test
	public void testRemoveLastAfterOffLeft() {
		list.add(47);
		list.add(83);
		list.back();
		list.removeLast();
		assertFalse(list.isOffLeft(), "list should change to not off left after removeLast()");
	}

	@Test
	public void testRemoveLast() {
		// 134 <=> 84 <=> 47
		list.add(47);
		list.add(84);
		list.add(134);

		assertEquals(134, (int) list.current.item);

		list.removeLast();
		assertNull(list.current);
		assertTrue(list.isOffRight());
		assertFalse(list.isOffLeft());

		assertEquals(2, list.size());
		list.first();
		assertEquals(134, (int) list.current.item);
		list.next();
		assertEquals(84, (int) list.current.item);
	}

	@Test
	public void testGetFirst() {
		list.add(47);
		assertEquals(47, (int) list.getFirst(), "list is 47, called getFirst().");

		list.add(83);
		assertEquals(83, (int) list.getFirst(), "list is 83<=>47, called getFirst().");

		list.next();
		assertEquals(47, (int) list.currentValue(), "list is 83<=>47, current at 47, getCurrent().");
		assertEquals(83, (int) list.getFirst(), "list is 83<=>47, current at 47, then called getFirst().");
		assertEquals(83, (int) list.currentValue(),
				"list is 83<=>47, current was at 47, then called getCurrent() after getFirst().");

		list.add(134);

		list.last();
		assertEquals(47, (int) list.current.item);
		assertEquals(134, (int) list.getFirst());
		assertEquals(134, (int) list.current.item);

	}

	@Test
	public void testGetFirstAfterOff() {
		list.add(47);
		list.back();
		assertEquals(47, (int) list.getFirst(), "list is 47. getFirst should change current to 47 after off left");
		assertFalse(list.isOffLeft(), "list should change to not off left after getFirst()");

		list.next();
		assertEquals(47, (int) list.getFirst(), "list is 47. getFirst should change current to 47 after off right");
		assertFalse(list.isOffRight(), "list should change to not off right after getFirst()");
	}

	@Test
	public void testGetLast() {
		list.add(47);
		assertEquals(47, (int) list.getLast(), "list is 47, called getLast().");

		list.add(83);
		assertEquals(47, (int) list.getLast(), "list is 83<=>47, current at 83, then called getLast().");
		assertEquals(47, (int) list.current.item,
				"list is 83<=>47, current was at 83, then called getLast(), current should be at 47.");
		list.add(134);

		assertEquals(134, (int) list.current.item);
		assertEquals(47, (int) list.getLast());
		assertEquals(47, (int) list.current.item);

	}

	@Test
	public void testGetLastAfterOff() {
		list.add(47);
		list.back();
		assertEquals(47, (int) list.getLast(), "list is 47. getLast should change current to 47 after off left");
		assertFalse(list.isOffLeft(), "list should change to not off left after getLast()");

		list.next();
		assertEquals(47, (int) list.getLast(), "list is 47. getLast should change current to 47 after off right");
		assertFalse(list.isOffRight(), "list should change to not off right after getLast()");
	}

	@Test
	public void testClear() {
		list.add(47);
		list.add(83);
		list.clear();
		assertNull(list.current);
		assertEquals(0, list.size());
		assertFalse(list.isOffLeft());
		assertFalse(list.isOffRight());
	}

	@Test
	public void testInitialOutput() {
		String output = "rrrrr\n" + "rrrrr\n" + "rrrrr\n" + "rrrrr\n" + "rrrrr\n";
		String entireTable = table.entireTable();
		assertEquals(output, entireTable,
				"entireTable() should have returned:\n" + output + "but instead was:\n" + entireTable);
	}

	@Test
	public void testInitialCurDLL() {

		// Test that the underlying CurDLL contains only a single node
		assertEquals(1, table.tableInfo.size(),
				"Initially, CurDLL should only have 1 node but instead had " + table.tableInfo.size());

		// Test that the single node contains the defaultValue at position (0, 0)
		assertEquals(new RowOrderedPosn(0, 0, rows, cols), table.tableInfo.get(0).theKey,
				"Initially, CurDLL should have had a node for (0,0) but instead was " + table.tableInfo.get(0).theKey);
		assertEquals(defaultValue, table.tableInfo.get(0).theValue, "Initially, CurDLL should have had a node with "
				+ defaultValue + " default value but instead was " + table.tableInfo.get(0).theValue);
	}

	@Test
	public void testChangeToFirstNode() {
		table.updateInfo(0, 0, newValue1);

		// Test that the underlying CurDLL has two nodes (0,0, newValue1) and (0,1,
		// defaultValue)
		assertEquals(2, table.tableInfo.size(),
				"CurDLL should have had 2 nodes but instead had " + table.tableInfo.size());

		// Test that the first node contains newValue1
		assertEquals(new RowOrderedPosn(0, 0, rows, cols), table.tableInfo.get(0).theKey,
				"CurDLL should have had a node for (0,0) but instead was " + table.tableInfo.get(0).theKey);
		assertEquals(newValue1, table.tableInfo.get(0).theValue, "First node should have had " + newValue1
				+ " value but instead was " + table.tableInfo.get(0).theValue);

		// Test that the second node contains defaultValue
		assertEquals(new RowOrderedPosn(0, 1, rows, cols), table.tableInfo.get(1).theKey,
				"CurDLL should have had a node for (0,1) but instead was " + table.tableInfo.get(1).theKey);
		assertEquals(defaultValue, table.tableInfo.get(1).theValue, "Second node should have had " + defaultValue
				+ " value but instead was " + table.tableInfo.get(1).theValue);

		// Test output
		String output = "grrrr\n" + "rrrrr\n" + "rrrrr\n" + "rrrrr\n" + "rrrrr\n";
		String entireTable = table.entireTable();
		assertEquals(output, entireTable,
				"entireTable() should have returned:\n" + output + "but instead was:\n" + entireTable);
	}

	@Test
	public void testSecondChangeToFirstNode() {
		table.updateInfo(0, 0, newValue1);
		table.updateInfo(0, 0, newValue2);

		// Test that the underlying CurDLL has two nodes (0,0, newValue2) and (0,1,
		// defaultValue)
		assertEquals(2, table.tableInfo.size(),
				"CurDLL should have had 2 nodes but instead had " + table.tableInfo.size());

		// Test that the first node contains newValue2
		assertEquals(new RowOrderedPosn(0, 0, rows, cols), table.tableInfo.get(0).theKey,
				"CurDLL should have had a node for (0,0) but instead was " + table.tableInfo.get(0).theKey);
		assertEquals(newValue2, table.tableInfo.get(0).theValue, "First node should have had " + newValue2
				+ " value but instead was " + table.tableInfo.get(0).theValue);

		// Test that the second node contains defaultValue
		assertEquals(new RowOrderedPosn(0, 1, rows, cols), table.tableInfo.get(1).theKey,
				"CurDLL should have had a node for (0,1) but instead was " + table.tableInfo.get(1).theKey);
		assertEquals(defaultValue, table.tableInfo.get(1).theValue, "Second node should have had " + defaultValue
				+ " value but instead was " + table.tableInfo.get(1).theValue);

		// Test output
		String output = "krrrr\n" + "rrrrr\n" + "rrrrr\n" + "rrrrr\n" + "rrrrr\n";
		String entireTable = table.entireTable();
		assertEquals(output, entireTable,
				"entireTable() should have returned:\n" + output + "but instead was:\n" + entireTable);

	}

	@Test
	public void testFutileChange() {
		table.updateInfo(0, 0, newValue1);
		table.updateInfo(0, 0, newValue1);

		// Test that the underlying CurDLL has two nodes (0,0, newValue1) and (0,1,
		// defaultValue)
		assertEquals(2, table.tableInfo.size(),
				"CurDLL should have had 2 nodes but instead had " + table.tableInfo.size());

		// Test that the first node contains newValue2
		assertEquals(new RowOrderedPosn(0, 0, rows, cols), table.tableInfo.get(0).theKey,
				"CurDLL should have had a node for (0,0) but instead was " + table.tableInfo.get(0).theKey);
		assertEquals(newValue1, table.tableInfo.get(0).theValue, "First node should have had " + newValue1
				+ " value but instead was " + table.tableInfo.get(0).theValue);

		// Test that the second node contains defaultValue
		assertEquals(new RowOrderedPosn(0, 1, rows, cols), table.tableInfo.get(1).theKey,
				"CurDLL should have had a node for (0,1) but instead was " + table.tableInfo.get(1).theKey);
		assertEquals(defaultValue, table.tableInfo.get(1).theValue, "Second node should have had " + defaultValue
				+ " value but instead was " + table.tableInfo.get(1).theValue);

		// Test output
		String output = "grrrr\n" + "rrrrr\n" + "rrrrr\n" + "rrrrr\n" + "rrrrr\n";
		String entireTable = table.entireTable();
		assertEquals(output, entireTable,
				"entireTable() should have returned:\n" + output + "but instead was:\n" + entireTable);

	}

	@Test
	public void testChangeLastNode() {
		table.updateInfo(rows - 1, cols - 1, newValue1);

		// Test that the underlying CurDLL has two nodes (0,0, defaultValue) and
		// (rows-1, cols-1, newValue1)
		assertEquals(2, table.tableInfo.size(),
				"CurDLL should have had 2 nodes but instead had " + table.tableInfo.size());

		// Test that the first node contains defaultValue
		assertEquals(new RowOrderedPosn(0, 0, rows, cols), table.tableInfo.get(0).theKey,
				"CurDLL should have had a node for (0,0) but instead was " + table.tableInfo.get(0).theKey);
		assertEquals(defaultValue, table.tableInfo.get(0).theValue, "First node should have had " + defaultValue
				+ " value but instead was " + table.tableInfo.get(0).theValue);

		// Test that the second node contains newValue1
		assertEquals(new RowOrderedPosn(rows - 1, cols - 1, rows, cols), table.tableInfo.get(1).theKey,
				"CurDLL should have had a node for (rows-1, cols-1) but instead was " + table.tableInfo.get(1).theKey);
		assertEquals(newValue1, table.tableInfo.get(1).theValue, "Second node should have had " + newValue1
				+ " value but instead was " + table.tableInfo.get(1).theValue);

		// Test output
		String output = "rrrrr\n" + "rrrrr\n" + "rrrrr\n" + "rrrrr\n" + "rrrrg\n";
		String entireTable = table.entireTable();
		assertEquals(output, entireTable,
				"entireTable() should have returned:\n" + output + "but instead was:\n" + entireTable);

	}

	@Test
	public void testAddIntermediateNode() {
		table.updateInfo(0, 1, newValue1);

		// Test that the underlying CurDLL has three nodes (0,0, defaultValue), (0, 1,
		// newValue1) and (0, 2, defaultValue)
		assertEquals(3, table.tableInfo.size(),
				"CurDLL should have had 3 nodes but instead had " + table.tableInfo.size());

		// Test that the first node contains defaultValue
		assertEquals(new RowOrderedPosn(0, 0, rows, cols), table.tableInfo.get(0).theKey,
				"CurDLL should have had a node for (0,0) but instead was " + table.tableInfo.get(0).theKey);
		assertEquals(defaultValue, table.tableInfo.get(0).theValue, "First node should have had " + defaultValue
				+ " value but instead was " + table.tableInfo.get(0).theValue);

		// Test that the second node contains newValue1
		assertEquals(new RowOrderedPosn(0, 1, rows, cols), table.tableInfo.get(1).theKey,
				"CurDLL should have had a node for (0,1) but instead was " + table.tableInfo.get(1).theKey);
		assertEquals(newValue1, table.tableInfo.get(1).theValue, "Second node should have had " + newValue1
				+ " value but instead was " + table.tableInfo.get(1).theValue);

		// Test that the third node contains defaultValue
		assertEquals(new RowOrderedPosn(0, 2, rows, cols), table.tableInfo.get(2).theKey,
				"CurDLL should have had a node for (0,2) but instead was " + table.tableInfo.get(2).theKey);
		assertEquals(defaultValue, table.tableInfo.get(2).theValue, "Third node should have had " + defaultValue
				+ " value but instead was " + table.tableInfo.get(2).theValue);
		// Test output
		String output = "rgrrr\n" + "rrrrr\n" + "rrrrr\n" + "rrrrr\n" + "rrrrr\n";
		String entireTable = table.entireTable();
		assertEquals(output, entireTable,
				"entireTable() should have returned:\n" + output + "but instead was:\n" + entireTable);

	}

	@Test
	public void testChangeIntermediateNode() {
		table.updateInfo(0, 1, newValue1);
		// change the newly added node to a new value
		table.updateInfo(0, 1, newValue2);

		// Test that the underlying CurDLL has three nodes (0,0, defaultValue), (0, 1,
		// newValue2) and (0, 2, defaultValue)
		assertEquals(3, table.tableInfo.size(),
				"CurDLL should have had 3 nodes but instead had " + table.tableInfo.size());

		// Test that the first node contains defaultValue
		assertEquals(new RowOrderedPosn(0, 0, rows, cols), table.tableInfo.get(0).theKey,
				"CurDLL should have had a node for (0,0) but instead was " + table.tableInfo.get(0).theKey);
		assertEquals(defaultValue, table.tableInfo.get(0).theValue, "First node should have had " + defaultValue
				+ " value but instead was " + table.tableInfo.get(0).theValue);

		// Test that the second node contains newValue2
		assertEquals(new RowOrderedPosn(0, 1, rows, cols), table.tableInfo.get(1).theKey,
				"CurDLL should have had a node for (0,1) but instead was " + table.tableInfo.get(1).theKey);
		assertEquals(newValue2, table.tableInfo.get(1).theValue, "Second node should have had " + newValue2
				+ " value but instead was " + table.tableInfo.get(1).theValue);

		// Test that the third node contains defaultValue
		assertEquals(new RowOrderedPosn(0, 2, rows, cols), table.tableInfo.get(2).theKey,
				"CurDLL should have had a node for (0,2) but instead was " + table.tableInfo.get(2).theKey);
		assertEquals(defaultValue, table.tableInfo.get(2).theValue, "Third node should have had " + defaultValue
				+ " value but instead was " + table.tableInfo.get(2).theValue);

		// Test output
		String output = "rkrrr\n" + "rrrrr\n" + "rrrrr\n" + "rrrrr\n" + "rrrrr\n";
		String entireTable = table.entireTable();
		assertEquals(output, entireTable,
				"entireTable() should have returned:\n" + output + "but instead was:\n" + entireTable);
	}

	@Test
	public void testSequentialUpdates() {
		table.updateInfo(0, 1, newValue1);
		table.updateInfo(0, 2, newValue1);
		table.updateInfo(0, 2, newValue2);

		// Test that the underlying CurDLL has four nodes (0,0, defaultValue), (0, 1,
		// newValue1), (0,2, newValue2) and (0, 3, defaultValue)
		assertEquals(4, table.tableInfo.size(),
				"CurDLL should have had 4 nodes but instead had " + table.tableInfo.size());

		// Test that the first node contains defaultValue
		assertEquals(new RowOrderedPosn(0, 0, rows, cols), table.tableInfo.get(0).theKey,
				"CurDLL should have had a node for (0,0) but instead was " + table.tableInfo.get(0).theKey);
		assertEquals(defaultValue, table.tableInfo.get(0).theValue, "First node should have had " + defaultValue
				+ " value but instead was " + table.tableInfo.get(0).theValue);

		// Test that the second node contains newValue1
		assertEquals(new RowOrderedPosn(0, 1, rows, cols), table.tableInfo.get(1).theKey,
				"CurDLL should have had a node for (0,1) but instead was " + table.tableInfo.get(1).theKey);
		assertEquals(newValue1, table.tableInfo.get(1).theValue, "Second node should have had " + newValue1
				+ " value but instead was " + table.tableInfo.get(1).theValue);

		// Test that the third node contains newValue2
		assertEquals(new RowOrderedPosn(0, 2, rows, cols), table.tableInfo.get(2).theKey,
				"CurDLL should have had a node for (0,2) but instead was " + table.tableInfo.get(2).theKey);
		assertEquals(newValue2, table.tableInfo.get(2).theValue, "Third node should have had " + newValue2
				+ " value but instead was " + table.tableInfo.get(2).theValue);

		// Test that the fourth node contains defaultValue
		assertEquals(new RowOrderedPosn(0, 3, rows, cols), table.tableInfo.get(3).theKey,
				"CurDLL should have had a node for (0,3) but instead was " + table.tableInfo.get(3).theKey);
		assertEquals(defaultValue, table.tableInfo.get(3).theValue, "Third node should have had " + defaultValue
				+ " value but instead was " + table.tableInfo.get(3).theValue);

		// Test output
		String output = "rgkrr\n" + "rrrrr\n" + "rrrrr\n" + "rrrrr\n" + "rrrrr\n";
		String entireTable = table.entireTable();
		assertEquals(output, entireTable,
				"entireTable() should have returned:\n" + output + "but instead was:\n" + entireTable);

	}

	@Test
	public void testMultipleChanges() {
		table.updateInfo(0, 1, newValue1);
		table.updateInfo(0, 2, newValue1);
		table.updateInfo(0, 3, newValue1);
		table.updateInfo(0, 4, newValue2);
		table.updateInfo(0, 2, defaultValue);

		assertEquals(6, table.tableInfo.size(),
				"CurDLL should have had 6 nodes but instead had " + table.tableInfo.size());

		// Test that the first node contains defaultValue
		assertEquals(new RowOrderedPosn(0, 0, rows, cols), table.tableInfo.get(0).theKey,
				"CurDLL should have had a node for (0,0) but instead was " + table.tableInfo.get(0).theKey);
		assertEquals(defaultValue, table.tableInfo.get(0).theValue, "First node should have had " + defaultValue
				+ " value but instead was " + table.tableInfo.get(0).theValue);

		// Test that the second node contains newValue1
		assertEquals(new RowOrderedPosn(0, 1, rows, cols), table.tableInfo.get(1).theKey,
				"CurDLL should have had a node for (0,1) but instead was " + table.tableInfo.get(1).theKey);
		assertEquals(newValue1, table.tableInfo.get(1).theValue, "Second node should have had " + newValue1
				+ " value but instead was " + table.tableInfo.get(1).theValue);

		// Test that the third node contains defaultValue
		assertEquals(new RowOrderedPosn(0, 2, rows, cols), table.tableInfo.get(2).theKey,
				"CurDLL should have had a node for (0,2) but instead was " + table.tableInfo.get(2).theKey);
		assertEquals(defaultValue, table.tableInfo.get(2).theValue, "Third node should have had " + defaultValue
				+ " value but instead was " + table.tableInfo.get(2).theValue);

		// Test that the fourth node contains newValue1
		assertEquals(new RowOrderedPosn(0, 3, rows, cols), table.tableInfo.get(3).theKey,
				"CurDLL should have had a node for (0,3) but instead was " + table.tableInfo.get(3).theKey);
		assertEquals(newValue1, table.tableInfo.get(3).theValue, "Fourth node should have had " + newValue1
				+ " value but instead was " + table.tableInfo.get(3).theValue);

		// Test that the fifth node contains newValue2
		assertEquals(new RowOrderedPosn(0, 4, rows, cols), table.tableInfo.get(4).theKey,
				"CurDLL should have had a node for (0,4) but instead was " + table.tableInfo.get(4).theKey);
		assertEquals(newValue2, table.tableInfo.get(4).theValue, "Fifth node should have had " + newValue2
				+ " value but instead was " + table.tableInfo.get(4).theValue);

		// Test that the sixth node contains defaultValue
		assertEquals(new RowOrderedPosn(1, 0, rows, cols), table.tableInfo.get(5).theKey,
				"CurDLL should have had a node for (1,0) but instead was " + table.tableInfo.get(5).theKey);
		assertEquals(defaultValue, table.tableInfo.get(5).theValue, "Sixth node should have had " + defaultValue
				+ " value but instead was " + table.tableInfo.get(5).theValue);

		// Test output
		String output = "rgrgk\n" + "rrrrr\n" + "rrrrr\n" + "rrrrr\n" + "rrrrr\n";
		String entireTable = table.entireTable();
		assertEquals(output, entireTable,
				"entireTable() should have returned:\n" + output + "but instead was:\n" + entireTable);

	}

	@Test
	public void testSimpleContraction() {
		table.updateInfo(0, 1, newValue1);
		// change back to defaultValue
		table.updateInfo(0, 1, defaultValue);

		// Test that the underlying CurDLL contains only a single node
		assertEquals(1, table.tableInfo.size(),
				"After contraction, CurDLL should only have 1 node but instead had " + table.tableInfo.size());

		// Test that the single node contains the defaultValue at position (0, 0)
		assertEquals(new RowOrderedPosn(0, 0, rows, cols), table.tableInfo.get(0).theKey,
				"After contraction, CurDLL should have had a node for (0,0) but instead was "
						+ table.tableInfo.get(0).theKey);
		assertEquals(defaultValue, table.tableInfo.get(0).theValue,
				"After contraction, CurDLL should have had a node with " + defaultValue
						+ " default value but instead was " + table.tableInfo.get(0).theValue);

		// Test output
		String output = "rrrrr\n" + "rrrrr\n" + "rrrrr\n" + "rrrrr\n" + "rrrrr\n";
		String entireTable = table.entireTable();
		assertEquals(output, entireTable,
				"entireTable() should have returned:\n" + output + "but instead was:\n" + entireTable);
	}

	@Test
	public void testIntermediateContraction() {
		table.updateInfo(0, 1, newValue1);
		table.updateInfo(0, 2, newValue1);

		// Test that the underlying CurDLL has three nodes (0,0, defaultValue), (0, 1,
		// newValue1) and (0, 3, defaultValue)
		assertEquals(3, table.tableInfo.size(),
				"CurDLL should have had 3 nodes but instead had " + table.tableInfo.size());

		// Test that the first node contains defaultValue
		assertEquals(new RowOrderedPosn(0, 0, rows, cols), table.tableInfo.get(0).theKey,
				"CurDLL should have had a node for (0,0) but instead was " + table.tableInfo.get(0).theKey);
		assertEquals(defaultValue, table.tableInfo.get(0).theValue, "First node should have had " + defaultValue
				+ " value but instead was " + table.tableInfo.get(0).theValue);

		// Test that the second node contains newValue1
		assertEquals(new RowOrderedPosn(0, 1, rows, cols), table.tableInfo.get(1).theKey,
				"CurDLL should have had a node for (0,1) but instead was " + table.tableInfo.get(1).theKey);
		assertEquals(newValue1, table.tableInfo.get(1).theValue, "Second node should have had " + newValue1
				+ " value but instead was " + table.tableInfo.get(1).theValue);

		// Test that the third node contains defaultValue
		assertEquals(new RowOrderedPosn(0, 3, rows, cols), table.tableInfo.get(2).theKey,
				"CurDLL should have had a node for (0,3) but instead was " + table.tableInfo.get(2).theKey);
		assertEquals(defaultValue, table.tableInfo.get(2).theValue, "Third node should have had " + defaultValue
				+ " value but instead was " + table.tableInfo.get(2).theValue);

		// Test output
		String output = "rggrr\n" + "rrrrr\n" + "rrrrr\n" + "rrrrr\n" + "rrrrr\n";
		String entireTable = table.entireTable();
		assertEquals(output, entireTable,
				"entireTable() should have returned:\n" + output + "but instead was:\n" + entireTable);
	}

	@Test
	public void testSequentialContraction() {
		table.updateInfo(0, 1, newValue1);
		table.updateInfo(0, 2, newValue1);
		table.updateInfo(0, 2, defaultValue);

		// Test that the underlying CurDLL has three nodes (0,0, defaultValue), (0, 1,
		// newValue1) and (0, 2, defaultValue)
		assertEquals(3, table.tableInfo.size(),
				"CurDLL should have had 3 nodes but instead had " + table.tableInfo.size());

		// Test that the first node contains defaultValue
		assertEquals(new RowOrderedPosn(0, 0, rows, cols), table.tableInfo.get(0).theKey,
				"CurDLL should have had a node for (0,0) but instead was " + table.tableInfo.get(0).theKey);
		assertEquals(defaultValue, table.tableInfo.get(0).theValue, "First node should have had " + defaultValue
				+ " value but instead was " + table.tableInfo.get(0).theValue);

		// Test that the second node contains newValue1
		assertEquals(new RowOrderedPosn(0, 1, rows, cols), table.tableInfo.get(1).theKey,
				"CurDLL should have had a node for (0,1) but instead was " + table.tableInfo.get(1).theKey);
		assertEquals(newValue1, table.tableInfo.get(1).theValue, "Second node should have had " + newValue1
				+ " value but instead was " + table.tableInfo.get(1).theValue);

		// Test that the third node contains defaultValue
		assertEquals(new RowOrderedPosn(0, 2, rows, cols), table.tableInfo.get(2).theKey,
				"CurDLL should have had a node for (0,2) but instead was " + table.tableInfo.get(2).theKey);
		assertEquals(defaultValue, table.tableInfo.get(2).theValue, "Third node should have had " + defaultValue
				+ " value but instead was " + table.tableInfo.get(2).theValue);

		// Test output
		String output = "rgrrr\n" + "rrrrr\n" + "rrrrr\n" + "rrrrr\n" + "rrrrr\n";
		String entireTable = table.entireTable();
		assertEquals(output, entireTable,
				"entireTable() should have returned:\n" + output + "but instead was:\n" + entireTable);
	}

	@Test
	public void testReadMeExample() {
		table.updateInfo(0, 3, newValue1);
		table.updateInfo(0, 4, newValue1);
		table.updateInfo(2, 1, newValue2);
		table.updateInfo(3, 3, newValue1);
		table.updateInfo(4, 2, newValue2);

		// Test that the underlying CurDLL has 9 nodes:
		// <Association: Position: (0,0)=r>
		// <Association: Position: (0,3)=g>
		// <Association: Position: (1,0)=r>
		// <Association: Position: (2,1)=k>
		// <Association: Position: (2,2)=r>
		// <Association: Position: (3,3)=g>
		// <Association: Position: (3,4)=r>
		// <Association: Position: (4,2)=k>
		// <Association: Position: (4,3)=r>
		assertEquals(9, table.tableInfo.size(),
				"CurDLL should have had 9 nodes but instead had " + table.tableInfo.size());

		// Test that the first node contains defaultValue
		// <Association: Position: (0,0)=r>
		assertEquals(new RowOrderedPosn(0, 0, rows, cols), table.tableInfo.get(0).theKey,
				"CurDLL should have had a node for (0,0) but instead was " + table.tableInfo.get(0).theKey);
		assertEquals(defaultValue, table.tableInfo.get(0).theValue, "First node should have had " + defaultValue
				+ " value but instead was " + table.tableInfo.get(0).theValue);

		// Test that the second node contains newValue1
		// <Association: Position: (0,3)=g>
		assertEquals(new RowOrderedPosn(0, 3, rows, cols), table.tableInfo.get(1).theKey,
				"CurDLL should have had a node for (0,3) but instead was " + table.tableInfo.get(1).theKey);
		assertEquals(newValue1, table.tableInfo.get(1).theValue, "Second node should have had " + newValue1
				+ " value but instead was " + table.tableInfo.get(1).theValue);

		// Test that the third node contains defaultValue
		// <Association: Position: (1,0)=r>
		assertEquals(new RowOrderedPosn(1, 0, rows, cols), table.tableInfo.get(2).theKey,
				"CurDLL should have had a node for (1,0) but instead was " + table.tableInfo.get(2).theKey);
		assertEquals(defaultValue, table.tableInfo.get(2).theValue, "Third node should have had " + defaultValue
				+ " value but instead was " + table.tableInfo.get(2).theValue);

		// Test that the fourth node contains newValue2
		// <Association: Position: (2,1)=k>
		assertEquals(new RowOrderedPosn(2, 1, rows, cols), table.tableInfo.get(3).theKey,
				"CurDLL should have had a node for (2,1) but instead was " + table.tableInfo.get(3).theKey);
		assertEquals(newValue2, table.tableInfo.get(3).theValue, "Fourth node should have had " + newValue2
				+ " value but instead was " + table.tableInfo.get(3).theValue);

		// Test that the fifth node contains defaultValue
		// <Association: Position: (2,2)=r>
		assertEquals(new RowOrderedPosn(2, 2, rows, cols), table.tableInfo.get(4).theKey,
				"CurDLL should have had a node for (2,2) but instead was " + table.tableInfo.get(4).theKey);
		assertEquals(defaultValue, table.tableInfo.get(4).theValue, "Fifth node should have had " + defaultValue
				+ " value but instead was " + table.tableInfo.get(4).theValue);

		// Test that the sixth node contains newValue1
		// <Association: Position: (3,3)=g>
		assertEquals(new RowOrderedPosn(3, 3, rows, cols), table.tableInfo.get(5).theKey,
				"CurDLL should have had a node for (3,3) but instead was " + table.tableInfo.get(5).theKey);
		assertEquals(newValue1, table.tableInfo.get(5).theValue, "Sixth node should have had " + newValue1
				+ " value but instead was " + table.tableInfo.get(5).theValue);

		// Test that the seventh node contains defaultValue
		// <Association: Position: (3,4)=r>
		assertEquals(new RowOrderedPosn(3, 4, rows, cols), table.tableInfo.get(6).theKey,
				"CurDLL should have had a node for (3,4) but instead was " + table.tableInfo.get(6).theKey);
		assertEquals(defaultValue, table.tableInfo.get(6).theValue, "Seventh node should have had " + defaultValue
				+ " value but instead was " + table.tableInfo.get(6).theValue);

		// Test that the eighth node contains newValue2
		// <Association: Position: (4,2)=k>
		assertEquals(new RowOrderedPosn(4, 2, rows, cols), table.tableInfo.get(7).theKey,
				"CurDLL should have had a node for (4,2) but instead was " + table.tableInfo.get(7).theKey);
		assertEquals(newValue2, table.tableInfo.get(7).theValue, "Eighth node should have had " + newValue2
				+ " value but instead was " + table.tableInfo.get(7).theValue);

		// Test that the ninth node contains defaultValue
		// <Association: Position: (4,3)=r>
		assertEquals(new RowOrderedPosn(4, 3, rows, cols), table.tableInfo.get(8).theKey,
				"CurDLL should have had a node for (4,3) but instead was " + table.tableInfo.get(8).theKey);
		assertEquals(defaultValue, table.tableInfo.get(8).theValue, "Ninth node should have had " + defaultValue
				+ " value but instead was " + table.tableInfo.get(8).theValue);

		// Test output
		String output = "rrrgg\n" + "rrrrr\n" + "rkrrr\n" + "rrrgr\n" + "rrkrr\n";
		String entireTable = table.entireTable();
		assertEquals(output, entireTable,
				"entireTable() should have returned:\n" + output + "but instead was:\n" + entireTable);
	}

	@Test
	public void testContractionEnd() {
		table.updateInfo(rows - 1, cols - 1, newValue1);
		table.updateInfo(rows - 1, cols - 2, newValue1);

		// Test that the underlying CurDLL has two nodes (0,0, defaultValue) and
		// (rows-1, cols-2, newValue1)
		assertEquals(2, table.tableInfo.size(),
				"CurDLL should have had 2 nodes but instead had " + table.tableInfo.size());

		// Test that the first node contains defaultValue
		assertEquals(new RowOrderedPosn(0, 0, rows, cols), table.tableInfo.get(0).theKey,
				"CurDLL should have had a node for (0,0) but instead was " + table.tableInfo.get(0).theKey);
		assertEquals(defaultValue, table.tableInfo.get(0).theValue, "First node should have had " + defaultValue
				+ " value but instead was " + table.tableInfo.get(0).theValue);

		// Test that the second node contains newValue1
		assertEquals(new RowOrderedPosn(rows - 1, cols - 2, rows, cols), table.tableInfo.get(1).theKey,
				"CurDLL should have had a node for (rows-1, cols-2) but instead was " + table.tableInfo.get(1).theKey);
		assertEquals(newValue1, table.tableInfo.get(1).theValue, "Second node should have had " + newValue1
				+ " value but instead was " + table.tableInfo.get(1).theValue);

		// Test output
		String output = "rrrrr\n" + "rrrrr\n" + "rrrrr\n" + "rrrrr\n" + "rrrgg\n";
		String entireTable = table.entireTable();
		assertEquals(output, entireTable,
				"entireTable() should have returned:\n" + output + "but instead was:\n" + entireTable);

	}

	@Test
	public void testUpdateAtEnd() {
		table.updateInfo(rows - 1, cols - 1, newValue1);
		table.updateInfo(rows - 1, cols - 1, newValue2);

		// Test that the underlying CurDLL has two nodes (0,0, defaultValue) and
		// (rows-1, cols-1, newValue2)
		assertEquals(2, table.tableInfo.size(),
				"CurDLL should have had 2 nodes but instead had " + table.tableInfo.size());

		// Test that the first node contains defaultValue
		assertEquals(new RowOrderedPosn(0, 0, rows, cols), table.tableInfo.get(0).theKey,
				"CurDLL should have had a node for (0,0) but instead was " + table.tableInfo.get(0).theKey);
		assertEquals(defaultValue, table.tableInfo.get(0).theValue, "First node should have had " + defaultValue
				+ " value but instead was " + table.tableInfo.get(0).theValue);

		// Test that the second node contains newValue2
		assertEquals(new RowOrderedPosn(rows - 1, cols - 1, rows, cols), table.tableInfo.get(1).theKey,
				"CurDLL should have had a node for (rows-1, cols-1) but instead was " + table.tableInfo.get(1).theKey);
		assertEquals(newValue2, table.tableInfo.get(1).theValue, "Second node should have had " + newValue2
				+ " value but instead was " + table.tableInfo.get(1).theValue);

		// Test output
		String output = "rrrrr\n" + "rrrrr\n" + "rrrrr\n" + "rrrrr\n" + "rrrrk\n";
		String entireTable = table.entireTable();
		assertEquals(output, entireTable,
				"entireTable() should have returned:\n" + output + "but instead was:\n" + entireTable);

	}
}
