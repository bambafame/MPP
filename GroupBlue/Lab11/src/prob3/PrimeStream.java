package prob3;

import java.util.function.UnaryOperator;
import java.util.stream.Stream;

public class PrimeStream {
  public static boolean isPrime(int n) {
    if(n < 2) return false;
    for (int i = 2; i < Math.sqrt(n); i++) {
      if(n%i == 0) return false;
    }
    return true;
  }

  //Part A
  final Stream<Integer> primes = Stream.iterate(2, new UnaryOperator<Integer>() {
    private int currentNumber = 3;

    @Override
    public Integer apply(Integer num) {
      while (true) {
        if (isPrime(currentNumber)) {
          return currentNumber++;
        }
        currentNumber++;
      }
    }
  });

  private Stream<Integer> generatePrimes() {
    return Stream.iterate(2, new UnaryOperator<Integer>() {
      private int current = 3;
      @Override
      public Integer apply(Integer integer) {
        while (true) {
          if (isPrime(current)) {
            return current++;
          }
          current++;
        }
      }
    });
  }

  //Part B
  public void printFirstNPrimes(long n) {
    generatePrimes().limit(n).forEach(System.out::println);
  }

  public static void main(String[] args) {
    //Part B test
    PrimeStream ps = new PrimeStream();
    ps.printFirstNPrimes(10);
    System.out.println("====");
    ps.printFirstNPrimes(5);
  }


}
