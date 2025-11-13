package sc2002project;

import java.time.LocalDateTime;
import java.util.spi.LocaleNameProvider;
// import the enums 

// class variables must have the EXACT SAME names as the headers for universal csv writeback
public class SystemDataEntities {

    //✅ 
    // key for hash map is username 
    public static class Credentials {
        String Password;
        boolean Firsttimelogin;
        String Type;
        //boolean AccountActivated; // to check if alread enrolled into system?

        Credentials(){} // <-- REQUIRED for reflection for universal loadcsv

        Credentials (String Password, boolean Firsttimelogin, String Type){
            this.Password = Password;
            this.Firsttimelogin = Firsttimelogin;
            this.Type = Type;
            }
        }   

    /*String id✅ 
    String name✅ 
    String password --> not used in obj class?
    int yearOfStudy✅ 
    String major ✅*/ 
    // key for hashmap is studentID
    public static class StudentCSVData {
        String StudentID;
        String Name;
        String Major;
        int Year;
        String Email;

        StudentCSVData(){} // <-- REQUIRED for reflection for universal loadcsv

        StudentCSVData(String studentID, String name, String major, int year, String email){
            this.StudentID = studentID;
            this.Name = name;
            this.Major = major;
            this.Email = email;
            this.Year = year;
        }
    }

    // unknown
    // key for hash map is the StaffID
    public static class StaffCSVData {
        String StaffID;
        String Name;
        String Role;
        String Department;
        String Email;

        StaffCSVData(){} // <-- REQUIRED for reflection for universal loadcsv

        StaffCSVData(String staffID, String name, String role, String department, String email){
            this.StaffID = staffID;
            this.Name = name;
            this.Role = role;
            this.Department = department;
            this.Email = email;
        }
    }

    /*private String companyName;✅ 
     private String department;//of the company rep✅ 
     private String position;//of the company rep✅ 
     private boolean isApproved;//account created approved by career centre✅ --> Status 
     private List<Internship> internshipsCreated; */
     // key for hash map is the CompanyRepID --> change to username ?
    public static class CompanyCSVData {
        String CompanyRepID; // before @ of email (any id?)
        String Name;
        String CompanyName;
        String Department;
        String Position;
        String Email;
        String Status;

        CompanyCSVData(){} // REQUIRED for reflection for universal loadcsv

        CompanyCSVData(String CompanyRepID, String Name, String CompanyName, String Department, String Position, String Email, String Status) {
            this.CompanyRepID = CompanyRepID;
            this.Name = Name;
            this.CompanyName = CompanyName;
            this.Department = Department;
            this.Position = Position;
            this.Email = Email;
            this.Status = Status;
        }
        public String getCompanyStatus() {
            return Status;
        }
    }
    // under company rep in assignment pdf
    // check object class for extra parameters that need to be stored 
    /*private String title;✅
     private String description;✅ 
     private InternshipLevel level; //when you initialise it you initialise it as InternshipLevel.BASIC etc✅ 
     private String preferredMajor;✅ 
     private String openDate;✅ 
     private String closeDate;✅ 
     private String status; // Pending, Approved, Rejected, Filled   by careercentrestaff✅ 
     private String companyName;✅ 
     private CompanyRepresentative compRep;✅ 
     private boolean visible; --> visible to who?
     private int slots;✅ 
     private int internshipId;/////string or int (the key✅)
     private List<Application> applications; */
     // key for hash map is the internship ID --> IdGenerator 
    public static class InternshipData {
        String UniqueID;
        String InternshipTitle;
        String Description;
        String InternshipLevel; // basic medium advanced 
        String PrefferedMajors; //assume 1 will do
        int OpeningDate;
        int ClosingDate;
        String Status; //(“Pending”, “Approved”, “Rejected”, “Filled”) approved by staff 
        String CompanyName;
        String CompanyRepInCharge; //(automatically assigned)?
        int NumberofSlots; //max 10
        String Visibility; //?

        InternshipData() {} // no arg com

        InternshipData(String uniqueID, String internshipTitle, String description, String internshipLevel,
                       String prefferedMajors, int openingDate, int closingDate,
                       String status, String companyName, String companyRepInCharge,
                       int numberofSlots) {

            this.UniqueID = uniqueID;
            this.InternshipTitle = internshipTitle;
            this.Description = description;
            this.InternshipLevel = internshipLevel;
            this.PrefferedMajors = prefferedMajors;
            this.OpeningDate = openingDate;
            this.ClosingDate = closingDate;
            this.Status = status;
            this.CompanyName = companyName;
            this.CompanyRepInCharge = companyRepInCharge;
            this.NumberofSlots = numberofSlots;
        }
        
    }
    // for student?
    // check object class for extra parameters that need to be stored 
    /*private String id; (the key✅)            
    private String studentId;  ✅          
    private String internshipId;  ✅     
    private ApplicationStatus status;✅ (PENDING, SUCCESSFUL, UNSUCCESSFUL, WITHDRAWN)
    private boolean acceptedByStudent; ✅*/
    // key for hash map is the application ID --> IdGenerator ID (private String id;)
    public static class ApplicationData {
        String UniqueID;
        String StudentID;
        String InternshipID;
        ApplicationStatus Status; //whether company accepts students application (inbcludes withdrawal)
        boolean AcceptedByStudent;
        //boolean Withdraw; //True --> want to withdraw //no need?

        ApplicationData() {} // no arg con 

        ApplicationData(String uniqueID, String studentID, String internshipID, 
                        ApplicationStatus status, boolean acceptedByStudent /*boolean withdraw*/) {
            this.UniqueID = uniqueID;
            this.StudentID = studentID;
            this.InternshipID = internshipID;
            this.Status = status;
            this.AcceptedByStudent = acceptedByStudent;
            //this.Withdraw = withdraw;

        }   
    }
    // for staff 
    // check object class for extra parameters that need to be stored 
    /*private String id; (the key✅)                      
    private String applicationId;✅           
    private String studentId;✅               
    private WithdrawalStatus status;✅  (PENDING,APPROVED,REJECTED)     
    private LocalDateTime requestTime;✅      
    private String remarks;✅*/
    // key --> IdGenerator ID (private String id;)
    public static class WithdrawalData {
        private String UniqueID;
        private String ApplicationID; // application ID uniqueID        
        private String StudentID;               
        private WithdrawalStatus Status; // enum WithdrawalStatus       
        private LocalDateTime RequestTime;
        private String Remarks; 

        WithdrawalData() {}  // no arg constructor

        WithdrawalData(String uniqueID, String applicationId, String studentId, 
                       WithdrawalStatus status, LocalDateTime requestTime, String remarks) {
            this.UniqueID = uniqueID;
            this.ApplicationID = applicationId;
            this.StudentID = studentId;
            this.Status = status;
            this.RequestTime = requestTime;
            this.Remarks = remarks;

            }

    }

}
