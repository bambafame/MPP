public class SalariedEmployee  extends Employee {
  final double salary;

  public SalariedEmployee(String employeeId, double salary) {
    super(employeeId);
    this.salary = salary;
  }

  @Override
  double calcGrossPay(int month, int year) {
    return salary;
  }
}