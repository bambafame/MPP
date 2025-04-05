package prob1;

public class CannotFly implements FlyBehavior {

  @Override
  public void fly() {
    System.out.println("Cannot fly");
  }
}
