// main app class 
// runs main
// include login and logout functions to dictate current user 
package IPMS.System;

import java.util.*;
import System.SystemDataEntities.*;
import IPMS.SystemPages.MainPage;
import IPMS.SystemPages.Page;
import IPMS.SystemPages.PageAction;
import IPMS.UserManagement.UserManager;
import IPMS.System.SystemData.*;
import IPMS.ObjectClasses.User;
import java.io.*;

public class SystemApp {

    public static String currentUser = null;
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        // things to run before the start of the code 
        UserManager.UsernameCSVGenerator();
        SystemData.loadIntoMap("password", Credentials.class);

        Stack<Page> nav = new Stack<>();
        nav.push(new MainPage());   // Start at the main menu

        // --- Main Loop ---
        while (!nav.isEmpty()) {

            Page current = nav.peek();   // Top of stack = current page

            current.showMenu();          // Display page UI
            PageAction action = current.next();  // Process user input

            // Handle page navigation
            switch (action.gettype()) {
                case PUSH -> nav.push(action.getnextPage());
                case POP  -> nav.pop();
                case EXIT -> nav.clear();  // Clear stack = exit program
            }
        }
        

        //File OutputFile  = new File("C:\\Users\\luther tang\\Desktop\\VSC files\\Java\\sc2002 project\\PasswordCSVFolder\\usernames_and_passwords.csv");
        //System.out.println(OutputFile.exists());

        /*int option2;
        int optionBack = 3;
        // optionback 3 or 4?
        while (optionBack == 3 || optionBack == 4){
            int option = MainMenu();
            switch (option) {
            // login
            case 1 -> {
                String username = User.login();
                if (!username.equals("NIL")) {
                    SystemData.loadIntoMap("internship", InternshipData.class);
                    SystemData.loadIntoMap("application", ApplicationData.class);
                    SystemData.loadIntoMap("withdrawal", WithdrawalData.class);
                    currentUser = username;
                    switch (SystemData.getCredentialsType(username).toLowerCase()) {
                        case "student" -> {
                            // load relevaent maps (code here)
                            SystemData.loadIntoMap("student", StudentCSVData.class);
                            // student map
                            Map<String, StudentCSVData> studentmap = SystemData.getStudentMap();
                            // get users object
                            StudentCSVData data = studentmap.get(currentUser);

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
                            SystemData.loadIntoMap("staff", StudentCSVData.class);
                            // career staff map?
                            Map<String, StaffCSVData> staffmap = SystemData.getStaffMap();
                            // get users object
                            StaffCSVData data = staffmap.get(currentUser);
                            
                            CareerCenter staffObj = new CareerCenter(
                                    data.StaffID,
                                    data.Name,
                                    data.Department
                                );

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
                            SystemData.loadIntoMap("staff", StudentCSVData.class);
                            // career staff map?
                            Map<String, CompanyCSVData> companymap = SystemData.getCompanyMap();
                            // get users object
                            CompanyCSVData data = companymap.get(currentUser);

                            CompanyRepresentative companyObj = CompanyRepresentative(
                                    data.Email,
                                    data.Name,
                                    data.CompanyName,
                                    data.Department,
                                    data.Position
                                );
                                
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
                optionBack = UserManager.CompanyStatusCheck();
                
            }

            // exit
            case 4 -> {
                currentUser = null;
                System.out.println("Exiting...");
            }

            } // switch end bracket
        
        }*/
    }       

    public static void setCurrentUser(String user) {
        currentUser = user;
    }       

    //username from user login and creates object?
    // protected or public or private?
    public static void login(String username) {
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


    public static int StudentMenu() {
        System.out.println("======= STUDENT =======");
        System.out.println("[1] View all available Internships");
        System.out.println("[2] View your Internship Applications");
        System.out.println("[3] View your Withdrawal Applications");
        System.out.println("[4] Logout\n");

        System.out.print("Enter an option (1-4): ");
        return readIntInRange(1, 4);
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

    /*//Returns a internship list filtered to a specific company
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
    }*/

    public static void printList()  {
        // universal print list func 
    }


    
}
