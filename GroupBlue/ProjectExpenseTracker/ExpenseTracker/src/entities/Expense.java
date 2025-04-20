package entities;

import java.util.Date;

public class Expense {
  private int id;
  private String userId;
  private int categoryId;
  private double amount;
  private Date date;
  private String description;

  public Expense() {}

  public Expense(String userId, int categoryId, double amount, Date date, String description) {
    this.userId = userId;
    this.categoryId = categoryId;
    this.amount = amount;
    this.date = date;
    this.description = description;
  }

  public int getId() { return id; }
  public void setId(int id) { this.id = id; }

  public String getUserId() { return userId; }
  public void setUserId(String userId) { this.userId = userId; }

  public int getCategoryId() { return categoryId; }
  public void setCategoryId(int categoryId) { this.categoryId = categoryId; }

  public double getAmount() { return amount; }
  public void setAmount(double amount) { this.amount = amount; }

  public Date getDate() { return date; }
  public void setDate(Date date) { this.date = date; }

  public String getDescription() { return description; }
  public void setDescription(String description) { this.description = description; }
}
