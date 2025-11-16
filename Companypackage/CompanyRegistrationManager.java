package Companypackage;
import java.util.Scanner;

public class CompanyRegistrationManager { //or comp controller i didnt see that class at first so i replaced it with this class
    private Scanner scanner;
    
    public CompanyRegistrationManager(Scanner scanner) {
        this.scanner = scanner;
    }
    
    /**
     * Collects input and creates CompanyRepresentative directly
     * Returns null if user exits
     */
    public CompanyRepresentative collectAndCreateCompanyRep() {
        String email = getValidEmail();
        if (email == null) return null; // User exited
        
        System.out.println("\nProceeding with registration.\n");
        System.out.println("======= REGISTRATION =======");
        
        System.out.print("Enter your Name: ");
        String name = scanner.nextLine().trim();
        
        System.out.print("Enter your Company Name: ");
        String companyName = scanner.nextLine().trim();
        
        System.out.print("Enter your Department: ");
        String department = scanner.nextLine().trim();
        
        System.out.print("Enter your Position: ");
        String position = scanner.nextLine().trim();
        
        // Create the CompanyRepresentative object immediately
        return new CompanyRepresentative(email, name, companyName, department, position);
    }
    
    /**
     * Gets and validates email input
     * Returns null if user exits
     */
    private String getValidEmail() {
        while (true) {
            System.out.print("Enter your Email (or type EXIT to leave): ");
            String emailInput = scanner.nextLine().trim();
            
            if (emailInput.equalsIgnoreCase("exit")) {
                return null;
            }
            
            if (!emailInput.contains("@")) {
                System.out.println("Invalid email format. Please try again.\n");
                continue;
            }
            
            String username = emailInput.substring(0, emailInput.indexOf('@'));
            
            if (SystemData.representatives.containsKey(username)) {
                System.out.println("An Account with the Email " + emailInput + " already exists\n");
                return null;
            }
            
            System.out.println("\nEmail does not exist in system.");
            System.out.println("Do you wish to proceed with registration?\n");
            System.out.println("[1] Re-enter Email");
            System.out.println("[2] Continue with this email");
            System.out.println("[3] Exit\n");
            System.out.print("Enter an option (1-3): ");
            
            int option = readIntInRange(1, 3);
            
            switch (option) {
                case 1 -> {
                    System.out.println("\nPlease re-enter your email.\n");
                    continue;
                }
                case 2 -> {
                    return emailInput;
                }
                case 3 -> {
                    return null;
                }
            }
        }
    }
    
    /**
     * Registers the company representative in the system
     */
    public void registerCompanyRepresentative(CompanyRepresentative compRep) {//in hashmap
        String username = compRep.getUserId().substring(0, compRep.getUserId().indexOf('@'));
        
        // Store in representatives HashMap
        SystemData.representatives.put(username, compRep);
        
        // Create login credentials
        Credentials creds = new Credentials("password", true, "company");//idk whats credentials
        SystemData.credentials.put(username, creds);
    }
    
    /**
     * Display confirmation for a CompanyRepresentative
     */
    public void displayConfirmation(CompanyRepresentative compRep) {
        String username = compRep.getUserId().substring(0, compRep.getUserId().indexOf('@'));
        System.out.println("\nPlease confirm your details:");
        System.out.println("EMAIL: " + compRep.getUserId());
        System.out.println("USERNAME: " + username);
        System.out.println("NAME: " + compRep.getName());  // Company rep's name
        System.out.println("COMPANY: " + compRep.getCompanyName());  // Company name
        System.out.println("DEPARTMENT: " + compRep.getDepartment());
        System.out.println("POSITION: " + compRep.getPosition());
    }
    
    /**
     * Utility method to read integer in range
     */
    private int readIntInRange(int min, int max) {
        while (true) {
            try {
                int value = Integer.parseInt(scanner.nextLine().trim());
                if (value >= min && value <= max) {
                    return value;
                }
                System.out.print("Please enter a number between " + min + " and " + max + ": ");
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a number: ");
            }
        }
    }
}