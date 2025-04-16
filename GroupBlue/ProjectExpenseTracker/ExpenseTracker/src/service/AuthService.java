package service;

import dao.UserDAO;
import entities.User;
import java.sql.Connection;

public class AuthService {

  private final UserDAO userDAO;

  public AuthService(Connection conn) {
    this.userDAO = new UserDAO(conn);
  }

  public User login(String username, String password) {
    try {
      User user = userDAO.findByUsername(username);
      if (user != null && user.getPassword().equals(password)) {
        return user;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

}
