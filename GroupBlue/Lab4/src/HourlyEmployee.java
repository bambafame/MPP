
class HourlyEmployee extends Employee {
  final double rate;
  final int hoursWorked;

  public HourlyEmployee(String employeeId, double rate, int hoursWorked) {
    super(employeeId);
    this.rate = rate;
    this.hoursWorked = hoursWorked;
  }

  @Override
  double calcGrossPay(int month, int year) {
    return rate * hoursWorked * 4;
  }
}
