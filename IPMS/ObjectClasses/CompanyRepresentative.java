package IPMS.ObjectClasses;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import IPMS.Enums.*;
import IPMS.System.SystemData;
import IPMS.ObjectClasses.Internship;
package IPMS.ObjectClasses;

import java.util.ArrayList;
import java.util.List;

import IPMS.Companypackage.CompanyApprovalStatus;
import IPMS.Enums.*;
import IPMS.ObjectClasses.*;


import IPMS.Enums.*;
import IPMS.System.SystemData;
<<<<<<< HEAD
>>>>>>> c4cf1788ed1d81e3e8bc804b1428193d484ce307
=======
>>>>>>> c4cf1788ed1d81e3e8bc804b1428193d484ce307

public class CompanyRepresentative extends User {

     private String companyName;
<<<<<<< HEAD
<<<<<<< HEAD
     private String department;
     private String position;
     private CompanyApprovalStatus status;
     private int noOfInternships;/////i added this since i deleted the array list for internships

     // Constructor
     // Company Representatives ID is their company email address.
=======
=======
>>>>>>> c4cf1788ed1d81e3e8bc804b1428193d484ce307
     private String department;//of the company rep
     private String position;//of the company rep
     private CompanyApprovalStatus status;
     private List<Internship> internshipsCreated; //for each comp rep to track their internships

     //Constructor
     //Company Representatives ID is their company email address.
<<<<<<< HEAD
>>>>>>> c4cf1788ed1d81e3e8bc804b1428193d484ce307
=======
>>>>>>> c4cf1788ed1d81e3e8bc804b1428193d484ce307
     public CompanyRepresentative(String email, String name, String companyName,
                                 String department, String position) {
          super(email, name, email);
          this.companyName = companyName;
          this.department = department;
          this.position = position;
          this.status = CompanyApprovalStatus.PENDING;
<<<<<<< HEAD
<<<<<<< HEAD
          this.noOfInternships = 0;
     }
     
     /**
      * Request registration with Career Center
      */
=======
=======
>>>>>>> c4cf1788ed1d81e3e8bc804b1428193d484ce307
          this.internshipsCreated = new ArrayList<>();
     }
     
     ////////////
<<<<<<< HEAD
>>>>>>> c4cf1788ed1d81e3e8bc804b1428193d484ce307
=======
>>>>>>> c4cf1788ed1d81e3e8bc804b1428193d484ce307
     public void requestRegistration(CareerCenter careerCenter) {
          careerCenter.addPendingCompany(this);
     }

<<<<<<< HEAD
<<<<<<< HEAD
     /**
      * Create a new internship opportunity
      */
     public void createInternship(String title, String description, InternshipLevel level,
                                 String preferredMajor, LocalDate openDate, LocalDate closeDate, int slots) {
          if (!isApproved()) {
               System.out.println("Account not approved by Career Center yet!");
               return;
          }
          
          if (noOfInternships>= 5) {
               System.out.println("You cannot create more than 5 internships.");
               return;
          }
          
          Internship internship = new Internship(title, description, level, preferredMajor,
                                               openDate, closeDate, companyName, this.companyName, slots);
          
          // Store both locally and in SystemData
          noOfInternships++;
          SystemData.setInternshipKeyValue(internship.getId(), internship);
          
          System.out.println("Internship created and awaiting Career Center approval.");
     }

     /**
      * Delete an internship
      */
     public void deleteInternship(String internshipId) {
          SystemData.removeinternship(internshipId);
     }
     
     /**
      * Get all internships created by this company representative
      */
     public List<Internship> getInternshipsCreated() {
          List<Internship> internships = new ArrayList<>();
          Map<String, Internship> allInternships = SystemData.getInternshipMap();
          
          for (Internship internship : allInternships.values()) {
               if (internship.getCompanyName().equals(this.companyName)) {
                    internships.add(internship);
               }
          }
          return internships;
     }

     /**
      * Toggle visibility of an internship
      */
=======
=======
>>>>>>> c4cf1788ed1d81e3e8bc804b1428193d484ce307
     public void createInternship(String title, String description, InternshipLevel level,
                                 String preferredMajor, String openDate, String closeDate, int slots) {
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

<<<<<<< HEAD
>>>>>>> c4cf1788ed1d81e3e8bc804b1428193d484ce307
=======
>>>>>>> c4cf1788ed1d81e3e8bc804b1428193d484ce307
     public void toggleVisibility(Internship internship, boolean visible) {
          internship.setVisible(visible);
     }

<<<<<<< HEAD
<<<<<<< HEAD
     /**
      * View all applications for a specific internship
      */
     public void viewApplications(Internship internship) {
          System.out.println("Applications for " + internship.getTitle() + ":");
          
          Map<String, Application> allApplications = SystemData.getApplicationMap();
          for (Application app : allApplications.values()) {
               if (app.getInternshipId().equals(internship.getId())) {
                    System.out.println(app);
               }
          }
     }

     /**
      * Approve a student's application
      */
     public void approveApplication(Application app) {
          app.setStatus(ApplicationStatus.SUCCESSFUL);
     }

     /**
      * Reject a student's application
      */
     public void rejectApplication(Application app) {
          app.setStatus(ApplicationStatus.UNSUCCESSFUL);
     }

     // Getters and Setters
     
     public void setStatus(CompanyApprovalStatus status) {
=======
=======
>>>>>>> c4cf1788ed1d81e3e8bc804b1428193d484ce307
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
<<<<<<< HEAD
>>>>>>> c4cf1788ed1d81e3e8bc804b1428193d484ce307
=======
>>>>>>> c4cf1788ed1d81e3e8bc804b1428193d484ce307
          this.status = status;
     }

     public CompanyApprovalStatus getStatus() {
          return status;
     }

<<<<<<< HEAD
<<<<<<< HEAD
     public boolean isApproved() {
          return this.status == CompanyApprovalStatus.APPROVED; 
     }
=======
=======
>>>>>>> c4cf1788ed1d81e3e8bc804b1428193d484ce307
     // public void setApproved(boolean approved) { this.isApproved = approved; }
     
     // public boolean isApproved() { //the comp rep's account status
     //      return this.status == CompanyApprovalStatus.APPROVED; 
     // }
<<<<<<< HEAD
>>>>>>> c4cf1788ed1d81e3e8bc804b1428193d484ce307
=======
>>>>>>> c4cf1788ed1d81e3e8bc804b1428193d484ce307

     public String getCompanyName() {
          return companyName;
     }
<<<<<<< HEAD
<<<<<<< HEAD
     
     public String getDepartment() {
          return department;
     }
     
     public String getPosition() {
          return position;    
     }
}
=======
=======
>>>>>>> c4cf1788ed1d81e3e8bc804b1428193d484ce307
     public String getDepartment() {
          return department;
     }
     public String getPosition() {
          return position;    

     }
}

<<<<<<< HEAD
>>>>>>> c4cf1788ed1d81e3e8bc804b1428193d484ce307
=======
>>>>>>> c4cf1788ed1d81e3e8bc804b1428193d484ce307
