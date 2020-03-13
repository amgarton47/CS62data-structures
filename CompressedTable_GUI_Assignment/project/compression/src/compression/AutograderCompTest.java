package compression;

/**
 * Helper class to check compatibility between student's implementation and
 * auto-grader
 * 
 * @author Sean Zhu
 *
 */
public class AutograderCompTest {
	public AutograderCompTest() {

	}

	/**
	 * method that tests compatibility of student's implementation of
	 * CurDoublyLinkedList and the auto-grader.
	 * 
	 * @post print success or failure message
	 */
	public static void testCurDoublyLinkedList() {
		try {
			CurDoublyLinkedList<Integer> list = new CurDoublyLinkedList<Integer>();
			list.add(1);
			if (list.current == null) {
				System.out
				.println("CurDoublyLinkedList compatibility test failed.");
				System.out
				.println("Current is null.");
				return;
			}
			list.first();
			list.last();
			list.next();
			list.back();
			list.isOffRight();
			list.isOffLeft();
			list.isOff();
			list.currentValue();
			list.addAfterCurrent(2);
			list.removeCurrent();
			list.addFirst(0); // implemented
			list.removeFirst();
			list.addLast(3); // implemented
			list.removeLast();
			list.getFirst(); // implemented
			list.getLast(); // implemented
			list.clear(); // half-implemented
			list = new CurDoublyLinkedList<Integer>();
			list.add(1);
			list.toString(); // implemented
			String expected = "CurDoublyLinkedList:\n" + "1\n";
			if (expected.equals(list.otherString())) {
				System.out
						.println("CurDoublyLinkedList compatibility test passed.");
			} else {
				System.out
						.println("CurDoublyLinkedList compatibility test failed.");
				System.out
						.println("otherString() method might have been modified.");
			}
		} catch (Exception | Error e) {
			System.out.println("CurDoublyLinkedList compatibility test failed."
					+ e);

		}
	}

	/**
	 * method that tests compatibility of student's implementation of
	 * CompressedTable and the auto-grader.
	 * 
	 * @post print success or failure message
	 */
	public static void testCompressedTable() {
		try {
			int numRow = 5;
			int numCol = 6;
			CompressedTable<Integer> table = new CompressedTable<Integer>(
					numRow, numCol, 0);
			table.getInfo(0, 0);
			table.updateInfo(1, 1, 1);
			if (table.tableInfo != null) {
				System.out
						.println("CompressedTable compatibility test passed.");
			} else {
				System.out
						.println("CompressedTable compatibility test failed.");
				System.out
						.println("Make sure to use the protected instance variable tableInfo.");
			}
		} catch (Exception | Error e) {
			System.out
					.println("CompressedTable compatibility test failed." + e);
		}
	}

	public static void main(String[] args) {
		AutograderCompTest a = new AutograderCompTest();
		a.testCurDoublyLinkedList();
		a.testCompressedTable();

	}
}
