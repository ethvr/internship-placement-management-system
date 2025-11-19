package System;

import System.SystemDataEntities.*;
import ObjectClasses.*;
import Enums.*;
import java.time.LocalDate;

public class SystemConverter {

    /* ============================================================
       HELPER METHODS
       ============================================================ */

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
        return v.equalsIgnoreCase("true") ||
               v.equalsIgnoreCase("yes") ||
               v.equalsIgnoreCase("visible");
    }

    /* ============================================================
       STUDENT
       ============================================================ */

    public static Student toStudent(StudentCSVData data) {
        if (data == null) return null;

        // Student constructor requires password → provide dummy (real password comes from Credentials)
        String dummyPassword = "DEFAULT";

        return new Student(
                data.getStudentID(),
                data.getName(),
                dummyPassword,
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
        return rep;
    }

    public static CompanyCSVData toCompanyCSV(CompanyRepresentative rep) {
        if (rep == null) return null;

        return new CompanyCSVData(
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

    public static Internship toInternship(InternshipData data) {
        if (data == null) return null;

        InternshipStatus status = parseInternshipStatus(data.getStatus());
        boolean visible = parseVisibility(data.getVisibility());

        Internship i = new Internship(
            data.getInternshipTitle(),
            data.getDescription(),
            data.getInternshipLevel(),
            data.getPrefferedMajors(),
            data.getOpeningDate(),
            data.getClosingDate(),
            data.getCompanyName(),
            data.getCompanyRepInCharge(),
            data.getNumberofSlots()
        );
        // adjust status/visibility if present
        i.setStatus(status);
        i.setVisible(visible);
        return i;
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
                i.getCompRep(),       // you might change this once compRep becomes an object
                i.getSlots()
        );
    }

    /* ============================================================
       APPLICATION
       ============================================================ */

    public static Application toApplication(ApplicationData data) {
        if (data == null) return null;

        Application app = new Application(
            data.getStudentID(),
            data.getInternshipID(),
            data.getStatus(),
            data.getAcceptedByStudent()
        );
        app.setId(data.getUniqueID());
        return app;
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

    public static WithdrawalRequest toWithdrawal(WithdrawalData data) {
        if (data == null) return null;

        WithdrawalRequest wr = new WithdrawalRequest(
            data.getApplicationID(),
            data.getStudentID(),
            data.getStatus()
        );
        wr.setId(data.getUniqueID());
        wr.setRequestTime(data.getRequestTime());
        wr.setRemarks(data.getRemarks());
        return wr;
    }

}
