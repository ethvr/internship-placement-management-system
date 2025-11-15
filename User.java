
import java.util.Map;
import java.util.Scanner;

abstract class User {
    private String userId;
    private String name;
    private String password;
    private boolean firstTimeLogin = true;

    public User(String userId, String name) {
        this.userId = userId;
        this.name = name;
        this.password = "password";
    }

    /**
     * Login method - returns username if successful, "NIL" if failed
     */
    public static String login() {
        Scanner sc = new Scanner(System.in);
        
        System.out.print("Enter your Username: ");
        String nameInput = sc.nextLine().trim();

        // Get credentials from SystemData
        Map<String, SystemData.Credentials> creds = SystemData.getCredentialsMap();

        if (creds.containsKey(nameInput)) {
            SystemData.Credentials c = creds.get(nameInput);

            System.out.print("Enter your password: ");
            String pwInput = sc.nextLine();

            if (c.Password.equals(pwInput)) {
                System.out.println("Login successful!");

                // Force password change on first login
                if (c.Firsttimelogin) {
                    forcePasswordChange(nameInput, c.Password);
                }

                return nameInput; // Return username for SystemApp
            } else {
                System.out.println("Wrong password.");
                return "NIL";
            }
        } else {
            System.out.println("Invalid username.");
            return "NIL";
        }
    }

    /**
     * Force password change on first login
     */
    private static void forcePasswordChange(String username, String currentPassword) {
        System.out.println("\n=== FIRST TIME LOGIN ===");
        System.out.println("You must change your password.");
        
        String newPassword = changePasswordProcess(currentPassword);
        
        if (newPassword != null) {
            // Update in SystemData
            SystemData.setPassword(newPassword, username);
            SystemData.setFirsttimelogin(false, username);
            SystemData.saveAllData(); // Persist the change
            System.out.println("Password changed successfully!\n");
        }
    }

    /**
     * Change password (for existing users)
     */
    public void changePassword() {
        System.out.println("\n=== CHANGE PASSWORD ===");
        String newPassword = changePasswordProcess(this.password);
        
        if (newPassword != null) {
            this.password = newPassword;
            // Note: You should also update SystemData here if userId maps to a username
            System.out.println("Password changed successfully!\n");
        }
    }

    /**
     * Password change logic with validation
     */
    private static String changePasswordProcess(String currentPassword) {
        Scanner scanner = new Scanner(System.in);
        int numberOfTries = 0;
        int maxTries = 5;

        while (numberOfTries < maxTries) {
            System.out.print("Enter your current password: ");
            String oldPW = scanner.nextLine();

            if (!oldPW.equals(currentPassword)) {
                System.out.println("Wrong password. Please try again.");
                numberOfTries++;
                if (numberOfTries >= maxTries) {
                    System.out.println("Too many failed attempts. Password change cancelled.");
                    return null;
                }
                continue;
            }

            // Current password verified, now get new password
            while (true) {
                System.out.println("\n--- Password Requirements ---");
                System.out.println("• 10-20 characters long");
                System.out.println("• At least 3 letters");
                System.out.println("• At least 3 numbers");
                System.out.println("• At least 1 special character");
                System.out.print("\nEnter your new password: ");
                String newPW = scanner.nextLine();

                // Validate length
                if (newPW.length() < 10 || newPW.length() > 20) {
                    System.out.println("❌ Password does not meet length requirements (10-20 characters).");
                    continue;
                }

                // Count character types
                int numCount = 0;
                int charCount = 0;
                int specCount = 0;

                for (char c : newPW.toCharArray()) {
                    if (Character.isDigit(c)) {
                        numCount++;
                    } else if (Character.isLetter(c)) {
                        charCount++;
                    } else {
                        specCount++;
                    }
                }

                // Validate character requirements
                if (numCount < 3) {
                    System.out.println("❌ Password must contain at least 3 numbers.");
                    continue;
                }
                if (charCount < 3) {
                    System.out.println("❌ Password must contain at least 3 letters.");
                    continue;
                }
                if (specCount == 0) {
                    System.out.println("❌ Password must contain at least 1 special character.");
                    continue;
                }

                // All validations passed
                return newPW;
            }
        }

        return null;
    }

    public void logout() {
        System.out.println(name + " logged out.");
    }

    public String getName() {
        return name;
    }

    public String getUserId() {
        return userId;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
}
