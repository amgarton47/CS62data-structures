package matrix;

public class Tests {
	/**
	 * Check the getting and setting capabilities of the matrix
	 * 
	 * @param m the matrix
	 * @param rows the number of rows that are supposed to be in the matrix
	 * @param cols the number of columns that are supposed to be in the matrix
	 */
	public static void checkGetSet(Matrix<Integer> m, int rows, int cols) {
		boolean failed = false;

		try {
			// set the entry
			for( int i = 0; i < rows; i++ ) {
				for( int j = 0; j < cols; j++ ) {
					m.set(i,j,1);
				}
			}
		} catch( Exception e ) {
			System.err.println("Bad set. Exception: " + e);
			failed = true;
		}

		try {
			// set the entry
			for( int i = 0; i < rows; i++ ) {
				for( int j = 0; j < cols; j++ ) {
					if( m.get(i,j) != 1 ) {
						System.err.println("Found bad entry: (" + i + ", " + j);
						failed = true;
					}
				}
			}
		} catch( Exception e ) {
			System.err.println("Bad get. Exception: " + e);
			failed = true;	
		}

		if( !failed ) {
			System.out.println("checkGetSet: PASSED");
		} else {
			System.out.println("checkGetSet: FAILED");
		}
	}
	
	/**
	 * Check to make sure that off by one errors outside the accessible
	 * range are handled properly by the matrix.
	 * 
	 * @param m
	 * @param rows
	 * @param cols
	 */
	public static void rangeCheck(Matrix<Integer> m, int rows, int cols) {
		boolean failed = false;


		// check off by one errors in the rows
		int[] badVals = {-1, rows};

		for( int i = 0; i < badVals.length; i++ ) {
			try {
				m.get(badVals[i], 0);
				System.err.println("Bad row error checking for: " + badVals[i]);
				failed = true;
			} catch( IllegalArgumentException e ) {
				// we should get an illegal argument
			} catch( Exception e ) {
				failed = true;
				System.err.println("Bad row error checking for: " + badVals[i]);
				System.err.println("Received error: " + e);
			}
		}

		// check off by one errors in the rows
		badVals[1] = cols;

		for( int i = 0; i < badVals.length; i++ ) {
			try {
				m.get(0, badVals[i]);
				System.err.println("Bad col error checking for: " + badVals[i]);
				failed = true;
			} catch( IllegalArgumentException e ) {
				// we should get an illegal argument
			} catch( Exception e ) {
				failed = true;
				System.err.println("Bad col error checking for: " + badVals[i]);
				System.err.println("Received error: " + e);
			}
		}

		if( !failed ) {
			System.out.println("rangeCheck: PASSED");
		} else {
			System.out.println("rangeCheck: FAILED");
		}
	}
	
	public static void testBadMatrix() {
		checkGetSet(new BadMatrix<Integer>(3, 3), 3, 3);
		rangeCheck(new BadMatrix<Integer>(3, 3), 3, 3);
		checkGetSet(new BadMatrix<Integer>(2, 3), 2, 3);
		rangeCheck(new BadMatrix<Integer>(2, 3), 2, 3);
	}

	public static void testBadMatrix2() {
		checkGetSet(new BadMatrix2<Integer>(3, 3), 3, 3);
		rangeCheck(new BadMatrix2<Integer>(3, 3), 3, 3);
		checkGetSet(new BadMatrix2<Integer>(2, 3), 2, 3);
		rangeCheck(new BadMatrix2<Integer>(2, 3), 2, 3);
	}
	
	public static void testBadMatrix3() {
		checkGetSet(new BadMatrix3<Integer>(3, 3), 3, 3);
		rangeCheck(new BadMatrix3<Integer>(3, 3), 3, 3);
		checkGetSet(new BadMatrix3<Integer>(2, 3), 2, 3);
		rangeCheck(new BadMatrix3<Integer>(2, 3), 2, 3);
	}
	
	public static void main(String[] args) {
		testBadMatrix();
	}
}
