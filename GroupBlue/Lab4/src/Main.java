import java.time.LocalDate;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        SalariedEmployee salariedEmployee = new SalariedEmployee("Emp01", 5000);
        PayCheck salariedPayCheck = salariedEmployee.calcCompensation(4, 2025);

        // Testing Hourly Employee
        HourlyEmployee hourlyEmployee = new HourlyEmployee("Emp02", 20, 40); // $20/hour for 40 hours/week
        PayCheck hourlyPayCheck = hourlyEmployee.calcCompensation(4, 2025); // April 2025
        hourlyPayCheck.print();

        // Testing Commissioned Employee
        ArrayList<Order> orders = new ArrayList<>();
        orders.add(new Order(1, LocalDate.of(2025, 4, 15), 50));
        orders.add(new Order(2, LocalDate.of(2025, 3, 20), 100));

        CommissionedEmployee commissionedEmployee = new CommissionedEmployee("Emp03", 3000, 0.1, orders);
        PayCheck commissionedPayCheck = commissionedEmployee.calcCompensation(4, 2025); // April 2025
        commissionedPayCheck.print();
    }
}
