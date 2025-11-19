
package IPMS.ObjectClasses;

import java.util.ArrayList;
import java.util.List;

//import Companypackage.CompanyApprovalStatus;
import IPMS.Enums.*;
import IPMS.ObjectClasses.*;
import IPMS.Enums.*;
import IPMS.System.SystemData;
import java.time.LocalDate;

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

     public CompanyRepresentative(String CompRepID, String name, String email, String companyName,
                                 String department, String position, CompanyApprovalStatus status) {
          super(CompRepID, name, email);
          this.companyName = companyName;
          this.department = department;
          this.position = position;
          this.status = status;
          this.internshipsCreated = new ArrayList<>();
     }

     
     /* 
     public void requestRegistration(CareerCenter careerCenter) {
          careerCenter.addPendingCompany(this);
     }*/

     public void createInternship(String title, String description, InternshipLevel level,
                                  String preferredMajor, LocalDate openDate, LocalDate closeDate, int slots) {
          if (!isApproved()) {
               System.out.println("Account not approved by Career Center yet!");
               return;
         }
          if (SystemData.getILMcompany(this.getUserId()).size() >= 5) {
            System.out.println("You cannot create more than 5 internships.");
            return;
          }
          String compRepID = this.getUserId();
          Internship internship = new Internship(title, description, level, preferredMajor,
                                               openDate, closeDate, companyName, compRepID, slots);
          internshipsCreated.add(internship);

          SystemData.InternshipCreation(internship);
          System.out.println("Internship created and awaiting Career Center approval.");
     }

     /** 
      * @return boolean
      */
     public boolean isApproved() {
          return status == CompanyApprovalStatus.APPROVED;
     }

     /** 
      * @param internship
      */
     public void deleteInternship(Internship internship) {
          internshipsCreated.remove(internship);
          SystemData.removeInternship(internship);
     }
     /** 
      * @return List<Internship>
      */
     public List<Internship> getInternshipsCreated() {
          String key = this.getCompanyRepID();
          return SystemData.getILMcompany(key);
     }

     /** 
      * @param internship
      * @param visible
      */
     public void toggleVisibility(Internship internship, boolean visible) {
          internship.setVisible(visible);
     }

     /** 
      * @param internship
      */
     // views applications for a specific internship 
     public void viewApplications(Internship internship) {
          System.out.println("Applications for " + internship.getTitle() + ":");
          String key = internship.getInternshipId();
          List<Application> appList = SystemData.getALMinternship(key);
          if (appList == null) {
               System.out.println("No Applications as of this time");
          }
          else {
               for (Application app : appList) {
                    System.out.println(app);
               }
          }
     }

     /** 
      * @param app
      */
     public void approveApplication(Application app) {
        app.setStatus(ApplicationStatus.SUCCESSFUL);
        
     }

     /** 
      * @param app
      */
     public void rejectApplication(Application app) {
        app.setStatus(ApplicationStatus.UNSUCCESSFUL);
     }


     /** 
      * @param status
      */
     // setters and getters
     public void setStatus(CompanyApprovalStatus status){
          this.status = status;
     }



     /** 
      * @return String
      */
     // public void setApproved(boolean approved) { this.isApproved = approved; }
     
     // public boolean isApproved() { //the comp rep's account status
     //      return this.status == CompanyApprovalStatus.APPROVED; 
     // }

     public String getCompanyName() {
          return companyName;
     }
     /** 
      * @return String
      */
     public String getDepartment() {
          return department;
     }
     /** 
      * @return String
      */
     public String getPosition() {
          return position;    
     }
     /** 
      * @return String
      */
     public String getCompanyRepID() {
          return getUserId();
     }
     /** 
      * @return CompanyApprovalStatus
      */
     public CompanyApprovalStatus getStatus() {
          return status;
     }

}

