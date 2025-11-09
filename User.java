package sc2002project;

import java.util.HashMap;
import java.util.Scanner;

abstract class User extends SystemData {
    private String userId;
    private String name;
    //private String email;
    //private String Username;
    private String password;
    private boolean Firsttimelogin = true;

    /* 
    private static String UsernameGenerator(String email){
        
        String username = email.substring(0,email.indexOf("@"));

        return username;

    }*/

    //public User(String userId, String name, String email)
    public User(String userId, String name) {
        this.userId = userId;
        this.name = name;
        //this.email = email;
        this.password = "password";
        //this.Username = UsernameGenerator(email);
    }

    public static void login() {

        Scanner sc = new Scanner(System.in);
        
        System.out.print("Enter your Username:");
        String NameInput = sc.nextLine();

        HashMap<String, SystemData.Credentials> creds = SystemData.getCredentialsMap();

        if(creds.containsKey(NameInput)) {
            SystemData.Credentials c = creds.get(NameInput);  // ← this gets the credentials object

            System.out.print("Enter your password: ");
            String pwInput = sc.nextLine();

            if(c.Password.equals(pwInput)) {

                System.out.println("Login successful!");

                if(c.Firsttimelogin) {
                    
                    ForcePasswordChange();
                    
                }

            } 
            
            else {
                    System.out.println("Wrong password.");
            }
        } 
        
        else {
                System.out.println("Invalid username.");
        }
    }


    private void ForcePasswordChange() {
        if (password.equals("password")) {

            System.out.println("First time login. Please change your password.");

            this.changePassword();
        }
    }

    public void changePassword() {
        Scanner scanner = new Scanner(System.in);
        int numberoftries = 0;
        int pending = 1;

        while (numberoftries < 5) {
            System.out.println("Enter your current password: ");
            String oldPW = scanner.nextLine();

            if (!oldPW.equals(password)) {
                System.out.println("Wrong password, please try again.");
                numberoftries++;
                continue;
            } else {

                while (pending == 1) {

                    System.out.println("Your password should contain at least 10 characters and a maximum of 20 characters");
                    System.out.println("Your password should contain at least 3 letters and 3 numbers");
                    System.out.println("Your password should contain at least 1 special character");
                    System.out.println("Enter your new password:");
                    String NewPW = scanner.nextLine();

                    if (NewPW.length() < 10 || NewPW.length() > 20) {
                        System.out.println("your password does not meet the length requirements");
                        pending = 1;
                        continue;
                    }

                    int NumCount = 0;
                    int CharCount = 0;
                    int SpecCount = 0;

                    for (int i = 0; i < NewPW.length(); i++) {
                        char PWchar = NewPW.charAt(i);
                        if (Character.isDigit(PWchar)) {
                            NumCount++;
                        } else if (Character.isLetter(PWchar)) {
                            CharCount++;
                        } else if (!Character.isLetterOrDigit(PWchar)) {
                            SpecCount++;
                        }
                    }

                    if (NumCount < 3 || SpecCount == 0 || CharCount < 3) {
                        System.out.println("Password requirements not met, try again.");
                        pending = 1;
                        continue;
                    } else {
                        System.out.println("Password change successful.");
                        this.password = NewPW;
                        pending = 0;
                    }
                }
            }
        }



        scanner.close();//dont close?
    }

    public void UpdatePassword(){

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
}
