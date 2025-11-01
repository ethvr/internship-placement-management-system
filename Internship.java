<<<<<<< HEAD
=======

import java.util.ArrayList;
import java.util.List;

public class Internship {
     private String title;
     private String description;
     private String level;
     private String preferredMajor;
     private String openDate;
     private String closeDate;
     private String status; // Pending, Approved, Rejected, Filled
     private String companyName;
     private CompanyRepresentative compRep;
     private boolean visible;
     private int slots;
     private List<Application> applications;

     public Internship(String title, String description, String level, String preferredMajor,
                      String openDate, String closeDate, String companyName,
                      CompanyRepresentative compRep, int slots) {
          this.title = title;
          this.description = description;
          this.level = level;
          this.preferredMajor = preferredMajor;
          this.openDate = openDate;
          this.closeDate = closeDate;
          this.status = "Pending";
          this.companyName = companyName;
          this.compRep = compRep;
          this.visible = false;
          this.slots = slots;
          this.applications = new ArrayList<>();
     }

     //adds an application to this internship's list of applications
     public void addApplication(Application app) {
          applications.add(app);
     }
     public void setVisible(boolean visible) {
          this.visible = visible;
     }
     public String getTitle(){
          return title;
     }
     public List<Application> getApplications() {
          return applications;
     }
     public String getCompanyName() {
          return companyName;
     }
     public String getStatus() {
          return status; }
     
}

>>>>>>> 7a1c595 (sd)
