package IPMS.OldFunctions;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import IPMS.ObjectClasses.Student;
import System.SystemDataEntities.ApplicationData;
import System.SystemDataEntities.CompanyCSVData;
import System.SystemDataEntities.Credentials;
import System.SystemDataEntities.InternshipData;
import System.SystemDataEntities.StaffCSVData;
import System.SystemDataEntities.StudentCSVData;
import System.SystemDataEntities.WithdrawalData;

public class OldFunctionsFromAllClasses {

     // try with hash map
    public static void loadStudentMap() {

        File folder = new File("C:\\Users\\Luther\\Desktop\\VScode\\Java file\\IPMS\\PeopleCSVFolder");
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
                String studentID = parts[0];
                String name = parts[1];
                String role = parts[2];
                int year = Integer.parseInt(parts[3]);
                String email = parts[4];
                String username = email.split("@")[0];
                StudentMap.put(username, new Student(studentID, name, email, year, role));
            }

            br.close();
        } catch (Exception e) {
            System.out.println("Error reading file: " + e);
        }

    }

    /*public static void setApplicationKeyValue(String username, Application obj) {
        ApplicationMap.put(username, obj);
    }
    public static void setALMcompany(String key, Application obj) {
        ALMcompany
            .computeIfAbsent(key, k -> new ArrayList<>())
            .add(obj);
    }
    public static void setALMstudent(String key, Application obj) {
        ALMcompany
            .computeIfAbsent(key, k -> new ArrayList<>())
            .add(obj);
    }*/

        public static int SystemAppreadIntInRange(int min, int max) {
        while (true) {
            if (!sc.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                sc.next(); // discard invalid input
                continue;
            }

            int option = sc.nextInt();
            if (option >= min && option <= max) {
                return option;
            }

            System.out.println("Invalid option, try again.");
        }
    }

    /*//Returns a internship list filtered to a specific company
    public static List<InternshipData> filterByCompanyName(String companyName) {
        List<InternshipData> results = new ArrayList<>();

        for (InternshipData i : SystemData.getInternshipMap().values()) {
            if (i.CompanyName.equalsIgnoreCase(companyName)) {
                results.add(i);
            }
        }
        return results;
    }

    public static List<InternshipData> filterByClosingDate(int closing) {
        List<InternshipData> results = new ArrayList<>();

        for (InternshipData i : SystemData.getInternshipMap().values()) {
            if (i.ClosingDate == closing) {
                results.add(i);
            }
        }
        return results;
    }*/

    public static void printList()  {
        // universal print list func 
    }

        // shows the main menu
    public static int MainMenu() {

        System.out.println("======= WELCOME =======");
        System.out.println("[1] Login");
        System.out.println("[2] Create Account (Company Representative)");
        System.out.println("[3] Check Company Representative account status");
        System.out.println("[4] Exit\n");

        System.out.print("Enter an option (1-4): ");
        return readIntInRange(1, 4);
    }   


    public static int StudentMenu() {
        System.out.println("======= STUDENT =======");
        System.out.println("[1] View all available Internships");
        System.out.println("[2] View your Internship Applications");
        System.out.println("[3] View your Withdrawal Applications");
        System.out.println("[4] Logout\n");

        System.out.print("Enter an option (1-4): ");
        return readIntInRange(1, 4);
    }


    public static int StaffMenu() {
        System.out.println("======= CAREER STAFF =======");
        System.out.println("[1] View all Company Representative Account Request");
        System.out.println("[2] View all Internship Applications Requests");
        System.out.println("[3] View all available Internships");
        System.out.println("[4] Logout\n");

        System.out.print("Enter an option (1-4): ");
        return readIntInRange(1, 4);
    }


    public static int CompanyMenu() {
        System.out.println("======= COMPANY REP =======");
        System.out.println("[1] View all listed Internships opportunities");
        System.out.println("[2] View pending Internships");
        System.out.println("[3] View all Internship Applications");
        System.out.println("[4] Logout\n");

        System.out.print("Enter an option (1-4): ");
        return readIntInRange(1, 4);
    }

    public static void StudentMenu2(int option2) {
        System.out.println("======= STUDENT =======");
        
        // switch in func or in top?
        switch (option2) {
            case 1 -> {

            }
             
        }
    }

    public static void StafftMenu2(int option2) {
        System.out.println("======= CAREER STAFF =======");
        System.out.println();
    }
    
    public static void CompanyMenu2(int option2) {
        System.out.println("======= COMPANY REP =======");
        System.out.println();
    }


    //File OutputFile  = new File("C:\\Users\\luther tang\\Desktop\\VSC files\\Java\\sc2002 project\\PasswordCSVFolder\\usernames_and_passwords.csv");
        //System.out.println(OutputFile.exists());

        /*int option2;
        int optionBack = 3;
        // optionback 3 or 4?
        while (optionBack == 3 || optionBack == 4){
            int option = MainMenu();
            switch (option) {
            // login
            case 1 -> {
                String username = User.login();
                if (!username.equals("NIL")) {
                    SystemData.loadIntoMap("internship", InternshipData.class);
                    SystemData.loadIntoMap("application", ApplicationData.class);
                    SystemData.loadIntoMap("withdrawal", WithdrawalData.class);
                    currentUser = username;
                    switch (SystemData.getCredentialsType(username).toLowerCase()) {
                        case "student" -> {
                            // load relevaent maps (code here)
                            SystemData.loadIntoMap("student", StudentCSVData.class);
                            // student map
                            Map<String, StudentCSVData> studentmap = SystemData.getStudentMap();
                            // get users object
                            StudentCSVData data = studentmap.get(currentUser);

                            Student studentObj = new Student(
                                    data.StudentID,
                                    data.Name,
                                    data.Year,
                                    data.Major
                                );

                            option2 = StudentMenu();
                            switch (option2) {
                                case 1 -> {

                                }
                                case 2 -> {
                                    
                                }
                                case 3 -> {

                                }
                                case 4 -> {
                                    logout();
                                }
                            }
                        }

                        case "staff" -> {
                            // load relevaent maps (code here)
                            SystemData.loadIntoMap("staff", StudentCSVData.class);
                            // career staff map?
                            Map<String, StaffCSVData> staffmap = SystemData.getStaffMap();
                            // get users object
                            StaffCSVData data = staffmap.get(currentUser);
                            
                            CareerCenter staffObj = new CareerCenter(
                                    data.StaffID,
                                    data.Name,
                                    data.Department
                                );

                            // application withdrawal and internship maps 
                            option2 = StaffMenu();
                            switch (option2) {
                                case 1 -> {


                                }
                                case 2 -> {
                                    
                                }
                                case 3 -> {

                                }
                                case 4 -> {
                                    logout();
                                }
                            }
                        }

                        case "company" -> {
                            // load relevaent maps (code here)
                            SystemData.loadIntoMap("staff", StudentCSVData.class);
                            // career staff map?
                            Map<String, CompanyCSVData> companymap = SystemData.getCompanyMap();
                            // get users object
                            CompanyCSVData data = companymap.get(currentUser);

                            CompanyRepresentative companyObj = CompanyRepresentative(
                                    data.Email,
                                    data.Name,
                                    data.CompanyName,
                                    data.Department,
                                    data.Position
                                );
                                
                            option2 = CompanyMenu();
                            switch (option2) {
                                case 1 -> {

                                }
                                case 2 -> {
                                    
                                }
                                case 3 -> {

                                }
                                case 4 -> {
                                    logout();
                                }
                            }
                        }
                        
                        default -> {
                        }
                    }
                }
            }

            // comp rep account creation
            case 2 -> {
                // load relevaent maps (code here)
                // company map (check if account already exists?)
                // application withdrawal and internship maps 
                // account reg func
                // exit func/code?
                optionBack = 1;
                while (optionBack == 1) {
                    List<String> UserInput = UserManager.CompanyRepRegistrationInput();
                    if (UserInput.isEmpty()){
                        optionBack = 3;
                        System.out.println("Exiting...");
                        currentUser = null;
                
                    }
                    else {
                        optionBack = UserManager.CompanyRegistrationConfirmation(UserInput);
                        
                    }

                }
                                
            }
            
            case 3 -> {              
                optionBack = UserManager.CompanyStatusCheck();
                
            }

            // exit
            case 4 -> {
                currentUser = null;
                System.out.println("Exiting...");
            }

            } // switch end bracket
        
        }*/

    public static void UserManagerUsernameCSVGenerator(){

        //File StudentFile = new File("C:\\Users\\Luther\\Desktop\\VScode\\Java file\\sc2002project\\PeopleFolder\\sample_student_list.csv");
        //File StaffFile   = new File("C:\\Users\\Luther\\Desktop\\VScode\\Java file\\sc2002project\\PeopleFolder\\sample_staff_list.csv");
        //File RepresentativeFile;
        File OutputFile  = new File("C:\\Users\\luther tang\\Desktop\\VSC files\\Java\\sc2002 project\\PasswordCSVFolder\\usernames_and_passwords.csv");
        File InputFolder = new File("C:\\Users\\luther tang\\Desktop\\VSC files\\Java\\sc2002 project\\PeopleCSVFolder");
        File[] csvFiles = InputFolder.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".csv");
            }
        });

        HashSet<String> activatedUsers = new HashSet<>();
        // handles scenario if user files are updated with new users 
        if (OutputFile.exists()) {
            try (BufferedReader old = new BufferedReader(new FileReader(OutputFile))) {
                String header = old.readLine(); // skip header
                String dataline;

                while ((dataline = old.readLine()) != null) {
                    String[] parts = dataline.split(",");
                    String existingUsername = parts[0].trim(); // username column
                    //String activatedField = parts[4].trim(); // AccountActivated column
                    activatedUsers.add(existingUsername);
                    if (activatedField.equalsIgnoreCase("true")) {
                        activatedUsers.add(existingUsername);
                    }
                
                }

            } catch (IOException e) {
                System.out.println("Error reading existing output file: " + e.getMessage());
            }
        }

        boolean beginning = true;
        BufferedWriter bw = null;
        if(csvFiles == null) {
            System.out.println(".csv files not found");
        } else {
            int count = csvFiles.length;
            for (int i = 0; i < count; i++) {
                try {
                    if(beginning) {
                        bw = new BufferedWriter(new FileWriter(OutputFile));
                        bw.write("Username,Password,Firsttimelogin,Type");
                        bw.newLine();
                        beginning = false;
                    } else {
                        bw = new BufferedWriter(new FileWriter(OutputFile, true));
                    }
                } catch (IOException e) {
                    System.out.println("Error opening writer: " + e.getMessage());
                }
                // adds username, default PW and flag to csv file  
                try (
                    BufferedReader br = new BufferedReader(new FileReader(csvFiles[i]));
                ){
                    br.readLine(); // skip header line
                    String line;
                    while((line = br.readLine()) != null) {
                        String[] parts = line.split(",");

                        String email;
                        String type;
                        String username;
                        String filename = csvFiles[i].getName().toLowerCase();
                        
                        if (filename.contains("company")) {
                            email = parts[5];
                            username = email.split("@")[0];
                            if(activatedUsers.contains(username)) {
                                continue; // skip generating default user again
                            }
                            type = "company";
                        }

                        else if (filename.contains("student") || filename.contains("staff")) {
                            email = parts[4];
                            username = email.split("@")[0];
                            if(activatedUsers.contains(username)) {
                                continue; // skip generating default user again
                            }

                            if (filename.contains("staff")) {type = "staff";}

                            if (filename.contains("student")) {type = "student";}
                            
                        } 
                        
                        else {
                            email = parts[4];
                            username = email.split("@")[0];
                            if(activatedUsers.contains(username)) {
                                continue; // skip generating default user again
                            }
                            type = "unknown";
                        }

                        username = email.split("@")[0];

                        bw.write(username + ",password,true," + type); //default values 
                        bw.newLine();
                    }

                    bw.close();

                } catch(IOException e){
                    System.out.println("Error: "+e.getMessage());
                }

            }
        }

        
        
        // adds student username, default PW and flag to csv file  
        try (
            BufferedReader br = new BufferedReader(new FileReader(StudentFile));
            BufferedWriter bw = new BufferedWriter(new FileWriter(OutputFile))
        ){
            bw.write("Username,Password,Firsttimelogin"); // header for  new csv file 
            bw.newLine();

            br.readLine(); // skip header line

            String line;
            while((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String email = parts[4];
                String username = email.split("@")[0];

                bw.write(username + ",password,true"); //default values 
                bw.newLine();
            }

        } catch(IOException e){
            System.out.println("Error: "+e.getMessage());
        }

        // adds staff username, default PW and flag to csv file  
        try (
            BufferedReader br = new BufferedReader(new FileReader(StaffFile));
            BufferedWriter bw = new BufferedWriter(new FileWriter(OutputFile, true))// append must be true to add else file will be overwritten
        ){
            br.readLine(); // skip header line

            String line;
            while((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String email = parts[4];
                String username = email.split("@")[0];

                bw.write(username + ",password,true"); // default values 
                bw.newLine();
            }

        } catch(IOException e){
            System.out.println("Error: "+e.getMessage());
        }

        // adds company representative username, default PW and flag to csv file  
        // use a loop to combine staff and company representative?
        
    }


    // loads the data from the CSV files into the list to be accessed once logged in
    // changed to hashmap for faster lookup to instantiate 
    public static void SystemDataloadStudentList() {

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
    }

    public static void SystemDataloadStaffList() {

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
    
    }

    public static void SystemDataloadStaffMap() {

        File folder = new File("C:\\Users\\Luther\\Desktop\\VScode\\Java file\\sc2002project\\PeopleCSVFolder");
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

        
    }

    // loads the csv username and password sonto the hash map
    public static void SystemDataloadLoginMap() {

        // get path via directory
        // temp input
        File folder = new File("C:\\Users\\Luther\\Desktop\\VScode\\Java file\\sc2002project\\PasswordCSVFolder");

        File[] files = folder.listFiles();

        File targetFile = null;

        //get specific file 
        for(File f : files) {
            if(f.getName().contains("Passwords") /*&& f.getName().endsWith(".csv")*/) {
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

    // V1
    public static <T> void SystemDataloadIntoMap(String filename, Class<T> clazz, HashMap<String,T> map) {

        File folder;
        if (filename.contains("password")){ 
            folder = new File("C:\\Users\\Luther\\Desktop\\VScode\\Java file\\sc2002project\\PasswordCSVFolder");
        }
        else if (filename.contains("application") || filename.contains("withdrawal") || filename.contains("internship")) {
            folder = new File("C:\\Users\\Luther\\Desktop\\VScode\\Java file\\sc2002project\\OtherCSVFolder");
        }
        else { 
            folder = new File("C:\\Users\\Luther\\Desktop\\VScode\\Java file\\sc2002project\\PeopleCSVFolder");
        }

        try {
            File targetFile = null;
            for (File f : folder.listFiles()) {
                if(f.getName().contains(filename)) {
                    targetFile = f;
                    break;
                }
            }

            BufferedReader br = new BufferedReader(new FileReader(targetFile));
            String headerLine = br.readLine();
            // store headers into a array
            String[] headers = headerLine.split(",");

            String line;
            while((line = br.readLine()) != null){
                // store each LINE of data from the CSV into a array
                String[] parts = line.split(",");

                // creates a new object based on the class with the no arg constructor 
                T obj = clazz.getDeclaredConstructor().newInstance();

                // loop for each header 
                for (int i = 0; i < headers.length; i++){
                    // set the field to point to the field based off the header 
                    Field field = clazz.getDeclaredField(headers[i]);
                    field.setAccessible(true);

                    Object value;

                    if (field.getType() == int.class){
                        value = Integer.parseInt(parts[i]);
                    }
                    else if (field.getType() == boolean.class){
                        value = Boolean.parseBoolean(parts[i]);
                    }
                    else if (field.getType() == ApplicationData.class) {
                        // need addtional types for new classes?
                    }
                    else {
                        value = parts[i];
                    }

                    field.set(obj,value);
                }

                // key = username always ?
                Field emailField;
                String key;

                if (filename.contains("password")){
                    // password CSV has Username column already as header
                    key = parts[0];
                } else if (filename.contains("staff") || filename.contains("student")) {
                    // username derived from Email
                    emailField = clazz.getDeclaredField("Email");
                    emailField.setAccessible(true);
                    String email = (String) emailField.get(obj);
                    key = email.split("@")[0];
                }
                else {
                    // remaining classes eg application etc 
                }
                

                map.put(key,obj);
            }
            br.close();

        } catch(Exception e){
            System.out.println("Load Error: "+e.getMessage());
        }
    }

    // V1
    public static <T> void SystemDatawriteBackCSV(String filename, Map<String,T> map) {
        // chooses the correct folder based on the param filename 
        File folder;
        if (filename.contains("password")){ // choose password folder (passwordcsvfolder)
            folder = new File("C:\\Users\\Luther\\Desktop\\VScode\\Java file\\sc2002project\\PasswordCSVFolder");
        }
        else if (filename.contains("application") || filename.contains("withdrawal") || filename.contains("internship")) { // choose application/ withdrawal/ internship folder (othercsvfolder)
            folder = new File("C:\\Users\\Luther\\Desktop\\VScode\\Java file\\sc2002project\\OtherCSVFolder");
        }
        else { // choose student/company/staff folder (peoplecsvfolder)
            folder = new File("C:\\Users\\Luther\\Desktop\\VScode\\Java file\\sc2002project\\PeopleCSVFolder");
        }
        try {
            // chooses the correct csv file inside the folder 
            File targetFile = null;
            for(File f : folder.listFiles()) {
                if(f.getName().contains(filename)) {
                    targetFile = f;
                    break;
                }
            }

            BufferedReader br = new BufferedReader(new FileReader(targetFile));
            String headerLine = br.readLine(); // extract header from old file 
            br.close();

            String[] headers = headerLine.split(","); // store header into a array

            BufferedWriter bw = new BufferedWriter(new FileWriter(targetFile));
            bw.write(headerLine); // rewrite the header?
            bw.newLine();

            for (Map.Entry<String,T> entry : map.entrySet()) {

                StringBuilder sb = new StringBuilder();

                for (int i = 0; i < headers.length; i++) {
                    String h = headers[i];

                    if (h.equals("Username")) {
                        // if username field exists in the header array --> get key from the map which is the username 
                        sb.append(entry.getKey());
                    } else {
                        // A Field object represents one VARIABLE/ATTRIBUTE inside a class.
                        // .getValue() returns the object stored in the map (Map method)
                        // .getClass() return the runtime object type (Reflection method)
                        // .getDeclaredField(h) for private variables and returns (Reflection method)
                        // cannot replace Field with var as u still need Field methods (setAccessible()) 
                        Field field = entry.getValue().getClass().getDeclaredField(h);
                        // access modifier flag changed to make it so that you can access the private variables 
                        field.setAccessible(true);
                        // append to the string 
                        // .get() is a method of java.lang.reflect.Field
                        sb.append(field.get(entry.getValue()));
                    }
                    // adds the comma to a string and stops before the end
                    if (i < headers.length - 1) sb.append(",");
                }

                bw.write(sb.toString()); // converts the object to a string and writes it into the file 
                bw.newLine();
            }

            bw.close();

        } catch(Exception e) {
            System.out.println("WriteBack Error: "+e.getMessage());
        }
    }

    /*// try with hash map
    public static void SystemDataloadStudentMap() {

        File folder = new File("C:\\Users\\Luther\\Desktop\\VScode\\Java file\\IPMS\\PeopleCSVFolder");
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
                String studentID = parts[0];
                String name = parts[1];
                String role = parts[2];
                int year = Integer.parseInt(parts[3]);
                String email = parts[4];
                String username = email.split("@")[0];
                StudentMap.put(username, new StudentCSVData(studentID, name, role, year, email));
            }

            br.close();
        } catch (Exception e) {
            System.out.println("Error reading file: " + e);
        }

    } */

    // getter for data since private 
    // returns unmodifiable map --> encapsulation
    // can only read cannot write
    public static Map<String, StudentCSVData> SystemDatagetStudentMap(){
        
        return Collections.unmodifiableMap(StudentMap);

    }

    // getter for data since private 
    // returns unmodifiable map --> encapsulation
    // can only read cannot write
    public static Map<String, StaffCSVData> SystemDatagetStaffMap(){
        
        return Collections.unmodifiableMap(StaffMap);

    }

    public static Map<String, CompanyCSVData> SystemDatagetCompanyMap() {

        return Collections.unmodifiableMap(RepresentativeMap);

    }

    // getter for data since private 
    // returns unmodifiable map --> encapsulation
    // can only read cannot write
    public static Map<String, InternshipData> SystemDatagetInternshipMap(){
        
        return Collections.unmodifiableMap(InternshipMap);

    }

    // getter for data since private 
    // returns unmodifiable map --> encapsulation
    // can only read cannot write
    public static Map<String, ApplicationData> SystemDatagetApplicationMap(){
        
        return Collections.unmodifiableMap(ApplicationMap);

    }

    // getter for data since private 
    // returns unmodifiable map --> encapsulation
    // can only read cannot write
    public static Map<String, WithdrawalData> SystemDatagetWithdrawalMap(){
        
        return Collections.unmodifiableMap(WithdrawalMap);

    }



}
