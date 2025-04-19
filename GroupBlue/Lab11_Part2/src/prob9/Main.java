package prob9;

import java.util.function.IntUnaryOperator;
import java.util.function.UnaryOperator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {

  public static void main(String[] args) {
    printSquares(5);
  }

  public static void printSquares(int num) {
    IntStream.iterate(1, new IntUnaryOperator() {
      private int current = 2;

      @Override
      public int applyAsInt(int operand) {
        int result = current * current;
        current++;
        return result;
      }
    }).limit(num).forEach(System.out::println);
  }
}
