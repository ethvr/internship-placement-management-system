// main app class 
// runs main
// include login and logout functions to dictate current user 

import java.util.*;

public class SystemApp {

    public static String currentUser = null;
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        // Load ALL data at startup - single call
        SystemData.loadAllData();

        int option2;
        int optionBack = 3;
        
        while (optionBack == 3 || optionBack == 4){
            int option = MainMenu();
            switch (option) {
            // login
            case 1 -> {
                String username = User.login();
                if (!username.equals("NIL")) {
                    currentUser = username;
                    switch (SystemData.getCredentialsType(username).toLowerCase()) {
                        case "student" -> {
                            // Get student object from SystemData
                            Student studentObj = SystemData.getStudent(currentUser);
                            
                            if (studentObj == null) {
                                System.out.println("Error: Student data not found!");
                                logout();
                                break;
                            }

                            option2 = StudentMenu();
                            switch (option2) {
                                case 1 -> {
                                    // View all available internships
                                    viewAvailableInternships(studentObj);
                                }
                                case 2 -> {
                                    // View your applications
                                    viewStudentApplications(studentObj);
                                }
                                case 3 -> {
                                    // View your withdrawal applications
                                    viewStudentWithdrawals(studentObj);
                                }
                                case 4 -> {
                                    logout();
                                    SystemData.saveAllData(); // Save before logout
                                }
                            }
                        }

                        case "staff" -> {
                            // Get staff object from SystemData
                            CareerCenter staffObj = SystemData.getStaff(currentUser);
                            
                            if (staffObj == null) {
                                System.out.println("Error: Staff data not found!");
                                logout();
                                break;
                            }

                            option2 = StaffMenu();
                            switch (option2) {
                                case 1 -> {
                                    // View company rep account requests
                                    viewPendingCompanyReps(staffObj);
                                }
                                case 2 -> {
                                    // View internship approval requests
                                    viewPendingInternships(staffObj);
                                }
                                case 3 -> {
                                    // View all internships
                                    viewAllInternships();
                                }
                                case 4 -> {
                                    logout();
                                    SystemData.saveAllData(); // Save before logout
                                }
                            }
                        }

                        case "company" -> {
                            // Get company rep object from SystemData
                            CompanyRepresentative companyObj = SystemData.getCompanyRep(currentUser);
                            
                            if (companyObj == null) {
                                System.out.println("Error: Company representative data not found!");
                                logout();
                                break;
                            }

                            option2 = CompanyMenu();
                            switch (option2) {
                                case 1 -> {
                                    // View all listed internships
                                    viewAllInternships();
                                }
                                case 2 -> {
                                    // View pending internships (created by this rep)
                                    viewCompanyPendingInternships(companyObj);
                                }
                                case 3 -> {
                                    // View all applications for this company's internships
                                    viewCompanyApplications(companyObj);
                                }
                                case 4 -> {
                                    logout();
                                    SystemData.saveAllData(); // Save before logout
                                }
                            }
                        }
                        
                        default -> {
                            System.out.println("Unknown user type!");
                        }
                    }
                }
            }

            // comp rep account creation
            case 2 -> {
                optionBack = 1;
                while (optionBack == 1) {
                    List<String> UserInput = UserManager.CompanyRepRegistrationInput();
                    if (UserInput.isEmpty()){
                        optionBack = 3;
                        System.out.println("Exiting...");
                        currentUser = null;
                    }
                    else {
                        optionBack = UserManager.CompanyRegistrationConfirmation(UserInput);
                    }
                }
            }
            
            case 3 -> {              
                optionBack = CompanyStatusCheck();
            }

            // exit
            case 4 -> {
                currentUser = null;
                System.out.println("Saving all data...");
                SystemData.saveAllData(); // Save before exit
                System.out.println("Exiting...");
                optionBack = 0; // Break the loop
            }

            } // switch end bracket
        }
    }

    // ========== HELPER METHODS FOR STUDENT ==========
    
    private static void viewAvailableInternships(Student student) {
        System.out.println("\n===== AVAILABLE INTERNSHIPS =====");
        List<Internship> eligible = student.getEligibleInternships();
        
        if (eligible.isEmpty()) {
            System.out.println("No internships available for you at this time.");
            return;
        }
        
        for (int i = 0; i < eligible.size(); i++) {
            Internship internship = eligible.get(i);
            System.out.println("\n[" + (i + 1) + "] " + internship.getTitle());
            System.out.println("    Company: " + internship.getCompanyName());
            System.out.println("    Level: " + internship.getLevel());
            System.out.println("    Slots: " + internship.getSlots());
            System.out.println("    Description: " + internship.getDescription());
        }
        
        // Option to apply
        System.out.print("\nEnter internship number to apply (0 to go back): ");
        int choice = readIntInRange(0, eligible.size());
        
        if (choice > 0) {
            try {
                student.applyTo(eligible.get(choice - 1).getId());
                System.out.println("Application submitted successfully!");
                SystemData.saveAllData(); // Save after applying
            } catch (Exception e) {
                System.out.println("Application failed: " + e.getMessage());
            }
        }
    }
    
    private static void viewStudentApplications(Student student) {
        System.out.println("\n===== YOUR APPLICATIONS =====");
        boolean found = false;
        
        for (Application app : SystemData.getAllApplications()) {
            if (app.getStudentId().equals(student.getUserId())) {
                found = true;
                Internship internship = SystemData.getInternship(app.getInternshipId());
                System.out.println("\nApplication ID: " + app.getId());
                System.out.println("Internship: " + (internship != null ? internship.getTitle() : "Unknown"));
                System.out.println("Status: " + app.getStatus());
                System.out.println("Accepted by you: " + app.isAcceptedByStudent());
            }
        }
        
        if (!found) {
            System.out.println("You have no applications.");
        }
    }
    
    private static void viewStudentWithdrawals(Student student) {
        System.out.println("\n===== YOUR WITHDRAWAL REQUESTS =====");
        boolean found = false;
        
        for (WithdrawalRequest wr : SystemData.getAllWithdrawals()) {
            if (wr.getStudentId().equals(student.getUserId())) {
                found = true;
                System.out.println("\nWithdrawal ID: " + wr.getId());
                System.out.println("Application ID: " + wr.getApplicationId());
                System.out.println("Status: " + wr.getStatus());
                System.out.println("Request Time: " + wr.getRequestTime());
            }
        }
        
        if (!found) {
            System.out.println("You have no withdrawal requests.");
        }
    }

    // ========== HELPER METHODS FOR STAFF ==========
    
    private static void viewPendingCompanyReps(CareerCenter staff) {
        System.out.println("\n===== PENDING COMPANY REPRESENTATIVES =====");
        List<CompanyRepresentative> pending = staff.getPendingCompanies();
        
        if (pending.isEmpty()) {
            System.out.println("No pending company representative approvals.");
            return;
        }
        
        for (int i = 0; i < pending.size(); i++) {
            CompanyRepresentative rep = pending.get(i);
            System.out.println("\n[" + (i + 1) + "] " + rep.getName());
            System.out.println("    Company: " + rep.getCompanyName());
        }
    }
    
    private static void viewPendingInternships(CareerCenter staff) {
        System.out.println("\n===== PENDING INTERNSHIPS =====");
        boolean found = false;
        
        for (Internship internship : SystemData.getAllInternships()) {
            if (internship.getStatus().equals("Pending")) {
                found = true;
                System.out.println("\nID: " + internship.getId());
                System.out.println("Title: " + internship.getTitle());
                System.out.println("Company: " + internship.getCompanyName());
            }
        }
        
        if (!found) {
            System.out.println("No pending internships.");
        }
    }
    
    private static void viewAllInternships() {
        System.out.println("\n===== ALL INTERNSHIPS =====");
        
        for (Internship internship : SystemData.getAllInternships()) {
            System.out.println("\nTitle: " + internship.getTitle());
            System.out.println("Company: " + internship.getCompanyName());
            System.out.println("Status: " + internship.getStatus());
            System.out.println("Visible: " + internship.isVisible());
            System.out.println("Slots: " + internship.getSlots());
        }
    }

    // ========== HELPER METHODS FOR COMPANY REP ==========
    
    private static void viewCompanyPendingInternships(CompanyRepresentative company) {
        System.out.println("\n===== YOUR PENDING INTERNSHIPS =====");
        boolean found = false;
        
        for (Internship internship : company.getInternshipsCreated()) {
            if (internship.getStatus().equals("Pending")) {
                found = true;
                System.out.println("\nTitle: " + internship.getTitle());
                System.out.println("Status: " + internship.getStatus());
            }
        }
        
        if (!found) {
            System.out.println("No pending internships.");
        }
    }
    
    private static void viewCompanyApplications(CompanyRepresentative company) {
        System.out.println("\n===== APPLICATIONS FOR YOUR INTERNSHIPS =====");
        boolean found = false;
        
        for (Internship internship : company.getInternshipsCreated()) {
            List<Application> apps = internship.getApplications();
            if (!apps.isEmpty()) {
                found = true;
                System.out.println("\n" + internship.getTitle() + ":");
                for (Application app : apps) {
                    System.out.println("  - Student: " + app.getStudentId() + 
                                     ", Status: " + app.getStatus());
                }
            }
        }
        
        if (!found) {
            System.out.println("No applications for your internships.");
        }
    }

    // ========== UTILITY METHODS ==========
        
    protected static void logout() {
        currentUser = null;
        System.out.println("Logging out...");
    }

    protected static void login(String username) {
        currentUser = username;
    }

    public static int MainMenu() {
        System.out.println("\n======= WELCOME =======");
        System.out.println("[1] Login");
        System.out.println("[2] Create Account (Company Representative)");
        System.out.println("[3] Check Company Representative account status");
        System.out.println("[4] Exit\n");

        System.out.print("Enter an option (1-4): ");
        return readIntInRange(1, 4);
    }   

    public static int StudentMenu() {
        System.out.println("\n======= STUDENT =======");
        System.out.println("[1] View all available Internships");
        System.out.println("[2] View your Internship Applications");
        System.out.println("[3] View your Withdrawal Applications");
        System.out.println("[4] Logout\n");

        System.out.print("Enter an option (1-4): ");
        return readIntInRange(1, 4);
    }

    public static int StaffMenu() {
        System.out.println("\n======= CAREER STAFF =======");
        System.out.println("[1] View all Company Representative Account Request");
        System.out.println("[2] View all Internship Approval Requests");
        System.out.println("[3] View all available Internships");
        System.out.println("[4] Logout\n");

        System.out.print("Enter an option (1-4): ");
        return readIntInRange(1, 4);
    }

    public static int CompanyMenu() {
        System.out.println("\n======= COMPANY REP =======");
        System.out.println("[1] View all listed Internships opportunities");
        System.out.println("[2] View pending Internships");
        System.out.println("[3] View all Internship Applications");
        System.out.println("[4] Logout\n");

        System.out.print("Enter an option (1-4): ");
        return readIntInRange(1, 4);
    }

    public static int readIntInRange(int min, int max) {
        while (true) {
            if (!sc.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                sc.next(); // discard invalid input
                continue;
            }

            int option = sc.nextInt();
            sc.nextLine(); // Consume newline
            if (option >= min && option <= max) {
                return option;
            }

            System.out.println("Invalid option, try again.");
        }
    }

    public static int CompanyStatusCheck() {
        System.out.print("Enter your Email used for registration: ");
        String email;

        while (true) {
            email = sc.nextLine().trim();

            if (email.isEmpty()) {
                System.out.println("Email cannot be empty. Please try again:");
                continue;
            }

            if (!email.contains("@") 
                || email.startsWith("@") 
                || email.endsWith("@") 
                || email.indexOf('@') != email.lastIndexOf('@')) {

                System.out.println("Invalid email format. Please enter a valid email:");
                continue;
            }

            break;
        }

        String username = email.substring(0, email.indexOf('@')).trim();

        CompanyRepresentative company = SystemData.getCompanyRep(username);

        if (company == null) {
            System.out.println("Error: Account does not exist.");
            return 4;
        }

        System.out.println("====== STATUS ======");
        String status = company.isApproved() ? "Approved" : "Pending";
        System.out.println("Account Approval Status: " + status);

        if (company.isApproved()) {
            System.out.println("====== LOGIN DETAILS ======");
            System.out.println("Your Username is: " + username);
            System.out.println("Your Password is: password");
        }
        
        return 4;
    }

    // Filter methods - now return Internship objects instead of InternshipData
    public static List<Internship> filterByCompanyName(String companyName) {
        List<Internship> results = new ArrayList<>();

        for (Internship i : SystemData.getAllInternships()) {
            if (i.getCompanyName().equalsIgnoreCase(companyName)) {
                results.add(i);
            }
        }
        return results;
    }

    public static List<Internship> filterByClosingDate(int closing) {
        List<Internship> results = new ArrayList<>();

        for (Internship i : SystemData.getAllInternships()) {
            if (Integer.parseInt(i.getCloseDate()) == closing) {
                results.add(i);
            }
        }
        return results;
    }
}

