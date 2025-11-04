// Additional class to handle all the data?
// top class to allow all classes to access data instead of reading from csv file 
// do not need sorting function --> built in is extremely fast and efficient 
package sc2002project;

import java.io.*;
import java.util.*;

    
public class SystemData {
    
    // credential class for cleaner Credentials map
    static class Credentials {

    String Password;
    boolean Firsttimelogin;

    Credentials (String pw, boolean fl){
        this.Password = pw;
        this.Firsttimelogin = fl;
        }
    }   

    private static List<User> StudentList = new ArrayList<>();
    private static List<User> StaffList = new ArrayList<>();
    //private static List<Internship> InternshipList = new ArrayList<>();
    //private static List<Application> ApplicationList = new ArrayList<>();
    //private static List<WithdrawalRequest> WithdrawalRequestList = new ArrayList<>();
    private static HashMap<String, Credentials> CredentialsMap = new HashMap<>();

    public static void loadCredentialsCSV() {

        // get path via directory
        // temp input
        File folder = new File("C:\\Users\\Luther\\Desktop\\VScode\\Java file\\sc2002project\\CsvFiles");

        File[] files = folder.listFiles();

        File targetFile = null;

        //get specific file 
        for(File f : files) {
            if(f.getName().contains("Usernames_and_Passwords") /*&& f.getName().endsWith(".csv")*/) {
                targetFile = f;
                break;
            }
        }

        //reading from csv file and writing into hashmap
        try {
            BufferedReader br = new BufferedReader(new FileReader(targetFile));
            String line = br.readLine(); // skip header line
            
            while((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String username = parts[0];
                String password = parts[1];
                boolean firstLoginFlag = Boolean.parseBoolean(parts[2]);
                CredentialsMap.put(username, new Credentials(password, firstLoginFlag));
            }

            br.close();
        } catch (Exception e) {
            System.out.println("Error reading file: " + e);
        }
    }

    // getter for data since private 
    public static List<User> getStudentList(){
        
        return StudentList;

    }

    // getter for data since private 
    public static List<User> getStaffList(){
        
        return StaffList;

    }

    // getter for data since private 
    public static List<Internship> getInternshipList(){
        
        return InternshipList;

    }

    // getter for data since private 
    public static List<Application> getApplicationList(){
        
        return ApplicationList;

    }

    // getter for data since private 
    public static List<WithdrawalRequest> GetWithdrawalRequestList(){
        
        return WithdrawalRequestList;

    }

    // getter for data since private 
    public static HashMap<String, Credentials> getCredentialsMap(){
        
        return CredentialsMap;

    }








}
