package dao;

import entities.Admin;
import entities.RegularUser;
import entities.User;
import java.sql.*;

public class UserDAO {

  private final Connection conn;

  public UserDAO(Connection conn) {
    this.conn = conn;
  }

  public User findByUsername(String username) throws SQLException {
    String sql = "SELECT * FROM users WHERE username = ?";
    PreparedStatement ps = conn.prepareStatement(sql);
    ps.setString(1, username);
    ResultSet rs = ps.executeQuery();

    if (rs.next()) {
      String role = rs.getString("role");
      if ("ADMIN".equals(role)) {
        Admin admin = new Admin();
        admin.setUsername(rs.getString("username"));
        admin.setPassword(rs.getString("password"));
        admin.setFirstname(rs.getString("first_name"));
        admin.setLastname(rs.getString("last_name"));
        return admin;
      } else {
        RegularUser user = new RegularUser();
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setFirstname(rs.getString("first_name"));
        user.setLastname(rs.getString("last_name"));
        user.setBudgetLimit(rs.getDouble("budget_limit"));
        return user;
      }
    }
    return null;
  }
}
