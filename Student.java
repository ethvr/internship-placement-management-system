import java.util.*;

public class Student extends User {
    private int yearOfStudy;
    private String major;

    public Student(String id, String name, String password,
                   int yearOfStudy, String major) {
        super(id, name);
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

    public List<Internship> getEligibleInternships() {
        List<Internship> result = new ArrayList<>();
        for (Internship i : SystemData.getAllInternships()) {
            if (i.isVisibleTo(this)) {
                result.add(i);
            }
        }
        return result;
    }

    public void applyTo(String internshipId) {
        Internship target = SystemData.getInternship(internshipId);
        if (target == null)
            throw new IllegalArgumentException("No such internship");

        // Check eligibility rules
        if (!target.isVisibleTo(this))
            throw new IllegalStateException("Internship not available for you");

        if (countActiveApplications() >= 3)
            throw new IllegalStateException("You already have 3 active applications");

        if (yearOfStudy <= 2 && target.getLevel() != InternshipLevel.BASIC)
            throw new IllegalStateException("Year 1–2 can only apply to BASIC internships");

        // Create and register application
        Application app = new Application(
                this.getUserId(),
                target.getId(),
                ApplicationStatus.PENDING
        );
        SystemData.addApplication(app);
        target.addApplication(app);
    }

    public int countActiveApplications() {
        int c = 0;
        for (Application a : SystemData.getAllApplications()) {
            if (a.getStudentId().equals(this.getUserId()) && a.isActive()) {
                c++;
            }
        }
        return c;
    }

    public void acceptPlacement(String applicationId) {
        Application chosen = SystemData.getApplication(applicationId);
        if (chosen == null)
            throw new IllegalArgumentException("No such application");

        if (chosen.getStatus() != ApplicationStatus.SUCCESSFUL)
            throw new IllegalStateException("You can only accept a SUCCESSFUL offer");

        chosen.setAcceptedByStudent(true);

        // Withdraw all other applications
        for (Application a : SystemData.getAllApplications()) {
            if (a.getStudentId().equals(this.getUserId()) &&
                !a.getId().equals(applicationId)) {
                a.setStatus(ApplicationStatus.WITHDRAWN);
            }
        }

        Internship internship = SystemData.getInternship(chosen.getInternshipId());
        if (internship != null)
            internship.updateFilledSlots();
    }

    public void requestWithdrawal(String applicationId) {
        Application target = SystemData.getApplication(applicationId);
        if (target == null)
            throw new IllegalArgumentException("No such application");

        // Don't allow multiple withdrawal requests for same app
        for (WithdrawalRequest wr : SystemData.getAllWithdrawals()) {
            if (wr.getApplicationId().equals(applicationId))
                throw new IllegalStateException("Withdrawal already requested");
        }

        WithdrawalRequest wr = new WithdrawalRequest(
                applicationId,
                this.getUserId(),
                WithdrawalStatus.PENDING
        );
        SystemData.addWithdrawal(wr);
    }

    public void withdrawApplication(String applicationId) {
        Application target = SystemData.getApplication(applicationId);
        if (target == null)
            throw new IllegalArgumentException("No such application");

        if (target.getStatus() == ApplicationStatus.SUCCESSFUL && target.isAcceptedByStudent())
            throw new IllegalStateException("Cannot withdraw after accepting placement");

        target.setStatus(ApplicationStatus.WITHDRAWN);
    }
}
