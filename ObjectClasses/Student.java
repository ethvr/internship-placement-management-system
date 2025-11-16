package IPMS.ObjectClasses;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import IPMS.Enums.*;
import IPMS.System.SystemData;
import IPMS.UserManagement.IdGenerator;

public class Student extends User {
    private int yearOfStudy;
    private String major;
    private String email;

    public Student(String id, String name, //String password,
                   int yearOfStudy, String major) {
        super(id, name);
        this.yearOfStudy = yearOfStudy;
        this.major = major;
    }

    public Student(String id, String name, String email,
                   int yearOfStudy, String major) {
        super(id, name);
        this.email = email;
        this.yearOfStudy = yearOfStudy;
        this.major = major;
    }

    public String getEmail() {
        return email;
    }
    
    public int getYearOfStudy() {
        return yearOfStudy;
    }

    public void setYearOfStudy(int yearOfStudy) {
        this.yearOfStudy = yearOfStudy;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public List<Internship> getEligibleInternships(SystemData data) {
        List<Internship> result = new ArrayList<>();
        for (Internship i : data.internships) {
            if (i.isVisibleTo(this)) {
                result.add(i);
            }
        }
        return result;
    }

    public void applyTo(String internshipId, SystemData data) {
        Internship target = findInternshipById(internshipId, data);
        if (target == null)
            throw new IllegalArgumentException("No such internship");

        // Check eligibility rules
        if (!target.isVisibleTo(this))
            throw new IllegalStateException("Internship not available for you");

        if (countActiveApplications(data) >= 3)
            throw new IllegalStateException("You already have 3 active applications");

        if (yearOfStudy <= 2 && target.getLevel() != InternshipLevel.BASIC)
            throw new IllegalStateException("Year 1–2 can only apply to BASIC internships");

        // Create and register application
        Application app = new Application(
                this.getUserId(),
                target.getId(),
                ApplicationStatus.PENDING
        );
        data.applications.add(app);
        target.addApplication(app);
    }

    public int countActiveApplications(SystemData data) {
        int c = 0;
        for (Application a : data.applications) {
            if (a.getStudentId().equals(this.getUserId()) && a.isActive()) {
                c++;
            }
        }
        return c;
    }

    public void acceptPlacement(String applicationId, SystemData data) {
        Application chosen = findAppById(applicationId, data);
        if (chosen == null)
            throw new IllegalArgumentException("No such application");

        if (chosen.getStatus() != ApplicationStatus.SUCCESSFUL)
            throw new IllegalStateException("You can only accept a SUCCESSFUL offer");

        chosen.setAcceptedByStudent(true);

        for (Application a : data.applications) {
            if (a.getStudentId().equals(this.userId) &&
                !a.getId().equals(applicationId)) {
                a.setStatus(ApplicationStatus.WITHDRAWN);
            }
        }

        Internship internship = findInternshipById(chosen.getInternshipId(), data);
        if (internship != null)
            internship.updateFilledStatus(data);
    }

    public void requestWithdrawal(String applicationId, SystemData data) {
        Application target = findAppById(applicationId, data);
        if (target == null)
            throw new IllegalArgumentException("No such application");

        // Don’t allow multiple withdrawal requests for same app
        for (WithdrawalRequest wr : data.withdrawalRequests) {
            if (wr.getApplicationId().equals(applicationId))
                throw new IllegalStateException("Withdrawal already requested");
        }

        WithdrawalRequest wr = new WithdrawalRequest(
                applicationId,
                this.userId,
                WithdrawalStatus.PENDING
        );
        data.withdrawalRequests.add(wr);
    }

    public void withdrawApplication(String applicationId, SystemData data) {
        Application target = findAppById(applicationId, data);
        if (target == null)
            throw new IllegalArgumentException("No such application");

        if (target.getStatus() == ApplicationStatus.SUCCESSFUL && target.isAcceptedByStudent())
            throw new IllegalStateException("Cannot withdraw after accepting placement");

        target.setStatus(ApplicationStatus.WITHDRAWN);
    }

    private Application findAppById(String appId, SystemData data) {
        for (Application a : data.applications) {
            if (a.getId().equals(appId))
                return a;
        }
        return null;
    }

    private Internship findInternshipById(String id, SystemData data) {
        for (Internship i : data.internships) {
            if (i.getId().equals(id))
                return i;
        }
        return null;
    }

    public void viewApplications(SystemData data) {
        Map<String, Application> apps = data.getApplications();

        boolean found = false;
        for (Application app : apps.values()) {
            if (app.getStudentId().equals(this.getUserId())) {
                found = true;
                System.out.println("--------------------------------");
                System.out.println("Application ID : " + app.getId());
                System.out.println("Internship ID  : " + app.getInternshipId());
                System.out.println("Status         : " + app.getStatus());
                System.out.println("Accepted?      : " + app.isAcceptedByStudent());
            }
        }

        if (!found) {
            System.out.println("You have no internship applications.");
        }
    }

    public List<Application> getAllMyApplications(SystemData data) {
        List<Application> list = new ArrayList<>();
        for (Application app : data.getApplications().values()) {
            if (app.getStudentId().equals(this.getUserId())) {
                list.add(app);
            }
        }
        return list;
    }

    public void acceptInternship(String applicationId, SystemData data) {
        Application app = data.getApplications().get(applicationId);

        if (app == null) {
            System.out.println("Invalid Application ID.");
            return;
        }

        if (!app.getStudentId().equals(this.getUserId())) {
            System.out.println("You cannot accept someone else's application.");
            return;
        }

        if (app.getStatus() != ApplicationStatus.SUCCESSFUL) {
            System.out.println("You can only accept a SUCCESSFUL application.");
            return;
        }

        // Accept this internship
        app.setAcceptedByStudent(true);
        System.out.println("You have accepted the internship: " + app.getInternshipId());

        // Withdraw all other applications
        for (Application other : data.getApplications().values()) {
            if (other.getStudentId().equals(this.getUserId()) &&
                    !other.getId().equals(applicationId)) {
                this.requestWithdrawal(other.applicationID, data);
                other.setStatus(ApplicationStatus.WITHDRAWN);
                other.setAcceptedByStudent(false);
            }
        }
    }


    // REQUEST WITHDRAWAL
    public void requestWithdrawal(String applicationId, SystemData data) {
        Application app = data.getApplications().get(applicationId);

        if (app == null) {
            System.out.println("Invalid Application ID.");
            return;
        }

        if (!app.getStudentId().equals(this.getUserId())) {
            System.out.println("This application does not belong to you.");
            return;
        }

        // Create withdrawal request
        String wid = IdGenerator.nextWithdrawalId();
        WithdrawalRequest req = new WithdrawalRequest(
                wid,
                app.getId(),
                this.getUserId(),
                WithdrawalStatus.PENDING,
                LocalDateTime.now(),
                "Requested by student"
        );

        data.addWithdrawalRequest(req);
        System.out.println("Withdrawal request created. Request ID: " + wid);
    }
}

