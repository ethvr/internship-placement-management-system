
import java.io.*;
import java.lang.reflect.Field;
import java.util.*;

public class UserManager {
    
    private static List<String> RegistrationInput = new ArrayList<>();

    /**
     * Creates username/password entries for all users in CSV files
     * This should be called BEFORE SystemData.loadAllData()
     */
    public static void UsernameCSVGenerator() {
        File outputFile  = new File("data/PasswordCSVFolder");
        File inputFolder = new File("data/PeopleCSVFolder");

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

                        // Track so later files don't duplicate
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

    /**
     * Returns the user type for a given username
     */
    public static String getUserType(String username) {
        SystemData.Credentials credentials = SystemData.getCredentials(username);
        if (credentials == null) {
            return null;
        }
        return credentials.Type;
    }

    /**
     * Company Representative Registration - Collect input
     * Returns list of formatted strings for display, or empty list to exit
     */
    public static List<String> CompanyRepRegistrationInput() {
        RegistrationInput.clear();
        List<String> EmptyList = new ArrayList<>();
        List<String> printer = new ArrayList<>();

        Scanner sc = new Scanner(System.in);
        String emailInput;
        String username;

        // Check if email has already been registered
        while (true) {
            System.out.print("Enter your Email (or type EXIT to leave): ");
            emailInput = sc.nextLine().trim();
            
            if (emailInput.equalsIgnoreCase("exit")) {
                return EmptyList;
            }

            if (!emailInput.contains("@")) {
                System.out.println("Invalid email format. Please try again.\n");
                continue;
            }

            username = emailInput.substring(0, emailInput.indexOf('@')).trim();
            
            // Check if company rep already exists
            CompanyRepresentative existingRep = SystemData.getCompanyRep(username);
            
            if (existingRep != null) {
                System.out.println("An Account with the Email " + emailInput + " already exists\n");
                return EmptyList;
            } else {
                System.out.println("Email does not exist in our system.");
                System.out.println("Do you wish to proceed with registration?\n");
                System.out.println("[1] Re-enter Email");
                System.out.println("[2] Continue with this email");
                System.out.println("[3] Exit\n");
                System.out.print("Enter an option: ");
                
                int option = SystemApp.readIntInRange(1, 3);

                if (option == 1) {
                    System.out.println("\nPlease re-enter your email.\n");
                    continue;
                }

                if (option == 2) {
                    System.out.println("\nProceeding with registration.\n");
                    break;
                }

                if (option == 3) {
                    return EmptyList;
                }
            }
        }

        System.out.println("======= REGISTRATION ======");

        // Collect fields
        String companyRepId = IdGenerator.nextCompanyId();
        RegistrationInput.add(companyRepId);
        
        System.out.print("Enter your Name: ");
        String name = sc.nextLine().trim();
        RegistrationInput.add(name);
        printer.add("NAME: " + name);

        System.out.print("Enter your Company Name: ");
        String companyName = sc.nextLine().trim();
        RegistrationInput.add(companyName);
        printer.add("COMPANY NAME: " + companyName);

        System.out.print("Enter your Department: ");
        String department = sc.nextLine().trim();
        RegistrationInput.add(department);
        printer.add("DEPARTMENT: " + department);

        System.out.print("Enter your Position: ");
        String position = sc.nextLine().trim();
        RegistrationInput.add(position);
        printer.add("POSITION: " + position);

        RegistrationInput.add(emailInput);
        printer.add("EMAIL: " + emailInput);
        printer.add("USERNAME: " + username);

        String status = "Pending";
        RegistrationInput.add(status);
        printer.add("STATUS: " + status);

        return printer;
    }

    /**
     * Company Representative Registration - Confirmation
     * Returns: 1 = redo, 2 = continue (should never return), 3 = cancel/complete
     */
    public static int CompanyRegistrationConfirmation(List<String> printer) {
        Scanner sc = new Scanner(System.in);
        int option;

        System.out.println("\nPlease confirm your details:");
        for (String detail : printer) {
            System.out.println(detail);
        }
        System.out.println();
        System.out.println("[1] Redo details");
        System.out.println("[2] Continue");
        System.out.println("[3] Cancel registration\n");
        System.out.print("Enter an option: ");

        option = SystemApp.readIntInRange(1, 3);

        switch (option) {
            case 1 -> {
                return 1; // Redo
            }

            case 2 -> {
                // Extract username from email
                String email = RegistrationInput.get(5); // Email is at index 5
                String username = email.substring(0, email.indexOf('@')).trim();

                // Create CompanyRepresentative object
                // Order: companyRepId, name, companyName, department, position, email, status
                CompanyRepresentative newRep = new CompanyRepresentative(
                    RegistrationInput.get(5), // email
                    RegistrationInput.get(1), // name
                    RegistrationInput.get(2), // companyName
                    RegistrationInput.get(3), // department
                    RegistrationInput.get(4)  // position
                );
                
                // Add to SystemData
                SystemData.addCompanyRep(username, newRep);
                
                // Also add credentials for login
                // Note: You may need to manually add to credentials CSV or use SystemData method
                
                // Save all data
                SystemData.saveAllData();
                
                System.out.println("Registration complete. Please wait for your account to be approved.");

                return 3; // Return to main menu
            }

            case 3 -> {
                System.out.println("Registration cancelled.");
                return 3;
            }

            default -> {
                return 3;
            }
        }
    }
}
