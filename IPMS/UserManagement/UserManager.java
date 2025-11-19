// manages all users?
// add any new users to the exisitng csv file?
// able to read existing csv file to bulk enroll any type of users?
// able to read csv file for login 
// checks if someone is logged in?
// creates new csv file that only has the userid and password 
// handles login --> LoginMatch()
// username -f (field before @ in email 
// what if new student file etc wants to be introduced on top of pre existing data?
package IPMS.UserManagement;

import java.io.*;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.*;

import IPMS.System.SystemApp;
import IPMS.System.SystemData;
import IPMS.System.SystemDataEntities;
import IPMS.System.SystemDataEntities.*;
import IPMS.System.SystemConverter ;
import IPMS.ObjectClasses.*;
import IPMS.Enums.*;
import IPMS.SystemPages.PageUtilities.UniversalFunctions;

public class UserManager {
    
    private static List<String> RegistrationInput = new ArrayList<>();

    // creates username for all students and writes to Username_and_Password file 
    // handles all 3 type files by looping and checking 

    public static void UsernameCSVGenerator() {
        File outputFile  = new File("C:\\Users\\luther tang\\Desktop\\VSC files\\Java\\sc2002 project\\PasswordCSVFolder\\usernames_and_passwords.csv");
        File inputFolder = new File("C:\\Users\\luther tang\\Desktop\\VSC files\\Java\\sc2002 project\\PeopleCSVFolder");

        // 1) Build set of existing usernames (if output exists)
        HashSet<String> existing = new HashSet<>();
        if (outputFile.exists()) {
            try (BufferedReader old = new BufferedReader(new FileReader(outputFile))) {
                String header = old.readLine(); // skip header
                String line;
                while ((line = old.readLine()) != null) {
                    String[] parts = line.split(",", -1);
                    if (parts.length >= 1) existing.add(parts[0].trim());
                }
            } catch (IOException e) {
                System.out.println("Error reading existing output file: " + e.getMessage());
            }
        }

        // 2) Collect input CSV files
        File[] csvFiles = inputFolder.listFiles((dir, name) -> name.toLowerCase().endsWith(".csv"));
        if (csvFiles == null || csvFiles.length == 0) {
            System.out.println("No .csv files found in PeopleCSVFolder");
            return;
        }

        // 3) Decide writer mode and whether to write header
        boolean needHeader = !outputFile.exists() || outputFile.length() == 0;

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile, /* append */ true))) {
            if (needHeader) {
                bw.write("Username,Password,Firsttimelogin,Type");
                bw.newLine();
            }

            // 4) Process each input file and append only NEW usernames
            for (File f : csvFiles) {
                String fname = f.getName().toLowerCase();

                try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                    br.readLine(); // skip header
                    String line;
                    while ((line = br.readLine()) != null) {
                        if (line.isEmpty()) continue;
                        String[] parts = line.split(",", -1);

                        // Decide email column index by file type
                        int emailIdx;
                        String type;
                        if (fname.contains("company")) {
                            emailIdx = 5;   // CompanyCSVData: ... Email at index 5
                            type = "company";
                        } else if (fname.contains("staff")) {
                            emailIdx = 4;   // StaffCSVData: ... Email at index 4
                            type = "staff";
                        } else if (fname.contains("student")) {
                            emailIdx = 4;   // StudentCSVData: ... Email at index 4
                            type = "student";
                        } else {
                            // Unknown file layout—skip safely
                            continue;
                        }

                        if (parts.length <= emailIdx) continue; // malformed row safeguard
                        String email = parts[emailIdx].trim();
                        if (email.isEmpty() || !email.contains("@")) continue;

                        String username = email.substring(0, email.indexOf('@')).trim();
                        if (username.isEmpty() || existing.contains(username)) continue;

                        // Append default credentials
                        bw.write(username + ",password,true," + type);
                        bw.newLine();

                        // Track so later files don’t duplicate
                        existing.add(username);
                    }
                } catch (IOException e) {
                    System.out.println("Error reading " + f.getName() + ": " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Error writing output: " + e.getMessage());
        }
    }

    /*String CompanyRepID; // before @ of email --> change to idgenerator 
    String Name;
    String CompanyName;
    String Department;
    String Position;
    String Email;
    String Status; */
    // account registration 
    // return List<String> or return SystemDataEntities.CompanyCSVData
    public static List<String> CompanyRepRegistrationInput() {

        SystemData.loadIntoMap("company", CompanyCSVData.class);
        Map<String, CompanyRepresentative> map = SystemData.getCompanyMap();
        RegistrationInput.clear();
        List<String> EmptyList = new ArrayList<>();

        Scanner sc = new Scanner(System.in);
        String emailInput;
        String username;

        // checks if email has already been registered 
        while (true) {
            System.out.print("Enter your Email (or type EXIT to leave): ");
            emailInput = sc.nextLine();
            if (emailInput.equalsIgnoreCase("exit")) {
                return EmptyList;
            }

            username = emailInput.substring(0, emailInput.indexOf('@')).trim();
            if (map.containsKey(username)) {
                System.out.println("An Account with the Email " + emailInput + " already exists\n");
                return EmptyList;
            }
            else {
                System.out.println("Email does not exists");
                System.out.println("Do you wish to proceed with registration?\n");
                System.out.println("[1] Re-enter Email");
                System.out.println("[2] Continue with this email\n");
                System.out.println("[3] Exit");
                System.out.print("Enter an option: ");
                
                int option = UniversalFunctions.readIntInRange(1,2);

                if (option == 1) {
                    System.out.println("\nPlease re-enter your email.\n");
                    continue; // goes back to start of the email loop
                }

                if (option == 2) {
                    System.out.println("\nProceeding with registration.\n");
                    break; // exit the email loop and continue registration
                }

                if (option == 3) {
                    return EmptyList;
                }
                            
            }
        }
        
        Field[] fields = CompanyCSVData.class.getDeclaredFields();
        List<String> printer = new ArrayList<>();      

        System.out.println("======= REGISTRATION ======");

        for (Field f : fields) {
            String field = f.getName();

            if (field.equalsIgnoreCase("status")) {
                RegistrationInput.add("pending");
                continue;
            }
            else if (field.equalsIgnoreCase("email")) {
                System.out.print("Enter your " + field + ": ");
                String answer = sc.nextLine();
                String answer2 = field.toUpperCase() + ": " + answer;
                String answer3 = "USERNAME: " + username;
                printer.add(answer2);
                printer.add(answer3);
                RegistrationInput.add(answer);
                continue;

            }
            else if (field.equalsIgnoreCase("companyrepid")) {
                continue;
            }

            System.out.print("Enter your " + field + ": ");
            String answer = sc.nextLine();
            String answer2 = field.toUpperCase() + ": " + answer;
            printer.add(answer2);
            RegistrationInput.add(answer);

        }
        return printer;

    }

    public static int CompanyRegistrationConfirmation(List<String> printer) {
        
        int option;

        System.out.println("Please confirm your details,");
        for (String a : printer) {
            System.out.println(a);
        }
        System.out.println();
        System.out.println("[1] Redo details");
        System.out.println("[2] Continue");
        System.out.println("[3] Cancel registration\n");

        while (true) {
            option = UniversalFunctions.readIntInRange(1,3);

            switch (option) {
                case 1 -> {
                    return 1;
                }

                case 2 -> {
                    String username = null;
                    for (String line : printer) {
                        if (line.toLowerCase().startsWith("email: ")) {
                            username = line.split(":")[1].trim();
                            break;
                        }
                    }

                    CompanyRepresentative rep = new CompanyRepresentative(
                        //id self generated
                        RegistrationInput.get(1),   // name
                        RegistrationInput.get(2),   // email
                        RegistrationInput.get(3),   // company
                        RegistrationInput.get(4),   // department
                        RegistrationInput.get(5)    // position
                    );

                    // adds new runtime creation to map
                    SystemData.CompRepCreation(rep);

                    System.out.println("Registration complete, please wait for your account to be approved");

                    return 3; // return to main menu
                }   

                case 3 -> {
                    return 3;
                }
            }

        }
        
    }

    public static void CompanyStatusCheck() {
        Scanner sc = new Scanner(System.in);
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

        Map<String, CompanyRepresentative> map = SystemData.getCompanyMap();

        CompanyRepresentative data = map.get(username);

        CompanyApprovalStatus status;

        if (data == null) {
            System.out.println("Error: Account does not exist.");
            return; 
        }

        System.out.println("====== STATUS ======");
        status = SystemData.getCompanyStatus(username);
        System.out.println("Account Approval Status: " + status);

        if (status == CompanyApprovalStatus.APPROVED) {
            System.out.println("\n====== LOGIN DETAILS ======");
            System.out.println("Your Username is: " + username);
            System.out.println("Your Password is: password");
        }


    }

    public static CompanyCSVData toCompanyCSVDynamic(List<String> input) {
        try {
            CompanyCSVData obj = new CompanyCSVData();
            Field[] fields = CompanyCSVData.class.getDeclaredFields();

            if (input.size() != fields.length) {
                throw new IllegalArgumentException(
                    "Input size does not match CompanyCSVData fields: " + input.size() + " vs " + fields.length
                );
            }

            for (int i = 0; i < fields.length; i++) {
                Field f = fields[i];
                f.setAccessible(true);

                String raw = input.get(i);
                Class<?> type = f.getType();

                Object value = switch (type.getSimpleName()) {
                    case "int"       -> Integer.parseInt(raw);
                    case "boolean"   -> Boolean.parseBoolean(raw);
                    case "LocalDate" -> LocalDate.parse(raw);
                    case "CompanyApprovalStatus" -> CompanyApprovalStatus.valueOf(raw.toUpperCase());
                    default -> raw; // Strings or other types
                };

                f.set(obj, value);
            }

            return obj;

        } catch (Exception e) {
            throw new RuntimeException("Reflection registration error: " + e.getMessage());
        }
    }
    
    public static CompanyRepresentative toCompanyRepDynamic(List<String> input) {
        CompanyCSVData csv = toCompanyCSVDynamic(input);
        return SystemConverter.toCompanyRep(csv); // Use your existing converter!
    }

        
    

} // class end bracket
