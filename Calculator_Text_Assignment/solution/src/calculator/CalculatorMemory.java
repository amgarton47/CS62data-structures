package calculator;

import java.util.LinkedList;

public class CalculatorMemory {
	private LinkedList<Integer> stack = new LinkedList<Integer>();
	
	public void push(int number) {
		stack.add(number);
	}
	
	public int pop() {
		return stack.removeLast();
	}
	
	public boolean isEmpty() {
		return stack.size() == 0;
	}
	
	public int size() {
		return stack.size();
	}
	
	public void clear() {
		stack.clear();
	}
	
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		
		for( int i = stack.size()-1; i >= 0; i-- ) {
			buffer.append(stack.get(i) + "\n");
		}
		
		buffer.append("---\n");
		
		return buffer.toString();
	}
}
