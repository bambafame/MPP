package prob1;

public class MallardDuck extends Duck{

  MallardDuck() {
    flyBehavior = new FlyWithWings();
    quackBehavior = new Quack();
  }

  @Override
  void display() {
    System.out.println("Displaying");
  }
}
