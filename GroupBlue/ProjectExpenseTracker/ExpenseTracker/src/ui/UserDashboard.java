package ui;

import entities.RegularUser;
import java.sql.Connection;
import javax.swing.*;

public class UserDashboard {
  public UserDashboard(RegularUser user, Connection conn) {
    (new ExpenseManagementFrame(user, conn)).setVisible(true);
  }
}

