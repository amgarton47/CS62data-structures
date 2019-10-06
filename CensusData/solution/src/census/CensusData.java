import java.util.ArrayList;


/** 
 *  just a resizing array for holding the input
 *  
 *  note: array may not be full; see data_size field
 *  
 *  @author: Your Name Here
 */

public class CensusData {
    public static final int INITIAL_SIZE = 100;
    public ArrayList<CensusGroup> data = new ArrayList<CensusGroup>(INITIAL_SIZE);
        
    public void add(int population, float latitude, float longitude) {
        CensusGroup g = new CensusGroup(population,latitude,longitude); 
        data.add(g);
    }
    
    public int length() {
        return data.size();
    }
    
    public CensusGroup get(int i) {
        return data.get(i);
    }
}
