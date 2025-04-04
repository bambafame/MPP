abstract public class Employee {
    final String employeeId;

    public Employee(String employeeId) {
        this.employeeId = employeeId;
    }

    abstract double calcGrossPay(int month, int year);

    PayCheck calcCompensation(int month, int year) {
        double grossPay = calcGrossPay(month, year);

        double fica = 0.23 * grossPay;
        double state = 0.05 * grossPay;
        double local = 0.01 * grossPay;
        double medicare = 0.03 * grossPay;
        double socialSecurity = 0.075 * grossPay;

        double deductibles = fica + state + local + medicare + socialSecurity;

        double netPay = grossPay - deductibles;

        return new PayCheck(grossPay, fica, state, local, medicare, socialSecurity, netPay);
    }

    void print() {
        System.out.println("The employee ID is: " + employeeId);
    }
}