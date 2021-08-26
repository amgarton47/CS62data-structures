package introduction;

import java.util.Random;

/**
 * A Token is a virtual chip with a color and a numeric value
 *
 * @author: YOUR NAME HERE
 */
public class Token {
	int MAX_VALUE = 10;	// max legal value the token can take

	String color;	// The color of the imagined token
	int value;		// The value of the imagined token

	String[] colors = {
		"Green", "Blue", "Yellow", "Red"
	};

	/**
	 * Creates a Token with a specified color and value
	 *
	 *	@param	color of the desired Token
	 *	@param	numerical value of desired Token
	 */
	Token(String newColor, int newValue) {
		color = newColor;
		value = newValue;
	}

	/**
	 * Creates a Token with randomly chosen color and value
	 */
	Token() {
		Random rand = new Random();
		color = colors[rand.nextInt(colors.length)];
		value = rand.nextInt(MAX_VALUE + 1);
	}

	/**
	 * Return color of this Token
	 *
	 * @return color of this Token
	 */
	String getColor() {
		return color;
	}

	/**
	 * Returns value of this Token
	 *
	 * @return value of this Token
	 */
	int getValue() {
		// TODO: Have this function return the value of this Token
	}

	/**
	 * Sets the color of this Token
	 *
	 * @param color desired color
	 */
	void setColor(String newColor) {
		// TODO: Have this function change the color of this Token
	}

	/**
	 * Sets the value of this Token
	 *
	 * @param value desired value
	 */
	void setValue(int newValue) {
		// TODO: Have this function change the value of this Token
	}

	/**
	 * Returns if this Token has the maximum value
	 *
	 * @return true if this Token has maximum value
	 */
	boolean isMax() {
		return(value == MAX_VALUE);
	}

	/**
	 * Returns if this Token has a higher than average value
	 *
	 * @return true if this Token has a higher than average value
	 */
	boolean isHighValue() {
		// TODO write this function so that it returns true if the value is greater than half the maximum value
	}

	/**
	 * Returns string representation of a Token
	 * 
	 * @return string representation of a Token
	 */
	public String toString() {
		// TODO: Complete this line of code so that it will also print the value of the token
		
		return "Token's color is " + color + " and has value ";
	}

	/**
	 * test routine when this class is run as an Application
	 * output should be:
	 *	Green
	 *	5
	 *	false
	 *	true
	 *	Token's color is Yellow and has value 10
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
		example.setValue(10);
		System.out.println(example.isMax());

		// 5. test toString
		example.setColor("Yellow");
		System.out.println(example.toString());
	}
}
