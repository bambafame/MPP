import java.util.List;

public class CommissionedEmployee extends Employee {
  final double baseSalary;
  final double commissionRate;
  final List<Order> orders;

  public CommissionedEmployee(String employeeId, double baseSalary, double commissionRate, List<Order> orders) {
    super(employeeId);
    this.baseSalary = baseSalary;
    this.commissionRate = commissionRate;
    this.orders = orders;
  }

  // When employee makes a sale, the new order is added to their orders list
  void addNewOrder(Order order) {
    orders.add(order);
    System.out.println("New order added!");
  }

  @Override
  double calcGrossPay(int month, int year) {
    double totalAmountOrdered = 0;

    for (Order order : orders) {
      if (order.orderDate.getMonthValue() == month && order.orderDate.getYear() == year) {
        totalAmountOrdered += order.orderAmount;
      }
    }

    return baseSalary + (commissionRate * totalAmountOrdered);
  }
}
