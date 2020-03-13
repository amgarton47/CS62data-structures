/** CompressedTable.java
 * Class representing efficient implementation of 2-dimensional table 
 * when lots of repeated entries. Idea is to record entry only when a 
 * value changes in the table as scan from left to right through 
 * successive rows.
 * @author Kim Bruce, 
 * @version 3/1/98, Revised for Java 5, 2/2007, revised for standard graphics 2/12
 * @param <ValueType> type of value stored in the table
 */
package compression;

class CompressedTable<ValueType> implements TwoDTable<ValueType> {

	 // List holding table entries
	protected CurDoublyLinkedList<Association<RowOrderedPosn, ValueType>> tableInfo; 

	protected int numRows, numCols; // Number of rows and cols in table

	/**
	 * Constructor for table of size rows x cols, all of whose values are
	 * initially defaultValue
	 * 
	 * @param rows
	 *            # of rows in table
	 * @param cols
	 *            # of columns in table
	 * @param defaultValue
	 *            initial value of all entries in table
	 */
	public CompressedTable(int rows, int cols, ValueType defaultValue) {
		tableInfo = new CurDoublyLinkedList<Association<RowOrderedPosn, ValueType>>();
		numRows = rows;
		numCols = cols;
		RowOrderedPosn firstPos = new RowOrderedPosn(0, 0, numRows, numCols);
		Association<RowOrderedPosn, ValueType> firstAssoc = 
				new Association<RowOrderedPosn, ValueType>(firstPos, defaultValue);
		tableInfo.addFirst(firstAssoc);
	}

	/**
	 * post: returns with current value of list being either findPos or the last
	 * elt in the list representation which comes before that entry
	 * 
	 * @param findPos
	 */
	private void find(RowOrderedPosn findPos) {
		tableInfo.first();
		Association<RowOrderedPosn, ValueType> entry = tableInfo.currentValue();
		RowOrderedPosn pos = entry.getKey();
		while (!findPos.less(pos)) {
			// search through list until pass elt looking for
			tableInfo.next();
			if (tableInfo.isOff()) {
				break;
			}
			entry = tableInfo.currentValue();
			pos = entry.getKey();
		}
		tableInfo.back(); // Since passed desired entry, go back to it.
	}

	/**
	 * pre: (row,col) is legal position in table post: value in table for
	 * (row,col) is newInfo
	 * 
	 * @param row
	 *            row of element to be updated
	 * @param col
	 *            column of element to be update
	 * @param newInfo
	 *            new value to place in slot (row, col)
	 */
	public void updateInfo(int row, int col, ValueType newInfo) {
		Association<RowOrderedPosn, ValueType> newEntry;
		boolean optimizeBefore = false;
		RowOrderedPosn posn = new RowOrderedPosn(row, col, numRows, numCols);
		find(posn);
		Association<RowOrderedPosn, ValueType> entry = tableInfo.currentValue();
		ValueType info = entry.getValue();
		// Add new info to list if necessary
		if (!newInfo.equals(info)) { // change in info so must change
			// something
			if (posn.equals(entry.getKey())) {
				// change in node already in list
				entry.setValue(newInfo);
				optimizeBefore = true;
			} else { // change to node not explicitly in list
				newEntry = new Association<RowOrderedPosn, ValueType>(posn,
						newInfo);

				tableInfo.addAfterCurrent(newEntry);
			}
			// At this point, newInfo is at current node
			// Worry about whether to add a node at successor position (which
			// may not be in list!)
			RowOrderedPosn successorPosn = posn.next();
			if (successorPosn != null) {
				tableInfo.next(); // Move to next elt to see if must add
				if (tableInfo.isOff()) { // Off end of list so must add
					handleEndOfList(successorPosn, info);
				} else { // Not at end of list
					handleNotAtEnd(successorPosn, info, newInfo);
				}
				// At this point current node is right after node with newInfo
				tableInfo.back(); // Node with new info is now current
			}
			if (optimizeBefore) { // if changed existing node see if can
				// delete predecessor
				optimizeBefore(newInfo);
			}

		}
	}

