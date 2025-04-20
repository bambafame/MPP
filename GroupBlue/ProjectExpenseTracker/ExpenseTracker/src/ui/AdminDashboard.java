package ui;

import dao.CategoryDAO;
import dao.ExpenseDAO;
import entities.Admin;

import entities.Expense;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.util.List;

public class AdminDashboard extends JFrame {
    private final Admin admin;
    private final Connection conn;
    private final DefaultTableModel expenseModel;
    private final CategoryDAO categoryDAO;
    private final JTextField searchField;
    private final JComboBox<String> searchTypeBox;

    public AdminDashboard(Admin admin, Connection conn) {
        this.admin = admin;
        this.conn = conn;
        this.categoryDAO = new CategoryDAO(conn);

        setTitle("Admin Dashboard - " + admin.getUsername());
        setSize(900, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JLabel title = new JLabel("🛠 Admin Dashboard - All Expenses", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setBorder(new EmptyBorder(10, 10, 5, 10));
        add(title, BorderLayout.NORTH);



        // Search Panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBorder(new EmptyBorder(10, 20, 10, 20));

        searchTypeBox = new JComboBox<>(new String[]{"All", "Category", "Date", "Description"});
        searchField = new JTextField(20);
        JButton searchBtn = new JButton("🔍 Search");

        searchPanel.add(new JLabel("Search by:"));
        searchPanel.add(searchTypeBox);
        searchPanel.add(searchField);
        searchPanel.add(searchBtn);

        searchBtn.addActionListener(e -> performSearch());

        add(searchPanel, BorderLayout.BEFORE_FIRST_LINE);


        // Expenses Table
        expenseModel = new DefaultTableModel(new String[]{"ID", "User", "Category", "Amount", "Date", "Description"}, 0);
        JTable table = new JTable(expenseModel);
        table.setRowHeight(28);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new EmptyBorder(0, 20, 0, 20));
        add(scrollPane, BorderLayout.CENTER);

        // Manage categories button
        JButton manageCategoriesBtn = new JButton("📂 Manage Categories");
        manageCategoriesBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        manageCategoriesBtn.setBackground(new Color(33, 150, 243));
        manageCategoriesBtn.setForeground(Color.WHITE);
        manageCategoriesBtn.setOpaque(true);
        manageCategoriesBtn.setContentAreaFilled(true);
        manageCategoriesBtn.setBorderPainted(false);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        bottomPanel.add(manageCategoriesBtn);
        add(bottomPanel, BorderLayout.SOUTH);


        manageCategoriesBtn.addActionListener(e -> new CategoryManagementFrame(conn).setVisible(true));

        loadAllExpenses();
    }

    private void loadAllExpenses() {
        try {
            ExpenseDAO dao = new ExpenseDAO(conn);
            List<Expense> list = dao.getAllExpenses();
            populateTable(list);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading expenses: " + ex.getMessage());
        }
    }

    private void populateTable(List<Expense> list) {
        expenseModel.setRowCount(0);
        for (Expense e : list) {
            expenseModel.addRow(new Object[]{
                e.getId(),
                e.getUserId(),
                categoryDAO.findById(e.getCategoryId()).getName(),
                e.getAmount(),
                e.getDate(),
                e.getDescription()
            });
        }
    }

    private void performSearch() {
        try {
            String keyword = searchField.getText().trim();
            String type = (String) searchTypeBox.getSelectedItem();
            ExpenseDAO dao = new ExpenseDAO(conn);

            if (keyword.isEmpty() || type.equals("All")) {
                loadAllExpenses();
                return;
            }

            List<Expense> result;
            switch (type) {
                case "Category":
                    int catId = Integer.parseInt(keyword);
                    result = dao.searchByCategoryId(catId);
                    break;
                case "Date":
                    result = dao.searchByDate(keyword);
                    break;
                case "Description":
                    result = dao.searchByDescription(keyword);
                    break;
                default:
                    result = dao.getAllExpenses();
            }
            populateTable(result);

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Invalid search input.");
        }
    }

}

