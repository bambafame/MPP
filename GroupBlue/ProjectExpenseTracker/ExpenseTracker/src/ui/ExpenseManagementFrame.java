package ui;

import dao.ExpenseDAO;
import dao.UserDAO;
import entities.Category;
import entities.Expense;
import entities.User;

import java.io.File;
import java.io.FileWriter;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class ExpenseManagementFrame extends JFrame {
  private final User user;
  private final Connection conn;
  private final ExpenseDAO expenseDAO;
  private final UserDAO userDAO;
  private JTable table;
  private DefaultTableModel model;
  private JLabel summaryLabel;
  private JButton addBtn, updateBtn, deleteBtn;
  private boolean readOnly = false;
  private final JTextField searchField = new JTextField(15);
  private final JComboBox<String> searchTypeBox = new JComboBox<>(new String[]{"All", "Category", "Date", "Description"});

  public ExpenseManagementFrame(User user, Connection conn) {
    this.user = user;
    this.conn = conn;
    this.expenseDAO = new ExpenseDAO(conn);
    this.userDAO = new UserDAO(conn);
    setTitle("Expense Management - " + user.getUsername());
    setSize(850, 520);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    initUI();
    loadExpenses();
  }

  public void setReadOnly(boolean readOnly) {
    this.readOnly = readOnly;
    addBtn.setVisible(!readOnly);
    updateBtn.setVisible(!readOnly);
    deleteBtn.setVisible(!readOnly);
  }

  private void initUI() {
    JPanel panel = new JPanel(new BorderLayout());

    summaryLabel = new JLabel();
    summaryLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
    summaryLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    panel.add(summaryLabel, BorderLayout.NORTH);

    // === Search Panel ===
    JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    searchPanel.setBorder(new EmptyBorder(5, 10, 5, 10));
    JButton searchButton = new JButton("🔍 Search");
    styleButton(searchButton, new Color(33, 150, 243));
    searchButton.setForeground(Color.WHITE);
    searchButton.addActionListener(e -> performSearch());

    searchPanel.add(new JLabel("Search by:"));
    searchPanel.add(searchTypeBox);
    searchPanel.add(searchField);
    searchPanel.add(searchButton);
    panel.add(searchPanel, BorderLayout.BEFORE_FIRST_LINE);

    model = new DefaultTableModel(new String[]{"ID", "Amount", "Category", "Date", "Description"}, 0) {
      @Override
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    };
    table = new JTable(model);
    table.setRowHeight(28);
    table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
    table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
    JScrollPane scrollPane = new JScrollPane(table);

    // === Buttons ===
    addBtn = new JButton("➕ Add");
    updateBtn = new JButton("✏ Update");
    deleteBtn = new JButton("🗑 Delete");

    styleButton(addBtn, new Color(76, 175, 80));
    styleButton(updateBtn, new Color(255, 193, 7));
    styleButton(deleteBtn, new Color(244, 67, 54));

    addBtn.setForeground(Color.WHITE);
    updateBtn.setForeground(Color.BLACK);
    deleteBtn.setForeground(Color.WHITE);

    addBtn.addActionListener(e -> {
      if (readOnly) return;
      new ExpenseFormDialog(this, "Add Expense", null, expense -> {
        try {
          expenseDAO.addExpense(expense);
          loadExpenses();
        } catch (SQLException ex) {
          if (ex.getMessage().contains("Budget limit exceeded")) {
            JOptionPane.showMessageDialog(this, "Cannot add expense: Budget limit exceeded!");
          } else {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding expense.");
          }
        }
      }, user).setVisible(true);
    });

    updateBtn.addActionListener(e -> {
      if (readOnly) return;
      int row = table.getSelectedRow();
      if (row >= 0) {
        int id = Integer.parseInt(model.getValueAt(row, 0).toString());
        double amount = Double.parseDouble(model.getValueAt(row, 1).toString());
        int categoryId = Integer.parseInt(model.getValueAt(row, 2).toString());
        LocalDate date = LocalDate.parse(model.getValueAt(row, 3).toString());
        String description = model.getValueAt(row, 4).toString();

        Category category = new Category();
        category.setId(categoryId);

        Expense expense = new Expense();
        expense.setId(id);
        expense.setUser(user);
        expense.setCategory(category);
        expense.setAmount(amount);
        expense.setDate(java.sql.Date.valueOf(date));
        expense.setDescription(description);

        new ExpenseFormDialog(this, "Update Expense", expense, updatedExpense -> {
          try {
            expenseDAO.updateExpense(updatedExpense);
            loadExpenses();
          } catch (SQLException ex) {
            if (ex.getMessage().contains("Budget limit exceeded")) {
              JOptionPane.showMessageDialog(this, "Cannot update expense: Budget limit exceeded!");
            } else {
              ex.printStackTrace();
              JOptionPane.showMessageDialog(this, "Error updating expense.");
            }
          }
        }, user).setVisible(true);
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

    // Export CSV button
    JButton exportCsvBtn = new JButton("📁 Export CSV");
    exportCsvBtn.setForeground(Color.WHITE);
    styleButton(exportCsvBtn, new Color(100, 181, 246));
    exportCsvBtn.addActionListener(e -> exportToCSV());

    JPanel btnPanel = new JPanel();
    btnPanel.add(addBtn);
    btnPanel.add(updateBtn);
    btnPanel.add(deleteBtn);
    btnPanel.add(exportCsvBtn);

    panel.add(scrollPane, BorderLayout.CENTER);
    panel.add(btnPanel, BorderLayout.SOUTH);
    add(panel);
  }

  private void loadExpenses() {
    try {
      List<Expense> list = expenseDAO.getExpensesByUser(user.getUsername());
      model.setRowCount(0);
      for (Expense e : list) {
        model.addRow(new Object[]{
            e.getId(),
            e.getAmount(),
            e.getCategory().getId(),
            e.getDate().toString(),
            e.getDescription()
        });
      }
      updateSummary();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void performSearch() {
    try {
      String keyword = searchField.getText().trim();
      String type = (String) searchTypeBox.getSelectedItem();
      List<Expense> result;
      switch (type) {
        case "Category":
          int catId = Integer.parseInt(keyword);
          result = expenseDAO.searchByCategoryId(catId);
          break;
        case "Date":
          result = expenseDAO.searchByDate(keyword);
          break;
        case "Description":
          result = expenseDAO.searchByDescription(keyword);
          break;
        default:
          result = expenseDAO.getExpensesByUser(user.getUsername());
      }

      if (user.getRole() == User.Role.REGULAR) {
        result.removeIf(e -> !e.getUser().getUsername().equals(user.getUsername()));
      }
      model.setRowCount(0);
      for (Expense e : result) {
        model.addRow(new Object[]{
            e.getId(),
            e.getAmount(),
            e.getCategory().getId(),
            e.getDate().toString(),
            e.getDescription()
        });
      }
    } catch (Exception ex) {
      ex.printStackTrace();
      JOptionPane.showMessageDialog(this, "Search failed: " + ex.getMessage());
    }
  }

  private void updateSummary() {
    try {
      double totalSpent = expenseDAO.getTotalSpent(user.getUsername());
      double budgetLimit = userDAO.getBudgetLimit(user.getUsername());
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

  private void exportToCSV() {
    try {
      JFileChooser fileChooser = new JFileChooser();
      fileChooser.setDialogTitle("Save as CSV");
      int userSelection = fileChooser.showSaveDialog(this);

      if (userSelection == JFileChooser.APPROVE_OPTION) {
        File fileToSave = fileChooser.getSelectedFile();
        if (!fileToSave.getAbsolutePath().endsWith(".csv")) {
          fileToSave = new File(fileToSave.getAbsolutePath() + ".csv");
        }

        try (FileWriter fw = new FileWriter(fileToSave)) {
          // Write header
          for (int i = 0; i < model.getColumnCount(); i++) {
            fw.write(model.getColumnName(i));
            if (i < model.getColumnCount() - 1) fw.write(",");
          }
          fw.write("\n");

          // Write rows
          for (int row = 0; row < model.getRowCount(); row++) {
            for (int col = 0; col < model.getColumnCount(); col++) {
              Object value = model.getValueAt(row, col);
              fw.write(value != null ? value.toString() : "");
              if (col < model.getColumnCount() - 1) fw.write(",");
            }
            fw.write("\n");
          }

          JOptionPane.showMessageDialog(this, "Exported successfully to " + fileToSave.getAbsolutePath());
        }
      }
    } catch (Exception ex) {
      ex.printStackTrace();
      JOptionPane.showMessageDialog(this, "Export failed: " + ex.getMessage());
    }
  }

  private void styleButton(JButton button, Color color) {
    button.setFont(new Font("Segoe UI", Font.BOLD, 14));
    button.setBackground(color);
    button.setOpaque(true);
    button.setContentAreaFilled(true);
    button.setBorderPainted(false);
  }
}
