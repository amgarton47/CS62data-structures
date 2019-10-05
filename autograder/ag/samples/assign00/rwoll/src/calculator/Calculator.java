package calculator;

/**
 * JUnit Example from GitHub
 */

public class Calculator {
  public int evaluate(String expression) {
    int sum = 0;
    for (String summand: expression.split("\\+"))
      sum += Integer.valueOf(summand);
    return sum;
  }

  public int fibinocci(int rounds) {
    int f0 = 0;
    int f1 = 1;
    for (int i = 0; i < rounds; i++) {
      int temp = f1;
      f1 = evaluate(f0 + "+" + f1);
      f0 = temp;
    }

    return f1;
  }

  public static void main(String[] args) {
    Calculator calc = new Calculator();

    System.out.println("fibinocci(47): " + calc.fibinocci(47));
  }
}
