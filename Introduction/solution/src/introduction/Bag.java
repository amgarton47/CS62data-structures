package introduction;

/**
 * A Bag is a collection of Tokens (chips) that supports
 * functions that work by enumerating the contents.
 *
 * @author: cs062 staff
 */
public class Bag {

	private static final int DEFAULT_TOKENS = 10;	// default number of Tokens in bag

	Token[] contents;	// the Tokens contained in this bag

	/**
	 * Creates a new bag populated with the given number of Tokens
	 *
	 * @param numTokens number of desired Tokens
	 */
	public Bag(int numTokens){
		contents = new Token[numTokens];
		for(int i = 0; i < numTokens; i++) {
			contents[i] = new Token();
		}
	}

	/**
	 * Prints out the first Token in the bag
	 */
	public void firstToken() {
		System.out.println(contents[0]);
	}
	
	/**
	 *	Prints out each Token in the bag
	 *	use a for loop to enumerate the contents
	 */
	public void allTokens() {
		for(int i = 0; i < contents.length; i++) {
			System.out.println(contents[i]);
		}
	}
	
	/**
	 *	Prints out each Token in the bag
	 *	using a while loop to enumerate the contents
	 */
	public void allTokensWhile() {
		int i = 0;
		while(i < contents.length) {
			System.out.println(contents[i++]);
		}
	}
	
	/**
	 * Sums up the values of all Tokens in bag
	 *
	 * @return sum of values of all Tokens in bag
	 */
	public int addTokens() {
		int sum = 0;
		for(int i = 0; i < contents.length; i++)
			sum += contents[i].getValue();
		return sum;
	}
	
	/**
	 * Returns the number of Tokens with a high value
	 *
	 * @return number of high value Tokens
	 */
	public int highValueTokens() {
		int sum = 0;
		for(int i = 0; i < contents.length; i++)
			if (contents[i].isHighValue())
				sum++;
		return sum;
	}
	
	/**
	 * Returns the index of first green Token, if it exists
	 *
	 * @return index of first green Token, or -1 if none
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

		// test firstToken ... to print the first Token
		example.firstToken();
		System.out.println();

		// test allTokens ... to print all Tokens
		example.allTokens();
		System.out.println();

		// test allTokensWhile ... to print all Tokens using a while loop
		example.allTokensWhile();
		System.out.println();

		// test addTokens ... to print the sum of values of all Tokens
		System.out.println(example.addTokens());

		// test highValueTokens ... to count the high value Tokens
		System.out.println(example.highValueTokens());

		// test firstGree ... to find the first green Token
		System.out.println(example.firstGreen());
	}
}
