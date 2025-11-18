package IPMS.System;

import IPMS.System.SystemDataEntities.*;
import IPMS.ObjectClasses.*;
import IPMS.Enums.*;
<<<<<<< HEAD
import java.time.LocalDate;

public class SystemConverter {

    /* ============================================================
       HELPER METHODS
       ============================================================ */
=======

public class SystemConverter {

    /* ==============================
       HELPER PARSERS
       ============================== */
>>>>>>> 071a7f7e66cc371b2eb40ec6247ad244aad11744

    private static InternshipStatus parseInternshipStatus(String status) {
        if (status == null) return InternshipStatus.PENDING;
        try {
            return InternshipStatus.valueOf(status.toUpperCase());
        } catch (Exception e) {
            return InternshipStatus.PENDING;
        }
    }

    private static boolean parseVisibility(String v) {
        if (v == null) return false;
<<<<<<< HEAD
        return v.equalsIgnoreCase("true") ||
               v.equalsIgnoreCase("yes") ||
               v.equalsIgnoreCase("visible");
    }

    /* ============================================================
       STUDENT
       ============================================================ */
=======
        return v.equalsIgnoreCase("true")
            || v.equalsIgnoreCase("yes")
            || v.equalsIgnoreCase("visible")
            || v.equalsIgnoreCase("1");
    }

    /*private static CompanyRepStatus parseCompanyRepStatus(String s) {
        if (s == null) return CompanyRepStatus.PENDING;
        try {
            return CompanyRepStatus.valueOf(s.toUpperCase());
        } catch (Exception e) {
            return CompanyRepStatus.PENDING;
        }
    }*/

    /* ==============================
       STUDENT
       ============================== */
>>>>>>> 071a7f7e66cc371b2eb40ec6247ad244aad11744

    public static Student toStudent(StudentCSVData data) {
        if (data == null) return null;

<<<<<<< HEAD
        // Student constructor requires password → provide dummy (real password comes from Credentials)
        String dummyPassword = "DEFAULT";

=======
>>>>>>> 071a7f7e66cc371b2eb40ec6247ad244aad11744
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
<<<<<<< HEAD

    /* ============================================================
       COMPANY REPRESENTATIVE
       ============================================================ */

    public static CompanyRepresentative toCompanyRep(CompanyCSVData data) {
        if (data == null) return null;

        CompanyRepresentative rep = new CompanyRepresentative(
                data.getEmail(),           // userId
                data.getName(),            // name
                data.getCompanyName(),
                data.getDepartment(),
                data.getPosition()
        );

        rep.setApproved(data.getStatus().equalsIgnoreCase("APPROVED"));
=======
    /* ==============================
       STAFF    
       ============================== */

    public static CareerCenter toStaff(StaffCSVData data) {
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
                data.getCompanyRepID(),// email as userId
                data.getName(),           
                data.getEmail(),
                data.getCompanyName(),
                data.getDepartment(),
                data.getPosition(),
                data.getStatus()
        );

>>>>>>> 071a7f7e66cc371b2eb40ec6247ad244aad11744
        return rep;
    }

    public static CompanyCSVData toCompanyCSV(CompanyRepresentative rep) {
        if (rep == null) return null;

        return new CompanyCSVData(
<<<<<<< HEAD
                rep.getUserId(),                 // CompanyRepID
                rep.getName(),
                rep.getCompanyName(),
                rep.getDepartment(),
                rep.getPosition(),
                rep.getUserId(),                 // Email
                rep.isApproved() ? "APPROVED" : "PENDING"
        );
    }

    /* ============================================================
       INTERNSHIP
       ============================================================ */
=======
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
>>>>>>> 071a7f7e66cc371b2eb40ec6247ad244aad11744

    public static Internship toInternship(InternshipData data) {
        if (data == null) return null;

<<<<<<< HEAD
        InternshipStatus status = parseInternshipStatus(data.getStatus());
        boolean visible = parseVisibility(data.getVisibility());

        return new Internship(
                data.getUniqueID(),             // id
=======
        return new Internship(
>>>>>>> 071a7f7e66cc371b2eb40ec6247ad244aad11744
                data.getInternshipTitle(),
                data.getDescription(),
                data.getInternshipLevel(),
                data.getPrefferedMajors(),
                data.getOpeningDate(),
                data.getClosingDate(),
<<<<<<< HEAD
                status,
                data.getCompanyName(),
                data.getCompanyRepInCharge(),
                visible,
                data.getNumberofSlots()
=======
                parseInternshipStatus(data.getStatus()),
                data.getCompanyName(),
                data.getCompanyRepInCharge(),
                parseVisibility(data.getVisibility()),
                data.getNumberofSlots(),
                data.getUniqueID()
>>>>>>> 071a7f7e66cc371b2eb40ec6247ad244aad11744
        );
    }

    public static InternshipData toInternshipData(Internship i) {
        if (i == null) return null;

        return new InternshipData(
                i.getId(),
                i.getTitle(),
                i.getDescription(),
                i.getLevel(),
                i.getPreferredMajor(),
                i.getOpenDate(),
                i.getCloseDate(),
                i.getStatus().name(),
                i.getCompanyName(),
<<<<<<< HEAD
                i.getCompRep(),       // you might change this once compRep becomes an object
=======
                i.getCompRep(),     // for now this is a String
>>>>>>> 071a7f7e66cc371b2eb40ec6247ad244aad11744
                i.getSlots()
        );
    }

<<<<<<< HEAD
    /* ============================================================
       APPLICATION
       ============================================================ */
=======
    /* ==============================
       APPLICATION
       ============================== */
>>>>>>> 071a7f7e66cc371b2eb40ec6247ad244aad11744

    public static Application toApplication(ApplicationData data) {
        if (data == null) return null;

        return new Application(
<<<<<<< HEAD
                data.getUniqueID(),          // id
=======
                data.getUniqueID(),
>>>>>>> 071a7f7e66cc371b2eb40ec6247ad244aad11744
                data.getStudentID(),
                data.getInternshipID(),
                data.getStatus(),
                data.getAcceptedByStudent()
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

<<<<<<< HEAD
    /* ============================================================
       WITHDRAWAL
       ============================================================ */

    public static WithdrawalData toWithdrawalData(WithdrawalRequest wr) {
        if (wr == null) return null;

        return new WithdrawalData(
                wr.getId(),
                wr.getApplicationId(),
                wr.getStudentId(),
                wr.getStatus(),
                wr.getRequestTime(),
                wr.getRemarks()
        );
    }
=======
    /* ==============================
       WITHDRAWAL
       ============================== */
>>>>>>> 071a7f7e66cc371b2eb40ec6247ad244aad11744

    public static WithdrawalRequest toWithdrawal(WithdrawalData data) {
        if (data == null) return null;

        return new WithdrawalRequest(
                data.getUniqueID(),
                data.getApplicationID(),
                data.getStudentID(),
                data.getStatus(),
                data.getRequestTime(),
                data.getRemarks()
        );
    }
<<<<<<< HEAD
}

=======

    public static WithdrawalData toWithdrawalData(WithdrawalRequest wr) {
        if (wr == null) return null;

        return new WithdrawalData(
                wr.getId(),
                wr.getApplicationId(),
                wr.getStudentId(),
                wr.getStatus(),
                wr.getRequestTime(),
                wr.getRemarks()
        );
    }
>>>>>>> 071a7f7e66cc371b2eb40ec6247ad244aad11744
}
