package prob2;

import java.util.ArrayList;
import java.util.List;

public class Building {
  private double maintenanceCost;
  private List<Appartment> appartments;

  public Building(double maintenanceCost) {
    this.maintenanceCost = maintenanceCost;
    this.appartments = new ArrayList<>();
  }

  public double getMaintenanceCost() {
    return maintenanceCost;
  }

  public void setMaintenanceCost(double maintenanceCost) {
    this.maintenanceCost = maintenanceCost;
  }

  public List<Appartment> getAppartments() {
    return appartments;
  }

  public void setAppartments(List<Appartment> appartments) {
    this.appartments = appartments;
  }

  public void addAppartment(Appartment appartment) {
    this.appartments.add(appartment);
  }

  public double computeProfit() {
    double totalRent = appartments.stream().mapToDouble(Appartment::getRent).sum();
    return totalRent - this.maintenanceCost;
  }
}
