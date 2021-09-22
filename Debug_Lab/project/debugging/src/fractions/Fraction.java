package fractions;
/**
 * A class representing a fraction of integer values. The class
 * provides functionality for simplifying fractions, and performing
 * basic fraction computations.
 * 
 * @author Norm Krumpe https://www.youtube.com/watch?v=9gAjIQc4bPU
 * 
 */
 
public class Fraction
{
	private int numerator;
	private int denominator;

	/**
	 * Constructs a fraction with the specified numerator and
	 * denominator
	 * 
	 * @param numerator the numerator of the fraction
	 * @param denominator the denominator of the fraction
	 */
	public Fraction(int numerator, int denominator) {
		this.numerator = numerator;
		this.denominator = denominator;
	}

	/**
	 * Constructs a fraction with the specified numerator, and a
	 * denominator of 1
	 * 
	 * @param numerator the numerator of the fraction
	 */
	public Fraction(int numerator) {
		this(numerator, 1);
	}

	/**
	 * Constructs a fraction by extracting the numerator and
	 * denominator from a /-separated pair of integers. For example,
	 * given the String "4/17", the numerator would be 4 and the
	 * denominator would be 17. *
	 * 
	 * @param fractionString a string, such a 3/4, containing two int
	 *           values, separated by a slash
	 */
	public Fraction(String fractionString) {

		int separatorLocation = fractionString.indexOf("/");
		String strNumerator = fractionString.substring(0,
				separatorLocation);
		String strDenominator = fractionString
				.substring(separatorLocation + 1);

		this.numerator = Integer.parseInt(strNumerator);
		this.denominator = Integer.parseInt(strDenominator);

	}

	/**
	 * Gets this fraction's numerator
	 * 
	 * @return the numerator
	 */
	public int getNumerator() {
		return numerator;
	}

	/**
	 * Gets this fraction's denominator
	 * 
	 * @return the denominator
	 */
	public int getDenominator() {
		return denominator;
	}

	/**
	 * Simplifies the signs of this fraction's numerator and
	 * denominator. If both signs are negative, then the signs are
	 * dropped. If one sign is positive and the other is negative, the
	 * negative will be moved to the numerator. If the denominator is
	 * 0, no change is made to the signs.
	 */
	public void fixSigns() {
		if (denominator != 0 && numerator != 0) {
			numerator = (int) Math.signum(1.0 * numerator / denominator)
					* Math.abs(numerator);
			denominator = Math.abs(denominator);
		}
	}

	/**
	 * Returns a String representation of this fraction in the form
	 * "a/b"
	 * 
	 * @return a /-separated pair of integers representing this
	 *         fraction.
	 */
	public String toString() {
		return numerator + "/" + denominator;
	}

	/**
	 * Uses Euclid's algorithm for computing the greatest common
	 * divisor. See http://en.wikipedia.org/wiki/Euclidean_algorithm
	 * 
	 * @return the gcd of this fraction's numerator and denominator, or
	 *         0 if either is 0.
	 */
	private int gcd() {
		if (numerator == 0 || denominator == 0)
			return 0;

		int a = Math.max(Math.abs(numerator), Math.abs(denominator));
		int b = Math.min(Math.abs(numerator), Math.abs(denominator));

		int rem = a % b;

		while (rem != 0) {
			a = b;
			b = rem;
			rem = a % b;
		}

		return b;
	}

	/**
	 * Changes this fraction's numerator and denominator so that it is
	 * in "lowest terms" (sometimes referred to as a "common fraction".
	 * This includes fixing the signs. For example, if a fraction is
	 * 24/-18, this method will change it to -3/2.
	 */
	public void simplify() {
		if (numerator != 0 && denominator != 0) {
			fixSigns();
			int commonDivisor = gcd();
			numerator /= commonDivisor;
			denominator /= commonDivisor;
		}
	}

	/**
	 * Computes this fractions decimal value. For example, if the
	 * fraction is -3/4, then this method would return -0.75.
	 * 
	 * @return the decimal value equivalent to this fraction
	 */
	public double toDouble() {
		return 1.0 * numerator / denominator;
	}

	/**
	 * Returns a new fraction that is the reciprocal of this fraction.
	 * Does not modify this fraction.
	 * 
	 * @return a fraction whose numerator and denominator are the
	 *         denominator and numerator of this fraction.
	 */
	public Fraction getReciprocal() {
		return new Fraction(denominator, numerator);
	}

	/**
	 * Returns a 3-line representation of this fraction, with its
	 * numerator on the first line, its denominator on the last line,
	 * and a "fraction bar" made of hyphens as the separator. The
	 * length of the fraction bar is 2 more than the length of the
	 * longer number, and the numerator and denominator are centered
	 * above and below that bar, as much as possible.
	 * 
	 * @return a 3-line String representation of the fraction.
	 */
	public String toPrettyString() {
		String num = "" + numerator;
		String den = "" + denominator;
		int separatorLength = 2 + Math.max(num.length(), den.length());
		String padding = "";
		String separator = "";

		for (int i = 0; i < separatorLength; i++) {
			padding += " ";
			separator += "-";
		}

		num = padding.substring(0,
				(separator.length() - num.length()) / 2) + num;
		den = padding.substring(0,
				(separator.length() - den.length()) / 2) + den;

		return num + "\n" + separator + "\n" + den;

	}

	/**
	 * Multiplies this fraction by the specified fraction, simplifies
	 * the result, and returns it as a new fraction. Does not modify
	 * this fraction.
	 * 
	 * @param that The fraction to be multiplied by this fraction
	 * @return a new fraction equivalent to this fraction multiplied by
	 *         the parameter
	 */
	public Fraction multiply(Fraction that) {
		Fraction product = new Fraction(
				this.numerator * that.numerator, this.denominator
						* that.denominator);
		product.simplify();
		return product;
	}

	/**
	 * Divides this fraction by the specified fraction, simplifies the
	 * result, and returns it as a new fraction. Does not modify this
	 * fraction.
	 * 
	 * @param that The fraction to be divided into this fraction
	 * @return a new fraction equivalent to this fraction divided by
	 *         the parameter
	 */
	public Fraction divide(Fraction that) {
		Fraction quotient = new Fraction(this.numerator
				* that.denominator, this.denominator * that.numerator);
		quotient.simplify();
		return quotient;
	}

	/**
	 * Adds this fraction to the specified fraction, simplifies the
	 * result, and returns it as a new fraction. Does not modify this
	 * fraction.
	 * 
	 * @param that The fraction to be added to this fraction
	 * @return a new fraction equivalent to this fraction plus the
	 *         parameter
	 */
	public Fraction add(Fraction that) {
		int num = (this.numerator * that.denominator)
				+ (this.denominator * that.numerator);
		int den = this.denominator * that.denominator;
		Fraction sum = new Fraction(num, den);
		sum.simplify();
		return sum;

	}

	/**
	 * Subtracts the specified fraction from this fraction , simplifies
	 * the result, and returns it as a new fraction. Does not modify
	 * this fraction.
	 * 
	 * @param that The fraction to be subtracted from this fraction
	 * @return a new fraction equivalent to this fraction minus the
	 *         parameter
	 */
	public Fraction subtract(Fraction that) {
		return add(new Fraction(-1 * that.numerator, that.denominator));
	}

}
