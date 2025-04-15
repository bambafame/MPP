package entities;

public class RegularUser extends User{
  private double budgetLimit;

  public RegularUser(String firstname, String lastname) {
    super(firstname, lastname);
  }

  public RegularUser(String firstname, String lastname, double budgetLimit) {
    super(firstname, lastname);
    this.budgetLimit = budgetLimit;
  }

  public RegularUser() {}

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
