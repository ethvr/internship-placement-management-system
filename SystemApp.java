// main app class 
// runs main
// include login and logout functions to dictate current user 
package sc2002project;
import java.util.*;
import java.lang.reflect.Field;

import sc2002project.SystemDataEntities.*;
import sc2002project.SystemData.*;

import java.io.*;

public class SystemApp {

    public static String currentUser = null;
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        // things to run before the start of the code 
        UserManager.UsernameCSVGenerator();
        SystemData.loadIntoMap("password", Credentials.class);

        //File OutputFile  = new File("C:\\Users\\luther tang\\Desktop\\VSC files\\Java\\sc2002 project\\PasswordCSVFolder\\usernames_and_passwords.csv");
        //System.out.println(OutputFile.exists());

        int option2;
        int optionBack = 3;
        // optionback 3 or 4?
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
                            // load relevaent maps (code here)
                            SystemData.loadIntoMap("student", StudentCSVData.class);
                            SystemData.loadIntoMap("internship", InternshipData.class);
                            SystemData.loadIntoMap("application", ApplicationData.class);
                            SystemData.loadIntoMap("withdrawal", WithdrawalData.class);
                            // student map
                            Map<String, StudentCSVData> studentmap = SystemData.getStudentMap();
                            // get users object
                            StudentCSVData data = studentmap.get(currentuser);

                            Student studentObj = new Student(
                                    data.StudentID,
                                    data.Name,
                                    data.Year,
                                    data.Major
                            );

                            option2 = StudentMenu();
                            switch (option2) {
                                case 1 -> {

                                }
                                case 2 -> {
                                    
                                }
                                case 3 -> {

                                }
                                case 4 -> {
                                    logout();
                                }
                            }
                        }

                        case "staff" -> {
                            // load relevaent maps (code here)
                            // career staff map?
                            // application withdrawal and internship maps 
                            option2 = StaffMenu();
                            switch (option2) {
                                case 1 -> {


                                }
                                case 2 -> {
                                    
                                }
                                case 3 -> {

                                }
                                case 4 -> {
                                    logout();
                                }
                            }
                        }

                        case "company" -> {
                            // load relevaent maps (code here)
                            // company map?
                            // application withdrawal and internship maps 
                            option2 = CompanyMenu();
                            switch (option2) {
                                case 1 -> {

                                }
                                case 2 -> {
                                    
                                }
                                case 3 -> {

                                }
                                case 4 -> {
                                    logout();
                                }
                            }
                        }
                        
                        default -> {
                        }
                    }
                }
            }

            // comp rep account creation
            case 2 -> {
                // load relevaent maps (code here)
                // company map (check if account already exists?)
                // application withdrawal and internship maps 
                // account reg func
                // exit func/code?
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
                System.out.println("Exiting...");
            }

            } // switch end bracket
        
        }
    }
        

    // clears username upon logout to change object for next login
    // protected or public or private?
    protected static void logout() {
        currentUser = null;
        System.out.println("logging out...");

    }

    //username from user login and creates object?
    // protected or public or private?
    protected static void login(String username) {
        currentUser = username;
    }

    // shows the main menu
    public static int MainMenu() {

        System.out.println("======= WELCOME =======");
        System.out.println("[1] Login");
        System.out.println("[2] Create Account (Company Representative)");
        System.out.println("[3] Check Company Representative account status");
        System.out.println("[4] Exit\n");

        System.out.print("Enter an option (1-4): ");
        return readIntInRange(1, 4);
    }   


    private void studentMenu(Student student) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== Student Menu =====");
            System.out.println("1. View Available Internships");
            System.out.println("2. View My Applications");
            System.out.println("3. Apply for Internship");
            System.out.println("4. Accept Successful Internship");
            System.out.println("5. Request Withdrawal");
            System.out.println("6. Change Password");
            System.out.println("7. Logout");
            System.out.print("Enter option: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("=== Available Internships ===");
                    List<Internship> list = student.getEligibleInternships(systemData);
                    for (Internship i : list) {
                        System.out.println(i.getInternshipId() + " - " + i.getTitle());
                    }
                    break;

                case 2:
                    student.viewApplications(systemData);
                    break;

                case 3:
                    System.out.print("Enter Internship ID to apply: ");
                    String iid = sc.nextLine();
                    student.applyForInternship(iid, systemData);
                    break;

                case 4:
                    System.out.print("Enter Application ID to accept: ");
                    String aid = sc.nextLine();
                    student.acceptInternship(aid, systemData);
                    break;

                case 5:
                    System.out.print("Enter Application ID to withdraw: ");
                    String wid = sc.nextLine();
                    student.requestWithdrawal(wid, systemData);
                    break;

                case 6:
                    student.changePassword(student.name);
                    break;

                case 7:
                    System.out.println("Logging out...");
                    return;

                default:
                    System.out.println("Invalid option.");
            }
        }
    }



    public static int StaffMenu() {
        System.out.println("======= CAREER STAFF =======");
        System.out.println("[1] View all Company Representative Account Request");
        System.out.println("[2] View all Internship Applications Requests");
        System.out.println("[3] View all available Internships");
        System.out.println("[4] Logout\n");

        System.out.print("Enter an option (1-4): ");
        return readIntInRange(1, 4);
    }


    public static int CompanyMenu() {
        System.out.println("======= COMPANY REP =======");
        System.out.println("[1] View all listed Internships opportunities");
        System.out.println("[2] View pending Internships");
        System.out.println("[3] View all Internship Applications");
        System.out.println("[4] Logout\n");

        System.out.print("Enter an option (1-4): ");
        return readIntInRange(1, 4);
    }

    public static void StudentMenu2(int option2) {
        System.out.println("======= STUDENT =======");
        
        // switch in func or in top?
        switch (option2) {
            case 1 -> {

            }
             
        }
    }

    public static void StafftMenu2(int option2) {
        System.out.println("======= CAREER STAFF =======");
        System.out.println();
    }
    
    public static void CompanyMenu2(int option2) {
        System.out.println("======= COMPANY REP =======");
        System.out.println();
    }

    public static int readIntInRange(int min, int max) {
        while (true) {
            if (!sc.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                sc.next(); // discard invalid input
                continue;
            }

            int option = sc.nextInt();
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

        Map<String, CompanyCSVData> map = SystemData.getCompanyMap();

        SystemDataEntities.CompanyCSVData data = map.get(username);

        String status;

        if (data == null) {
            System.out.println("Error: Account does not exist.");
            return 4;
        } else {
            // make getter method?
            System.out.println("====== STATUS ======");
            status = data.getCompanyStatus(); 
            System.out.println("Account Approval Status: " + status);
        }

        if (status.equalsIgnoreCase("approved")) {
            System.out.println("====== LOGIN DETAILS ======");
            System.out.println("Your Username is: " + username);
            System.out.println("Your Password is: password");
        }
        return 4;

    }


    //Returns a internship list filtered to a specific company
    public static List<InternshipData> filterByCompanyName(String companyName) {
        List<InternshipData> results = new ArrayList<>();

        for (InternshipData i : SystemData.getInternshipMap().values()) {
            if (i.CompanyName.equalsIgnoreCase(companyName)) {
                results.add(i);
            }
        }
        return results;
    }

    public static List<InternshipData> filterByClosingDate(int closing) {
        List<InternshipData> results = new ArrayList<>();

        for (InternshipData i : SystemData.getInternshipMap().values()) {
            if (i.ClosingDate == closing) {
                results.add(i);
            }
        }
        return results;
    }

    public static void printList()  {
        // universal print list func 
    }


    
}
