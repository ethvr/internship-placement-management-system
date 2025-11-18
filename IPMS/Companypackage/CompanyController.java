package IPMS.Companypackage;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import IPMS.SystemPages.*;
import ObjectClasses.CompanyRepresentative;
import IPMS.Enums.*;
import IPMS.ObjectClasses.*;
import IPMS.System.*;

public class CompanyController {
    private Scanner scanner;
    
    public CompanyController(Scanner scanner) {
        this.scanner = scanner;
    }
    
    /**
     * Handle creating a new internship
     */
    public void handleCreateInternship(CompanyRepresentative compRep) {
        System.out.println("\n=== CREATE INTERNSHIP ===");

        System.out.print("Enter Internship Title: ");
        String title = scanner.nextLine().trim();

        System.out.print("Enter Description: ");
        String description = scanner.nextLine().trim();

        // Select internship level
        System.out.println("\nSelect Internship Level:");
        System.out.println("[1] BASIC");
        System.out.println("[2] INTERMEDIATE");
        System.out.println("[3] ADVANCED");
        System.out.print("Enter option: ");
        int levelChoice = UniversalFunctions.readIntInRange(1, 3);
        InternshipLevel level = switch (levelChoice) {
            case 1 -> InternshipLevel.BASIC;
            case 2 -> InternshipLevel.INTERMEDIATE;
            case 3 -> InternshipLevel.ADVANCED;
            default -> InternshipLevel.BASIC;
        };

        System.out.print("Enter Preferred Major (e.g., CSC, EEE, MAE): ");
        String preferredMajor = scanner.nextLine().trim();

        System.out.print("Enter Opening Date (YYYY-MM-DD): ");
        String openDate = scanner.nextLine().trim();

        System.out.print("Enter Closing Date (YYYY-MM-DD): ");
        String closeDate = scanner.nextLine().trim();

        System.out.print("Enter Number of Slots (max 10): ");
        int slots = UniversalFunctions.readIntInRange(1, 10);

        // Call the entity method
        compRep.createInternship(title, description, level, preferredMajor, 
                                openDate, closeDate, slots, SystemData.getInstance());
        
        System.out.println();
    }

