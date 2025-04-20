package ui;

import dao.ExpenseDAO;
import dao.UserDAO;
import entities.Expense;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class ExpenseManagementFrame extends JFrame {
  private final String userId;
  private final Connection conn;
  private final ExpenseDAO expenseDAO;
  private final UserDAO userDAO;
  private JTable table;
  private DefaultTableModel model;
  private JLabel summaryLabel;
  private JButton addBtn, updateBtn, deleteBtn;
  private boolean readOnly = false;

  public ExpenseManagementFrame(String userId, Connection conn) {
    this.userId = userId;
    this.conn = conn;
    this.expenseDAO = new ExpenseDAO(conn);
    this.userDAO = new UserDAO(conn);
    setTitle("Expense Management - " + userId);
    setSize(700, 500);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    initUI();
    loadExpenses();
  }

  public void setReadOnly(boolean readOnly) {
    this.readOnly = readOnly;
    addBtn.setEnabled(!readOnly);
    updateBtn.setEnabled(!readOnly);
    deleteBtn.setEnabled(!readOnly);
  }

  private void initUI() {
    JPanel panel = new JPanel(new BorderLayout());

    summaryLabel = new JLabel();
    summaryLabel.setFont(new Font("Arial", Font.BOLD, 14));
    summaryLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    panel.add(summaryLabel, BorderLayout.NORTH);

    model = new DefaultTableModel(new String[]{"ID", "Amount", "Category ID", "Date", "Description"}, 0);
    table = new JTable(model);
    JScrollPane scrollPane = new JScrollPane(table);

    JPanel formPanel = new JPanel(new GridLayout(5, 2, 5, 5));
    JTextField amountField = new JTextField();
    JTextField categoryIdField = new JTextField();
    JTextField dateField = new JTextField(LocalDate.now().toString());
    JTextField descField = new JTextField();

    formPanel.add(new JLabel("Amount:"));
    formPanel.add(amountField);
    formPanel.add(new JLabel("Category ID:"));
    formPanel.add(categoryIdField);
    formPanel.add(new JLabel("Date (yyyy-MM-dd):"));
    formPanel.add(dateField);
    formPanel.add(new JLabel("Description:"));
    formPanel.add(descField);

    addBtn = new JButton("Add");
    updateBtn = new JButton("Update");
    deleteBtn = new JButton("Delete");

    addBtn.addActionListener(e -> {
      if (readOnly) return;

      try {
        double amount = Double.parseDouble(amountField.getText());
        int categoryId = Integer.parseInt(categoryIdField.getText());
        LocalDate localDate = LocalDate.parse(dateField.getText());
        String description = descField.getText();
        Expense expense = new Expense(userId, categoryId, amount, java.sql.Date.valueOf(localDate), description);
        expenseDAO.addExpense(expense);
        loadExpenses();
      } catch (SQLException ex) {
        if (ex.getMessage().contains("Budget limit exceeded")) {
          JOptionPane.showMessageDialog(this, "Cannot add expense: Budget limit exceeded!");
        } else {
          ex.printStackTrace();
          JOptionPane.showMessageDialog(this, "Error adding expense.");
        }
      } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Invalid input");
      }
    });

    updateBtn.addActionListener(e -> {
      if (readOnly) return;

      int row = table.getSelectedRow();
      if (row >= 0) {
        try {
          int id = Integer.parseInt(model.getValueAt(row, 0).toString());
          double amount = 0;
          if(!amountField.getText().isEmpty() && !amountField.getText().isBlank()) {
            amount = Double.parseDouble(amountField.getText());
          }
          int categoryId = Integer.parseInt(categoryIdField.getText());
          LocalDate localDate = LocalDate.parse(dateField.getText());
          String description = descField.getText();
          Expense expense = new Expense(userId, categoryId, amount, java.sql.Date.valueOf(localDate), description);
          expenseDAO.updateExpense(expense);
          loadExpenses();
        } catch (SQLException ex) {
          if (ex.getMessage().contains("Budget limit exceeded")) {
            JOptionPane.showMessageDialog(this, "Cannot update expense: Budget limit exceeded!");
          } else {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating expense.");
          }
        } catch (Exception ex) {
          ex.printStackTrace();
          JOptionPane.showMessageDialog(this, "Invalid update");
        }
      }
    });

    deleteBtn.addActionListener(e -> {
      if (readOnly) return;

      int row = table.getSelectedRow();
      if (row >= 0) {
        try {
          int id = Integer.parseInt(model.getValueAt(row, 0).toString());
          expenseDAO.deleteExpense(id);
          loadExpenses();
        } catch (Exception ex) {
          ex.printStackTrace();
        }
      }
    });

    JPanel btnPanel = new JPanel();
    btnPanel.add(addBtn);
    btnPanel.add(updateBtn);
    btnPanel.add(deleteBtn);

    panel.add(formPanel, BorderLayout.WEST);
    panel.add(scrollPane, BorderLayout.CENTER);
    panel.add(btnPanel, BorderLayout.SOUTH);
    add(panel);
  }

  private void loadExpenses() {
    try {
      List<Expense> list = expenseDAO.getExpensesByUser(userId);
      model.setRowCount(0);
      for (Expense e : list) {
        model.addRow(new Object[]{
          e.getId(), e.getAmount(), e.getCategoryId(),
          e.getDate().toString(), e.getDescription()
        });
      }
      updateSummary();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void updateSummary() {
    try {
      double totalSpent = expenseDAO.getTotalSpent(userId);
      double budgetLimit = userDAO.getBudgetLimit(userId);
      summaryLabel.setText(String.format("Total Spent: $%.2f / Budget Limit: $%.2f", totalSpent, budgetLimit));

      if (totalSpent >= 0.8 * budgetLimit) {
        summaryLabel.setForeground(Color.RED);
      } else {
        summaryLabel.setForeground(new Color(0, 128, 0));
      }
    } catch (SQLException e) {
      e.printStackTrace();
      summaryLabel.setText("Could not load budget summary");
      summaryLabel.setForeground(Color.GRAY);
    }
  }
}

