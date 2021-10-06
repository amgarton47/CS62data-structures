package calculator;

import java.util.Scanner;

public class Calculator {
	private CalculatorMemory memory;

	public Calculator(){
		memory = new CalculatorMemory();
	}

	public void run() {
		Scanner in = new Scanner(System.in);

		System.out.print("Enter a number or operator: ");
		String line = in.nextLine();

		while( !line.equals("") ) {			
			try {
				int number = Integer.parseInt(line);
				memory.push(number);
			} catch (NumberFormatException e){
				if( line.equals("clear") ) {
					memory.clear();
				} else if( line.equals("pop") ) {
					if( memory.isEmpty() ) {
						System.out.println("Error: pop requires one argument");
					} else {
						System.out.println("Answer: " + memory.pop() );
					}
				} else {
					processOperation(line);
				}
			}

			System.out.println();
			System.out.println("Memory contents: ");
			System.out.println(memory);
			System.out.print("Enter a number or operator: ");
			line = in.nextLine();
		}
	}
	
	private void processOperation(String line) {
		// assume its an operator
		int first = 0;
		int second = 0;
		
		try {
			if( memory.size() < 2 ) {
				System.out.println("Error: operator requires two arguments");
			} else {
				second = memory.pop();
				first = memory.pop();
				int result = Operation.performOperation(line.charAt(0), first, second);
				memory.push(result);
				System.out.println("Answer: " + result);
			}
		} catch ( IllegalArgumentException e ){
			System.out.println("Error: expected number or operator");
		} catch ( ArithmeticException e ) {
			System.out.println("Error: divide by zero");
			memory.push(first);
			memory.push(second);
		}
	}

	public static void main(String[] args) {
		Calculator calc = new Calculator();
		calc.run();
	}
}
