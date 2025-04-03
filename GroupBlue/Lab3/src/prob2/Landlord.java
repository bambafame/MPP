package prob2;

import java.util.ArrayList;
import java.util.List;

public class Landlord {
  private String firstName;
  private String lastName;
  private List<Building> buildings;

  public Landlord(String firstName, String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
    buildings = new ArrayList<>();
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public void addBuilding(Building building) {
    this.buildings.add(building);
  }

  public List<Building> getBuildings() {
    return buildings;
  }

  public void setBuildings(List<Building> buildings) {
    this.buildings = buildings;
  }

  public double monthyTotalProfit() {
    return this.buildings.stream().mapToDouble(Building::computeProfit).sum();
  }
}
