package prob2B;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Order {
  private LocalDate date;
  private List<OrderLine> orderLines;

   Order() {
    this.date = LocalDate.now();
    this.orderLines = new ArrayList<>();
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public List<OrderLine> getOrderLines() {
    return orderLines;
  }

  public void setOrderLines(List<OrderLine> orderLines) {
    this.orderLines = orderLines;
  }

  public void addOrderLine(String product, int quantity) {
    OrderLine orderLine = new OrderLine(product, quantity, this);
    this.orderLines.add(orderLine);
  }

  @Override
  public String toString() {
    return "Order{date=" + date + "}";
  }
}
