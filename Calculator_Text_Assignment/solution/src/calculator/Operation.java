package calculator;

public class Operation {
	public static int performOperation(char op, int first, int second) {
		if( op == '+' ) {
			return first + second;
		} else if( op == '-' ) {
			return first - second;
		} else if ( op == '*' ) {
			return first * second;
		} else if ( op == '/' ) {
			return first / second;
		} else {
			// we shouldn't get here, but just in case
			throw new IllegalArgumentException("Illegal operator");
		}		
	}
}
