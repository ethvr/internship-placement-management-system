package project.internship-placement-management-system;

import java.util.ArrayList;
import java.util.List;

public class CompanyRepresentative extends User {

     private String companyName;
     private String department;
     private String position;
     private boolean isApproved;
     private List<Internship> internshipsCreated;

     //Company Representatives ID is their company email address.
     public CompanyRepresentative(String email, String name, String companyName, String department, String position) {
          super(email, name);
          this.companyName = companyName;
          this.department = department;
          this.position = position;
          this.isApproved = false;
          this.internshipsCreated = new ArrayList<>();
     }

     ////////////
     public void requestRegistration(CareerCenter careerCenter) {
          careerCenter.addPendingCompany(this);
     }

     public void createInternship(String title, String description, String level,
                                  String preferredMajor, String openDate, String closeDate, int slots) {
          if (!isApproved) {
               System.out.println("Account not approved by Career Center yet!");
               return;
         }
          if (internshipsCreated.size()>=5) {
               System.out.println("You cannot create more than 5 internships.");
               return;
          }
          Internship internship = new Internship(title, description, level, preferredMajor, openDate, closeDate, companyName,this, slots);//create internship 
          internshipsCreated.add(internship);
          System.out.println("Internship created and awaiting Career Center approval.");
     }

     public void toggleVisibility(Internship internship, boolean visible) {
          internship.setVisible(visible);
     }

     public void viewApplications(Internship internship) {
          System.out.println("Applications for " + internship.getTitle() + ":");
          List<Application> applicationList = internship.getApplications();
          if (applicationList.isEmpty()) {
               System.out.println("No applications yet.");
               return;
          }
          for (Application app : applicationList) {
               System.out.println("Student: " + app.getStudent().getName() + ", Status: " + app.getStatus());
          }
     }

     public void approveApplication(Application app) {
          app.setStatus("Successful");
     }

     public void rejectApplication(Application app) {
          app.setStatus("Unsuccessful");
     }

     // setters and getters
     public void setApproved(boolean approved) {
          this.isApproved = approved; }
     public boolean isApproved() { 
          return isApproved; }
     
}

