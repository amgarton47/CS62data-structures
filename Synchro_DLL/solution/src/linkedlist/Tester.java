package ArrayList;

public class Tester extends Thread {
	
	private static final int NUM_THREADS = 10;
	private static final int NUM_COPIES = 32;
	
	private ArrayList<Integer> myList;	// list I operate in
	private int myValue;					// value I store
	private int myCount;					// number of times to store it
	
	public Tester (ArrayList<Integer> testList, int myValue, int myCount) {
		super(Integer.toString(myValue));
		this.myList = testList;	
		this.myValue = myValue;
		this.myCount = myCount;
	}
	
	public void run() {
		// add the specified number of copies of my value
		for(int i = 0; i < myCount; i++)
			myList.add(myValue);
		
		// count them
		int found = 0;
		for(int i = 0; i < myList.size(); i++)
			if (myList.get(i) == myValue)
				found++;
		
		// report
		System.out.println("Tester(" + Thread.currentThread().getName() +
							") found " + found + "/" + myCount);
	}

	public static void main(String[] args) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		Tester testers[] = new Tester[NUM_THREADS];
		
		for(int i = 0; i < NUM_THREADS; i++) {
			testers[i] = new Tester(list, 666 * (i+1), NUM_COPIES);
			testers[i].start();
		}
		
		// wait for all threads to exit
		int alive;
		do {
			alive = 0;
			for(int i = 0; i < NUM_THREADS; i++)
				if (testers[i].isAlive())
					alive++;
		} while(alive > 0);
		
		System.out.println("All " + NUM_THREADS + " test threads have terminated");
	}
}