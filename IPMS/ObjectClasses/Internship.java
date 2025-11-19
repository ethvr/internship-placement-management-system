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
     /** 
      * @return String
      */
     public String getTitle(){
          return title;
     }
     /** 
      * @param title
      * @return String
      */
     public String setTitle(String title){
          this.title = title;
          return title;
     }
     /** 
      * @return List<Application>
      */
     public List<Application> getApplications() {
        String CompRepID = this.getCompRepID();
        List<Application> result = SystemData.getALMcompany(CompRepID);
        return result;
     }


     /** 
      * @return String
      */
     // getters 
     public String getInternshipId(){
          return internshipId;
     }
     /** 
      * @return String
      */
     public String getInternshipTitle(){
          return title;
     }
     /** 
      * @return String
      */
     public String getDescription() {
          return description;
     }
     /** 
      * @return InternshipLevel
      */
     public InternshipLevel getLevel() {
          return level;
     }
     /** 
      * @return String
      */
     public String getPreferredMajor() {
          return preferredMajor;
     }
     /** 
      * @return LocalDate
      */
     public LocalDate getOpenDate() {
          return openDate;
     }
     /** 
      * @return LocalDate
      */
     public LocalDate getCloseDate() {
          return closeDate;
     }
     /** 
      * @return InternshipStatus
      */
     public InternshipStatus getStatus() {
          return status; 
     }
     /** 
      * @return String
      */
     public String getCompanyName() {
          return companyName;
     }
     /** 
      * @return String
      */
     public String getCompRepID() {
          return compRepID;
     }
     /** 
      * @return int
      */
     public int getSlots() {
          return slots;
     }
     /** 
      * @return boolean
      */
     public boolean getVisibility() {
          return visible;
     }

     /** 
      * @param description
      * @return String
      */
     public String setDescription(String description){
          this.description = description;
          return description;
     }

     /** 
      * @param student
      * @return boolean
      */
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

     /** 
      * @param status
      */
     public void setStatus(InternshipStatus status) {
        this.status = status;
     }

     /** 
      * @param slots
      */
     public void setSlots(int slots) {
          this.slots = slots;
     }
     /** 
      * @param addSlots(
      */
     public void updateFilledSlots() {////////////////////
          this.slots-=1;
     }
     public void addSlots(){
          this.slots+=1;
     }

}

