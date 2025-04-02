package prob2A;

public class Student {
  private String name;
  private GradeReport gradeReport;

  public Student(String name) {
    this.name = name;
    this.gradeReport = new GradeReport(this);
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public GradeReport getGradeReport() {
    return gradeReport;
  }

  public void setGradeReport(GradeReport gradeReport) {
    this.gradeReport = gradeReport;
  }

  @Override
  public String toString() {
    return "Student{name='" + name + "', gradeReport=" + gradeReport.getGrade() + "}";
  }
}
