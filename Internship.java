
import java.util.ArrayList;
import java.util.List;

public class Internship {
     private String title;
     private String description;
     private InternshipLevel level; //when you initialise it you initialise it as InternshipLevel.BASIC etc
     private String preferredMajor;
     private String openDate;
     private String closeDate;
     private String status; // Pending, Approved, Rejected, Filled   by careercentrestaff
     private String companyName;
     private CompanyRepresentative compRep;
     private boolean visible;
     private int slots;
     private int internshipId;/////string or int 
     private List<Application> applications;

     public Internship(String title, String description, InternshipLevel level, String preferredMajor,
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
          this.internshipId = IdGenerator.nextInternshipId();
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
     
     public InternshipLevel getLevel() {
          return level;
     }

     public String getOpenDate() {
          return openDate;
     }
     public String getCloseDate() {
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
     public int getSlots() {
          return slots;
     }
     public void updateFilledStatusSlots() {////////////////////
          this.slots-=1;
     }
     public int getId(){
          return internshipId;
     }
     
}

