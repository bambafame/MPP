package prob11b;

import java.util.List;
import java.util.stream.Collectors;

public class LambdaLibrary {
  public static final TriFunction<List<Employee>, Integer, Character, String> tri =
      (emps, salary, letter) -> emps.stream()
          .filter(emp -> emp.getSalary() > salary && emp.getLastName().charAt(0) > letter)
          .map(Employee::fullName)
          .sorted()
          .collect(Collectors.joining(", "));
}
