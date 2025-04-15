package entities;

public class Admin extends User{
  public Admin() {}

  public Admin(String firstname, String lastname) {
    super(firstname, lastname);
  }

  public Role getRole() {
    return Role.ADMIN;
  }
}
