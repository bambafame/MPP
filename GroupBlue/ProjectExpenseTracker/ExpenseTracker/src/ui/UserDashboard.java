package ui;

import entities.RegularUser;
import java.sql.Connection;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;

public class UserDashboard extends JFrame {
  public UserDashboard(RegularUser user, Connection conn) {
    setTitle("User Dashboard - " + user.getUsername());
    setSize(600, 400);
    setLocationRelativeTo(null);

    /*try {
      ExpenseDAO dao = new ExpenseDAO(conn);
      List<Expense> expenses = dao.getUserExpenses(user.getUsername());

      DefaultListModel<String> model = new DefaultListModel<>();
      for (Expense e : expenses) {
        model.addElement("$" + e.getAmount() + " - " + e.getDescription());
      }

      JList<String> list = new JList<>(model);
      add(new JScrollPane(list));
    } catch (Exception ex) {
      ex.printStackTrace();
    }*/
  }
}
