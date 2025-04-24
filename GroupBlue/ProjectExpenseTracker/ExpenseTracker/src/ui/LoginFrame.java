package ui;

import entities.Admin;
import entities.RegularUser;
import entities.User;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import service.AuthService;

public class LoginFrame extends JFrame {
  private JTextField usernameField;
  private JPasswordField passwordField;

  public LoginFrame() {
    setTitle("🔐 Expense Tracker Login");
    setSize(400, 250);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    setLayout(new BorderLayout());

    JPanel centerPanel = new JPanel(new GridLayout(2, 2, 10, 10));
    centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

    centerPanel.add(new JLabel("Username:"));
    usernameField = new JTextField();
    centerPanel.add(usernameField);

    centerPanel.add(new JLabel("Password:"));
    passwordField = new JPasswordField();
    centerPanel.add(passwordField);

    JButton loginButton = new JButton("🔐 Login");
    loginButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
    loginButton.setBackground(new Color(33, 150, 243));
    loginButton.setForeground(Color.WHITE);
    loginButton.setOpaque(true);
    loginButton.setContentAreaFilled(true);
    loginButton.setBorderPainted(false);

    JPanel southPanel = new JPanel();
    southPanel.add(loginButton);

    // Add panels to frame
    add(centerPanel, BorderLayout.CENTER);
    add(southPanel, BorderLayout.SOUTH);

    loginButton.addActionListener(e -> login());
  }

  private void login() {
    try {
      Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/expenseTracker", "admin", "M@khfous$92");
      AuthService authService = new AuthService(conn);
      User user = authService.login(usernameField.getText(), new String(passwordField.getPassword()));
      if (user != null) {
        JOptionPane.showMessageDialog(this, "Welcome, " + user.getFirstname());

        if (user.getRole() == User.Role.REGULAR) {
          new UserDashboard((RegularUser) user, conn);
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

