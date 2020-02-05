package matrix;


import java.util.ArrayList;

// Class representing a two dimensional square matrix
public class BadMatrix2<E> extends Matrix<E>{
	// representation of matrix as a lists of lists
	private ArrayList<ArrayList<E>> rep;

	// number of rows and columns in the matrix
	private int numRows, numCols;
	
	//Create an empty matrix
	public BadMatrix2() {
		this(0,0);
	}

	// Create a new matrix with numRows rows and numCols columns
	public BadMatrix2(int numRows, int numCols) {
		this.numRows = numRows;
		this.numCols = numCols;
		
		rep = new ArrayList<ArrayList<E>>(numRows);
		
		for (int row = 0; row < numRows; row++) {
			rep.add(new ArrayList<E>(numCols));
			
			for (int col = 0; col < numCols; col++) {
				rep.get(row).add(null);
			}
		}
	}

	// Set the entry at (row,col) to newValue.
	public void set(int row, int col, E newValue) {
		checkRowCol(row, col);
		rep.get(row).set(col, newValue);
	}

	// Return the entry at (row,col)
	public E get(int row, int col) {
		checkRowCol(row, col);
		return rep.get(row).get(col);
	}
	
	private void checkRowCol(int row, int col) {
		if (row < 0 || row > numRows) {
			throw new IllegalArgumentException("Row out of bounds");
		}
		
		if (col < 0 || col > numCols) {
			throw new IllegalArgumentException("Col out of bounds");
		}
	}

	// Return the number of rows in the matrix
	public int getNumRows() {
		return numRows;
	}

	// Return the number of columns in the matrix
	public int getNumCols() {
		return numCols;
	}
}
