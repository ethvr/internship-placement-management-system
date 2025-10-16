package project;

public class Student {
     private String userID;
     private String name;
     private String major;
     private double gpa;
     
     public Student(String userID, String name, String major, double gpa) {
          this.userID = userID;
          this.name = name;
          this.major = major;
          this.gpa = gpa;
     }
     
     public String getUserID() {
          return userID;
     }
     
     public String getName() {
          return name;
     }
     
     public String getMajor() {
          return major;
     }
     
     public double getGpa() {
          return gpa;
     }
}
