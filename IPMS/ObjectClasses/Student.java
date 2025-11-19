package IPMS.ObjectClasses;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import IPMS.Enums.*;
import IPMS.System.SystemData;
import IPMS.UserManagement.IdGenerator;

public class Student extends User {

    private int yearOfStudy;
    private String major;

    // Constructors
    public Student(String id, String name, String email, int yearOfStudy, String major) {
        super(id, name, email);
        this.yearOfStudy = yearOfStudy;
        this.major = major;
    }

    // Getters / Setters

    public int getYearOfStudy() { return yearOfStudy; }

    public void setYearOfStudy(int yearOfStudy) { this.yearOfStudy = yearOfStudy; }

    public String getMajor() { return major; }

    public void setMajor(String major) { this.major = major; }

    // Internship Eligibility
    public List<Internship> getEligibleInternships() {
        List<Internship> result = new ArrayList<>();

        for (Internship i : SystemData.getInternshipMap().values()) {
            if (i.isVisibleTo(this)) {
                result.add(i);
            }
        }
        return result;
    }

    // Applying to Internship
    public void applyTo(String internshipId) {
        Internship internship = SystemData.getInternshipValue(internshipId);

        if (internship == null)
            throw new IllegalArgumentException("No such internship");

        if (!internship.isVisibleTo(this))
            throw new IllegalStateException("Internship not available for you");

        if (countActiveApplications() >= 3)
            throw new IllegalStateException("You already have 3 active applications");

        if (yearOfStudy <= 2 && internship.getLevel() != InternshipLevel.BASIC)
            throw new IllegalStateException("Year 1–2 can only apply to BASIC internships");

        Application app = new Application(
                this.getUserId(),
                internship.getInternshipId()
        );

        SystemData.ApplicationCreation(app);

        /*//key for company
        String compRepID = internship.getCompRepID();
        // key for value
        String appID = app.getApplicationID();
        //key for student
        String studentID = this.getUserId();

        SystemData.setALMcompany(compRepID, app);
        SystemData.setALMstudent(studentID, app);
        SystemData.setApplicationKeyValue(appID, app);*/

        /*SystemData.getApplicationMap().put(app.getId(), app);
        target.addApplication(app);*/
    }

    // Count Active Applications
    public int countActiveApplications() {
        List<Application> list = SystemData.getALMstudent(this.getUserId());
        if (list == null) {return 0;}
        else return list.size();
    }

    // Accepting Internship
    public void acceptPlacement(String applicationId) {
        Application application = SystemData.getApplicationValue(applicationId);

        if (application == null)
            throw new IllegalArgumentException("No such application");

        if (!application.getStudentId().equals(this.getUserId()))
            throw new IllegalStateException("This application does not belong to you.");

        if (application.getStatus() != ApplicationStatus.SUCCESSFUL)
            throw new IllegalStateException("You may only accept a SUCCESSFUL offer.");

        application.setAcceptedByStudent(AcceptedByStudentStatus.ACCEPTED);


        // Withdraw all other applications (stated in pdf)
        List<Application> appList = SystemData.getALMstudent(this.getUserId());

        Iterator<Application> it = appList.iterator();

        while (it.hasNext()) {
            Application a = it.next();

            // keep only ACCEPTED
            if (a.isAcceptedByStudent() != AcceptedByStudentStatus.ACCEPTED) {
                it.remove(); // safe removal
            }
        }
    }
        // Update internship filled status
        /*Internship internship = SystemData.getInternshipMap().get(chosen.getInternshipId());
            if (internship != null)
                internship.updateFilledStatus();
        }*/

    // Withdraw (student-side immediate withdrawal)
    public void withdrawApplication(String applicationId) {
        Application application = SystemData.getApplicationValue(applicationId);

        if (application == null)
            throw new IllegalArgumentException("No such application");

        if (!application.getStudentId().equals(this.getUserId()))
            throw new IllegalStateException("This application does not belong to you.");

        application.setStatus(ApplicationStatus.WITHDRAWN);
    }

    // Request Withdrawal (goes to Career Center for approval)
    public void requestWithdrawal(String applicationId, String remarks) {
        Application application = SystemData.getApplicationValue(applicationId);

        if (application == null) {
            System.out.println("Invalid Application ID.");
            return;
        }

        if (!application.getStudentId().equals(this.getUserId())) {
            System.out.println("This application does not belong to you.");
            return;
        }

        // Check if duplicate
        WithdrawalRequest wr = SystemData.getWithdrawalValue(applicationId);
        if (wr != null) {
            // withdrawal exists
            System.out.println("Withdrawal request already sent");
        }

        WithdrawalRequest obj = new WithdrawalRequest(
                applicationId,
                this.getUserId(),
                remarks
        );

        // appid as key for value
        String appID = applicationId;
        // studentid as key for list 
        String studentID = this.getUserId();

        // sync to maps
        SystemData.setWithdrawalKeyValue(appID, obj);
        SystemData.setWLMstudent(studentID, obj);
        
        application.setStatus(ApplicationStatus.WITHDRAWN);

        System.out.println("Withdrawal request created." );
    }

    // View All My Applications
    public List<Application> getAllMyApplications() {
        List<Application> list = new ArrayList<>();

        String key = this.getUserId();
        return list = SystemData.getALMstudent(key);

    }

    public List<WithdrawalRequest> getAllMyWithdrawalRequests() {
        List<WithdrawalRequest> list = new ArrayList<>();

        String key = this.getUserId();
        return list = SystemData.getWLMstudent(key);
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
