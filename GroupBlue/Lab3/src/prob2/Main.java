package prob2;

public class Main {

  public static void main(String[] args) {
    // Create a landlord
    Landlord landlord = new Landlord("Cheikh", "Fame");

    // Create buildings with maintenance cost
    Building b1 = new Building(500.0);
    Building b2 = new Building(700.0);

    // Add apartments to building1
    b1.addAppartment(new Appartment(1200.0));
    b1.addAppartment(new Appartment(1000.0));
    b1.addAppartment(new Appartment(950.0));

    // Add apartments to building2
    b2.addAppartment(new Appartment(1100.0));
    b2.addAppartment(new Appartment(1150.0));

    // Add buildings to the landlord
    landlord.addBuilding(b1);
    landlord.addBuilding(b2);

    // Display profits for each building
    System.out.println("Building 1 Profit: $" + b1.computeProfit());
    System.out.println("Building 2 Profit: $" + b2.computeProfit());

    // Display total monthly profit for the landlord
    System.out.println("Landlord " + landlord.getFirstName() + " " + landlord.getLastName()
        + "'s total monthly profit: $" + landlord.monthyTotalProfit());
  }
}
