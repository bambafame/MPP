package ui;

import entities.RegularUser;
import java.sql.Connection;
import javax.swing.*;

public class UserDashboard extends JFrame {
  public UserDashboard(RegularUser user, Connection conn) {
    setTitle("User Dashboard - " + user.getUsername());
    setSize(600, 400);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);

    new ExpenseManagementFrame(user.getUsername(), conn).setVisible(true);

    dispose();
  }
}

