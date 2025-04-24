// === Beautified ExpenseFormDialog.java ===
package ui;

import entities.Category;
import entities.Expense;
import entities.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDate;
import java.sql.Date;
import java.util.function.Consumer;

public class ExpenseFormDialog extends JDialog {
  private final JTextField amountField = new JTextField(15);
  private final JTextField categoryField = new JTextField(15);
  private final JTextField dateField = new JTextField(15);
  private final JTextArea descriptionArea = new JTextArea(3, 20);

  public ExpenseFormDialog(JFrame parent, String title, Expense existingExpense, Consumer<Expense> onSubmit, User user) {
    super(parent, title, true);
    setSize(450, 350);
    setLocationRelativeTo(parent);
    setLayout(new BorderLayout());

    JLabel headerLabel = new JLabel(title, SwingConstants.CENTER);
    headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
    headerLabel.setBorder(new EmptyBorder(15, 0, 10, 0));
    add(headerLabel, BorderLayout.NORTH);

    JPanel formPanel = new JPanel(new GridBagLayout());
    formPanel.setBorder(new EmptyBorder(10, 20, 10, 20));
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(8, 8, 8, 8);
    gbc.fill = GridBagConstraints.HORIZONTAL;

    gbc.gridx = 0; gbc.gridy = 0; formPanel.add(new JLabel("Amount:"), gbc);
    gbc.gridx = 1; formPanel.add(amountField, gbc);

    gbc.gridx = 0; gbc.gridy = 1; formPanel.add(new JLabel("Category ID:"), gbc);
    gbc.gridx = 1; formPanel.add(categoryField, gbc);

    gbc.gridx = 0; gbc.gridy = 2; formPanel.add(new JLabel("Date (yyyy-MM-dd):"), gbc);
    gbc.gridx = 1; formPanel.add(dateField, gbc);

    gbc.gridx = 0; gbc.gridy = 3; gbc.anchor = GridBagConstraints.NORTH;
    formPanel.add(new JLabel("Description:"), gbc);
    gbc.gridx = 1;
    descriptionArea.setLineWrap(true);
    descriptionArea.setWrapStyleWord(true);
    JScrollPane descScroll = new JScrollPane(descriptionArea);
    formPanel.add(descScroll, gbc);

    add(formPanel, BorderLayout.CENTER);

    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

    JButton okBtn = new JButton("✅ Save");
    JButton cancelBtn = new JButton("❌ Cancel");

    styleButton(okBtn, new Color(76, 175, 80));
    styleButton(cancelBtn, new Color(244, 67, 54));

    buttonPanel.add(okBtn);
    buttonPanel.add(cancelBtn);
    add(buttonPanel, BorderLayout.SOUTH);

    okBtn.addActionListener(e -> {
      try {
        double amount = Double.parseDouble(amountField.getText());
        int categoryId = Integer.parseInt(categoryField.getText());
        LocalDate date = LocalDate.parse(dateField.getText());
        String description = descriptionArea.getText();

        Category category = new Category();
        category.setId(categoryId);

        Expense expense = new Expense();
        expense.setUser(user);
        expense.setCategory(category);
        expense.setAmount(amount);
        expense.setDate(Date.valueOf(date));
        expense.setDescription(description);

        if (existingExpense != null) {
          expense.setId(existingExpense.getId());
        }

        onSubmit.accept(expense);
        dispose();
      } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Invalid input: " + ex.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
      }
    });

    cancelBtn.addActionListener(e -> dispose());

    if (existingExpense != null) {
      amountField.setText(String.valueOf(existingExpense.getAmount()));
      categoryField.setText(String.valueOf(existingExpense.getCategory().getId()));
      dateField.setText(existingExpense.getDate().toString());
      descriptionArea.setText(existingExpense.getDescription());
    } else {
      dateField.setText(LocalDate.now().toString());
    }
  }

  private void styleButton(JButton button, Color color) {
    button.setFont(new Font("Segoe UI", Font.BOLD, 14));
    button.setBackground(color);
    button.setForeground(Color.WHITE);
    button.setOpaque(true);
    button.setContentAreaFilled(true);
    button.setBorderPainted(false);
    button.setPreferredSize(new Dimension(120, 40));
  }
}