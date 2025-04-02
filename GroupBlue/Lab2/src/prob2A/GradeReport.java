package prob2A;

public class GradeReport {
  private Student student;
  private double grade;

  public GradeReport(Student student) {
    this.student = student;
  }

  public Student getStudent() {
    return student;
  }

  public void setStudent(Student student) {
    this.student = student;
  }

  public double getGrade() {
    return grade;
  }

  public void setGrade(double grade) {
    this.grade = grade;
  }

  @Override
  public String toString() {
    return "GradeReport{grade=" + grade + ", student=" + student.getName() + "}";
  }
}
