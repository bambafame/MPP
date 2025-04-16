package prob5;

import java.util.Comparator;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Examples {

  // A. (Employee e) -> e.getName()
  Function<Employee, String> getName1 = (Employee e) -> e.getName();
  Function<Employee, String> getName2 = Employee::getName;

  // B. (Employee e, String s) -> e.setName(s)
  BiConsumer<Employee, String> setName1 = (Employee e, String s) -> e.setName(s);
  BiConsumer<Employee, String> setName2 = Employee::setName;

  // C. (String s1, String s2) -> s1.compareTo(s2)
  BiFunction<String, String, Integer> compareTo1 = (String s1, String s2) -> s1.compareTo(s2);
  BiFunction<String, String, Integer> compareTo2 = String::compareTo;

  // D. (Integer x, Integer y) -> Math.pow(x, y)
  BiFunction<Integer, Integer, Double> pow1 = (Integer x, Integer y) -> Math.pow(x, y);
  BiFunction<Integer, Integer, Double> pow2 = Math::pow;

  // E. (Apple a) -> a.getWeight()
  Function<Apple, Integer> getWeight1 = (Apple a) -> a.getWeight();
  Function<Apple, Integer> getWeight2 = Apple::getWeight;

  // F. (String x) -> Integer.parseInt(x)
  Function<String, Integer> parseInt1 = (String x) -> Integer.parseInt(x);
  Function<String, Integer> parseInt2 = Integer::parseInt;

  // G. (Employee e1, Employee e2) -> comp.compare(e1, e2)
  EmployeeNameComparator comp = new EmployeeNameComparator();
  Comparator<Employee> compare1 = (Employee e1, Employee e2) -> comp.compare(e1, e2);
  Comparator<Employee> compare2 = comp::compare;
  BiFunction<Employee, Employee, Integer> compare3 = (Employee e1, Employee e2) -> comp.compare(e1, e2);
  BiFunction<Employee, Employee, Integer> compare4 = comp::compare;

  public void evaluator() {
    System.out.println("A: " + getName2.apply(new Employee("Yaakoub", 70_000)));

    Employee e = new Employee("Cheikh", 90_000);
    setName2.accept(e,"Bamba");
    System.out.println("B: " +e);

    System.out.println("C: " +compareTo2.apply("Moges", "Shaban"));

    System.out.println("D: " +pow2.apply(2, 4));

    Apple apple = new Apple(150);
    System.out.println("E: " +getWeight2.apply(apple));

    Employee e1 = new Employee("Yaakoub", 70_000);
    System.out.println("G: " +compare4.apply(e, e1));

  }

  public static void main(String[] args) {
    new Examples().evaluator();
  }

}
