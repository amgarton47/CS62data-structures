import java.util.ArrayList;

// Class representing a two dimensional square matrix
// Written 3/11/07 by Kim Bruce
public class Matrix<E> {
	// representation of matrix as a lists of lists
	private ArrayList<ArrayList<E>> rep;
	
	// number of rows and columns in the matrix
	private int numRows, numCols;
	
	// Create a new matrix with numRows rows and numCols columns
	public Matrix(int numRows,int numCols) {
		this.numRows = numRows;
		this.numCols = numCols;
		rep = new ArrayList<ArrayList<E>>(numRows);
		for (int row = 0; row < numRows; row++) {
			rep.add(new ArrayList<E>(numCols));
			for (int col = 0; col < numCols; col++){
				rep.get(row).add(null);
			}
		}
	}
	
	// Set the entry at (row,col) to newValue.
	public void set(int row, int col, E newValue) {
		rep.get(row).set(col, newValue);
	}
	
	// Return the entry at (row,col)
	public E get(int row, int col){
		return rep.get(row).get(col);
	}
	
	// Return the number of rows in the matrix
	public int numRows() {
		return numRows;
	}

	// Return the number of columns in the matrix
	public int numCols() {
		return numCols;
	}

}

