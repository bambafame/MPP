package prob2;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Order {
  private LocalDate orderDate;

  private List<Item> items;

  //package level access
  Order(LocalDate orderDate) {
    this.orderDate = orderDate;
    items = new ArrayList<>();
  }

  public void addItem(Item item){
    items.add(item);
  }

  public List<Item> getItems() {
    return Collections.unmodifiableList(items);
  }

  public LocalDate getOrderDate() {
    return orderDate;
  }

  @Override
  public String toString() {
    return orderDate + ": " +
        items.toString();
  }
}
