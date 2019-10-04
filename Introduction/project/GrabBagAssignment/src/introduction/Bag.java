package introduction;

/**
 * a Bag is a collection of Tokens (chips) that supports
 * functions that work by enumerating the contents.
 *
 * @author: YOUR NAME HERE
 */
public class Bag {

	private static final int DEFAULT_TOKENS = 10;	// test bag size

	Token[] contents;	// the Tokens contained in this bag

	/**
	 * create a new bag, populated with tokens
	 *
	 * @param: numTokens ... number of desired tokens
	 */
	public Bag(int numTokens){
		contents = new Token[numTokens];
		for(int i = 0; i < numTokens; i++) {
			contents[i] = new Token();
		}
	}

	/**
	 * firstChip
	 *		print out the first chip in the bag
	 */
	public void firstChip() {
		System.out.println(contents[0]);
	}
	
	/**
	 * allChips
	 *		print out each chip in the bag
	 *		using a for loop to enumerate the contents
	 */
	public void allChips() {
		for(int i = 0; i < contents.length; i++) {
			// TODO print out the currently chosen chip
		}
	}
	
	/**
	 * allChipsWhile
	 *		print out each chip in the bag
	 *		using a while loop to enumerate the contents
	 */
	public void allChipsWhile() {
		// TODO turn this into a while loop that enumerates and
		//      prints all of the chips in the bag
		while() {
		}
	}
	
	/**
	 * addChips
	 *
	 * @return sum of values of all chips in bag
	 */
	public int addChips() {
		// TODO write a loop that enumerates the entire bag
		//	    and sums the total of all of the chip values
		return 0;
	}
	
	/**
	 * chipHighValue
	 *
	 * @return number of high value chips
	 */
	public int chipHighValue() {
		// TODO write a loop that enumerates the entire bag
		//	    and returns the number of high value chips
		return 0;	
	}
	
	/**
	 * firstGreen
	 *
	 * @return index of first green chip, or -1
	 */
	public int firstGreen() {
		// TODO search bag for a green chip, if found, return its index
		return -1;
	}

	/**
	 * main ... test program when Bag is run as Application
	 */
	 public static void main(String[] args) {
		// create a bag full of random cips
		Bag example = new Bag(DEFAULT_TOKENS);

		// test firstChip ... to print the first
		example.firstChip();
		System.out.println();

		// test allChips ... to print them all
		example.allChips();
		System.out.println();

		// test allChipsWhile ... to print them all
		example.allChipsWhile();
		System.out.println();

		// test addChips ... to print the sum of values
		System.out.println(example.addChips());

		// test chipHighValue ... to count the high value chips
		System.out.println(example.chipHighValue());

		// test firstGree ... to find the first green chip
		System.out.println(example.firstGreen());
	}
}
