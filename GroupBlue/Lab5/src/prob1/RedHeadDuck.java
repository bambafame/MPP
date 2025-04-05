package prob1;

public class RedHeadDuck extends Duck{

  RedHeadDuck() {
    flyBehavior = new FlyWithWings();
    quackBehavior = new Quack();
  }

  @Override
  void display() {
    System.out.println("Displaying");
  }
}
