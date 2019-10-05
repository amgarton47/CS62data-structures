package calculator;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class Autograder {
  @Test
  public void evaluatesExpression() {
    Calculator calculator = new Calculator();
    int sum = calculator.evaluate("1+2+3");
    assertEquals(6, sum);
  }

	@Test
	public void evaluatesBadExpression() {
		assertEquals("A bad expression should return 0.", 0, 7);
	}
}
