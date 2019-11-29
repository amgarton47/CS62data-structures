package linkedlist;

public class Tester extends Thread {
	
	// how many tests to run
	private static final int MAX_CYCLES = 16;
	private static final int NUM_THREADS = 16;
	private static final int NODES_PER_THREAD = 32;
	
	private TestNode myNodes[];	// all possible list nodes
	private TestNode myList;	// DLL list header
	private static boolean stop;	// testing has ended
	private static int debug;	// level of debug output
		private static final int DBG_CYCLES = 1;
		private static final int DBG_WAITS = 2;
	
	// command to run this program
	private static final String runMe = "java linkedlist.Tester";
	
	// prefix for all audit failures
	private static final String error = "    ERROR: ";
	
	/**
	 * note the list head and which elements are ours
	 * @param list ... DLL list head
	 * @param myNodes ... array of nodes for this Tester
	 */
	public Tester (TestNode list, TestNode myNodes[]) {
		this.myList = list;
		this.myNodes = myNodes;
	}
	
	/**
	 * perform a series of insertions and deletions 
	 * on a set of per-thread nodes in a shared list.
	 */
	public void run() {
		int len = myNodes.length;
		int cycles = 0;		// for even-odd alternation
		String myName = "thread " + myNodes[0].value;
		
		while(!stop) {	// continue testing until we are killed
			if ((cycles++ % 2) == 0) {
				if (debug >= DBG_CYCLES)
					System.out.println(myName + " starting inserts");
				/*
				 * add half of my not-yet-in-list nodes to the list
				 */
				int candidates = 0;
				for(int i = 0; i < len; i++) {
					if (myNodes[i].in_list)
						continue;	// already in the list
					
					if (candidates++ %2 == 1)
						continue;	// only every other candidate
					
					myNodes[i].insert(myList);
					myNodes[i].in_list = true;
					Thread.yield(); 	// let someone else run
				}
			} else {
				/*
				 * remove half of my currently-in-list nodes
				 */
				if (debug >= DBG_CYCLES)
					System.out.println(myName + " starting removes");
				int candidates = 0;
				for(int i = 0; i < len; i++) {
					if (!myNodes[i].in_list)
						continue;	// not in the list
					
					if (candidates++ %2 == 1)
						continue;	// only every other candidate
					
					myNodes[i].remove();
					myNodes[i].in_list = false;
					Thread.yield(); // let someone else run
				}
			}
			
			// see if we have been told to stop
			if (stop)
				break;
			
			// wait for audit to complete
			if (debug >= DBG_WAITS)
				System.out.println(myName + " waiting");
			synchronized (this) {
				try {
					this.wait();
				} catch (InterruptedException e) {
					System.err.println(error + "Interrupted wait");
				}
			}
		}
		
		if (debug >= DBG_CYCLES)
			System.out.println(myName + " exiting");
	}
	
	/**
	 * notify all sleeping test threads
	 * 
	 * @param threads ... list of threads
	 */
	private static void wake_all(Thread[] threads) {
		for(int i = 0; i < threads.length; i++) {
			if (threads[i] == null)
				continue;
			if (debug >= DBG_WAITS)
				System.out.println("wake " + threads[i].getName());
			synchronized(threads[i]) {
				threads[i].notify();
			}
		}
	}
	
	/**
	 * wait for all threads to block or terminate
	 * 
	 * @param threads
	 */
	private static void wait_all(Thread[] threads) {
		int cycles = 0;
		int running;
		do {
			running = 0;
			for (Thread t: threads) {
				if (t == null)
					continue;
				Thread.State s = t.getState();
				if (s != Thread.State.WAITING && s != Thread.State.TERMINATED) {
					running += 1;
					if (cycles++ > 0 && debug > DBG_WAITS)
						System.out.println(" ... waiting for " + t.getName());
				}
			}
			Thread.yield();
		} while (running > 0);
	}
	
	/**
	 * audit the consistency of the list
	 * 	next/prev pointer consistency
	 * 	correct nodes in (and not in) the list
	 * 	list length
	 * 
	 * @param list	header for the list to be audited
	 * @param nodes	array[][] of all known nodes
	 * @return boolean (did we pass)
	 */
	private static int audits = 0;
	public static boolean audit(TestNode list, TestNode[][] nodes) {
		
		// note expected size and reset all found flags
		int expected = 0;	// expected nodes in list
		int possible = 0;	// total possible nodes
		for(int i = 0; i < nodes.length; i++)
			for(int j = 0; j < nodes[i].length; j++) {
				nodes[i][j].found = false;
				possible += 1;
				if (nodes[i][j].in_list)
					expected += 1;
			}
		
		System.out.println("Audit #" + ++audits + ":");
		
		// enumerate the list
		int found = 0;
		int errors = 0;
		for(TestNode current = (TestNode) list.next; 
				current != list; 
				current = (TestNode) current.next) {
			
			// check for breaks in the list
			if (current == null) {
				System.err.println(error + "null next pointer");
				errors += 1;
				break;
			}
			// check prev pointer consistency
			TestNode prev = (TestNode) current.prev;
			
			if (prev == null) {
				System.err.println(error + current.value +
						".prev == NULL");
				errors += 1;
				break;
			} else {	// does my prev point to me
				TestNode prev_next = (TestNode) prev.next;
				if (prev_next == null) {
					System.err.println(error + prev.value +
								".next=NULL");
					errors += 1;
				} else if (prev_next != current) {
					System.err.println(error + current.value + 
									".prev=" + prev.value + ", " +
									prev.value + ".next=" + prev_next.value);
					errors += 1;
				}
			}
			
			// check next pointer consistency
			TestNode next = (TestNode) current.next;
			if (next == null) {
				System.err.println(error + current.value +
						".next == NULL");
				errors += 1;
				break;
			} else {	// does my next point back at me
				TestNode next_prev = (TestNode) next.prev;
				if (next_prev == null) {
					System.err.println(error + next.value +
								".prev=NULL");
					errors += 1;
				} else if (next_prev != current) {
					System.err.println(error + current.value + 
							".next=" + next.value +", " +
							next.value + ".prev=" + next_prev.value);
					errors += 1;
				}
			}
			
			// check that we have not looped
			if (current.found) {
				System.err.println(error + "loop back to " + current.value);
				errors += 1;
				break;
			}
			
			// check that this node was expected to be in the list
			if (!current.in_list) {
				System.err.println(error + "node " + current.value + 
						" should not be in list");
				errors += 1;
			}

			current.found = true;
			found += 1;
		}
	
		if (found != expected) {
			System.err.println(error + "expected " + expected +
							" nodes, found " + found);
			errors += 1;
		}
		
		// if there were errors, they have been reported
		if (errors != 0)
			return false;
		
		System.out.println("    found " + found + "/" + possible +
					" in complete and consistent list");
		return true;
	}

