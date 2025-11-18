package IPMS.ObjectClasses;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import IPMS.Enums.*;
import IPMS.UserManagement.IdGenerator;
import IPMS.System.*;
import jdk.jfr.Description;
import IPMS.Companypackage.CompanyRepresentative;

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
     static Map<String,Application> applicationmap = SystemData.getApplicationMap();

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
<<<<<<< HEAD
          SystemData.setApplicationKeyValue(app.getId(), app);
=======
          data.applicationmap.put(app.getId(), app);
>>>>>>> c4cf1788ed1d81e3e8bc804b1428193d484ce307
     }
     public void setVisible(boolean visible) {
          this.visible = visible;
     }
     public String getTitle(){
          return title;
     }
     public String setTitle(String title){
          this.title = title;
          return title;
     }
     public List<Application> getApplications() {
        List<Application> result = new ArrayList<>();
<<<<<<< HEAD
        for (Application app : SystemData.SystemDatagetApplicationMap().values()) {
=======
        for (Application app : data.applicationmap.values()) {
>>>>>>> c4cf1788ed1d81e3e8bc804b1428193d484ce307
            if (app.getInternshipId().equals(this.internshipId)) {
                result.add(app);
            }
        }
        return result;
     }



     public String getInternshipTitle(){
          return title;
     }

     public String getDescription() {
          return description;
     }
     public String setDescription(String description){
          this.description = description;
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
     public boolean isVisible() {
          return visible;
     }

     public void setStatus(InternshipStatus status) {
        this.status = status;
    }
     public int getSlots() {
          return slots;
     }
     public void setSlots(int slots) {
          this.slots = slots;
     }
     public void updateFilledSlots() {////////////////////
          this.slots-=1;
     }
     public void addSlots(){
          this.slots+=1;
     }
     public String getId(){
          return internshipId;
     }
     public String getCompRep() {
          return compRep;
     }
     
}

