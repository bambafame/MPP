package prob1;

public class Main {

  public static void main(String[] args) {
    Duck[] ducks = {
      new MallardDuck(), new DecoyDuck(), new RedHeadDuck(), new RubberDuck()
    };

    for(Duck duck : ducks) {
      System.out.println(duck.getClass().getSimpleName()+ " : ");
      duck.display();
      duck.fly();
      duck.quack();
      duck.swim();
      System.out.println();
    }
  }
}
