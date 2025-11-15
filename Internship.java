
import java.util.ArrayList;
import java.util.List;

public class Internship {
     private String title;
     private String description;
     private InternshipLevel level; //when you initialise it you initialise it as InternshipLevel.BASIC etc
     private String preferredMajor;
     private String openDate;
     private String closeDate;
     private InternshipStatus status; // Pending, Approved, Rejected, Filled  by careercentrestaff
     private String companyName;
     private CompanyRepresentative compRep;
     private boolean visible;
     private int slots;
     private String internshipId;

     public Internship(String title, String description, InternshipLevel level, String preferredMajor,
                      String openDate, String closeDate, String companyName,
                      CompanyRepresentative compRep, int slots) {
          this.title = title;
          this.description = description;
          this.level = level;
          this.preferredMajor = preferredMajor;
          this.openDate = openDate;
          this.closeDate = closeDate;
          this.status = InternshipStatus.PENDING;
          this.companyName = companyName;
          this.compRep = compRep;
          this.visible = false;
          this.slots = slots;
          this.internshipId = IdGenerator.nextInternshipId();
     }

     public void addApplication(Application app) {
          SystemData.addApplication(app);
     }
     
     public void setVisible(boolean visible) {
          this.visible = visible;
     }
     
     public String getTitle() {
          return title;
     }
     
     public List<Application> getApplications() {
          List<Application> result = new ArrayList<>();
          for (Application app : SystemData.getAllApplications()) {
               if (app.getInternshipId().equals(this.internshipId)) {
                    result.add(app);
               }
          }
          return result;
     }

     public String getCompanyName() {
          return companyName;
     }
     
     public InternshipStatus getStatus() {
          return status; 
     }
     
     public void setStatus(InternshipStatus status) {
          this.status = status;
     }
     
     public InternshipLevel getLevel() {
          return level;
     }

     public String getOpenDate() {
          return openDate;
     }
     
     public String getCloseDate() {
          return closeDate;
     }
     
     public String getPreferredMajor() {
          return preferredMajor;
     }
     
     public String getDescription() {
          return description;
     }
     
     public CompanyRepresentative getCompRep() {
          return compRep;
     }
     
     public boolean isVisible() {
          return visible;
     }
     
     public boolean isVisibleTo(Student student) {
          if (slots < 1) {
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
     
     public void updateFilledSlots() {
          this.slots -= 1;
     }
     
     public String getId() {
          return internshipId;
     }
     
     public void setId(String id) {
          this.internshipId = id;
     }
}
