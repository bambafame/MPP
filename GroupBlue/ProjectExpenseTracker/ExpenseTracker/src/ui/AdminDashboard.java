package ui;

import entities.Admin;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.sql.Connection;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class AdminDashboard extends JFrame {
  public AdminDashboard(Admin admin, Connection conn) {
    setTitle("Admin Dashboard - " + admin.getUsername());
    setSize(600, 400);
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    setLocationRelativeTo(null);

    JLabel welcomeLabel = new JLabel("Welcome Admin: " + admin.getFirstname(), SwingConstants.CENTER);

    JButton manageCategoriesBtn = new JButton("Manage Categories");
    // TODO: Add button listener to open category management

    JPanel panel = new JPanel(new FlowLayout());
    panel.add(manageCategoriesBtn);

    add(welcomeLabel, BorderLayout.NORTH);
    add(panel, BorderLayout.CENTER);
  }
}
