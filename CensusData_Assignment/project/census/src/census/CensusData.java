package census;
/** 
 *  just a resizing array for holding the input
 *  
 *  note: array may not be full; see data_size field
 *  
 *  @author: Your Name Here
 */

public class CensusData {
	private static final int INITIAL_SIZE = 100;
	private CensusGroup[] data;
	private int data_size;
	
	public CensusData() {
		data = new CensusGroup[INITIAL_SIZE];
		data_size = 0;
	}
	
	public void add(int population, float latitude, float longitude) {
		if(data_size == data.length) { // resize
			CensusGroup[] new_data = new CensusGroup[data.length*2];
			for(int i=0; i < data.length; ++i)
				new_data[i] = data[i];
			data = new_data;
		}
		CensusGroup g = new CensusGroup(population,latitude,longitude); 
		data[data_size++] = g;
	}
}
