import java.time.LocalDate;

class Order {
    final int orderNo;
    final LocalDate orderDate;
    final double orderAmount;

    public Order(int orderNo, LocalDate orderDate, double orderAmount) {
        this.orderNo = orderNo;
        this.orderDate = orderDate;
        this.orderAmount = orderAmount;
    }
}
