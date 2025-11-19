package IPMS.ObjectClasses;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import IPMS.Enums.*;
import IPMS.UserManagement.IdGenerator;
import IPMS.System.*;
import jdk.jfr.Description;
//import Companypackage.CompanyRepresentative;

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
     private String compRepID;
     private boolean visible;
     private int slots;
     private String internshipId;
     static Map<String,Application> applicationmap = SystemData.getApplicationMap();

     public Internship(String title, String description, InternshipLevel level, String preferredMajor, LocalDate openDate, LocalDate closeDate, 
                       String companyName, String compRepID, int slots) {
          this.title = title;
          this.description = description;
          this.level = level;
          this.preferredMajor = preferredMajor;
          this.openDate = openDate;
          this.closeDate = closeDate;
          this.status = InternshipStatus.PENDING;
          this.companyName = companyName;
          this.compRepID = compRepID;
          this.visible = false;      ///////////
          this.slots = slots;        ///////////
          this.internshipId = IdGenerator.nextInternshipId();
     }

     public Internship(String title, String description, InternshipLevel level, String preferredMajor, LocalDate openDate, LocalDate closeDate, 
                       InternshipStatus status, String companyName, String compRepID, boolean visible, int slots, String internshipId) {
          this.title = title;
          this.description = description;
          this.level = level;
          this.preferredMajor = preferredMajor;
          this.openDate = openDate;
          this.closeDate = closeDate;
          this.status = status;
          this.companyName = companyName;
          this.compRepID = compRepID;
          this.visible = visible;
          this.slots = slots;
          this.internshipId = internshipId;
     }

     /*Internship(String internshipTitle, String description, InternshipLevel internshipLevel, String prefferedMajors, LocalDate openingDate, LocalDate closingDate, String companyName, String companyRepInCharge, int numberofSlots) {
          throw new UnsupportedOperationException("Not supported yet.");
     }*/
       


     //adds an application to this internship's list of applications
     /*public void addApplication(Application app, SystemData data) {


          SystemData.setApplicationKeyValue(app.getId(), app);

          data.applicationmap.put(app.getId(), app);

          data.applicationmap.put(app.getId(), app);

     }*/
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
        String CompRepID = this.getCompRepID();
        List<Application> result = SystemData.getALMcompany(CompRepID);
        return result;
     }


     // getters 
     public String getInternshipId(){
          return internshipId;
     }
     public String getInternshipTitle(){
          return title;
     }
     public String getDescription() {
          return description;
     }
     public InternshipLevel getLevel() {
          return level;
     }
     public String getPreferredMajor() {
          return preferredMajor;
     }
     public LocalDate getOpenDate() {
          return openDate;
     }
     public LocalDate getCloseDate() {
          return closeDate;
     }
     public InternshipStatus getStatus() {
          return status; 
     }
     public String getCompanyName() {
          return companyName;
     }
     public String getCompRepID() {
          return compRepID;
     }
     public int getSlots() {
          return slots;
     }
     public boolean getVisibility() {
          return visible;
     }

     public String setDescription(String description){
          this.description = description;
          return description;
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

     public void setSlots(int slots) {
          this.slots = slots;
     }
     public void updateFilledSlots() {////////////////////
          this.slots-=1;
     }
     public void addSlots(){
          this.slots+=1;
     }

}

