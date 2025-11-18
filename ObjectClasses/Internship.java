package IPMS.ObjectClasses;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import IPMS.Enums.*;
import IPMS.UserManagement.IdGenerator;
import IPMS.System.*;
import jdk.jfr.Description;
import Companypackage.CompanyRepresentative;

public class Internship {
     private String title;
     private String description;
     private InternshipLevel level; //when you initialise it you initialise it as InternshipLevel.BASIC etc
     private String preferredMajor;
     private LocalDate openDate;
     private LocalDate closeDate;
     private InternshipStatus status; // Pending, Approved, Rejected, Filled   by careercentrestaff
     private String companyName;
     //private CompanyRepresentative compRep; // change to string for now 
     private String compRep;
     private boolean visible;
     private int slots;
     private String internshipId;

     public Internship(String title, String description, InternshipLevel level, String preferredMajor, LocalDate openDate, LocalDate closeDate, 
                       String companyName, String compRep, int slots) {
          this.title = title;
          this.description = description;
          this.level = level;
          this.preferredMajor = preferredMajor;
          this.openDate = openDate;
          this.closeDate = closeDate;
          this.status = InternshipStatus.PENDING;
          this.companyName = companyName;
          this.compRep = compRep;
          this.visible = false;      ///////////
          this.slots = slots;        ///////////
          this.internshipId = IdGenerator.nextInternshipId();
     }

     public Internship(String title, String description, InternshipLevel level, String preferredMajor, LocalDate openDate, LocalDate closeDate, 
                       InternshipStatus status, String companyName, String compRep, boolean visible, int slots, String internshipId) {
          this.title = title;
          this.description = description;
          this.level = level;
          this.preferredMajor = preferredMajor;
          this.openDate = openDate;
          this.closeDate = closeDate;
          this.status = status;
          this.companyName = companyName;
          this.compRep = compRep;
          this.visible = visible;
          this.slots = slots;
          this.internshipId = internshipId;
     }

     /*Internship(String internshipTitle, String description, InternshipLevel internshipLevel, String prefferedMajors, LocalDate openingDate, LocalDate closingDate, String companyName, String companyRepInCharge, int numberofSlots) {
          throw new UnsupportedOperationException("Not supported yet.");
     }*/

     //adds an application to this internship's list of applications
     public void addApplication(Application app, SystemData data) {
          data.applications.put(app.getId(), app);
     }
     public void setVisible(boolean visible) {
          this.visible = visible;
     }
     public String getTitle(){
          return title;
     }
     public List<Application> getApplications(SystemData data) {
        List<Application> result = new ArrayList<>();
        for (Application app : data.applications.values()) {
            if (app.getInternshipId().equals(this.internshipId)) {
                result.add(app);
            }
        }
        return result;
     }

     public String getDescription() {
          return description;
     }

     public String getPreferredMajor() {
          return preferredMajor;
     }
     
     public String getCompanyName() {
          return companyName;
     }
     public InternshipStatus getStatus() {
          return status; }
     
     public InternshipLevel getLevel() {
          return level;
     }

     public LocalDate getOpenDate() {
          return openDate;
     }
     public LocalDate getCloseDate() {
          return closeDate;
     }
     public boolean isVisibleTo(Student student) {
          if (slots<1){
               return false;
          }
          if (!visible) {
               return false;
          }
          if (preferredMajor != null && !preferredMajor.isEmpty() &&
              !preferredMajor.equalsIgnoreCase(student.getMajor())) {
               return false;
          }
          return true;
     }

     public void setStatus(InternshipStatus status) {
        this.status = status;
    }
     public int getSlots() {
          return slots;
     }
     public void updateFilledSlots() {////////////////////
          this.slots-=1;
     }
     public String getId(){
          return internshipId;
     }
     public String getCompRep() {
          return compRep;
     }
     
}