	/**
	 * print out a usage message and exit
	 * @param arg that threw us into usage
	 */
	private static void usage(String arg) {
		if (!arg.equals("--help"))
			System.err.println("Illegal argument: " + arg);
		System.err.println("usage: " + runMe + " [" +
				" --cycles=#" +
				" --threads=#" +
				" --nodes=#" +
				" --races={RI}" +
				" ]");
		System.exit(1);
	}
	
	/**
	 * list integrity exerciser
	 *	create a set of exercise threads,
	 *	each of which runs through inserts and removes
	 *	and after each cycle, audit the list for correctness
	 * 
	 * arguments:
	 * 	--cycles=# ... max number of cycles
	 * 	--threads=# ... number of concurrent threads
	 * 	--nodes=# ... number of nodes per thread
	 * 	--races=[ir] ... enable insert/remove races
	 */
	public static void main(String[] args) {
		// process command line arguments
		int max_cycles = MAX_CYCLES;
		int num_threads = NUM_THREADS;
		int num_nodes = NODES_PER_THREAD;
		for(int i = 0; i < args.length; i++) {
			// make sure it is an option
			if (!args[i].startsWith("--"))
				usage(args[i]);
			// make sure it has a value
			int equals = args[i].indexOf("=");
			if (equals < 3 || equals > args[i].length() - 2)
				usage(args[i]);
			
			// process each option
			String option = args[i].substring(2,equals);
			String value = args[i].substring(equals+1);
			if (option.equals("cycles"))
				max_cycles = Integer.parseInt(value);
			else if (option.equals("threads"))
				num_threads = Integer.parseInt(value);
			else if (option.equals("nodes"))
				num_nodes = Integer.parseInt(value);
			else if (option.equals("races")) {
				DLL_Node.insert_race = value.contains("i") || value.contains("I");
				DLL_Node.remove_race = value.contains("r") || value.contains("R");
			} else if (option.equals("debug"))
				debug = Integer.parseInt(value);
			else
				usage(args[i]);
		}
		
		// announce what we are doing
		System.out.println("DLL MT-Tester: " +
						"cycles=" + max_cycles +
						", threads=" + num_threads +
						", nodes=" + num_nodes +
						", Ri=" + DLL_Node.insert_race +
						", Rr=" + DLL_Node.remove_race
						);
		
		// create a DLL list header
		TestNode test_list = new TestNode(0);
		
		// create DLL nodes for each tester
		TestNode[][] test_nodes = new TestNode[num_threads][];
		for(int i = 0; i < num_threads; i++) {
			test_nodes[i] = new TestNode[num_nodes];
			for(int j = 0; j < num_nodes; j++)
				test_nodes[i][j] = new TestNode((i*1000) + j + 1);
		}

		// run those threads through specified # of cycles
		stop = false;
		Boolean failures = false;
		Tester testers[] = new Tester[num_threads];
		int cycles;
		for(cycles = 0; cycles < max_cycles; cycles++) {
			// spawn half the threads in each of first two cycles
			//	so that some are inserting while others remove
			if (cycles == 0)
				for(int i = 0; i < num_threads/2; i++) {
					testers[i] = new Tester(test_list, test_nodes[i]);
					testers[i].setName("thread " + test_nodes[i][0].value);
					testers[i].start();
				}
			else if (cycles == 1)
				for(int i = num_threads/2; i < num_threads; i++) {
					testers[i] = new Tester(test_list, test_nodes[i]);
					testers[i].setName("thread " + test_nodes[i][0].value);
					testers[i].start();
				}
			
			// wait for all threads to complete current test cycle
			wait_all(testers);
			
			// audit the list for correctness
			failures |= !audit(test_list, test_nodes);
			if (failures)
				break;
			
			// wake up the sleepers for another testing cycle
			wake_all(testers);
		}
		
		// shut them down
		if (debug >= DBG_CYCLES)
			System.out.println("Shutting down");
		stop = true;
		wake_all(testers);
		wait_all(testers);
		
		if (!failures)
			System.out.println("\n" + cycles + " test cycles completed without error");
	}
}
