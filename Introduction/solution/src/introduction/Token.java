package introduction;

import java.util.Random;

/**
 * A Token is a virtual chip with a color and a numeric value
 *
 * @author: cs062
 */
public class Token {
	private static final int MAX_VALUE = 10;	// max legal value the token can take

	private String color;	// The color of the imagined token
	private int value;		// The value of the imagined token

	private static String[] colors = {
		"Green", "Blue", "Yellow", "Red"
	};

	/**
	 * Creates a Token with a specified color and value
	 *
	 *	@param	color of the desired Token
	 *	@param	numerical value of desired Token
	 */
	public Token(String color, int value) {
		this.color = color;
		this.value = value;
	}

	/**
	 * Creates a Token with randomly chosen color and value
	 */
	public Token() {
		Random rand = new Random();
		color = colors[rand.nextInt(colors.length)];
		value = rand.nextInt(MAX_VALUE + 1);
	}

	/**
	 * Return color of this Token
	 *
	 * @return color of this Token
	 */
	public String getColor() {
		return color;
	}

	/**
	 * Returns value of this Token
	 *
	 * @return value of this Token
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Sets the color of this Token
	 *
	 * @param color desired color
	 */
	public void setColor(String color) {
		this.color = color;
	}

	/**
	 * Sets the value of this Token
	 *
	 * @param value desired value
	 */
	public void setValue(int value) {
		this.value = value;
	}

	/**
	 * Returns if this Token has the maximum value
	 *
	 * @return true if this Token has maximum value
	 */
	public boolean isMax() {
		return(value == MAX_VALUE);
	}

	/**
	 * Returns if this Token has a higher than average value
	 *
	 * @return true if this Token has a higher than average value
	 */
	public boolean isHighValue() {
		return value > (MAX_VALUE/2);
	}

	/**
	 * Returns string representation of a Token
	 * 
	 * @return string representation of a Token
	 */
	public String toString() {		
		return "Token's color is " + color + " and has value " + value;
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
		example.setValue(MAX_VALUE);
		System.out.println(example.isMax());

		// 5. test toString
		example.setColor("Yellow");
		System.out.println(example.toString());
	}
}
