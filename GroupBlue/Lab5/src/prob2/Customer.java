package prob2;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Customer {
  private String name;
  private List<Order> orders;

   Customer(String name) {
    this.name = name;
    orders = new ArrayList<>();
  }

  public void addOrder(Order ord) {
    orders.add(ord);
  }

  public String getName() {
    return name;
  }

  public List<Order> getOrders() {
    return Collections.unmodifiableList(orders);
  }
}
