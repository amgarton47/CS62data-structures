package introduction;

import java.util.Random;

/**
 * a Token is a virtual with a color and a numeric value
 *
 * @author: YOUR NAME HERE
 */
public class Token {
	private static final int MAX_VALUE = 10;	// max legal value

	private String color;	// The color of the imagined token
	private int value;		// The value of the imagined token

	private static String[] colors = {
		"Green", "Blue", "Yellow", "Red"
	};

	/**
	 * create a token with a specified color and value
	 *
	 *	@param	color of the desired token
	 *	@param	numerical value of desired token
	 */
	public Token(String color, int value) {
		this.color = color;
		this.value = value;
	}

	/**
	 * create a token with randomly chosen color and value
	 */
	public Token() {
		Random rand = new Random();

		color = colors[rand.nextInt(colors.length)];
		value = rand.nextInt(MAX_VALUE + 1);
	}

	/**
	 * @return color of this token
	 */
	public String getColor() {
		return color;
	}

	/**
	 * @return value of this token
	 */
	public int getValue() {
		// TODO: Have this function return the value of the chip
	}

	/**
	 * set the color of this token
	 * @param newColor ... desired color
	 */
	public void setColor(String newColor) {
		// TODO: Have this function change the color of the chip
	}

	/**
	 * set the value of this token
	 * @param newValue ... desired value
	 */
	public void setValue(int newValue) {
		// TODO: Have this function change the value of the chip
	}

	/**
	 * @return True if token has maximum value
	 */
	public boolean isMax() {
		return(value == MAX_VALUE);
	}

	/**
	 * @return True if token has a higher than average value
	 */
	public boolean isHighValue() {
		// TODO write this function so that it returns true if the value is greater than half the maximum value
	}

	/**
	 * @return string representation of a token
	 */
	public String toString() {
		// TODO: Complete this line of code so that it will also print the value of the chip
		
		return "Chip is " + color + " and has value ";
	}

	/**
	 * test routine when this class is run as an Application
	 * output should be:
	 *	Green
	 *	5
	 *	false
	 *	true
	 *	Chip is Yellow and has value 10
	 */
	public static void main(String[] args) {
		// 1. create a token with specified value and color
		Token example = new Token("Green", 5);

		// 2. exercise getColor and getValue
		System.out.println(example.getColor());
		System.out.println(example.getValue());

		// 3. test highValue
		System.out.println(example.isHighValue());

		// 4. test setValue and isMax
		example.setValue(MAX_VALUE);
		System.out.println(example.isMax());

		// 5. test toString
		example.setColor("Yellow");
		System.out.println(example.toString());
	}
}
