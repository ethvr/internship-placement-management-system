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
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.*;
import IPMS.System.SystemData;
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

        //output file 
        Path PasswordPath = UniversalFunctions.FindFolder("Password");
        File PasswordFile = new File(PasswordPath.toFile(), "usernames_and_passwords.csv");

        //input file folder
        Path PeoplePath = UniversalFunctions.FindFolder("People");
        File PeopleFolder = PeoplePath.toFile();

        // 1) Build set of existing usernames (if output exists)
        HashSet<String> existing = new HashSet<>();
        if (PasswordFile.exists()) {
            try (BufferedReader old = new BufferedReader(new FileReader(PasswordFile))) {
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
        File[] csvFiles = PeopleFolder.listFiles((dir, name) -> name.toLowerCase().endsWith(".csv"));
        if (csvFiles == null || csvFiles.length == 0) {
            System.out.println("No .csv files found in PeopleCSVFolder");
            return;
        }

        // 3) Decide writer mode and whether to write header
        boolean needHeader = !PasswordFile.exists() || PasswordFile.length() == 0;

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(PasswordFile, /* append */ true))) {
            if (needHeader) {
                bw.write("Username,Password,Firsttimelogin,Type");
                bw.newLine();
            } else if (PasswordFile.length() > 0) {
                // Ensure a newline before appending if file does not end with one
                try (RandomAccessFile raf = new RandomAccessFile(PasswordFile, "r")) {
                    raf.seek(PasswordFile.length() - 1);
                    int lastChar = raf.read();
                    if (lastChar != '\n' && lastChar != '\r') {
                        bw.newLine();
                    }
                }
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
                        int emailIdx = -1;
                        String type = null;

                        // ===========================
                        // 1. Identify FILE TYPE
                        // ===========================
                        if (fname.contains("company")) {
                            emailIdx = 5;
                            type = "company";

                            // Must have status column
                            if (parts.length <= 6) {
                                //System.out.println("SKIP (company missing status): " + Arrays.toString(parts));
                                continue;
                            }

                            String statusStr = parts[6].trim().toUpperCase();
                            if (!statusStr.equals("APPROVED")) {
                                //System.out.println("SKIP (company not approved): " + Arrays.toString(parts));
                                continue;
                            }
                        }
                        else if (fname.contains("staff")) {
                            emailIdx = 4;
                            type = "staff";
                        }
                        else if (fname.contains("student")) {
                            emailIdx = 4;
                            type = "student";
                        }
                        else {
                            continue; // unrelated file
                        }

                        // ===========================
                        // 2. Validate EMAIL INDEX
                        // ===========================
                        if (emailIdx == -1 || parts.length <= emailIdx) {
                            //System.out.println("SKIP (missing email index): " + Arrays.toString(parts));
                            continue;
                        }

                        String email = parts[emailIdx].trim();
                        if (email.isEmpty() || !email.contains("@")) {
                            //System.out.println("SKIP (invalid email): " + Arrays.toString(parts));
                            continue;
                        }

                        // ===========================
                        // 3. Generate USERNAME
                        // ===========================
                        String username = email.substring(0, email.indexOf('@')).trim();
                        if (username.isEmpty()) {
                            //System.out.println("SKIP (empty username): " + Arrays.toString(parts));
                            continue;
                        }

                        if (existing.contains(username)) {
                            //System.out.println("SKIP (duplicate username): " + username);
                            continue;
                        }

                        // ===========================
                        // 4. WRITE NEW USER
                        // ===========================
                        bw.write(username + ",password,true," + type);
                        bw.newLine();
                        existing.add(username);

                        System.out.println("ADDED USER: " + username + " (" + type + ")");
                    }
                }
            }

        } catch (IOException e) {
            System.out.println("Error writing output: " + e.getMessage());
        }
    }

    /** 
     * @return List<String>
     */
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
            emailInput = sc.nextLine().trim();

            if (emailInput.equalsIgnoreCase("exit")) {
                return EmptyList;
            }

            // === EMAIL FORMAT VALIDATION ===
            if (!emailInput.contains("@") ||
                emailInput.startsWith("@") ||
                emailInput.endsWith("@") ||
                emailInput.contains(" ") ||
                emailInput.indexOf('@') == emailInput.length() - 1) {

                System.out.println("Invalid email format. Please enter a valid email.\n");
                continue;
            }

            // extract username before @
            username = emailInput.substring(0, emailInput.indexOf('@')).trim();

            // check if already exists
            if (map.containsKey(username)) {
                System.out.println("An account with the Email " + emailInput + " already exists\n");
                return EmptyList;
            }

            // email not in system
            System.out.println("Email does not exist in system");
            System.out.println("Do you wish to proceed with registration?\n");
            System.out.println("[1] Re-enter Email");
            System.out.println("[2] Continue with this email");
            System.out.println("[3] Exit");
            System.out.print("Enter an option: ");

            int option = UniversalFunctions.readIntInRange(1, 3);

            if (option == 1) {
                System.out.println("\nPlease re-enter your email.\n");
                continue; // restart email input
            }

            if (option == 2) {
                System.out.println("\nProceeding with registration.\n");
                break; // exit loop, valid email provided
            }

            if (option == 3) {
                return EmptyList;
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
                String email = emailInput;
                String answer2 = field.toUpperCase() + ": " + email;
                String answer3 = "USERNAME: " + username;
                printer.add(answer2);
                printer.add(answer3);
                RegistrationInput.add(email);
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

    /** 
     * @param printer
     * @return int
     */
    public static int CompanyRegistrationConfirmation(List<String> printer) {
        
        int option;

        System.out.println("\n== Please confirm your detail ==\n");
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
                    /*String username = null;
                    for (String line : printer) {
                        if (line.toLowerCase().startsWith("email: ")) {
                            username = line.split(":")[1].trim();
                            break;
                        }
                    }*/

                    CompanyRepresentative rep = new CompanyRepresentative(
                        RegistrationInput.get(4),   // email
                        RegistrationInput.get(0),   // name
                        RegistrationInput.get(1),   // company
                        RegistrationInput.get(2),   // department
                        RegistrationInput.get(3)    // position
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
        String email;

        while (true) {
            System.out.print("Enter your Email used for registration (or type EXIT to cancel): ");
            email = sc.nextLine().trim();

            // ✅ Allow user to exit
            if (email.equalsIgnoreCase("exit")) {
                System.out.println("Returning to main menu...");
                return;
            }

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

        CompanyRepresentative data = SystemData.getCompanyValue(username);

        boolean Firsttimelogin = SystemData.getFirsttimelogin(username);

        CompanyApprovalStatus status;

        if (data == null) {
            System.out.println("Error: Account does not exist.");
            return; 
        }

        System.out.println("====== STATUS ======");
        status = SystemData.getCompanyStatus(username);
        System.out.println("Account Approval Status: " + status);

        if (status == CompanyApprovalStatus.APPROVED && Firsttimelogin) {
            System.out.println("\n====== LOGIN DETAILS ======");
            System.out.println("Your Username is: " + username);
            System.out.println("Your Password is: password");
        }

        else {
            System.out.println("\n====== LOGIN DETAILS ======");
            System.out.println("Your Username is: " + username);
            System.out.println("Your Password is: *you have already changed your password*");
        }


    }

    /** 
     * @param input
     * @return CompanyCSVData
     */
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
    
    /** 
     * @param input
     * @return CompanyRepresentative
     */
    public static CompanyRepresentative toCompanyRepDynamic(List<String> input) {
        CompanyCSVData csv = toCompanyCSVDynamic(input);
        return SystemConverter.toCompanyRep(csv); // Use your existing converter!
    }

        
    

} // class end bracket
