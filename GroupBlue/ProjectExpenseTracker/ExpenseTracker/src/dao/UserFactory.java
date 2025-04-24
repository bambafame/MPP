package dao;

import entities.Admin;
import entities.RegularUser;
import entities.User;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserFactory {
  public static User createUserFromResultSet(ResultSet rs) throws SQLException {
    String role = rs.getString("role");
    User user;
    if (role.equalsIgnoreCase("REGULAR")) {
      user = new RegularUser();
    } else if (role.equalsIgnoreCase("ADMIN")) {
      user = new Admin();
    } else {
      throw new RuntimeException("Unknown role: " + role);
    }
    user.setUsername(rs.getString("user_id"));
    user.setFirstname(rs.getString("first_name"));
    user.setLastname(rs.getString("last_name"));
    user.setRole(User.Role.valueOf(role));
    return user;
  }
}
