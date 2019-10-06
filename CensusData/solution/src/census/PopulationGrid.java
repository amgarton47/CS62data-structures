/**
 * Maintain the population data as a grid for the queries
 * 
 * @author kpcoogan
 */
import java.util.concurrent.ForkJoinPool;

public class PopulationGrid {
    // the 2-d array that holds the populations of each rectangle
    private int[][] popGrid;
    private int rows;
    private int cols;
    
    // row and col sizes for table
    float rowSize, colSize;
    
    // run the code to warm up JVM for timing
    private static final int NUM_WARMUP = 20;
    
    // save this many timings for averaging
    private static final int NUM_SAVED = 10;
    private long[] mmTimings = new long[NUM_SAVED]; //calc min/max
    private long[] bTimings = new long[NUM_SAVED];  //build table
    private long[] qTimings = new long[NUM_SAVED];  //last query
    
    // offset makes rows and columns slightly bigger so that we don't
    // caclulate a row that is 1 too big, or 1 too small due to roundoff
    private static final float EPSILON = 0.00001f;
    
    // the average time to build/preprocess the grid
    private double averagePPNanos;
    
    // the average time of the last query
    private double averageQNanos;
    
    // the CensusData used to build the grid
    private CensusData data;
    
    // what versions to use depend on parallel/seq and hard/easy
    private boolean parallel;
    private boolean hard;
    
    // the extreme values for longitude and latitude
    private float maxLong, minLong, maxLat, minLat;
    
    /**
     * PopulationGrid -- holds a representation of the population of the US
     * Census data that can be used to answer queries
     * 
     * Constructor does all pre-processing required to enable queries
     * Queries are called separately by constructing class.
     * 
     * @param data CensusData in linear array
     * @param rows associated with y and latitude
     * @param cols associated with x and longitude
     * @param parallel boolean indicating whether parallel processing is
     *      requested
     */
    public PopulationGrid(CensusData data, int rows, int cols, boolean parallel, boolean hard) {
        this.data = data;
        this.popGrid = new int [cols][rows];
        this.rows = rows;
        this.cols = cols;
        this.parallel = parallel;
        this.hard = hard;


        if(parallel) {
            calcMinMaxLongLatPar();
            if(hard) {
                buildPopGridPar();
            }
        } else {
            calcMinMaxLongLatSeq();
            if(hard) {
                buildPopGridSeq();
            }
        }
        
        // offset min and max values so float comparisons work well. 
        //maxLong -= EPSILON;
        //minLong += EPSILON;
        //maxLat -= EPSILON;
        //minLat += EPSILON;        
        
        calcPreProcessNanos();
    }
    
    public int populationQuery(int startRow, int endRow, int startCol, int endCol) {
        assert (startRow >= 1) &&
                (endRow >= startRow) &&
                (startCol >= 1) &&
                (endCol >= startCol) &&
                (endRow <= rows) &&
                (endCol <= cols);
        
        int population = 0;
        
        if(hard) {
            population = popQueryConst(startRow, endRow, startCol, endCol);
        } else if(!parallel) {
            population = popQueryEasySeq(startRow, endRow, startCol, endCol);
        } else {
            population = popQueryEasyPar(startRow, endRow, startCol, endCol);
        }
        
        return population;
    }

    /**
     * calcMinMaxLongLatSeq()
     *  calculates the min/max long/lat values sequentially
     *  by linearly walking the data and searching for extreme values.
     */
    private void calcMinMaxLongLatSeq() {
        for (int reps = 0; reps < (NUM_WARMUP + NUM_SAVED); reps++) {
            long last = System.nanoTime();

            CensusGroup firstGroup = data.get(0);
            maxLong = firstGroup.getLongitude();
            minLong = maxLong;
            maxLat = firstGroup.getLatitude();
            minLat = maxLat;
            
            for (int i = 0; i < data.length(); i++) {
                CensusGroup cg = data.get(i);
                float nextLong = cg.getLongitude();
                float nextLat = cg.getLatitude();
                maxLong = Math.max(maxLong, nextLong);
                minLong = Math.min(minLong, nextLong);
                maxLat = Math.max(maxLat, nextLat);
                minLat = Math.min(minLat, nextLat);
            }

            mmTimings[reps % NUM_SAVED] = System.nanoTime() - last;
        }

        rowSize = (maxLat - minLat) / rows + EPSILON;
        colSize = (maxLong - minLong) / cols + EPSILON;
    }
    
