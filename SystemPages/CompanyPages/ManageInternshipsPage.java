package SystemPages.CompanyPages;
import java.util.Scanner;
import java.util.List;
import Companypackage.CompanyRepresentative;
import SystemPages.Page;
import SystemPages.PageAction;
import SystemPages.UniversalFunctions;
import Internship;
import InternshipLevel;
import InternshipStatus;
import SystemData;  

public class ManageInternshipsPage implements Page {
    private final String username;
    private Scanner scanner;

    public ManageInternshipsPage(String username) {
        this.username = username;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void showMenu() {
        System.out.println("======= MANAGE INTERNSHIPS =======");
        System.out.println("[1] Create New Internship");
        System.out.println("[2] Edit Internship");
        System.out.println("[3] Delete Internship");
        System.out.println("[4] Toggle Visibility");
        System.out.println("[5] Back\n");
        System.out.print("Enter an option (1-5): ");
    }

    @Override
    public PageAction next() {
        int opt = UniversalFunctions.readIntInRange(1, 5);

        CompanyRepresentative compRep = SystemData.representatives.get(username);
        
        if (compRep == null) {
            System.out.println("Error: Company representative not found.\n");
            return PageAction.pop();
        }

        switch (opt) {
            case 1 -> {
                createInternship(compRep);
                return PageAction.push(this);
            }
            case 2 -> {
                editInternship(compRep);
                return PageAction.push(this);
            }
            case 3 -> {
                deleteInternship(compRep);
                return PageAction.push(this);
            }
            case 4 -> {
                toggleVisibility(compRep);
                return PageAction.push(this);
            }
            case 5 -> {
                return PageAction.pop();
            }
            default -> {
                return PageAction.pop();
            }
        }
    }

    /**
     * Collect input and call compRep.createInternship()
     */
    private void createInternship(CompanyRepresentative compRep) {
        System.out.println("\n=== CREATE INTERNSHIP ===");

        System.out.print("Enter Internship Title: ");
        String title = scanner.nextLine().trim();

        System.out.print("Enter Description: ");
        String description = scanner.nextLine().trim();

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

        // Just call the existing method!
        compRep.createInternship(title, description, level, preferredMajor, 
                                openDate, closeDate, slots, SystemData.getInstance());
        
        System.out.println(); // Add spacing
    }

    /**
     * Edit internship (only if not approved)
     */
    private void editInternship(CompanyRepresentative compRep) {
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

        // Check if approved
        if (selectedInternship.getStatus() == InternshipStatus.APPROVED) {
            System.out.println("Cannot edit: Internship already approved by Career Center.\n");
            return;
        }

        System.out.println("\nEditing: " + selectedInternship.getTitle());
        System.out.println("(Simplified edit - add more fields if needed)\n");

        System.out.print("New Number of Slots (current: " + selectedInternship.getSlots() + "): ");
        int newSlots = UniversalFunctions.readIntInRange(1, 10);
        selectedInternship.setSlots(newSlots);

        System.out.println("Internship updated successfully!\n");
    }

    /**
     * Select and delete internship using compRep.deleteInternship()
     */
    private void deleteInternship(CompanyRepresentative compRep) {
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

        System.out.print("Are you sure you want to delete '" + selectedInternship.getTitle() + "'? (yes/no): ");
        String confirm = scanner.nextLine().trim().toLowerCase();

        if (confirm.equals("yes") || confirm.equals("y")) {
            // Just call the existing method!
            compRep.deleteInternship(selectedInternship, SystemData.getInstance());
            System.out.println("Internship deleted successfully!\n");
        } else {
            System.out.println("Deletion cancelled.\n");
        }
    }

    /**
     * Toggle visibility using compRep.toggleVisibility()
     */
    private void toggleVisibility(CompanyRepresentative compRep) {
        System.out.println("\n=== TOGGLE VISIBILITY ===");
        
        List<Internship> internships = compRep.getInternshipsCreated();
        
        if (internships.isEmpty()) {
            System.out.println("You have no internships created yet.\n");
            return;
        }

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
        boolean currentVisibility = intern.isVisible();
        
        // Just call the existing method!
        compRep.toggleVisibility(selectedInternship, !currentVisibility);
        
        String newStatus = !currentVisibility ? "visible" : "hidden";
        System.out.println("Internship is now " + newStatus + ".\n");
    }
}