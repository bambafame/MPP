package prob1;

public class RubberDuck extends Duck {

  RubberDuck() {
    flyBehavior = new CannotFly();
    quackBehavior = new Squeak();
  }

  @Override
  void display() {
    System.out.println("Displaying");
  }
}
