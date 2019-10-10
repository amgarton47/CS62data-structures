import java.util.concurrent.RecursiveAction;


public class PMaxMin extends RecursiveAction{
    private static final int SEQUENTIAL_CUTOFF = 500;
    private float maxLong;
    private float maxLat;
    private float minLong;
    private float minLat;
    private CensusData data;
    private int low, high;
    
    public PMaxMin(CensusData data, int first, int last) {
        this.data = data;
        low = first;
        high = last;
    }
    
    public void compute() {
        if (high - low < SEQUENTIAL_CUTOFF) {
            findMaxMin();
        } else {
                PMaxMin left = new PMaxMin(data, low, (high + low) / 2);
                PMaxMin right = new PMaxMin(data, (high + low) / 2, high);
                left.fork();
                right.compute(); 
                left.join(); 
                setExtremes(left,right);
        }
    }

    private void setExtremes(PMaxMin left, PMaxMin right) {
        minLat = Math.min(left.minLatitude(),right.minLatitude());
        maxLat = Math.max(left.maxLatitude(),right.maxLatitude());
        minLong = Math.min(left.minLongitude(),right.minLongitude());
        maxLong = Math.max(left.maxLongitude(),right.maxLongitude());
    }

    private void findMaxMin() {
        CensusGroup first = data.get(low);
        minLat = first.getLatitude();
        maxLat = minLat;
        minLong = first.getLongitude();
        maxLong = minLong;
        for (int i = low+1; i < high; i++) {
            CensusGroup cg = data.get(i);
            float nextLat = cg.getLatitude();
            float nextLong = cg.getLongitude();
            
            minLat = Math.min(nextLat, minLat);
            maxLat = Math.max(nextLat, maxLat);
            minLong = Math.min(nextLong, minLong);
            maxLong = Math.max(nextLong, maxLong);
        }
    }


    public float maxLatitude() {
        return maxLat;
    }

    public float minLatitude() {
        return minLat;
    }

    public float maxLongitude() {
        return maxLong;
    }

    public float minLongitude() {
        return minLong;
    }
}
