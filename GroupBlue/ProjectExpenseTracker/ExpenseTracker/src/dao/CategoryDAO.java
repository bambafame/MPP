package dao;

import entities.Category;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {
  private final Connection conn;

  public CategoryDAO(Connection conn) {
    this.conn = conn;
  }

  // CREATE: Add a new category
  public void insertCategory(String name, String description) {
    String sql = "INSERT INTO categories(name, description) VALUES (?, ?)";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setString(1, name);
      ps.setString(2, description);
      ps.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  // READ: Get all categories
  public List<Category> findAll() {
    List<Category> categories = new ArrayList<>();
    String sql = "SELECT * FROM categories ORDER BY name";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        Category category = new Category();
        category.setId(rs.getInt("id"));
        category.setName(rs.getString("name"));
        category.setDescription(rs.getString("description"));
        categories.add(category);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return categories;
  }

  // UPDATE: Modify name and description of a category
  public void updateCategory(int id, String newName, String newDescription) {
    String sql = "UPDATE categories SET name = ?, description = ? WHERE id = ?";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setString(1, newName);
      ps.setString(2, newDescription);
      ps.setInt(3, id);
      ps.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  // DELETE: Remove a category by ID
  public void deleteCategory(int id) {
    String sql = "DELETE FROM categories WHERE id = ?";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setInt(1, id);
      ps.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  // (Optional) Find a category by ID
  public Category findById(int id) {
    String sql = "SELECT * FROM categories WHERE id = ?";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setInt(1, id);
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        Category category = new Category();
        category.setId(rs.getInt("id"));
        category.setName(rs.getString("name"));
        category.setDescription(rs.getString("description"));
        return category;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }
}