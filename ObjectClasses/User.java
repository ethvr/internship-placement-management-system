package IPMS.ObjectClasses;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Scanner;
import IPMS.System.SystemData;
import IPMS.System.SystemApp;
import IPMS.System.SystemDataEntities.*;

public abstract class User {
    private String userID;
    private String name;
    private String email;
    //private String Username;
    //private String password;
    //private boolean Firsttimelogin = true; true --> not yet logged in for the first time 

    //public User(String userId, String name, String email)
    public User(String userID, String name, String email) {
        this.userID = userID;
        this.name = name;
        this.email = email;
        //this.password = "password";
        //this.Username = UsernameGenerator(email);
    }

    // login succesfull --> retun username
    // Username not found --> return NIL 
    // exceed max number of tries --> return NIL 
    // return name to search for correct object to instantiate
    public static String login() {

        Scanner sc = new Scanner(System.in);
        int PWTries = 5;
        String nil = "NIL";
        
        System.out.println();
        System.out.print("Enter your Username: ");
        String NameInput = sc.nextLine();
        String pwInput = null;

        while (true) {
            if(SystemData.checkUsername(NameInput)) {

                while(PWTries > 0 || !pwInput.equals("break")) {

                    System.out.println("You have " + PWTries + " tries left, type \"0\" to exit");
                    System.out.print("Enter your password: ");
                    pwInput = sc.nextLine();

                    if(SystemData.checkPassword(NameInput,pwInput)) {
                        System.out.println("Login successful!");
                        if (SystemData.getFirsttimelogin(NameInput)) {
                            System.out.println("First time login. Please change your password");
                            changePassword(NameInput);
                            //sc.close();
                            pwInput = "break";
                            SystemData.setFirsttimelogin(false, NameInput);
                            //System.out.println("exiting login");
                            return NameInput;
                        }
                        
                        else {
                            //sc.close();
                            System.out.println("Welcome back " + NameInput);
                            return NameInput;
                        }
                    } 
                    
                    else {
                            System.out.println("Wrong password.");
                            PWTries--;
                    }

                }
            System.out.println("Maximum number of tries attempted, please contact support for help");
            //sc.close();
            return nil;
            
            } 

            else {
                    System.out.println("Invalid username. Please try again.");
                    //sc.close();
                    return nil;
            }
        }
        

    }

    public static void logout() {
        SystemApp.setCurrentUser(null);
        System.out.println("Logging out...");
    }

    // takes in the username returned by the login() function
    public static void changePassword(String username) {

        // when login is unsuccessful --> returns NIL
        // not necessary?
        if (username.equals("NIL")) {
            return;
        }

        Scanner scanner = new Scanner(System.in);
        int numberoftries = 5;
        boolean pending = true;

        while (numberoftries > 0) {

            if (!pending) {
                break;
            }

            System.out.println("You have " + numberoftries + " tries left");
            System.out.print("Enter your current password: ");
            String oldPW = scanner.nextLine();

            // checks if password match before changing based on hash map
            //if (!oldPW.equals(SystemData.getCredentials(username).Password))
            if (!SystemData.checkPassword(username,oldPW)) {
                System.out.println("Wrong password, please try again.");
                numberoftries++;
                continue;
            } else {

                while (pending) {

                    System.out.println("Your password should contain at least 10 characters and a maximum of 20 characters");
                    System.out.println("Your password should contain at least 3 letters and 3 numbers");
                    System.out.println("Your password should contain at least 1 special character");
                    System.out.print("Enter your new password: ");
                    String NewPW = scanner.nextLine();

                    if (NewPW.length() < 10 || NewPW.length() > 20) {
                        System.out.println("your password does not meet the length requirements");
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
                        continue;
                    } else {
                        System.out.println("Password change successful.");
                        SystemData.setPassword(NewPW, username);
                        pending = false;
                        return;
                    }
                }
            }
        }
        if (numberoftries < 1){
            System.out.println("Maximum number of tries reached. Contact support for help");
        }
        //System.out.println("exiting change password");

    }

    public String getName() {
        return name;
    }

    public String getUserId() {
        return userID;
    }

    public String getEmail() {
        return email;
    }
}
