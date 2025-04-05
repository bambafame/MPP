package prob2;

import java.time.LocalDate;

public class CustOrderFactory {
  public static Customer createCustomer(String name) {
    return new Customer(name);
  }

  public static Order createOrder(Customer customer) {
    if(customer == null) throw new IllegalArgumentException("Customer cannot be null");

    Order order = new Order(LocalDate.now());
    customer.addOrder(order);
    return order;
  }

  public static Item createItem(String name) {
    return new Item(name);
  }

  public static void addItemToOrder(Item item, Order order) {
    if(order == null || item == null) throw new IllegalArgumentException("Order and Item cannot be null");

    order.addItem(item);
  }

  public static void addOrderToCustomer(Customer customer, Order order) {
    if(order == null || customer == null) throw new IllegalArgumentException("Order and Customer cannot be null");

    customer.addOrder(order);
  }

}
