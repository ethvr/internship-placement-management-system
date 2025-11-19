package IPMS.System;

import IPMS.System.SystemDataEntities.*;
import IPMS.ObjectClasses.*;
import IPMS.Enums.*;
import java.time.LocalDate;


public class SystemConverter {

    /* ==============================
       HELPER PARSERS
       ============================== */

    /*private static InternshipStatus parseInternshipStatus(String status) {
        if (status == null) return InternshipStatus.PENDING;
        try {
            return InternshipStatus.valueOf(status.toUpperCase());
        } catch (Exception e) {
            return InternshipStatus.PENDING;
        }
    }

    private static boolean parseVisibility(String v) {
        if (v == null) return false;
        return v.equalsIgnoreCase("true")
            || v.equalsIgnoreCase("yes")
            || v.equalsIgnoreCase("visible")
            || v.equalsIgnoreCase("1");
    }

    private static CompanyApprovalStatus parseCompanyRepStatus(String s) {
        if (s == null) return CompanyApprovalStatus.PENDING;
        try {
            return CompanyApprovalStatus.valueOf(s.toUpperCase());
        } catch (Exception e) {
            return CompanyApprovalStatus.PENDING;
        }
    }*/

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

    /* ==============================
       COMPANY REPRESENTATIVE
       ============================== */

    public static CompanyRepresentative toCompanyRep(CompanyCSVData data) {
        if (data == null) return null;
        String email = data.getEmail();
        String username = email.substring(0, email.indexOf("@"));

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

    public static CompanyCSVData toCompanyCSV(CompanyRepresentative rep) {
        if (rep == null) return null;

        return new CompanyCSVData(
                rep.getUserId(),       // CompanyRepID should already exists when you run 
                rep.getName(),
                rep.getEmail(),        // Email
                rep.getCompanyName(),
                rep.getDepartment(),
                rep.getPosition(),
                rep.getStatus()
        );
    }


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

    public static ApplicationData toApplicationData(Application app) {
        if (app == null) return null;

        return new ApplicationData(
                app.getId(),
                app.getStudentId(),
                app.getInternshipId(),
                app.getStatus(),
                app.isAcceptedByStudent()
        );
    }

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
