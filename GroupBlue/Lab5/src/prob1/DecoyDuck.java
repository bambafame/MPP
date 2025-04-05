package prob1;

public class DecoyDuck extends Duck{

  DecoyDuck() {
    flyBehavior = new CannotFly();
    quackBehavior = new MuteQuack();
  }

  @Override
  void display() {
    System.out.println("Displaying");
  }
}
