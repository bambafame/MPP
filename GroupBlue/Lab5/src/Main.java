import prob1.DecoyDuck;
import prob1.Duck;
import prob1.DuckFactory;
import prob1.MallardDuck;
import prob1.RedHeadDuck;
import prob1.RubberDuck;
import prob2.CustOrderFactory;
import prob2.Customer;
import prob2.Item;
import prob2.Order;

public class Main {
  public static void main(String[] args) {
    /*Customer customer = CustOrderFactory.createCustomer("Cheikh");
    Order order1 = CustOrderFactory.createOrder(customer);

    Item item1 = CustOrderFactory.createItem("Laptop");
    Item item2 = CustOrderFactory.createItem("Mouse");

    CustOrderFactory.addItemToOrder(item1, order1);
    CustOrderFactory.addItemToOrder(item2, order1);

    System.out.println("Customer: " + customer.getName());
    for (Order order : customer.getOrders()) {
      System.out.println("Order Number: " + order.getOrderDate());
      for (Item item : order.getItems()) {
        System.out.println(" - Item: " + item.getName());
      }
    }*/
    Duck[] ducks = {
        DuckFactory.createDuck("Mallard"), DuckFactory.createDuck("Decoy"), DuckFactory.createDuck("RedHead"), DuckFactory.createDuck("Rubber")
    };

    for(Duck duck : ducks) {
      System.out.println(duck.getClass().getSimpleName()+ " : ");
      //duck.display();
      duck.fly();
      duck.quack();
      duck.swim();
      System.out.println();
    }

  }
}
