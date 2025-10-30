package sc2002project;

import java.util.Scanner;

abstract class User {
    private String userId;
    private String name;
    private String password;
    private boolean loggedIn;

    public User(String userId, String name) {
        this.userId = userId;
        this.name = name;
        this.password = "password";
    }

    public boolean login(String userID, String pw) {
        return userID.equals(userID) && password.equals(pw);
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
