package prob2B;

public class OrderLine {
  private String product;
  private int quantity;
  private Order order;

  public OrderLine(String product, int quantity, Order order) {
    this.product = product;
    this.quantity = quantity;
    this.order = order;
  }

  public String getProduct() {
    return product;
  }

  public void setProduct(String product) {
    this.product = product;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public Order getOrder() {
    return order;
  }

  public void setOrder(Order order) {
    this.order = order;
  }

  @Override
  public String toString() {
    return "OrderLine{product='" + product + "', quantity=" + quantity +
        ", orderDate=" + (order != null ? order.getDate() : "None") + "}";
  }

}
