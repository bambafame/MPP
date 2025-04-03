package prob3.partA;

public class Cylinder extends Circle{
  private double height;

  public Cylinder(double radius, double height) {
    super(radius);
    this.height = height;
  }

  public double getHeight() {
    return height;
  }

  public void setHeight(double height) {
    this.height = height;
  }

  public double computeVolume() {
    return super.computeArea() * height;
  }
}
