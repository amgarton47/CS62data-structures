package compression;

/** CompressedTable.java
 * Class representing efficient implementation of 2-dimensional table 
 * when lots of repeated entries. Idea is to record entry only when a 
 * value changes in the table as scan from left to right through 
 * successive rows.
 * @author Kim Bruce, 
 * @version 3/1/98, Revised for Java 5, 2/2007, revised for standard graphics 2/12
 * @param <ValueType> type of value stored in the table
 */

class CompressedTable<ValueType> implements TwoDTable<ValueType> {
	// List holding table entries - do not change
	protected CurDoublyLinkedList<Association<RowOrderedPosn, ValueType>> tableInfo; 

	// more instance variables

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

	}

	/**
	 * @post: returns with current value of list being either findPos or the last
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
	 * @pre: (row,col) is legal position in table
   * @post: value in table for * (row,col) is newInfo
	 * 
	 * @param row
	 *            row of element to be updated
	 * @param col
	 *            column of element to be update
	 * @param newInfo
	 *            new value to place in slot (row, col)
	 */
	public void updateInfo(int row, int col, ValueType newInfo) {

	}



	/**
	 *  @pre: (row,col) is legal position in table
	 *  @param row
	 *  		a row of the table
	 *  @param col
	 *  		a column of the table
	 *  @return value stored in (row,col) entry of table
	 */
	public ValueType getInfo(int row, int col) {

		return null;   // fix this!
	}

	/**
	 *  @return
	 *  		 succinct description of contents of table
	 */
	public String toString() { // do not change
	    return tableInfo.otherString();
	}

	/**
	 * program to test implementation of CompressedTable
	 * @param args
	 * 			ignored, as not used in main
	 */
	public static void main(String[] args) {
		
		// add your own tests to make sure your implementation is correct!!
		CompressedTable<String> table = new CompressedTable<String>(5, 6, "x");
		System.out.println("table is " + table);
		table.updateInfo(0, 1, "a");
		System.out.println("table is " + table);
		table.updateInfo(0, 1, "x");
		System.out.println("table is " + table);	
		
		// uncomment below for compatibility check with auto-grader
//		AutograderCompTest a = new AutograderCompTest();
//		a.testCurDoublyLinkedList();
//		a.testCompressedTable();

	}

}
