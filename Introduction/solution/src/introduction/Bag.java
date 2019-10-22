package introduction;

/**
 * a Bag is a collection of Tokens (chips) that supports
 * functions that work by enumerating the contents.
 *
 * @author: Mark Kampe
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
			System.out.println(contents[i]);
		}
	}
	
	/**
	 * allChipsWhile
	 *		print out each chip in the bag
	 *		using a while loop to enumerate the contents
	 */
	public void allChipsWhile() {
		int i = 0;
		while(i < contents.length) {
			System.out.println(contents[i++]);
		}
	}
	
	/**
	 * addChips
	 *
	 * @return sum of values of all chips in bag
	 */
	public int addChips() {
		int sum = 0;
		for(int i = 0; i < contents.length; i++)
			sum += contents[i].getValue();
		return sum;
	}
	
	/**
	 * chipHighValue
	 *
	 * @return number of high value chips
	 */
	public int chipHighValue() {
		int sum = 0;
		for(int i = 0; i < contents.length; i++)
			if (contents[i].isHighValue())
				sum++;
		return sum;
	}
	
	/**
	 * firstGreen
	 *
	 * @return index of first green chip, or -1
	 */
	public int firstGreen() {
		for(int i = 0; i < contents.length; i++)
			if (contents[i].getColor().equals("Green"))
				return(i);

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
