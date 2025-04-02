package prob2B;

public class Main {

  public static void main(String[] args) {
    Order order = new Order();
    System.out.println(order);
    order.addOrderLine("Laptop", 2);
    order.addOrderLine("TV", 1);
    for (OrderLine line : order.getOrderLines()) {
      System.out.println("  " + line);
    }
  }
}
