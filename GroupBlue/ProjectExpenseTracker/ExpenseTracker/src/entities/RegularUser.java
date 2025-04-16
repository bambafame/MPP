package entities;

public class RegularUser extends User{
  private double budgetLimit;

  public RegularUser(String firstname, String lastname) {
    super(firstname, lastname);
    this.role = Role.REGULAR;
  }

  public RegularUser(String firstname, String lastname, double budgetLimit) {
    super(firstname, lastname);
    this.budgetLimit = budgetLimit;
    this.role = Role.REGULAR;
  }

  public RegularUser() {
    this.role = Role.REGULAR;
  }

  public double getBudgetLimit() {
    return budgetLimit;
  }

  public void setBudgetLimit(double budgetLimit) {
    this.budgetLimit = budgetLimit;
  }

  public Role getRole() {
    return Role.REGULAR;
  }
}
