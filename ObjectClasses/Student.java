package IPMS.ObjectClasses;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import IPMS.Enums.*;
import IPMS.System.SystemData;
import IPMS.UserManagement.IdGenerator;

public class Student extends User {

    private int yearOfStudy;
    private String major;

    public Student(String id, String name, String email, int yearOfStudy, String major) {
        super(id, name, email);
        this.yearOfStudy = yearOfStudy;
        this.major = major;
    }

    // Getters / Setters
    public String getEmail() { return this.getEmail(); }

    public int getYearOfStudy() { return yearOfStudy; }

    public void setYearOfStudy(int yearOfStudy) { this.yearOfStudy = yearOfStudy; }

    public String getMajor() { return major; }

    public void setMajor(String major) { this.major = major; }

    // Internship Eligibility
    public List<Internship> getEligibleInternships() {
        List<Internship> result = new ArrayList<>();

        for (Internship i : SystemData.SystemDatagetInternshipMap().values()) {
            if (i.isVisibleTo(this)) {
                result.add(i);
            }
        }
        return result;
    }

    // Applying to Internship
    public void applyTo(String internshipId) {
        Internship target = SystemData.getInternshipValue(internshipId);

        if (target == null)
            throw new IllegalArgumentException("No such internship");

        if (!target.isVisibleTo(this))
            throw new IllegalStateException("Internship not available for you");

        if (countActiveApplications() >= 3)
            throw new IllegalStateException("You already have 3 active applications");

        if (yearOfStudy <= 2 && target.getLevel() != InternshipLevel.BASIC)
            throw new IllegalStateException("Year 1–2 can only apply to BASIC internships");

        Application app = new Application(
                IdGenerator.nextApplicationId(),
                this.getUserId(),
                target.getId(),
                ApplicationStatus.PENDING,
                false
        );

        SystemData.setApplicationKeyValue(app.getId(), app);
        target.addApplication(app);
    }

    // Count Active Applications
    public int countActiveApplications() {
        int c = 0;
        for (Application a : SystemData.SystemDatagetApplicationMap().values()) {
            if (a.getStudentId().equals(this.getUserId()) && a.isActive())
                c++;
        }
        return c;
    }

    // Accepting Internship
    public void acceptPlacement(String applicationId) {
        Application chosen = SystemData.SystemDatagetApplicationMap().get(applicationId);

        if (chosen == null)
            throw new IllegalArgumentException("No such application");

        if (!chosen.getStudentId().equals(this.getUserId()))
            throw new IllegalStateException("This application does not belong to you.");

        if (chosen.getStatus() != ApplicationStatus.SUCCESSFUL)
            throw new IllegalStateException("You may only accept a SUCCESSFUL offer.");

        chosen.setAcceptedByStudent(true);

        // Withdraw all other applications
        for (Application a : SystemData.SystemDatagetApplicationMap().values()) {
            if (a.getStudentId().equals(this.userId) &&
                !a.getId().equals(applicationId)) {
                a.setStatus(ApplicationStatus.WITHDRAWN);
                a.setAcceptedByStudent(false);
            }
        }

        // Update internship filled status
        Internship internship = SystemData.SystemDatagetInternshipMap().get(chosen.getInternshipId());
        if (internship != null)
            internship.updateFilledStatus();
    }

    // Withdraw (student-side immediate withdrawal)
    public void withdrawApplication(String applicationId) {
        Application app = SystemData.getApplicationValue(applicationId);

        if (app == null)
            throw new IllegalArgumentException("No such application");

        if (!app.getStudentId().equals(this.getUserId()))
            throw new IllegalStateException("This application does not belong to you.");

        if (app.getStatus() == ApplicationStatus.SUCCESSFUL && app.isAcceptedByStudent())
            throw new IllegalStateException("Cannot withdraw after accepting placement");

        app.setStatus(ApplicationStatus.WITHDRAWN);
    }

    // Request Withdrawal (goes to Career Center for approval)
    public void requestWithdrawal(String applicationId) {
        Application app = SystemData.getApplicationValue(applicationId);

        if (app == null) {
            System.out.println("Invalid Application ID.");
            return;
        }

        if (!app.getStudentId().equals(this.getUserId())) {
            System.out.println("This application does not belong to you.");
            return;
        }

        // Check if duplicate
        for (WithdrawalRequest wr : SystemData.SystemDatagetWithdrawalMap().values()) {
            if (wr.getApplicationId().equals(applicationId)) {
                System.out.println("You already requested withdrawal for this application.");
                return;
            }
        }

        String wid = IdGenerator.nextWithdrawalId();

        WithdrawalRequest req = new WithdrawalRequest(
                wid,
                app.getId(),
                this.getUserId(),
                WithdrawalStatus.PENDING,
                LocalDate.now(),
                "Requested by student"
        );

        SystemData.setWithdrawalKeyValue(wid, req);

        System.out.println("Withdrawal request created. Request ID: " + wid);
    }

    // View All My Applications
    public List<Application> getAllMyApplications() {
        List<Application> list = new ArrayList<>();

        for (Application app : SystemData.SystemDatagetApplicationMap().values()) {
            if (app.getStudentId().equals(this.getUserId()))
                list.add(app);
        }

        return list;
    }

    public void viewApplications() {
        List<Application> apps = getAllMyApplications();

        if (apps.isEmpty()) {
            System.out.println("You have no internship applications.");
            return;
        }

        for (Application app : apps) {
            System.out.println("--------------------------------");
            System.out.println("Application ID : " + app.getId());
            System.out.println("Internship ID  : " + app.getInternshipId());
            System.out.println("Status         : " + app.getStatus());
            System.out.println("Accepted?      : " + app.isAcceptedByStudent());
        }
    }
}
