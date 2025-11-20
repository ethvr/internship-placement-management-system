package IPMS.System;

import IPMS.System.SystemDataEntities.*;
import IPMS.ObjectClasses.*;



public class SystemConverter {

    /* ==============================
       STUDENT
       ============================== */

    public static Student toStudent(StudentCSVData data) {
        if (data == null) return null;

        return new Student(
                data.getStudentID(),
                data.getName(),
                data.getEmail(),
                data.getYear(),
                data.getMajor()
        );
    }

    /** 
     * @param s
     * @return StudentCSVData
     */
    public static StudentCSVData toStudentCSV(Student s) {
        if (s == null) return null;

        return new StudentCSVData(
                s.getUserId(),
                s.getName(),
                s.getMajor(),
                s.getYearOfStudy(),
                s.getEmail()
        );
    }
    /** 
     * @param data
     * @return CareerCenter
     */
    /* ==============================
       STAFF    
       ============================== */

    public static CareerCenter toCareerCenter(StaffCSVData data) {
        if (data == null) return null;

        return new CareerCenter(
                data.getStaffID(),
                data.getName(),
                data.getEmail(),
                data.getDepartment(),
                data.getRole()
        );
    }

    /** 
     * @param c
     * @return StaffCSVData
     */
    public static StaffCSVData toStaffCSV(CareerCenter c) {
        if (c == null) return null;

        return new StaffCSVData(
                c.getUserId(),
                c.getName(),
                c.getRole(),
                c.getStaffDepartment(),
                c.getEmail()
        );
    }

    /** 
     * @param data
     * @return CompanyRepresentative
     */
    /* ==============================
       COMPANY REPRESENTATIVE
       ============================== */

    public static CompanyRepresentative toCompanyRep(CompanyCSVData data) {
        if (data == null) return null;

        CompanyRepresentative rep = new CompanyRepresentative(
                data.getCompanyRepID(), // email as userId
                data.getName(),           
                data.getEmail(),
                data.getCompanyName(),
                data.getDepartment(),
                data.getPosition(),
                data.getStatus()
        );

        return rep;
    }

    /** 
     * @param rep
     * @return CompanyCSVData
     */
    public static CompanyCSVData toCompanyCSV(CompanyRepresentative rep) {
        if (rep == null) return null;

        return new CompanyCSVData(
                rep.getUserId(),        // CompanyRepID
                rep.getName(),          // Name
                rep.getCompanyName(),   // CompanyName
                rep.getDepartment(),    // Department
                rep.getPosition(),      // Position
                rep.getEmail(),         // Email
                rep.getStatus()         // Status
        );

    }


    /** 
     * @param data
     * @return Internship
     */
    /* ==============================
       INTERNSHIP
       ============================== */

    public static Internship toInternship(InternshipData data) {
        if (data == null) return null;

        return new Internship(
                data.getInternshipTitle(),
                data.getDescription(),
                data.getInternshipLevel(), //enum
                data.getPrefferedMajors(),
                data.getOpeningDate(),
                data.getClosingDate(),
                data.getStatus(), //enum
                data.getCompanyName(),
                data.getCompanyRepInCharge(),
                data.getVisibility(), // boolean
                data.getNumberofSlots(),
                data.getUniqueID()
        );
    }

    /** 
     * @param i
     * @return InternshipData
     */
    public static InternshipData toInternshipData(Internship i) {
        if (i == null) return null;

        return new InternshipData(
                i.getInternshipId(),
                i.getInternshipTitle(),
                i.getDescription(),
                i.getLevel(),
                i.getPreferredMajor(),
                i.getOpenDate(),
                i.getCloseDate(),
                i.getStatus(),
                i.getCompanyName(),
                i.getCompRepID(),     // for now this is a String
                i.getSlots(),
                i.getVisibility()
        );
    }

    /** 
     * @param data
     * @return Application
     */
    /* ==============================
       APPLICATION
       ============================== */

    public static Application toApplication(ApplicationData data) {
        if (data == null) return null;

        return new Application(
                data.getUniqueID(),
                data.getStudentID(),
                data.getInternshipID(),
                data.getStatus(), //enum
                data.getAcceptedByStudent() //enum
        );
    }

    /** 
     * @param app
     * @return ApplicationData
     */
    public static ApplicationData toApplicationData(Application app) {
        if (app == null) return null;

        return new ApplicationData(
                app.getApplicationID(),
                app.getStudentId(),
                app.getInternshipId(),
                app.getStatus(),
                app.getAcceptedByStudent()
        );
    }

    /** 
     * @param data
     * @return WithdrawalRequest
     */
    /* ==============================
       WITHDRAWAL
       ============================== */

    public static WithdrawalRequest toWithdrawal(WithdrawalData data) {
        if (data == null) return null;

        return new WithdrawalRequest(
                data.getUniqueID(),
                data.getApplicationID(),
                data.getStudentID(),
                data.getStatus(), //enum
                data.getRequestDate(),
                data.getRemarks()
        );
    }

    /** 
     * @param wr
     * @return WithdrawalData
     */
    public static WithdrawalData toWithdrawalData(WithdrawalRequest wr) {
        if (wr == null) return null;

        return new WithdrawalData(
                wr.getId(),
                wr.getApplicationId(),
                wr.getStudentId(),
                wr.getStatus(),
                wr.getRequestDate(),
                wr.getRemarks()
        );
    }
}
