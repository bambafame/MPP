package ui;

import dao.ExpenseDAO;
import dao.UserDAO;
import entities.Admin;
import entities.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

public class AdminDashboard extends JFrame {
    private final Connection conn;
    private final ExpenseDAO expenseDAO;
    private final UserDAO userDAO;
    private JTable userTable;

    public AdminDashboard(Admin admin, Connection conn) {
        this.conn = conn;
        this.expenseDAO = new ExpenseDAO(conn);
        this.userDAO = new UserDAO(conn);

        setTitle("Admin Dashboard - " + admin.getUsername());
        setSize(950, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Welcome Label
        JLabel welcomeLabel = new JLabel("Welcome Admin: " + admin.getFirstname(), SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        welcomeLabel.setForeground(new Color(33, 150, 243));
        welcomeLabel.setBorder(new EmptyBorder(15, 10, 15, 10));
        add(welcomeLabel, BorderLayout.NORTH);

        // Top Panel with Buttons
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        topPanel.setBorder(new EmptyBorder(10, 20, 10, 20));

        JButton manageCategoriesBtn = new JButton("📂 Manage Categories");
        JButton manageExpensesBtn = new JButton("💰 Manage Expenses");

        styleButton(manageCategoriesBtn, new Color(76, 175, 80));
        styleButton(manageExpensesBtn, new Color(255, 193, 7));

        manageCategoriesBtn.addActionListener(e -> new CategoryManagementFrame(conn).setVisible(true));
        manageExpensesBtn.addActionListener(e -> new ExpenseManagementFrame(admin, conn).setVisible(true));

        topPanel.add(manageCategoriesBtn);
        topPanel.add(manageExpensesBtn);
        add(topPanel, BorderLayout.CENTER);

        // User Table Section
        String[] columnNames = {"Username", "Budget Limit ($)", "Total Spent ($)", "Action"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3;
            }
        };

        userTable = new JTable(tableModel);
        userTable.setRowHeight(32);
        userTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        userTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 15));

        JScrollPane scrollPane = new JScrollPane(userTable);
        scrollPane.setPreferredSize(new Dimension(880, 350));
        scrollPane.setBorder(new EmptyBorder(10, 20, 20, 20));
        add(scrollPane, BorderLayout.SOUTH);

        loadUserData(tableModel);
        addButtonToTable();
    }

    private void styleButton(JButton button, Color background) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(background);
        button.setForeground(Color.WHITE);
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(200, 40));
    }

    private void loadUserData(DefaultTableModel model) {
        try {
            List<String> usernames = userDAO.getAllRegularUsernames();
            model.setRowCount(0);
            for (String username : usernames) {
                double spent = expenseDAO.getTotalSpent(username);
                double limit = userDAO.getBudgetLimit(username);
                Vector<Object> row = new Vector<>();
                row.add(username);
                row.add(String.format("%.2f", limit));
                row.add(String.format("%.2f", spent));
                row.add("📄 View Expenses");
                model.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "❌ Failed to load user data.");
        }
    }

    private void addButtonToTable() {
        TableColumnModel columnModel = userTable.getColumnModel();
        columnModel.getColumn(3).setCellRenderer(new ButtonRenderer());
        columnModel.getColumn(3).setCellEditor(new ButtonEditor(new JCheckBox(), conn, userTable));
    }

    private static class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setFont(new Font("Segoe UI", Font.BOLD, 13));
            setBackground(new Color(33, 150, 243));
            setOpaque(true);
            setForeground(Color.WHITE);
            setBorderPainted(false);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus,
            int row, int column) {
            setText((value == null) ? "View" : value.toString());
            return this;
        }
    }

    private static class ButtonEditor extends DefaultCellEditor {
        private final JButton button;
        private String username;
        private boolean clicked;
        private final JTable table;
        private final Connection conn;

        public ButtonEditor(JCheckBox checkBox, Connection conn, JTable table) {
            super(checkBox);
            this.conn = conn;
            this.table = table;
            this.button = new JButton("View Expenses");
            button.setFont(new Font("Segoe UI", Font.BOLD, 13));
            button.setBackground(new Color(33, 150, 243));
            button.setForeground(Color.WHITE);
            button.setOpaque(true);
            button.setContentAreaFilled(true);
            button.setBorderPainted(false);
            button.addActionListener(e -> fireEditingStopped());
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
            username = table.getValueAt(row, 0).toString();
            button.setText((value == null) ? "View" : value.toString());
            clicked = true;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (clicked) {
                try {
                    UserDAO userDAO = new UserDAO(conn);
                    User user = userDAO.findByUsername(username);
                    ExpenseManagementFrame frame = new ExpenseManagementFrame(user, conn);
                    frame.setReadOnly(true);
                    frame.setVisible(true);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(table, "❌ Failed to open expense management for user: " + username);
                }
            }
            clicked = false;
            return "📄 View Expenses";
        }

        @Override
        public boolean stopCellEditing() {
            clicked = false;
            return super.stopCellEditing();
        }

        @Override
        protected void fireEditingStopped() {
            super.fireEditingStopped();
        }
    }
}
