/**
 * PAddPopGrids
 *  adds two PopulationGrids 2-d arrays.
 *  pre: both grids of same size;
 *  post: sum[i][j] = grid1[i][j] + grid2[i][j], for all i,j
 */

import java.util.concurrent.RecursiveAction;

public class PAddPopGrids extends RecursiveAction {
    private static final int SEQUENTIAL_CUTOFF = 500;

    private int[][] grid1, grid2, sum;

    private int low, high;
    
    private int numRows;
    private int numCols;
    
    /**
     * 
     * @param grid1
     * @param grid2
     * @param low -- lowest entry number
     * @param high -- largest entry number
     */
    public PAddPopGrids(int[][] grid1, int[][] grid2, int low, int high) { 
        this.grid1 = grid1;
        this.grid2 = grid2;
        
        this.low = low;
        this.high = high;
        
        // assume arrays were declared grid1[rows][cols]
        numRows = grid1.length;
        numCols = grid1[0].length;
        
        sum = new int[numRows][numCols];
    }
    
    
    @Override
    protected void compute() {
        if(high - low < SEQUENTIAL_CUTOFF) {
            sumGrids();
        } else {
            PAddPopGrids left = 
                    new PAddPopGrids(grid1, grid2, low, (high + low) /2);
            PAddPopGrids right = 
                    new PAddPopGrids(grid1, grid2, (high + low) /2, high);
            left.fork();
            right.compute(); 
            left.join(); 
        }
        
    }

    private void sumGrids() {
        for (int i = low; i < high; i++) {
            int row = i / numCols;
            int col = i % numCols;
            
            sum[row][col] = grid1[row][col] + grid2[row][col];
        }
    }

    public int[][] getGridSum() {
        return sum;
    }
}