    /**
     * popQueryEasySeq
     *  perform population query sequentially, and by linearly walking 
     *  the data for groups that should be included.     
     */
    public int popQueryEasySeq(int startRow, int endRow, 
                                int startCol, int endCol) {
        //total population
        int population = 0;
        
        for(int reps = 0; reps < (NUM_WARMUP + NUM_SAVED); reps++) {
            long last = System.nanoTime();
            
            population = 0;

            for (int i = 0; i < data.length(); i++) {
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
            
            qTimings[reps % NUM_SAVED] = System.nanoTime() - last;
        }
        calcQueryNanos();
        
        return (population);
    }
    
    /**
     * buildPopGridSeq
     *  Sequentially build aggregate 2-d table that represents population
     *  of each rectangle as that rectangles population plus all populations
     *  to left and above current rectangle.
     */
    public void buildPopGridSeq() {
       
        for (int reps = 0; reps < (NUM_WARMUP + NUM_SAVED); reps++) {
            long last = System.nanoTime();

            //create a new grid (initialized thanks to java) each time
            popGrid = new int[cols][rows];
            
            // initial O(n) pass to calculate populations of each grid location
            for (int i = 0; i < data.length(); i++) {
                CensusGroup cg = data.get(i);
                float nextLat = cg.getLatitude();
                float nextLong = cg.getLongitude();
                
                //calculate the row and col of the grid, use 1..n numbering
                //for "user-friendliness"
                int row = (int) Math.floor((nextLat - minLat)/rowSize);
                int col = (int) Math.floor((nextLong - minLong)/colSize);
    
                popGrid[col][row] += cg.getPopulation();         
            }
        
            bTimings[reps % NUM_SAVED] = System.nanoTime() - last;
            
        }
        
        aggregateGrid();
    }
    
        
    /** second O(xy) pass to calculate aggregate populations
     * we'll start in top left corner, completing each row as we move
     * down the table
     * 
     * use popGrid[i][j] = popGrid[i][j] + popGrid[i-1][j] 
     *                      + popGrid[i][j+1] - popGrid[i-1][j+1]
     * where location 0,0 is considered bottom-left of table
     */
    private void aggregateGrid() {
        for (int j = rows - 1; j >= 0; j--) {
            for (int i = 0; i < cols; i++) {
                if(i > 0) {
                    popGrid[i][j] += popGrid[i-1][j];
                }
                if(j < rows - 1) {
                    popGrid[i][j] += popGrid[i][j+1];
                }
                if((i > 0) && (j < rows - 1)) {
                    popGrid[i][j] -= popGrid[i-1][j+1];
                }
            }
        }
    }
    
    
    /**
     * popQueryConst
     *  perform population query in constant time using previously created
     *  popGrid with aggregate populations.
     *  
     *  NOTE: row and col values are coming in using "user-friendly" 1..n
     *  numbering. popGrid is a 2-d array using 0..(1-n) numbering
     *  
     *  Also, we assume that bottom-left corner of the table is the (0,0) pt.
     *  Our equation states that the pop is 
     *      the bottom right corner of query rect
     *      minus the value above the top right corner
     *      minus the value to the left of the bottom left corner
     *      plus the value above and left of the top-left corner
     *  
     *  The bottom right corner is [endCol-1][startRow-1]
     *  The top right corner is [endCol-1][endRow-1]
     *  The bottom left corner is [startCol-1][startRow-1]
     *  The top left corner is [startCol-1][endRow-1]
     */
    public int popQueryConst(int startRow, int endRow, 
                                int startCol, int endCol) {

        int population = 0;
        
        for(int reps = 0; reps < (NUM_WARMUP + NUM_SAVED); reps++) {
            long last = System.nanoTime();
            
            population = popGrid[endCol - 1][startRow - 1];
            if(endRow < rows) {
                population -= popGrid[endCol-1][endRow];
            }
            if((startCol - 2) >= 0) {
                population -= popGrid[startCol - 2][startRow - 1];
            }
            if((endRow < rows) && ((startCol - 2) >= 0)) {
                population += popGrid[startCol - 2][endRow];
            }
            
            qTimings[reps % NUM_SAVED] = System.nanoTime() - last;
        }
        calcQueryNanos();
        
        return population;
    }
    
    /**
     * calcMinMaxLongLatPar()
     *  calculates the min/max long/lat values in parallel
     */
    private void calcMinMaxLongLatPar() {
        ForkJoinPool fjPool = new ForkJoinPool();
        for (int reps = 0; reps < (NUM_WARMUP + NUM_SAVED); reps++) {
            long last = System.nanoTime();
            PMaxMin maxmin = new PMaxMin(data, 0, data.length());
            fjPool.invoke(maxmin);
            maxLong = maxmin.maxLongitude();
            minLong = maxmin.minLongitude();
            maxLat = maxmin.maxLatitude();
            minLat = maxmin.minLatitude();
        
            mmTimings[reps % NUM_SAVED] = System.nanoTime() - last;
        }

        rowSize = (maxLat - minLat) / rows + EPSILON;
        colSize = (maxLong - minLong) / cols + EPSILON;
    }
    
    /**
     *      
     */
    public int popQueryEasyPar(int startRow, int endRow, 
                                int startCol, int endCol) {
        ForkJoinPool fjPool = new ForkJoinPool();
        
        //total population
        int population = 0;
        
        for(int reps = 0; reps < (NUM_WARMUP + NUM_SAVED); reps++) {
            long last = System.nanoTime();            
            PPopQuery popQuery = new PPopQuery(data, 0, data.length(), 
                    startRow, endRow, startCol, endCol, 
                    maxLat, minLat, maxLong, minLong, rowSize, colSize);
            fjPool.invoke(popQuery);
            population = popQuery.getPopulation();

            qTimings[reps % NUM_SAVED] = System.nanoTime() - last;
        }
        calcQueryNanos();
        
        return (population);
    }

    
    /**
     * buildPopGridSeq
     *  Sequentially build aggregate 2-d table that represents population
     *  of each rectangle as that rectangles population plus all populations
     *  to left and above current rectangle.
     */
    public void buildPopGridPar() {
        
        // initial O(n) pass to calculate populations of each grid location
        for(int reps = 0; reps < (NUM_WARMUP + NUM_SAVED); reps++) {
            long last = System.nanoTime();            
            ForkJoinPool fjPool = new ForkJoinPool();
            PBuildGrid pbg = new PBuildGrid(data, cols, rows, 0, data.length(),
                minLong, minLat, colSize, rowSize);
            fjPool.invoke(pbg);
            popGrid = pbg.getPopGrid();

            bTimings[reps % NUM_SAVED] = System.nanoTime() - last;
        }
        aggregateGrid();
    }

    
    
    /**
     * calculates the average pre-processing time (in nanoseconds) 
     */
    private void calcPreProcessNanos() {
        long sum = 0;
        for(int i = 0; i < NUM_SAVED; i++) {
            sum = sum + mmTimings[i] + bTimings[i];
        }
        
        averagePPNanos = (double) sum / NUM_SAVED;        
    }
    
    /**
     * getAverageSecs
     * @return the calculated average pre-processing time in seconds 
     */
    public double getPreprocessAverageSecs() {
        return averagePPNanos / 1e9;
    }

    /**
     * calcQueryNanos
     * calcualates the average time of the last query (in nanoseconds)
     */
    private void calcQueryNanos() {
        long sum = 0;
        for (int i = 0; i < NUM_SAVED; i++) {
            sum = sum + qTimings[i];
        }
        
        averageQNanos = (double) sum / NUM_SAVED;
    }
    
    /**
     * getLastQueryAverageSecs()
     * @return the calculated average time of the last query in seconds
     */
    public double getLastQueryAverageSecs() {
        return averageQNanos / 1e9;
    }
    
    /**
     * getters and setters
     */
    public float getMaxLatitude() {
        return maxLat;
    }

    public float getMinLatitude() {
        return minLat;
    }

    public float getMaxLongitude() {
        return maxLong;
    }

    public float getMinLongitude() {
        return minLong;
    }
    
}
