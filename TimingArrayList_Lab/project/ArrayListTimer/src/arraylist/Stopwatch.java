package arraylist;

/**
 *  A stopwatch class for measuring elapsed time
 *
 */


public class Stopwatch { 
    private long start = -1;

    /**
     * Starts the stopwatch.  If start is called and the stopwatch
     * has already been started, the start time is reset to the
     * new start time.
     */
    public void start() {
        start = System.currentTimeMillis();
    }

    /**
     * Returns the elapsed time (in seconds) since the stopwatch was started.
     *
     * @return elapsed time (in seconds) since the stopwatch was created
     */
    public double elapsedTime() {
    	if( start == -1 ) {
    		System.err.println("Warning: elapsedTime called without starting stopwatch");
    		return 0;
    	}else {    	
    		long now = System.currentTimeMillis();
    		return (now - start) / 1000.0;
    	}
    }
    
    
}