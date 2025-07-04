package dao;

import entities.Category;
import entities.Expense;
import entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExpenseDAO {
  private final Connection conn;

  public ExpenseDAO(Connection conn) {
    this.conn = conn;
  }

  public void addExpense(Expense e) throws SQLException {
    if (!canAddOrUpdateExpense(e.getUser().getUsername(), e.getAmount())) {
      throw new SQLException("Budget limit exceeded");
    }

    String sql = "INSERT INTO expenses (user_id, category_id, amount, date, description) VALUES (?, ?, ?, ?, ?)";
    PreparedStatement ps = conn.prepareStatement(sql);
    ps.setString(1, e.getUser().getUsername());
    ps.setInt(2, e.getCategory().getId());
    ps.setDouble(3, e.getAmount());
    ps.setDate(4, new java.sql.Date(e.getDate().getTime()));
    ps.setString(5, e.getDescription());
    ps.executeUpdate();
  }

  public void updateExpense(Expense e) throws SQLException {
    if (!canAddOrUpdateExpense(e.getUser().getUsername(), e.getAmount(), e.getId())) {
      throw new SQLException("Budget limit exceeded");
    }

    String sql = "UPDATE expenses SET category_id=?, amount=?, date=?, description=? WHERE id=?";
    PreparedStatement ps = conn.prepareStatement(sql);
    ps.setInt(1, e.getCategory().getId());
    ps.setDouble(2, e.getAmount());
    ps.setDate(3, new java.sql.Date(e.getDate().getTime()));
    ps.setString(4, e.getDescription());
    ps.setInt(5, e.getId());
    ps.executeUpdate();
  }

  private boolean canAddOrUpdateExpense(String userId, double amountToAdd) throws SQLException {
    return canAddOrUpdateExpense(userId, amountToAdd, -1);
  }

  private boolean canAddOrUpdateExpense(String userId, double amountToAdd, int excludeExpenseId) throws SQLException {
    double totalExpenses = 0;
    double budgetLimit = 0;

    String expenseSql = excludeExpenseId == -1 ?
        "SELECT SUM(amount) AS total FROM expenses WHERE user_id=?" :
        "SELECT SUM(amount) AS total FROM expenses WHERE user_id=? AND id != ?";

    PreparedStatement expStmt = conn.prepareStatement(expenseSql);
    expStmt.setString(1, userId);
    if (excludeExpenseId != -1) expStmt.setInt(2, excludeExpenseId);
    ResultSet expRs = expStmt.executeQuery();
    if (expRs.next()) {
      totalExpenses = expRs.getDouble("total");
    }

    String budgetSql = "SELECT budget_limit FROM users WHERE username=?";
    PreparedStatement budgetStmt = conn.prepareStatement(budgetSql);
    budgetStmt.setString(1, userId);
    ResultSet budgetRs = budgetStmt.executeQuery();
    if (budgetRs.next()) {
      budgetLimit = budgetRs.getDouble("budget_limit");
    }

    return (totalExpenses + amountToAdd) <= budgetLimit;
  }

  public double getTotalSpent(String userId) throws SQLException {
    String sql = "SELECT SUM(amount) AS total FROM expenses WHERE user_id=?";
    PreparedStatement ps = conn.prepareStatement(sql);
    ps.setString(1, userId);
    ResultSet rs = ps.executeQuery();
    if (rs.next()) {
      return rs.getDouble("total");
    }
    return 0.0;
  }

  public void deleteExpense(int id) throws SQLException {
    String sql = "DELETE FROM expenses WHERE id=?";
    PreparedStatement ps = conn.prepareStatement(sql);
    ps.setInt(1, id);
    ps.executeUpdate();
  }

  public List<Expense> getExpensesByUser(String userId) throws SQLException {
    String sql = "SELECT * FROM expenses WHERE user_id=?";
    PreparedStatement ps = conn.prepareStatement(sql);
    ps.setString(1, userId);
    ResultSet rs = ps.executeQuery();

    List<Expense> list = new ArrayList<>();
    while (rs.next()) {
      Expense e = new Expense();
      e.setId(rs.getInt("id"));


      User user = new User() {};
      user.setUsername(rs.getString("user_id"));
      e.setUser(user);

      Category cat = new Category();
      cat.setId(rs.getInt("category_id"));
      e.setCategory(cat);

      e.setAmount(rs.getDouble("amount"));
      e.setDate(rs.getDate("date"));
      e.setDescription(rs.getString("description"));

      list.add(e);
    }
    return list;
  }

  public List<Expense> searchByCategoryId(int categoryId) {
    List<Expense> list = new ArrayList<>();
    String sql = "SELECT e.*, c.name AS category_name, u.username, u.first_name, u.last_name, u.role " +
        "FROM expenses e " +
        "JOIN categories c ON e.category_id = c.id " +
        "JOIN users u ON e.user_id = u.username " +
        "WHERE e.category_id = ?";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setInt(1, categoryId);
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        Expense e = new Expense();
        e.setId(rs.getInt("id"));

        Category category = new Category();
        category.setId(rs.getInt("category_id"));
        category.setName(rs.getString("category_name"));
        e.setCategory(category);

        User user = UserFactory.createUserFromResultSet(rs);
        e.setUser(user);

        e.setAmount(rs.getDouble("amount"));
        e.setDate(rs.getDate("date"));
        e.setDescription(rs.getString("description"));
        list.add(e);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return list;
  }

  public List<Expense> searchByDate(String date) {
    List<Expense> list = new ArrayList<>();
    String sql = "SELECT e.*, c.name AS category_name, u.username, u.first_name, u.last_name, u.role " +
        "FROM expenses e " +
        "JOIN categories c ON e.category_id = c.id " +
        "JOIN users u ON e.user_id = u.username " +
        "WHERE e.date = ?";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setDate(1, java.sql.Date.valueOf(date));
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        Expense e = new Expense();
        e.setId(rs.getInt("id"));

        Category category = new Category();
        category.setId(rs.getInt("category_id"));
        category.setName(rs.getString("category_name"));
        e.setCategory(category);

        User user = UserFactory.createUserFromResultSet(rs);
        e.setUser(user);

        e.setAmount(rs.getDouble("amount"));
        e.setDate(rs.getDate("date"));
        e.setDescription(rs.getString("description"));
        list.add(e);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return list;
  }

  public List<Expense> searchByDescription(String keyword) {
    List<Expense> list = new ArrayList<>();
    String sql = "SELECT e.*, c.name AS category_name, u.username, u.first_name, u.last_name, u.role " +
        "FROM expenses e " +
        "JOIN categories c ON e.category_id = c.id " +
        "JOIN users u ON e.user_id = u.username " +
        "WHERE e.description LIKE ?";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setString(1, "%" + keyword + "%");
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        Expense e = new Expense();
        e.setId(rs.getInt("id"));

        Category category = new Category();
        category.setId(rs.getInt("category_id"));
        category.setName(rs.getString("category_name"));
        e.setCategory(category);

        User user = UserFactory.createUserFromResultSet(rs);
        e.setUser(user);

        e.setAmount(rs.getDouble("amount"));
        e.setDate(rs.getDate("date"));
        e.setDescription(rs.getString("description"));
        list.add(e);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return list;
  }

}