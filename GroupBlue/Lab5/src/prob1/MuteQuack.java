package prob1;

public class MuteQuack implements QuackBehavior {

  @Override
  public void quack() {
    System.out.println("Cannot squack");
  }
}
