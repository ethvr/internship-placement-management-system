// manages all users?
// add any new users to the exisitng csv file?
// able to read existing csv file to bulk enroll any type of users?
// able to read csv file for login 
// checks if someone is logged in?
// creates new csv file that only has the userid and password 
// handles login --> LoginMatch()
// username --> name before @ in email 
package sc2002project;
import java.io.*;
import java.util.*;

public class UserManager {
    
    private String Username;
    private String UserPassword;

    //function checks if the user exists in the system 
    protected boolean UsernameVerification() {
        boolean result = false;

        // code 

        return result; 
    } 

    // creates username for all students?
    public static void UsernameCreation(){

        // file location
        File InputFile = new File("C:\\Users\\Luther\\Desktop\\VScode\\Java file\\sc2002project\\CsvFiles\\sample_student_list.csv");
        File OutputFile = new File("C:\\Users\\Luther\\Desktop\\VScode\\Java file\\sc2002project\\CsvFiles\\Usernames_and_Passwords.csv");

        try {
            BufferedReader br = new BufferedReader(new FileReader(InputFile));
            BufferedWriter bw = new BufferedWriter(new FileWriter(OutputFile));

            bw.write("Username,Password,Firsttimelogin");
            bw.newLine();

            String line;
            while((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                String email = parts[4]; // <- email column index
                String username = email.split("@")[0];

                bw.write(username + ",password,true");
                bw.newLine();
            }

            br.close();
            bw.close();

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            }

    }

    //function checks if the userid and PW match 
    protected boolean LoginMatch() {
        boolean result = false;

        //code here 

        return result;

    } 

    //function allows for mass regitration via CSV file (students & staff) 
    //protected??
    public void FileRegistration() {

        //pathname of the CSV file 
        File file = new File("C:\\Users\\Luther\\Desktop\\VScode\\Java file\\sc2002project\\sample_student_list.csv");

        try {
            Scanner scan = new Scanner(file);

            while(scan.hasNextLine()) {
                String line = scan.nextLine();
                System.out.println(line); // each CSV row
            }

            scan.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found! Please insert the CSV file into the correct folder");

        //code here   

    }


}}
