package IPMS.SystemPages.StudentPages;

import java.util.List;

import IPMS.ObjectClasses.Student;
import IPMS.ObjectClasses.Application;
import IPMS.Enums.ApplicationStatus;
import IPMS.Enums.WithdrawalStatus;
import IPMS.System.SystemData;
import IPMS.SystemPages.Page;
import IPMS.SystemPages.PageAction;
import IPMS.SystemPages.UniversalFunctions;

public class ViewApplicationsPage implements Page {

    private final Student student;

    public ViewApplicationsPage(Student student) {
        this.student = student;
    }

    @Override
    public void showMenu() {
        System.out.println("\n===== YOUR INTERNSHIP APPLICATIONS =====");

        List<Application> apps = student.getAllMyApplications();

        if (apps.isEmpty()) {
            System.out.println("You have not applied to any internships.");
            return;
        }

        int index = 1;
        for (Application a : apps) {
            System.out.printf("[%d] Application ID: %s\n", index++, a.getId());
            System.out.println("    Internship ID : " + a.getInternshipId());
            System.out.println("    Status        : " + a.getStatus());
            System.out.println("    Accepted?     : " + a.isAcceptedByStudent());
            System.out.println();
        }

        System.out.println("[1] Accept Offer");
        System.out.println("[2] Withdraw Application");
        System.out.println("[3] Request Withdrawal (After Accepting)");
        System.out.println("[4] Back");
        System.out.print("Enter an option: ");
    }

    @Override
    public PageAction next() {

        int choice = UniversalFunctions.readIntInRange(1, 4);

        switch (choice) {
            case 1 -> handleAcceptOffer();
            case 2 -> handleWithdrawApplication();
            case 3 -> handleRequestWithdrawal();
            case 4 -> { return PageAction.pop(); }

            default -> System.out.println("Invalid option.");
        }

        return PageAction.stay();
    }

    /* ============================================================
       OPTION 1 — ACCEPT SUCCESSFUL APPLICATION
       ============================================================ */
    private void handleAcceptOffer() {
        String id = getAppIdFromUser("Enter Application ID to accept: ");

        Application app = SystemData.getApplicationValue(id);
        if (invalidApp(app)) return;

        if (app.getStatus() != ApplicationStatus.SUCCESSFUL) {
            System.out.println("You may only accept SUCCESSFUL applications.");
            return;
        }

        if (app.isAcceptedByStudent()) {
            System.out.println("You already accepted this offer.");
            return;
        }

        student.acceptPlacement(id);
        SystemData.saveAll();

        System.out.println("Offer accepted! Other applications were withdrawn.");
    }

    /* ============================================================
       OPTION 2 — WITHDRAW (allowed only before acceptance)
       ============================================================ */
    private void handleWithdrawApplication() {
        String id = getAppIdFromUser("Enter Application ID to withdraw: ");

        Application app = SystemData.getApplicationValue(id);
        if (invalidApp(app)) return;

        if (app.getStatus() == ApplicationStatus.SUCCESSFUL && app.isAcceptedByStudent()) {
            System.out.println("You cannot directly withdraw an accepted application.");
            System.out.println("Submit a withdrawal request instead.");
            return;
        }

        app.setStatus(ApplicationStatus.WITHDRAWN);
        SystemData.saveAll();

        System.out.println("Application withdrawn.");
    }

    /* ============================================================
       OPTION 3 — WITHDRAWAL REQUEST (ONLY AFTER ACCEPTING)
       ============================================================ */
    private void handleRequestWithdrawal() {
        String id = getAppIdFromUser("Enter Application ID to request withdrawal: ");

        Application app = SystemData.getApplicationValue(id);
        if (invalidApp(app)) return;

        if (!app.isAcceptedByStudent()) {
            System.out.println("You may only request withdrawal AFTER accepting an internship.");
            return;
        }

        // Prevent duplicate requests
        boolean exists = SystemData.getWithdrawalMap().values().stream()
                .anyMatch(w -> w.getApplicationId().equals(app.getId()));

        if (exists) {
            System.out.println("You already submitted a withdrawal request for this application.");
            return;
        }

        student.requestWithdrawal(id);
        SystemData.saveAll();
    }

    /* ============================================================
       HELPER METHODS
       ============================================================ */

    private String getAppIdFromUser(String msg) {
        System.out.print(msg);
        return UniversalFunctions.readString();
    }

    private boolean invalidApp(Application app) {
        if (app == null) {
            System.out.println("Invalid Application ID.");
            return true;
        }
        if (!app.getStudentId().equals(student.getUserId())) {
            System.out.println("This application does NOT belong to you.");
            return true;
        }
        return false;
    }
}
