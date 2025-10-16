package project.internship-placement-management-system;

public class Application {
     private Student student;
     private Internship internship;
     private String status; // Pending, Successful, Unsuccessful

     //constructor
     public Application(Student student, Internship internship) {
          this.student = student;
          this.internship = internship;
          this.status = "Pending";
     }

     public void setStatus(String status) {//of application
          this.status = status;}

     public String getStatus() {
          return status;}

     public Student getStudent() {
          return student;}

     public Internship getInternship() {
          return internship;}
}

