package calculator;

public class Operation {
	private char op;
	
	public Operation(char op) {
		if( op == '+' || op == '-' ||
			op == '*' || op == '/' ) {
			this.op = op;
		} else {
			throw new IllegalArgumentException("Illegal operator");
		}	
	}
	
	public int performOperation(int first, int second) {
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
