package prob2A;

public class Main {

  public static void main(String[] args) {
    Student student = new Student("Yakoubou");
    student.getGradeReport().setGrade(18);

    System.out.println(student);
    System.out.println(student.getGradeReport());
    System.out.println("Student from GradeReport: " + student.getGradeReport().getStudent().getName());
  }
}
