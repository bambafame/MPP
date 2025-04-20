package entities;

import java.util.Objects;

public class Category {
  private int id;
  private String name;
  private String description;

  public Category() {
  }

  public Category(String name) {
    this.name = name;
  }

  public Category(String name, String description) {
    this.name = name;
    this.description = description;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Category category)) {
      return false;
    }
    return id == category.id && Objects.equals(name, category.name)
        && Objects.equals(description, category.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, description);
  }
}
