package prob3.partB;

public class Cylinder  {
  private Circle circle;
  private double height;

  public Cylinder(double radius, double height) {
    this.circle = new Circle(radius);
    this.height = height;
  }

  public double getHeight() {
    return height;
  }

  public void setHeight(double height) {
    this.height = height;
  }

  public Circle getCircle() {
    return circle;
  }

  public void setCircle(Circle circle) {
    this.circle = circle;
  }

  public double computeVolume() {
    return circle.computeArea() * height;
  }
}
