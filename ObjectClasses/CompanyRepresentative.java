package IPMS.ObjectClasses;

import java.util.ArrayList;
import java.util.List;

import IPMS.Companypackage.CompanyApprovalStatus;
import IPMS.Enums.*;
import IPMS.ObjectClasses.*;


public class CompanyRepresentative extends User {

     private String companyName;
     private String department;//of the company rep
     private String position;//of the company rep
     private CompanyApprovalStatus status;
     private List<Internship> internshipsCreated; //for each comp rep to track their internships

     //Constructor
     //Company Representatives ID is their company email address.
     public CompanyRepresentative(String email, String name, String companyName,
                                 String department, String position) {
          super(email, name, email);
          this.companyName = companyName;
          this.department = department;
          this.position = position;
          this.status = CompanyApprovalStatus.PENDING;
          this.internshipsCreated = new ArrayList<>();
     }
     
     ////////////
     public void requestRegistration(CareerCenter careerCenter) {
          careerCenter.addPendingCompany(this);
     }

     public void createInternship(String title, String description, InternshipLevel level,
                                 String preferredMajor, String openDate, String closeDate, int slots, SystemData data) {
          if (!isApproved()) {
               System.out.println("Account not approved by Career Center yet!");
               return;
         }
          if (internshipsCreated.size() >= 5) {
            System.out.println("You cannot create more than 5 internships.");
            return;
          }
          Internship internship = new Internship(title, description, level, preferredMajor,
                                               openDate, closeDate, companyName, this, slots);
          internshipsCreated.add(internship);
          data.InternshipMap.put(internship.getId(), internship);
          System.out.println("Internship created and awaiting Career Center approval.");
     }

     public void deleteInternship(Internship internship, SystemData data) {
          internshipsCreated.remove(internship);
          data.InternshipMap.remove(internship.getId());
     }
     public List<Internship> getInternshipsCreated() {
          return internshipsCreated;
     }

     public void toggleVisibility(Internship internship, boolean visible) {
          internship.setVisible(visible);
     }

     public void viewApplications(Internship internship, SystemData data) {
          System.out.println("Applications for " + internship.getTitle() + ":");
          for (Application app : InternshipMap.getApplications(data)) {
               System.out.println(app);
          }
     }

     public void approveApplication(Application app) {
        app.setStatus(ApplicationStatus.SUCCESSFUL);
     }

     public void rejectApplication(Application app) {
        app.setStatus(ApplicationStatus.UNSUCCESSFUL);
     }

     
     // setters and getters
     public void setStatus(CompanyApprovalStatus status){
          this.status = status;
     }

     public CompanyApprovalStatus getStatus() {
          return status;
     }

     // public void setApproved(boolean approved) { this.isApproved = approved; }
     
     // public boolean isApproved() { //the comp rep's account status
     //      return this.status == CompanyApprovalStatus.APPROVED; 
     // }

     public String getCompanyName() {
          return companyName;
     }
     public String getDepartment() {
          return department;
     }
     public String getPosition() {
          return position;    

     }
}
