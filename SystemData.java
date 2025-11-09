// Additional class to handle all the data?
// top class to allow all classes to access data instead of reading from csv file 
// do not need sorting function --> built in is extremely fast and efficient 

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

    static class StudentCSVData {
        String studentID;
        String name;
        String major;
        String email;
        int year;

        StudentCSVData(String studentID, String name, String major, int year, String email){
            this.studentID = studentID;
            this.name = name;
            this.major = major;
            this.email = email;
            this.year = year;
        }
    }

    static class StaffCSVData {
        String staffID;
        String name;
        String role;
        String department;
        String email;

        StaffCSVData(String staffID, String name, String role, String department, String email){
            this.staffID = staffID;
            this.name = name;
            this.role = role;
            this.department = department;
            this.email = email;
        }
    }

    // changed student and staff to hashmap for faster lookup when instantiating
    //private static List<StudentCSVData> StudentList = new ArrayList<>();
    private static HashMap<String, StudentCSVData> StudentMap = new HashMap<>();
    //private static List<StaffCSVData> StaffList = new ArrayList<>();
    private static HashMap<String, StaffCSVData> StaffMap = new HashMap<>();
    public Map<String, User> users = new HashMap<>();
    public Map<String, Internship> internships = new HashMap<>();
    public Map<String, Application> applications = new HashMap<>();
    public Map<String, WithdrawalRequest> withdrawalRequests = new HashMap<>();
    private static HashMap<String, Credentials> LoginMap = new HashMap<>();

    // loads the data from the CSV files into the list to be accessed once logged in
    // changed to hashmap for faster lookup to instantiate 
    /*public static void loadStudentList() {

        File folder = new File("C:\\Users\\Luther\\Desktop\\VScode\\Java file\\sc2002project\\CsvFiles");
        File[] files = folder.listFiles();
        File targetFile = null;

        for(File f : files) {
            if(f.getName().contains("student")) {
                targetFile = f;
                break;
            }
        }

        try {
            BufferedReader br = new BufferedReader(new FileReader(targetFile));
            String line = br.readLine(); // skip header line
            
            while((line = br.readLine()) != null) {

                String[] parts = line.split(",");

                String id = parts[0];
                String name = parts[1];
                String role = parts[2];
                int year = Integer.parseInt(parts[3]);
                String email = parts[4];

                StudentCSVData student = new StudentCSVData(id, name, role, year, email);
                StudentList.add(student);
            }

            br.close();
        } catch (Exception e) {
            System.out.println("Error loading student csv: " + e);
        }
    }*/

    // try with hash map
    public static void loadStudentMap() {

        File folder = new File("C:\\Users\\Luther\\Desktop\\VScode\\Java file\\sc2002project\\CsvFiles");
        File[] files = folder.listFiles();
        File targetFile = null;

        for(File f : files) {
            if(f.getName().contains("student")) {
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
                String id = parts[0];
                String name = parts[1];
                String role = parts[2];
                int year = Integer.parseInt(parts[3]);
                String email = parts[4];
                String username = email.split("@")[0];
                StudentMap.put(username, new StudentCSVData(id, name, role, year, email));
            }

            br.close();
        } catch (Exception e) {
            System.out.println("Error reading file: " + e);
        }

        try {
            BufferedReader br = new BufferedReader(new FileReader(targetFile));
            String line = br.readLine(); // skip header line
            
            while((line = br.readLine()) != null) {

                String[] parts = line.split(",");

                String id = parts[0];
                String name = parts[1];
                String role = parts[2];
                int year = Integer.parseInt(parts[3]);
                String email = parts[4];

                StudentCSVData student = new StudentCSVData(id, name, role, year, email);
                StudentList.add(student);
            }

            br.close();
        } catch (Exception e) {
            System.out.println("Error loading student csv: " + e);
        }
    }
    /*public static void loadStaffList() {
        // get path via directory
        // temp input
        File folder = new File("C:\\Users\\Luther\\Desktop\\VScode\\Java file\\sc2002project\\CsvFiles");

        File[] files = folder.listFiles();

        File targetFile = null;

        //get specific file 
        for(File f : files) {
            if(f.getName().contains("staff"))
            //&& f.getName().endsWith(".csv")) 
            {
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
                String staffID = parts[0];
                String name = parts[1];
                String role = parts[2];
                String department = parts[3];
                String email = parts[4];

                StaffCSVData staff = new StaffCSVData(staffID, name, role, department, email);
                StaffList.add(staff);
            }

            br.close();
        } catch (Exception e) {
            System.out.println("Error reading file: " + e);
        }
    
    }*/

    public static void loadStaffMap() {

        File folder = new File("C:\\Users\\Luther\\Desktop\\VScode\\Java file\\sc2002project\\CsvFiles");
        File[] files = folder.listFiles();
        File targetFile = null;

        for(File f : files) {
            if(f.getName().contains("staff")) {
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
                String staffID = parts[0];
                String name = parts[1];
                String role = parts[2];
                String department = parts[3];
                String email = parts[4];
                String username = email.split("@")[0];
                StaffMap.put(username, new StaffCSVData(staffID, name, role, department, email));
            }

            br.close();
        } catch (Exception e) {
            System.out.println("Error reading file: " + e);
        }

        try {
            BufferedReader br = new BufferedReader(new FileReader(targetFile));
            String line = br.readLine(); // skip header line
            
            while((line = br.readLine()) != null) {

                String[] parts = line.split(",");

                String id = parts[0];
                String name = parts[1];
                String role = parts[2];
                int year = Integer.parseInt(parts[3]);
                String email = parts[4];

                StudentCSVData student = new StudentCSVData(id, name, role, year, email);
                StudentList.add(student);
            }

            br.close();
        } catch (Exception e) {
            System.out.println("Error loading student csv: " + e);
        }
    }

    // loads the csv username and password sonto the hash map
    public static void loadLoginMap() {

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
                LoginMap.put(username, new Credentials(password, firstLoginFlag));
            }

            br.close();
        } catch (Exception e) {
            System.out.println("Error reading file: " + e);
        }
    }

    // getter for data since private 
    public static HashMap<String, StudentCSVData> getStudentList(){
    
        return StudentMap;

    }

    // getter for data since private 
    public static HashMap<String, StaffCSVData> getStaffMap(){
        
        return StaffMap;

    }

    // getter for data since private 
    public static HashMap<String, Credentials> getLoginMap(){
        
        return LoginMap;

    }

}