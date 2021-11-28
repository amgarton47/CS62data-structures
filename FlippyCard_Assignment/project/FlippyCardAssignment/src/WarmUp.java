import java.util.Arrays;

public class WarmUp {

	/**
	 * Counts how many cards are hearts
	 * 
	 * @param cards an array of cards
	 * @return the number of cards that are hearts
	 */
	public static int countHearts(Card[] cards) {
		int count = 0;

		for (Card card : cards) {
			if (card.getSuit().equals("hearts")) {
				count++;
			}
		}

		return count;
	}

	/**
	 * Add the elements of two arrays of the same length. The function assumes that
	 * the arrays are of the same length
	 * 
	 * @param array1
	 * @param array2
	 * @return a new array that is the element-wise sum of the input arrays
	 */
	public static double[] addArraysSameLength(double[] array1, double[] array2) {
		// TODO: fill in the method!
		return null;
	}

	// TODO: write the reverseArray method, including method header and JavaDoc
	// comments

	public static void main(String[] args) {
		/*
		 * String[] words = {"I", "love", "my", "CS", "classes", "!"};
		 * 
		 * System.out.println("Before: " + Arrays.toString(words)); reverseArray(words);
		 * System.out.println("After: " + Arrays.toString(words));
		 */

		Card[] cards = new Card[4];

		cards[0] = new Card(1, "clubs");
		cards[1] = new Card(1, "hearts");
		cards[2] = new Card(3, "hearts");
		cards[3] = new Card(2, "diamonds");

		System.out.println(WarmUp.countHearts(cards));
	}
}
