package prob1;

public abstract class Duck {
  FlyBehavior flyBehavior;
  QuackBehavior quackBehavior;

  abstract void display();

  public void swim() {
    System.out.println("Swimming");
  }

  public void fly() {
    flyBehavior.fly();
  }

  public void quack() {
    quackBehavior.quack();
  }

  public void setFlyBehavior(FlyBehavior flyBehavior) {
    this.flyBehavior = flyBehavior;
  }

  public void setQuackBehavior(QuackBehavior quackBehavior) {
    this.quackBehavior = quackBehavior;
  }
}
