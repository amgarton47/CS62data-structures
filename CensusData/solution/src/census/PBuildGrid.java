import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class PBuildGrid extends RecursiveAction {
    private final static int SEQUENTIAL_CUTOFF = 500;
    private int high, low;
    private int cols, rows;
    private float minLong, minLat;
    private double colSize, rowSize;
    
    private CensusData data;
    
    private int[][] popGrid;
    
    public PBuildGrid(CensusData data, int cols, int rows, int low, int high,
            float minLong, float minLat, double colSize, double rowSize) {
        this.data = data;
        this.high = high;
        this.low = low;
        this.cols = cols;
        this.rows = rows;
        this.minLong = minLong;
        this.minLat = minLat;
        this.colSize = colSize;
        this.rowSize = rowSize;
        
        popGrid = new int[cols][rows];
    }
    
    @Override
    protected void compute() {
        if (high - low < SEQUENTIAL_CUTOFF) {
            buildGrid();
        } else {
            PBuildGrid left = new PBuildGrid(data, cols, rows, 
                    low, (high + low) / 2, minLong, minLat, colSize, rowSize);
            PBuildGrid right = new PBuildGrid(data, cols, rows, 
                    (high + low) / 2, high, minLong, minLat, colSize, rowSize);
            left.fork();
            right.compute();
            left.join();
            
            // Add grids sequentially
            gridAdd(right.getPopGrid(), left.getPopGrid());
            
            // OR Add grids in parallel
            /*
            int gridSize = left.getPopGrid().length * left.getPopGrid()[0].length;
            
            ForkJoinPool fjPool = new ForkJoinPool();
            PAddPopGrids papg = new PAddPopGrids(left.getPopGrid(), 
                    right.getPopGrid(), 0, gridSize);
            fjPool.invoke(papg);
            popGrid = papg.getGridSum();
            */            
        }      
    }
    
    private void buildGrid() {
        for (int i = low; i < high; i++) {
            CensusGroup cg = data.get(i);
            float nextLat = cg.getLatitude();
            float nextLong = cg.getLongitude();
            
            //calculate the row and col of the grid, use 1..n numbering
            //for "user-friendliness"
            int row = (int) Math.floor((nextLat - minLat)/rowSize);
            int col = (int) Math.floor((nextLong - minLong)/colSize);
            
            popGrid[col][row] += cg.getPopulation();
        }
    }
    
    
    private void gridAdd(int[][] grid1, int[][] grid2) {
        for (int i = 0; i < grid1.length; i++) {
            for (int j = 0; j < grid1[0].length; j++) {
                popGrid[i][j] = grid1[i][j] + grid2[i][j];
            }
        }
    }
    
    /**
     * getters and setters
     */
    public int[][] getPopGrid() {
        return popGrid;
    }
}
