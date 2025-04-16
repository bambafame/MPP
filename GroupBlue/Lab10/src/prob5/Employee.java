package prob5;

import java.util.Objects;

public class Employee {

  private String name;
  private double salary;

  public Employee() {
  }

  public Employee(String name, double salary) {
    this.name = name;
    this.salary = salary;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public double getSalary() {
    return salary;
  }

  public void setSalary(double salary) {
    this.salary = salary;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Employee employee)) {
      return false;
    }
    return Double.compare(salary, employee.salary) == 0 && Objects.equals(name,
        employee.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, salary);
  }

  @Override
  public String toString() {
    return "Employee " +
        "name='" + name + '\'' +
        ", salary=" + salary;
  }
}
