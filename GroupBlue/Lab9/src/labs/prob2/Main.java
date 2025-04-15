package labs.prob2;

public class Main {

  public static void main(String[] args) {
    ExprType expr =
        new Multiplication(
            new Addition(
                new Constants(2),
                new Constants(3)
            ),
            new Constants(4)
        );

    int result = eval(expr);
    System.out.println(result);
  }

  public static int eval(ExprType exprType) {
    return  switch (exprType) {
      case Constants constants -> constants.value();
      case Addition addition -> eval(addition.exp1()) + eval(addition.exp2());
      case Multiplication multiplication -> eval(multiplication.exp1()) * eval(multiplication.exp2());
    };
  }
}
