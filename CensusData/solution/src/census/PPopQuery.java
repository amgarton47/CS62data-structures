import java.util.concurrent.RecursiveAction;


public class PPopQuery extends RecursiveAction{
    private static final int SEQUENTIAL_CUTOFF = 500;
    private int population;

    private int startRow, endRow, startCol, endCol;
    private float rowSize, colSize;
    
    private float maxLat, minLat, maxLong, minLong;
    
    private CensusData data;
    private int low, high;
    
    public PPopQuery(CensusData data, int first, int last, 
            int startRow, int endRow, int startCol, int endCol,
            float maxLat, float minLat, float maxLong, float minLong, 
            float rowSize, float colSize) {
        this.data = data;
        low = first;
        high = last;
        
        this.startRow = startRow;
        this.endRow = endRow;
        this.startCol = startCol;
        this.endCol = endCol;
        
        this.maxLat = maxLat;
        this.minLat = minLat;
        this.maxLong = maxLong;
        this.minLong = minLong;
        
        this.rowSize = rowSize;
        this.colSize = colSize;


    }
    
    public void compute() {
        if (high - low < SEQUENTIAL_CUTOFF) {
            findPopulation();
        } else {
            PPopQuery left = new PPopQuery(data, low, (high + low) / 2,
                    startRow, endRow, startCol, endCol,
                    maxLat, minLat, maxLong, minLong,
                    rowSize, colSize);
            PPopQuery right = new PPopQuery(data, (high + low) / 2, high,
                    startRow, endRow, startCol, endCol,
                    maxLat, minLat, maxLong, minLong,
                    rowSize, colSize);
                left.fork();
                right.compute(); 
                left.join(); 
                population = right.getPopulation() + left.getPopulation();
        }
    }

    private void findPopulation() {
        population = 0;
        
        for (int i = low; i < high; i++) {
            CensusGroup cg = data.get(i);
            float nextLat = cg.getLatitude();
            float nextLong = cg.getLongitude();
            
            //calculate the row and col of the grid, use 1..n numbering
            //for "user-friendliness"
            int row = (int) Math.floor((nextLat - minLat)/rowSize) + 1;
            int col = (int) Math.floor((nextLong - minLong)/colSize) + 1;
            
            if(row >= startRow && row <= endRow &&
                    col >= startCol && col <= endCol) {
                population += cg.getPopulation();
            }            
        }
    }
    
    public int getPopulation() {
        return population;
    }
}