    /**
     * Handle editing an existing internship
     */
    public void handleEditInternship(CompanyRepresentative compRep) {
        System.out.println("\n=== EDIT INTERNSHIP ===");
        
        List<Internship> internships = compRep.getInternshipsCreated();
        
        if (internships.isEmpty()) {
            System.out.println("You have no internships created yet.\n");
            return;
        }

        // Display internships
        System.out.println("Your Internships:");
        for (int i = 0; i < internships.size(); i++) {
            Internship intern = internships.get(i);
            System.out.println("[" + (i + 1) + "] " + intern.getTitle() + 
                             " - Status: " + intern.getStatus());
        }
        System.out.println("[0] Cancel\n");

        System.out.print("Select internship to edit: ");
        int choice = UniversalFunctions.readIntInRange(0, internships.size());
        
        if (choice == 0) return;

        Internship selectedInternship = internships.get(choice - 1);

        // Check if approved (cannot edit if approved)
        if (selectedInternship.getStatus() == InternshipStatus.APPROVED) {
            System.out.println("Cannot edit: Internship already approved by Career Center.\n");
            return;
        }

        System.out.println("\nEditing: " + selectedInternship.getTitle());
        System.out.println("Leave blank to keep current value.\n");

        // Edit title
        System.out.print("New Title [" + selectedInternship.getTitle() + "]: ");
        String newTitle = scanner.nextLine().trim();
        if (!newTitle.isEmpty()) {
            selectedInternship.setTitle(newTitle);
        }

        // Edit description
        System.out.print("New Description [" + selectedInternship.getDescription() + "]: ");
        String newDesc = scanner.nextLine().trim();
        if (!newDesc.isEmpty()) {
            selectedInternship.setDescription(newDesc);
        }

        // Edit slots
        System.out.print("New Number of Slots [" + selectedInternship.getSlots() + "]: ");
        String slotsInput = scanner.nextLine().trim();
        if (!slotsInput.isEmpty()) {
            try {
                int newSlots = Integer.parseInt(slotsInput);
                if (newSlots >= 1 && newSlots <= 10) {
                    selectedInternship.setSlots(newSlots);
                } else {
                    System.out.println("Invalid slots. Keeping current value.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Keeping current value.");
            }
        }

        System.out.println("Internship updated successfully!\n");
    }

    /**
     * Handle deleting an internship
     */
    public void handleDeleteInternship(CompanyRepresentative compRep) {
        System.out.println("\n=== DELETE INTERNSHIP ===");
        
        List<Internship> internships = compRep.getInternshipsCreated();
        
        if (internships.isEmpty()) {
            System.out.println("You have no internships created yet.\n");
            return;
        }

        // Display internships
        System.out.println("Your Internships:");
        for (int i = 0; i < internships.size(); i++) {
            Internship intern = internships.get(i);
            System.out.println("[" + (i + 1) + "] " + intern.getTitle() + 
                             " - Status: " + intern.getStatus());
        }
        System.out.println("[0] Cancel\n");

        System.out.print("Select internship to delete: ");
        int choice = UniversalFunctions.readIntInRange(0, internships.size());
        
        if (choice == 0) return;

        Internship selectedInternship = internships.get(choice - 1);

        // Confirm deletion
        System.out.print("Are you sure you want to delete '" + selectedInternship.getTitle() + "'? (yes/no): ");
        String confirm = scanner.nextLine().trim().toLowerCase();

        if (confirm.equals("yes") || confirm.equals("y")) {
            compRep.deleteInternship(selectedInternship, SystemData.getInstance());
            System.out.println("Internship deleted successfully!\n");
        } else {
            System.out.println("Deletion cancelled.\n");
        }
    }

    /**
     * Handle toggling visibility of an internship
     */
    public void handleToggleVisibility(CompanyRepresentative compRep) {
        System.out.println("\n=== TOGGLE VISIBILITY ===");
        
        List<Internship> internships = compRep.getInternshipsCreated();
        
        if (internships.isEmpty()) {
            System.out.println("You have no internships created yet.\n");
            return;
        }

        // Display internships with visibility status
        System.out.println("Your Internships:");
        for (int i = 0; i < internships.size(); i++) {
            Internship intern = internships.get(i);
            String visible = intern.isVisible() ? "Visible" : "Hidden";
            System.out.println("[" + (i + 1) + "] " + intern.getTitle() + " - " + visible);
        }
        System.out.println("[0] Cancel\n");

        System.out.print("Select internship to toggle visibility: ");
        int choice = UniversalFunctions.readIntInRange(0, internships.size());
        
        if (choice == 0) return;

        Internship selectedInternship = internships.get(choice - 1);
        boolean currentVisibility = selectedInternship.isVisible();
        
        // Toggle visibility
        compRep.toggleVisibility(selectedInternship, !currentVisibility);
        
        String newStatus = !currentVisibility ? "visible" : "hidden";
        System.out.println("Internship is now " + newStatus + ".\n");
    }

    /**
     * Handle viewing applications for internships
     */
    public void handleViewApplications(CompanyRepresentative compRep) {
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
     * Handle approving a student application
     */
    public void handleApproveApplication(CompanyRepresentative compRep) {
        System.out.println("\n=== APPROVE APPLICATION ===");
        
        // Select internship
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
            compRep.approveApplication(selectedApp);
            System.out.println("Application approved successfully!\n");
        } else {
            System.out.println("Approval cancelled.\n");
        }
    }

    /**
     * Handle rejecting a student application
     */
    public void handleRejectApplication(CompanyRepresentative compRep) {
        System.out.println("\n=== REJECT APPLICATION ===");
        
        // Select internship
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
