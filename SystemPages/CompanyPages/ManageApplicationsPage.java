package SystemPages.CompanyPages;
import java.util.List;
import java.util.Scanner;
import Companypackage.CompanyRepresentative;
import SystemPages.Page;
import SystemPages.PageAction;
import SystemPages.UniversalFunctions;
import Internship;
import Application;
import ApplicationStatus;
import SystemData;


public class ManageApplicationsPage implements Page {
    private final String username;
    private Scanner scanner;

    public ManageApplicationsPage(String username) {
        this.username = username;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void showMenu() {
        System.out.println("======= MANAGE APPLICATIONS =======");
        System.out.println("[1] View Applications for an Internship");
        System.out.println("[2] Approve Application");
        System.out.println("[3] Reject Application");
        System.out.println("[4] Back\n");
        System.out.print("Enter an option (1-4): ");
    }

    @Override
    public PageAction next() {
        int opt = UniversalFunctions.readIntInRange(1, 4);

        CompanyRepresentative compRep = SystemData.representatives.get(username);
        
        if (compRep == null) {
            System.out.println("Error: Company representative not found.\n");
            return PageAction.pop();
        }

        switch (opt) {
            case 1 -> {
                viewApplications(compRep);
                return PageAction.push(this);
            }
            case 2 -> {
                approveApplication(compRep);
                return PageAction.push(this);
            }
            case 3 -> {
                rejectApplication(compRep);
                return PageAction.push(this);
            }
            case 4 -> {
                return PageAction.pop();
            }
            default -> {
                return PageAction.pop();
            }
        }
    }

    /**
     * View applications for a selected internship
     */
    private void viewApplications(CompanyRepresentative compRep) {
        System.out.println("\n=== VIEW APPLICATIONS ===");
        
        List<Internship> internships = compRep.getInternshipsCreated();
        
        if (internships.isEmpty()) {
            System.out.println("You have no internships created yet.\n");
            return;
        }

        // Display internships
        System.out.println("Select an internship to view applications:");
        for (int i = 0; i < internships.size(); i++) {
            Internship intern = internships.get(i);
            System.out.println("[" + (i + 1) + "] " + intern.getTitle() + 
                             " (" + intern.getStatus() + ")");
        }
        System.out.println("[0] Cancel\n");

        System.out.print("Select internship: ");
        int choice = UniversalFunctions.readIntInRange(0, internships.size());
        
        if (choice == 0) return;

        Internship selectedInternship = internships.get(choice - 1);
        
        // Use the existing method to view applications
        System.out.println();
        compRep.viewApplications(selectedInternship, SystemData.getInstance());
        System.out.println();
    }

    /**
     * Approve a student application
     */
    private void approveApplication(CompanyRepresentative compRep) {
        System.out.println("\n=== APPROVE APPLICATION ===");
        
        // First select internship
        Internship selectedInternship = selectInternship(compRep);
        if (selectedInternship == null) return;
        
        // Get applications for this internship
        List<Application> applications = selectedInternship.getApplications(SystemData.getInstance());
        
        if (applications.isEmpty()) {
            System.out.println("No applications for this internship.\n");
            return;
        }

        // Show pending applications only
        List<Application> pendingApps = new ArrayList<>();
        System.out.println("Pending Applications:");
        
        int index = 1;
        for (Application app : applications) {
            if (app.getStatus() == ApplicationStatus.PENDING) {
                System.out.println("[" + index + "] Student ID: " + app.getStudentId() + 
                                 " - Status: " + app.getStatus());
                pendingApps.add(app);
                index++;
            }
        }
        
        if (pendingApps.isEmpty()) {
            System.out.println("No pending applications.\n");
            return;
        }
        
        System.out.println("[0] Cancel\n");
        System.out.print("Select application to approve: ");
        int choice = UniversalFunctions.readIntInRange(0, pendingApps.size());
        
        if (choice == 0) return;

        Application selectedApp = pendingApps.get(choice - 1);
        
        // Confirm
        System.out.print("Approve application from Student " + selectedApp.getStudentId() + "? (yes/no): ");
        String confirm = scanner.nextLine().trim().toLowerCase();
        
        if (confirm.equals("yes") || confirm.equals("y")) {
            // Use existing method
            compRep.approveApplication(selectedApp);
            System.out.println("Application approved successfully!\n");
        } else {
            System.out.println("Approval cancelled.\n");
        }
    }

    /**
     * Reject a student application
     */
    private void rejectApplication(CompanyRepresentative compRep) {
        System.out.println("\n=== REJECT APPLICATION ===");
        
        // First select internship
        Internship selectedInternship = selectInternship(compRep);
        if (selectedInternship == null) return;
        
        // Get applications for this internship
        List<Application> applications = selectedInternship.getApplications(SystemData.getInstance());
        
        if (applications.isEmpty()) {
            System.out.println("No applications for this internship.\n");
            return;
        }

        // Show pending applications only
        List<Application> pendingApps = new ArrayList<>();
        System.out.println("Pending Applications:");
        
        int index = 1;
        for (Application app : applications) {
            if (app.getStatus() == ApplicationStatus.PENDING) {
                System.out.println("[" + index + "] Student ID: " + app.getStudentId() + 
                                 " - Status: " + app.getStatus());
                pendingApps.add(app);
                index++;
            }
        }
        
        if (pendingApps.isEmpty()) {
            System.out.println("No pending applications.\n");
            return;
        }
        
        System.out.println("[0] Cancel\n");
        System.out.print("Select application to reject: ");
        int choice = UniversalFunctions.readIntInRange(0, pendingApps.size());
        
        if (choice == 0) return;

        Application selectedApp = pendingApps.get(choice - 1);
        
        // Confirm
        System.out.print("Reject application from Student " + selectedApp.getStudentId() + "? (yes/no): ");
        String confirm = scanner.nextLine().trim().toLowerCase();
        
        if (confirm.equals("yes") || confirm.equals("y")) {
            // Use existing method
            compRep.rejectApplication(selectedApp);
            System.out.println("Application rejected.\n");
        } else {
            System.out.println("Rejection cancelled.\n");
        }
    }

    /**
     * Helper method to select an internship
     */
    private Internship selectInternship(CompanyRepresentative compRep) {
        List<Internship> internships = compRep.getInternshipsCreated();
        
        if (internships.isEmpty()) {
            System.out.println("You have no internships created yet.\n");
            return null;
        }

        System.out.println("Select an internship:");
        for (int i = 0; i < internships.size(); i++) {
            Internship intern = internships.get(i);
            System.out.println("[" + (i + 1) + "] " + intern.getTitle());
        }
        System.out.println("[0] Cancel\n");

        System.out.print("Select internship: ");
        int choice = UniversalFunctions.readIntInRange(0, internships.size());
        
        if (choice == 0) return null;
        
        return internships.get(choice - 1);
    }
}
