import java.time.LocalDate;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Student extends User {
    private final int yearOfStudy;
    private final String major;

    private final Map<String, Application> applicationsById = new LinkedHashMap<>();
    private final Map<String, Application> applicationsByInternship = new HashMap<>();
    private Application acceptedApplication;
    private final List<WithdrawalRequest> withdrawalRequests = new ArrayList<>();

    public Student(String userId, String name, String password, int yearOfStudy, String major) {
        super(userId, name, password);
        if (userId == null || !userId.matches("U\\d{7}[A-Z]")) throw new IllegalArgumentException();
        if (yearOfStudy < 1 || yearOfStudy > 4) throw new IllegalArgumentException();
        if (major == null || major.isBlank()) throw new IllegalArgumentException();
        this.yearOfStudy = yearOfStudy;
        this.major = major.trim().toUpperCase(Locale.ROOT);
    }

    public int getYearOfStudy() { return yearOfStudy; }
    public String getMajor() { return major; }


    public List<Internship> getAppliedInternships(Map<String, Internship> lookup) {
        return applicationsByInternship.keySet().stream()
                .map(lookup::get)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public Application applyToInternship(Internship i, Function<Student, Application> factory) {
        Objects.requireNonNull(i);
        if (hasAcceptedPlacement()) throw new IllegalStateException();
        if (applicationsByInternship.containsKey(i.getId())) throw new IllegalStateException();
        if (!levelAllowed(i)) throw new IllegalStateException();
        if (!majorMatches(i)) throw new IllegalStateException();
        if (!"Approved".equalsIgnoreCase(i.getStatus())) throw new IllegalStateException();
        if (isFilled(i)) throw new IllegalStateException();
        LocalDate t = LocalDate.now();
        LocalDate o = i.getOpenDate(), c = i.getCloseDate();
        if ((o != null && t.isBefore(o)) || (c != null && t.isAfter(c))) throw new IllegalStateException();
        if (getActiveApplications().size() >= 3) throw new IllegalStateException();

        Application app = factory.apply(this);
        applicationsById.put(app.getId(), app);
        applicationsByInternship.put(i.getId(), app);
        return app;
    }

    public void acceptPlacement(String applicationId) {
        Application app = applicationsById.get(applicationId);
        if (app == null) throw new NoSuchElementException();
        if (hasAcceptedPlacement()) throw new IllegalStateException();
        if (app.getStatus() != ApplicationStatus.SUCCESSFUL) throw new IllegalStateException();

        app.setAcceptedByStudent(true);
        acceptedApplication = app;

        for (Application other : applicationsById.values()) {
            if (other == app) continue;
            if (isActive(other)) other.setStatus(ApplicationStatus.WITHDRAWN);
        }
    }

    public WithdrawalRequest requestWithdrawal(String applicationId, String reason,
                                               BiFunction<Student, Application, WithdrawalRequest> factory) {
        Application app = applicationsById.get(applicationId);
        if (app == null) throw new NoSuchElementException();
        WithdrawalRequest req = factory.apply(this, app);
        withdrawalRequests.add(req);
        return req;
    }

    public void onWithdrawalDecision(String withdrawalRequestId, String decision) {
        WithdrawalRequest req = withdrawalRequests.stream()
                .filter(r -> r.getId().equals(withdrawalRequestId))
                .findFirst().orElseThrow(NoSuchElementException::new);
        if (!"Approved".equalsIgnoreCase(decision) && !"Rejected".equalsIgnoreCase(decision))
            throw new IllegalArgumentException();
        req.setDecision(decision);
        if ("Approved".equalsIgnoreCase(decision)) {
            Application app = applicationsById.get(req.getApplicationId());
            if (app != null) {
                if (acceptedApplication == app) acceptedApplication = null;
                app.setAcceptedByStudent(false);
                app.setStatus(ApplicationStatus.WITHDRAWN);
            }
        }
    }

    public boolean hasAcceptedPlacement() {
        return acceptedApplication != null && acceptedApplication.isAcceptedByStudent();
    }

    public List<Application> getApplications() {
        List<Application> list = new ArrayList<>(applicationsById.values());
        Collections.reverse(list);
        return list;
    }

    public List<Application> getActiveApplications() {
        return applicationsById.values().stream().filter(this::isActive).collect(Collectors.toList());
    }

    private boolean isFilled(Internship i) {
        return "Filled".equalsIgnoreCase(i.getStatus()) || i.getSlotsConfirmed() >= i.getSlotsTotal();
    }

    private boolean levelAllowed(Internship i) {
        if (yearOfStudy <= 2) return i.getLevel() == InternshipLevel.BASIC;
        return true;
    }

    private boolean majorMatches(Internship i) {
        String pref = i.getPreferredMajor();
        return pref == null || pref.isBlank() || pref.equalsIgnoreCase(major);
    }

    private boolean isActive(Application a) {
        if (a.getStatus() == ApplicationStatus.PENDING) return true;
        if (a.getStatus() == ApplicationStatus.SUCCESSFUL && !a.isAcceptedByStudent()) return true;
        return false;
    }

    public void addExistingApplication(Application app) {
        applicationsById.put(app.getId(), app);
        applicationsByInternship.put(app.getInternshipId(), app);
        if (app.isAcceptedByStudent() && app.getStatus() == ApplicationStatus.SUCCESSFUL) {
            acceptedApplication = app;
        }
    }

    public List<WithdrawalRequest> getWithdrawalRequests() {
        return Collections.unmodifiableList(withdrawalRequests);
    }
}
