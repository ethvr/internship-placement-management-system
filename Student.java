import java.util.*;

public class Student extends User {
    private int yearOfStudy;
    private String major;

    public Student(String id, String name, String password,
                   int yearOfStudy, String major) {
        super(id, name, password);
        this.yearOfStudy = yearOfStudy;
        this.major = major;
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
                IdGenerator.nextAppId(),
                this.userId,
                target.getId(),
                ApplicationStatus.PENDING
        );
        data.applications.add(app);
        target.addApplication(app.getId());
    }

    /** Count active (non-withdrawn) applications */
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

        // Update internship filled status
        Internship internship = findInternshipById(chosen.getInternshipId(), data);
        if (internship != null)
            internship.updateFilledStatus(data);
    }

    /** Request withdrawal (before or after placement confirmation) */
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
                IdGenerator.nextWithdrawalId(),
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
}
