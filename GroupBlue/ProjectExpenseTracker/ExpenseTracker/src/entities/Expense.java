package entities;

import entities.Category;
import entities.User;
import java.util.Date;

public class Expense {
  private int id;
  private User user;
  private Category category;
  private double amount;
  private Date date;
  private String description;

  public Expense() {}

  public Expense(User user, Category category, double amount, Date date, String description) {
    this.user = user;
    this.category = category;
    this.amount = amount;
    this.date = date;
    this.description = description;
  }

  public int getId() { return id; }
  public void setId(int id) { this.id = id; }

  public User getUser() { return user; }
  public void setUser(User user) { this.user = user; }

  public Category getCategory() { return category; }
  public void setCategory(Category category) { this.category = category; }

  public double getAmount() { return amount; }
  public void setAmount(double amount) { this.amount = amount; }

  public Date getDate() { return date; }
  public void setDate(Date date) { this.date = date; }

  public String getDescription() { return description; }
  public void setDescription(String description) { this.description = description; }
}