	/**
	 * pre: Current is now off of right end of list. I.e. current == null and
	 * offRight is true post: If the successor position is not past last
	 * position in table, then must add node at success position with info as
	 * value. Current is at elt corresponding to added node
	 * 
	 * @param successorPosn
	 *            position in table immediately after one just added (whether or
	 *            not in list representation)
	 * @param info
	 *            information that used to be held in predecessor position, but
	 *            has since been changed
	 */
	private void handleEndOfList(RowOrderedPosn successorPosn, ValueType info) {
		if (successorPosn != null) {
			tableInfo.back();
			Association<RowOrderedPosn, ValueType> newEntry = new Association<RowOrderedPosn, ValueType>(
					successorPosn, info);
			tableInfo.addAfterCurrent(newEntry);
		}
	}

	/**
	 * pre: Current is at node following node with new info post: compare next
	 * entry with the one that was just changed or added. If the successor
	 * position is not already in the list representation, then add node
	 * corresponding to successorPosn with oldInfo. Current node is at newly
	 * added node
	 * 
	 * @param successorPosn
	 *            position in table immediately after one just added (whether or
	 *            not in list representation)
	 * @param oldInfo
	 *            information that used to be held in predecessor position, but
	 *            has since been changed
	 * @param newInfo
	 *            information just added to new position before calling this
	 *            method
	 */
	private void handleNotAtEnd(RowOrderedPosn successorPosn,
			ValueType oldInfo, ValueType newInfo) {
		Association<RowOrderedPosn, ValueType> nextEntry = tableInfo
				.currentValue();
		tableInfo.back(); // Move back to where new entry was
		RowOrderedPosn nextPosn = nextEntry.getKey();
		if (!nextPosn.equals(successorPosn)) {
			// must add node to set next posn to oldInfo
			Association<RowOrderedPosn, ValueType> newEntry = new Association<RowOrderedPosn, ValueType>(
					successorPosn, oldInfo);
			tableInfo.addAfterCurrent(newEntry);
		} else {
			optimizeAfter(newInfo, nextEntry);
		}
	}

	/**
	 *  pre: At node with new info
	 *  post: drop next node if it has same info. Current set to node after the node
	 *  with newInfo
	 * @param newInfo
	 * 			information just added to new position before calling this
	 *            method
	 * @param nextEntry
	 * 			Next entry in list representing table
	 */
	private void optimizeAfter(ValueType newInfo,
			Association<RowOrderedPosn, ValueType> nextEntry) {
		ValueType nextValue = nextEntry.getValue();
		if (newInfo.equals(nextValue)) {
			// current node has same value as next node
			// drop the next node
			tableInfo.next();
			tableInfo.removeCurrent();
		} else {
			tableInfo.next();
		}
	}

	/**
	 *  pre: At node with new info
	 *  post: drop new node if preceding node has same info.
	 * Current set to node with newInfo covering the position added
	 * @param newInfo
	 * 		new value just entered in table
	 */
	private void optimizeBefore(ValueType newInfo) {
		tableInfo.back();
		if (!tableInfo.isOff()) {
			Association<RowOrderedPosn, ValueType> backEntry = tableInfo
					.currentValue();
			ValueType backInfo = backEntry.getValue();
			tableInfo.next();
			if (backInfo.equals(newInfo)) {
				tableInfo.removeCurrent();
				tableInfo.back(); // go back to node with new info
			}
		}
	}

	/**
	 *  pre: (row,col) is legal position in table
	 *  @param row
	 *  		a row of the table
	 *  @param col
	 *  		a column of the table
	 *  @return value stored in (row,col) entry of table
	 */
	public ValueType getInfo(int row, int col) {
		RowOrderedPosn posn = new RowOrderedPosn(row, col, numRows, numCols);
		find(posn);
		Association<RowOrderedPosn, ValueType> entry = tableInfo.currentValue();
		return entry.getValue();
	}

	/**
	 *  @return
	 *  		 succinct description of contents of table
	 */
	public String toString() {
		return tableInfo.otherString();
	}

	/**
	 * program to test implementation of ComptressedTable
	 * @param args
	 * 			ignored, as not used in main
	 */
	public static void main(String[] args) {
		CompressedTable<String> table = new CompressedTable<String>(5, 6, "x");
		System.out.println("table is " + table);
		table.updateInfo(0, 1, "a");
		System.out.println("table is " + table);
		table.updateInfo(0, 1, "x");
		System.out.println("table is " + table);

	}

}
