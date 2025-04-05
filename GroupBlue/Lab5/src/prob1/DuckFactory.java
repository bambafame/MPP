package prob1;

public class DuckFactory {

  public static Duck createDuck(String type){
    Duck duck = null;
    switch (type) {
      case "Mallard" -> duck = new MallardDuck();
      case "RedHead" -> duck = new RedHeadDuck();
      case "Decoy" -> duck = new DecoyDuck();
      case "Rubber" -> duck = new RubberDuck();
      default -> throw new IllegalArgumentException("Duck not available");
    }
    return duck;
  }
}
