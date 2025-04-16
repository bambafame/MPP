package ui;

import entities.Admin;
import entities.RegularUser;
import entities.User;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import service.AuthService;

public class LoginFrame extends JFrame {
  private JTextField usernameField;
  private JPasswordField passwordField;

  public LoginFrame() {
    setTitle("Login");
    setSize(300, 200);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(EXIT_ON_CLOSE);

    usernameField = new JTextField();
    passwordField = new JPasswordField();
    JButton loginBtn = new JButton("Login");

    loginBtn.addActionListener(e -> login());

    setLayout(new GridLayout(3, 2));
    add(new JLabel("Username:"));
    add(usernameField);
    add(new JLabel("Password:"));
    add(passwordField);
    add(new JLabel());
    add(loginBtn);
  }

  private void login() {
    try {
      Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/expenseTracker", "admin", "M@khfous$92");
      AuthService authService = new AuthService(conn);
      User user = authService.login(usernameField.getText(), new String(passwordField.getPassword()));
      if (user != null) {
        JOptionPane.showMessageDialog(this, "Welcome, " + user.getFirstname());

        if (user.getRole() == User.Role.REGULAR) {
          new UserDashboard((RegularUser) user, conn).setVisible(true);
        } else if (user.getRole() == User.Role.ADMIN) {
          new AdminDashboard((Admin) user, conn).setVisible(true);
        }

        this.dispose(); // Close login window
      } else {
        JOptionPane.showMessageDialog(this, "Invalid credentials");
      }
    } catch (Exception ex) {
      ex.printStackTrace();
      JOptionPane.showMessageDialog(this, "Error connecting to DB");
    }
  }
}

