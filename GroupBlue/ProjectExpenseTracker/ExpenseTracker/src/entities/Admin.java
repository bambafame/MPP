package entities;

public class Admin extends User{
  public Admin() {
    this.role = Role.ADMIN;
  }

  public Admin(String firstname, String lastname) {
    super(firstname, lastname);
    this.role = Role.ADMIN;
  }

  public Role getRole() {
    return Role.ADMIN;
  }
}
