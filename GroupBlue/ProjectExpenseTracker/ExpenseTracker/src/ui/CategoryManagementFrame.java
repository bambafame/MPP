package ui;

import dao.CategoryDAO;
import entities.Category;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.Connection;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class CategoryManagementFrame extends JFrame {
  private final CategoryDAO dao;
  private final DefaultTableModel tableModel;
  private final JTable table;

  public CategoryManagementFrame(Connection conn) {
    this.dao = new CategoryDAO(conn);

    setTitle("🗂 Category Management");
    setSize(700, 450);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    setLayout(new BorderLayout());

    // Table Setup
    tableModel = new DefaultTableModel(new String[]{"ID", "Name", "Description"}, 0);
    table = new JTable(tableModel);
    table.setRowHeight(28);
    table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 15));
    table.setGridColor(Color.LIGHT_GRAY);
    refreshTable();

    JScrollPane tableScroll = new JScrollPane(table);
    tableScroll.setBorder(new EmptyBorder(10, 10, 10, 10));

    // Button Panel
    JButton addBtn = new JButton("➕ Add");
    JButton updateBtn = new JButton("✏ Update");
    JButton deleteBtn = new JButton("🗑 Delete");

    Font btnFont = new Font("Segoe UI", Font.BOLD, 14);
    addBtn.setFont(btnFont);
    updateBtn.setFont(btnFont);
    deleteBtn.setFont(btnFont);

    addBtn.setBackground(new Color(76, 175, 80));      // green
    updateBtn.setBackground(new Color(255, 193, 7));   // yellow
    deleteBtn.setBackground(new Color(244, 67, 54));   // red


    addBtn.setForeground(Color.WHITE);
    updateBtn.setForeground(Color.WHITE);
    deleteBtn.setForeground(Color.WHITE);

    addBtn.setOpaque(true);
    updateBtn.setOpaque(true);
    deleteBtn.setOpaque(true);

    addBtn.setContentAreaFilled(true);
    updateBtn.setContentAreaFilled(true);
    deleteBtn.setContentAreaFilled(true);

    addBtn.setBorderPainted(false);
    updateBtn.setBorderPainted(false);
    deleteBtn.setBorderPainted(false);

    JPanel btnPanel = new JPanel();
    btnPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
    btnPanel.setLayout(new GridLayout(1, 3, 20, 0));
    btnPanel.add(addBtn);
    btnPanel.add(updateBtn);
    btnPanel.add(deleteBtn);

    // Add everything to frame
    add(new JLabel("  🗂 Manage Categories", SwingConstants.LEFT), BorderLayout.NORTH);
    add(tableScroll, BorderLayout.CENTER);
    add(btnPanel, BorderLayout.SOUTH);

    // Button Actions
    addBtn.addActionListener(e -> showCategoryForm("Add", -1));
    updateBtn.addActionListener(e -> {
      int row = table.getSelectedRow();
      if (row != -1) {
        int id = (int) tableModel.getValueAt(row, 0);
        showCategoryForm("Update", id);
      } else {
        JOptionPane.showMessageDialog(this, "Select a category to update.");
      }
    });
    deleteBtn.addActionListener(e -> {
      int row = table.getSelectedRow();
      if (row != -1) {
        int id = (int) tableModel.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Delete this category?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
          dao.deleteCategory(id);
          refreshTable();
        }
      } else {
        JOptionPane.showMessageDialog(this, "Select a category to delete.");
      }
    });
  }

  private void showCategoryForm(String mode, int categoryId) {
    JTextField nameField = new JTextField();
    JTextArea descArea = new JTextArea(4, 20);
    descArea.setWrapStyleWord(true);
    descArea.setLineWrap(true);
    JScrollPane scroll = new JScrollPane(descArea);

    JPanel panel = new JPanel(new GridLayout(0, 1, 5, 5));
    panel.setBorder(new EmptyBorder(10, 10, 10, 10));
    panel.add(new JLabel("Name:"));
    panel.add(nameField);
    panel.add(new JLabel("Description:"));
    panel.add(scroll);

    if (mode.equals("Update") && categoryId != -1) {
      Category cat = dao.findById(categoryId);
      nameField.setText(cat.getName());
      descArea.setText(cat.getDescription());
    }

    int result = JOptionPane.showConfirmDialog(this, panel, mode + " Category", JOptionPane.OK_CANCEL_OPTION);
    if (result == JOptionPane.OK_OPTION) {
      if (mode.equals("Add")) {
        dao.insertCategory(nameField.getText(), descArea.getText());
      } else {
        dao.updateCategory(categoryId, nameField.getText(), descArea.getText());
      }
      refreshTable();
    }
  }

  private void refreshTable() {
    tableModel.setRowCount(0);
    for (Category c : dao.findAll()) {
      tableModel.addRow(new Object[]{c.getId(), c.getName(), c.getDescription()});
    }
  }
}