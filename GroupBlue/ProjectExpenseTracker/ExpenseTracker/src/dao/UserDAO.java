package dao;

import entities.Admin;
import entities.RegularUser;
import entities.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

  public double getBudgetLimit(String userId) throws SQLException {
    String sql = "SELECT budget_limit FROM users WHERE username = ?";
    PreparedStatement ps = conn.prepareStatement(sql);
    ps.setString(1, userId);
    ResultSet rs = ps.executeQuery();
    if (rs.next()) {
      return rs.getDouble("budget_limit");
    }
    return 0.0;
  }
  public List<String> getAllRegularUsernames() throws SQLException {
    List<String> usernames = new ArrayList<>();
    String sql = "SELECT username FROM users WHERE role = 'REGULAR'";
    try (PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery()) {
      while (rs.next()) {
        usernames.add(rs.getString("username"));
      }
    }
    return usernames;
  }

}
