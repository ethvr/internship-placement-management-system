// Additional class to handle all the data?
// top class to allow all classes to access data instead of reading from csv file 
// do not need sorting function --> built in is extremely fast and efficient 
// create dynamic write back function (not done)
// create dynamic read csv file function? (not done)
// company, internship, application and withdrawl read function (not done)
package IPMS.System;

import java.io.*;
import java.util.*;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;

import IPMS.Enums.*;
import IPMS.System.SystemDataEntities.*; // change for final 
import IPMS.ObjectClasses.*;
import IPMS.Companypackage.*;

public class SystemData {
    //private static List<Application> ApplicationList = new ArrayList<>();
    //private static List<WithdrawalRequest> WithdrawalRequestList = new ArrayList<>();
    //private static List<Internship> InternshipList = new ArrayList<>();
    //private static List<StaffCSVData> StaffList = new ArrayList<>();
    //private static List<StudentCSVData> StudentList = new ArrayList<>();

    // changed student and staff to hashmap for faster lookup when instantiating
    
    // Key --> student ID --> change to username?
    private static HashMap<String, StudentCSVData> StudentCSVMap = new HashMap<>();
    private static HashMap<String, Student> StudentMap = new HashMap<>();
    // Key --> staff ID --> change to username?
    private static HashMap<String, StaffCSVData> StaffCSVMap = new HashMap<>();
    private static HashMap<String, CareerCenter> StaffMap = new HashMap<>();
    // Key --> Comp rep ID --> change to username?
    private static HashMap<String, CompanyCSVData> RepresentativeCSVMap = new HashMap<>();
    private static HashMap<String, CompanyRepresentative> RepresentativeMap = new HashMap<>();
    // Key --> ID generator 
    private static HashMap<String, InternshipData> InternshipCSVMap = new HashMap<>();
    private static HashMap<String, Internship> InternshipMap = new HashMap<>();
    // Key --> ID generator
    private static HashMap<String, ApplicationData> ApplicationCSVMap = new HashMap<>();
    private static HashMap<String, Application> ApplicationMap = new HashMap<>();
    // Key --> ID generator
    private static HashMap<String, WithdrawalData> WithdrawalCSVMap = new HashMap<>();
    private static HashMap<String, WithdrawalRequest> WithdrawalMap = new HashMap<>();
    // Key --> Username --> string before @ of email 
    private static HashMap<String, Credentials> LoginMap = new HashMap<>();

    // universal CSV load (name of file to load from, type of object to store in value pair of map, the map)
    public static <T> void loadIntoMap(String filename, Class<T> clazz) {
        // Pick folder
        // adjust folder path

        //HashMap<String,T> map;

        // file path for desktop
        //File PasswordFolder = new File("C:\\Users\\Luther\\Desktop\\VScode\\Java file\\IPMS\\PasswordCSVFolder");
        //File OtherFolder = new File("C:\\Users\\Luther\\Desktop\\VScode\\Java file\\IPMS\\OtherCSVFolder");
        //File PeopleFolder = new File("C:\\Users\\Luther\\Desktop\\VScode\\Java file\\IPMS\\PeopleCSVFolder");

        // file path for laptop
        File PasswordFolder = new File("C:\\Users\\Luther\\Desktop\\VScode\\Java file\\github pull push\\IPMS\\PasswordCSVFolder");
        File OtherFolder = new File("C:\\Users\\Luther\\Desktop\\VScode\\Java file\\github pull push\\IPMS\\OtherCSVFolder");
        File PeopleFolder = new File("C:\\Users\\Luther\\Desktop\\VScode\\Java file\\github pull push\\IPMS\\PeopleCSVFolder");

        File folder = switch (filename.toLowerCase()) {
            case "student", "staff", "company" -> PeopleFolder;
            case "internship", "application", "withdrawal" -> OtherFolder;
            case "password" -> PasswordFolder;
            default -> throw new IllegalArgumentException("invalid");
        };

        @SuppressWarnings("unchecked")
        HashMap<String,T> map = switch (filename.toLowerCase()) {
            case "student"    -> (HashMap<String,T>) StudentCSVMap;
            case "staff"      -> (HashMap<String,T>) StaffCSVMap;
            case "company"    -> (HashMap<String,T>) RepresentativeCSVMap;
            case "internship" -> (HashMap<String,T>) InternshipCSVMap;
            case "application"-> (HashMap<String,T>) ApplicationCSVMap;
            case "withdrawal" -> (HashMap<String,T>) WithdrawalCSVMap;
            case "password"   -> (HashMap<String,T>) LoginMap; // Credentials is already an entity
            default -> throw new IllegalArgumentException("invalid");
        };


        /*if (filename.toLowerCase().contains("password")) {
            folder = new File("C:\\Users\\Luther\\Desktop\\VScode\\Java file\\IPMS\\PasswordCSVFolder");
        } else if (filename.toLowerCase().contains("application") 
                || filename.toLowerCase().contains("withdrawal") 
                || filename.toLowerCase().contains("internship")) {
            folder = new File("C:\\Users\\Luther\\Desktop\\VScode\\Java file\\IPMS\\OtherCSVFolder");
        } else {
            folder = new File("C:\\Users\\Luther\\Desktop\\VScode\\Java file\\IPMS\\PeopleCSVFolder");
        }*/

        try {
            File targetFile = null;
            for (File f : Objects.requireNonNull(folder.listFiles())) {
                if (f.getName().toLowerCase().contains(filename.toLowerCase())) {
                    targetFile = f; 
                    break;
                }
            }
            if (targetFile == null) throw new FileNotFoundException("CSV not found for: " + filename);

            try (BufferedReader br = new BufferedReader(new FileReader(targetFile))) {
                String headerLine = br.readLine();
                if (headerLine == null) return;
                String[] headers = headerLine.split(",");

                // Map header -> index for stable lookup
                Map<String,Integer> hIndex = new HashMap<>();
                for (int i = 0; i < headers.length; i++) hIndex.put(headers[i], i);

                String line;
                while ((line = br.readLine()) != null) {
                    if (line.isEmpty()) continue;
                    String[] parts = line.split(",", -1); // keep empty cells //why -1?

                    // more dynamic way rather than passing the class as a parameter?
                    T obj = clazz.getDeclaredConstructor().newInstance();

                    // Populate fields by exact header names
                    for (String h : headers) {
                        try {
                            Field field = clazz.getDeclaredField(h);
                            field.setAccessible(true);
                            String raw = parts[hIndex.get(h)];

                            Object value = null;
                            Class<?> type = field.getType();

                            if (type == int.class) {
                                value = raw.isEmpty() ? 0 : Integer.parseInt(raw);
                            } else if (type == boolean.class) {
                                value = raw.equalsIgnoreCase("true");
                            } else if (type == LocalDateTime.class) {
                                value = raw.isEmpty() ? null : LocalDateTime.parse(raw);
                            } else if (type.isEnum()) {
                                @SuppressWarnings("rawtypes")
                                Class<? extends Enum> et = type.asSubclass(Enum.class);
                                value = raw.isEmpty() ? null : Enum.valueOf(et, raw);
                            } else {
                                // String or other reference types
                                value = raw;
                            }

                            field.set(obj, value);
                        } catch (NoSuchFieldException ignore) {
                            // Header exists in CSV but not in class → skip (or log if you prefer)
                        }
                    }

                    // Decide the map key:
                    String key;
                    // key decider for loginmap
                    if (hIndex.containsKey("Username")) {
                        key = parts[hIndex.get("Username")];
                    } 
                    // key decider for application internship and withdrawal
                    else if (hIndex.containsKey("UniqueID")) {
                        Field f = clazz.getDeclaredField("UniqueID");
                        f.setAccessible(true);
                        key = String.valueOf(f.get(obj));
                    } 
                    // key decider for student staff and companyrep
                    else if (hIndex.containsKey("Email")) {
                        Field f = clazz.getDeclaredField("Email");
                        f.setAccessible(true);
                        String email = (String) f.get(obj);
                        key = (email == null) ? "" : email.split("@")[0];
                    } 
                    else {
                        // Fallback: first column
                        key = parts[0];
                        System.out.println("fallback key for map utilised");
                    }

                    if (key == null || key.isEmpty()) {
                        throw new IllegalStateException("Empty key when loading " + filename + " into map");
                    }
                    map.put(key, obj);
                }
            }
        } catch (Exception e) {
            System.out.println("Load Error: " + e.getMessage());
        }
    }

    


    // dynamic writeback only for hashmaps 
    // map parameter --> pass using the get method (immutable map)
    public static <T> void writeBackCSV(String filename, Map<String,T> map) {

        // file path for desktop
        //File PasswordFolder = new File("C:\\Users\\Luther\\Desktop\\VScode\\Java file\\IPMS\\PasswordCSVFolder");
        //File OtherFolder = new File("C:\\Users\\Luther\\Desktop\\VScode\\Java file\\IPMS\\OtherCSVFolder");
        //File PeopleFolder = new File("C:\\Users\\Luther\\Desktop\\VScode\\Java file\\IPMS\\PeopleCSVFolder");

        // file path for laptop
        File PasswordFolder = new File("C:\\Users\\Luther\\Desktop\\VScode\\Java file\\github pull push\\IPMS\\PasswordCSVFolder");
        File OtherFolder = new File("C:\\Users\\Luther\\Desktop\\VScode\\Java file\\github pull push\\IPMS\\OtherCSVFolder");
        File PeopleFolder = new File("C:\\Users\\Luther\\Desktop\\VScode\\Java file\\github pull push\\IPMS\\PeopleCSVFolder");

        File folder = switch (filename.toLowerCase()) {
            case "student", "staff", "company" -> PeopleFolder;
            case "internship", "application", "withdrawal" -> OtherFolder;
            case "password" -> PasswordFolder;
            default -> throw new IllegalArgumentException("invalid");
        };

        // Pick folder
        /*File folder;
        if (filename.toLowerCase().contains("password")) {
            folder = new File("C:\\Users\\luther tang\\Desktop\\VSC files\\Java\\sc2002 project\\PasswordCSVFolder");
        } else if (filename.toLowerCase().contains("application") 
                || filename.toLowerCase().contains("withdrawal") 
                || filename.toLowerCase().contains("internship")) {
            folder = new File("C:\\Users\\luther tang\\Desktop\\VSC files\\Java\\sc2002 project\\OtherCSVFolder");
        } else {
            folder = new File("C:\\Users\\luther tang\\Desktop\\VSC files\\Java\\sc2002 project\\PeopleCSVFolder");
        }*/

        try {
            // Find the file
            File targetFile = null;
            for (File f : Objects.requireNonNull(folder.listFiles())) {
                if (f.getName().toLowerCase().contains(filename.toLowerCase())) {
                    targetFile = f; break;
                }
            }
            if (targetFile == null) throw new FileNotFoundException("CSV not found for: " + filename);

            // Read header to preserve column order
            String headerLine;
            try (BufferedReader br = new BufferedReader(new FileReader(targetFile))) {
                headerLine = br.readLine();
            }
            if (headerLine == null) {
                System.out.println("Error, no header found");
                return;
            }
            String[] headers = headerLine.split(",");
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(targetFile, false))) {
                bw.write(headerLine);
                bw.newLine();

                for (Map.Entry<String,T> entry : map.entrySet()) {
                    StringBuilder sb = new StringBuilder();

                    for (int i = 0; i < headers.length; i++) {
                        String h = headers[i];
                        String cell = "";

                        if (h.equals("Username")) {
                            cell = entry.getKey();
                        } else {
                            try {
                                Field field = entry.getValue().getClass().getDeclaredField(h);
                                field.setAccessible(true);
                                Object v = field.get(entry.getValue());

                                if (v == null) {
                                    cell = "";
                                } else if (v instanceof LocalDateTime) {
                                    //.toString() converts date to standard ISO format (2025-03-10T16:55:21)
                                    cell = ((LocalDateTime) v).toString();
                                } else if (v instanceof Enum<?>) {
                                    cell = ((Enum<?>) v).name();
                                } else {
                                    cell = String.valueOf(v);
                                }
                            } catch (NoSuchFieldException ignore) {
                                // Header not present in class → write empty column
                                cell = "";
                            }
                        }

                        sb.append(cell);
                        if (i < headers.length - 1) sb.append(",");
                    }

                    bw.write(sb.toString());
                    bw.newLine();
                }
            }
        } catch (Exception e) {
            System.out.println("WriteBack Error: " + e.getMessage());
        }
    }

    public static void buildObjectMapsFromEntities() {
        StudentMap.clear();
        StaffMap.clear();
        RepresentativeMap.clear();
        InternshipMap.clear();
        ApplicationMap.clear();
        WithdrawalMap.clear();

        // Students
        for (StudentCSVData data : StudentCSVMap.values()) {
            Student s = SystemConverter.toStudent(data);
            if (s != null) {
                StudentMap.put(s.getUserId(), s);
            }
        }

        // Staff → CareerCenter
        for (StaffCSVData data : StaffCSVMap.values()) {
            CareerCenter staff = new CareerCenter(
                    data.getStaffID(),
                    data.getName(),
                    data.getDepartment()
            );
            StaffMap.put(staff.getUserId(), staff);
        }

        // Company reps
        for (CompanyCSVData data : RepresentativeCSVMap.values()) {
            CompanyRepresentative rep = SystemConverter.toCompanyRep(data);
            if (rep != null) {
                RepresentativeMap.put(rep.getUserId(), rep);
            }
        }

        // Internships
        for (InternshipData data : InternshipCSVMap.values()) {
            Internship i = SystemConverter.toInternship(data);
            if (i != null) {
                InternshipMap.put(i.getId(), i);
            }
        }

        // Applications
        for (ApplicationData data : ApplicationCSVMap.values()) {
            Application a = SystemConverter.toApplication(data);
            if (a != null) {
                ApplicationMap.put(a.getId(), a);
            }
        }

        // Withdrawals
        for (WithdrawalData data : WithdrawalCSVMap.values()) {
            WithdrawalRequest wr = SystemConverter.toWithdrawal(data);
            if (wr != null) {
                WithdrawalMap.put(wr.getId(), wr);
            }
        }
    }

    public static void loadAll() {
        // Load entities from CSV files
        loadIntoMap("student",    StudentCSVData.class);
        loadIntoMap("staff",      StaffCSVData.class);
        loadIntoMap("company",    CompanyCSVData.class);
        loadIntoMap("internship", InternshipData.class);
        loadIntoMap("application",ApplicationData.class);
        loadIntoMap("withdrawal", WithdrawalData.class);
        loadIntoMap("password",   Credentials.class);

        // Build runtime object maps
        buildObjectMapsFromEntities();
    }

    public static void syncEntitiesFromObjects() {
        StudentCSVMap.clear();
        StaffCSVMap.clear();
        RepresentativeCSVMap.clear();
        InternshipCSVMap.clear();
        ApplicationCSVMap.clear();
        WithdrawalCSVMap.clear();

        // Students
        for (Student student : StudentMap.values()) {
            StudentCSVData row = SystemConverter.toStudentCSV(student);
            StudentCSVMap.put(student.getUserId(), row);
        }

        //staff
        for (CareerCenter staff : StaffMap.values()) {
            StaffCSVData row = SystemConverter.toStaffCSV(staff);
                StaffCSVMap.put(staff.getUserId(), row);
        }

        // Staff????????????
        for (CareerCenter staff : StaffMap.values()) {
            StaffCSVData row = new StaffCSVData(
                    staff.getUserId(),
                    staff.getName(),
                    "CareerCenter",           // Role if you need
                    staff.getStaffDepartment(),
                    ""                        // Email if you track it
            );
            StaffCSVMap.put(staff.getUserId(), row);
        }

        // Company reps
        for (CompanyRepresentative rep : RepresentativeMap.values()) {
            CompanyCSVData row = SystemConverter.toCompanyCSV(rep);
            RepresentativeCSVMap.put(rep.getUserId(), row);
        }

        // Internships
        for (Internship i : InternshipMap.values()) {
            InternshipData row = SystemConverter.toInternshipData(i);
            InternshipCSVMap.put(i.getId(), row);
        }

        // Applications
        for (Application app : ApplicationMap.values()) {
            ApplicationData row = SystemConverter.toApplicationData(app);
            ApplicationCSVMap.put(app.getId(), row);
        }

        // Withdrawals
        for (WithdrawalRequest wr : WithdrawalMap.values()) {
            WithdrawalData row = SystemConverter.toWithdrawalData(wr);
            WithdrawalCSVMap.put(wr.getId(), row);
        }

        // LoginMap is already the CSV entity map (Credentials), and you’re already mutating it directly.
    }

    public static void saveAll() {
        syncEntitiesFromObjects();

        writeBackCSV("student",    StudentCSVMap);
        writeBackCSV("staff",      StaffCSVMap);
        writeBackCSV("company",    RepresentativeCSVMap);
        writeBackCSV("internship", InternshipCSVMap);
        writeBackCSV("application",ApplicationCSVMap);
        writeBackCSV("withdrawal", WithdrawalCSVMap);
        writeBackCSV("password",   LoginMap);
    }



    public static List<InternshipData> filterByKeyword(String word) {
        List<InternshipData> results = new ArrayList<>();

        for (InternshipData i : getInternshipMap().values()) {
            if (i.Description.contains(word) && "visible".equalsIgnoreCase(i.Visibility)) {
                results.add(i);
            }
        }
        return results;
    }
    //Returns a internship list filtered to a specific company
    // used for students
    public static List<InternshipData> filterByCompanyName(String companyName) {
        List<InternshipData> results = new ArrayList<>();

        for (InternshipData i : getInternshipMap().values()) {
            if (i.CompanyName.equalsIgnoreCase(companyName) && "visible".equalsIgnoreCase(i.Visibility)) {
                results.add(i);
            }
        }
        return results;
    }

    public static List<InternshipData> filterByYearOfStudy(int year) {
        List<InternshipData> results = new ArrayList<>();

        for (InternshipData i : getInternshipMap().values()) {

            if (!"visible".equalsIgnoreCase(i.Visibility)) continue;

            InternshipLevel level = i.InternshipLevel;

            if (year <= 2) {
                if (level == InternshipLevel.BASIC) {
                    results.add(i);
                }
            } else {
                results.add(i);
            }
        }

        return results;
    }


    public static List<InternshipData> filterByMajor(String major) {
        List<InternshipData> results = new ArrayList<>();

        for (InternshipData i : getInternshipMap().values()) {
            if (i.PrefferedMajors.equalsIgnoreCase(major) && "visible".equalsIgnoreCase(i.Visibility)) {
                results.add(i);
            }
        }
        return results;
    }


    // used for students
    public static List<InternshipData> filterByInternshipLevel(InternshipLevel level) {
        List<InternshipData> results = new ArrayList<>();

        for (InternshipData i : getInternshipMap().values()) {
            if (i.InternshipLevel == level && "visible".equalsIgnoreCase(i.Visibility)) {
                results.add(i);
            }
        }
        return results;
    }

    public static List<InternshipData> filterByInternshipLevel(InternshipLevel level, List<InternshipData> list) {
        List<InternshipData> results = new ArrayList<>();

        for (InternshipData i : list) {
            if (i.InternshipLevel == level && "visible".equalsIgnoreCase(i.Visibility)) {
                results.add(i);
            }
        }
        return results;
    }

    // used for students
    public static List<InternshipData> filterByClosingDate(LocalDate date) {
        List<InternshipData> results = new ArrayList<>();

        for (InternshipData i : getInternshipMap().values()) {
            if (i.ClosingDate.isBefore(date) && "visible".equalsIgnoreCase(i.Visibility)) {
                results.add(i);
            }
        }
        return results;
    }

    // used for students
    public static List<InternshipData> filterBySlotsLeft(int slots) {
        List<InternshipData> results = new ArrayList<>();

        for (InternshipData i : getInternshipMap().values()) {
            if (i.NumberofSlots == slots && "visible".equalsIgnoreCase(i.Visibility)) {
                results.add(i);
            }
        }
        return results;
    }

    // getter for data since private 
    // returns unmodifiable map --> encapsulation
    // can only read cannot write
    public static Map<String, Credentials> getLoginMap(){
        
        return Collections.unmodifiableMap(LoginMap);

    }

    // gets password and firsttime login based on suername 
    /*public static SystemDataEntities.Credentials getCredentials(String username) {
        SystemDataEntities.Credentials c = LoginMap.get(username); 
        return c;

    }*/

    // checks if username exists 
    public static boolean checkUsername(String username) {
        return LoginMap.containsKey(username);
    }

    public static boolean checkPassword(String username, String password) {
        Credentials c = LoginMap.get(username);
        String mapPassword = c.Password;
        return mapPassword.equals(password);

    }

    public static boolean getFirsttimelogin(String username) {
        Credentials c = LoginMap.get(username);
        return c.Firsttimelogin;
    }

    // changes the password based on new input passwrod and matches via username
    public static void setPassword(String Password, String username) {
        LoginMap.get(username).Password = Password;
    }

    // checks for first time login, match with username 
    public static void setFirsttimelogin(boolean flag, String username) {
        LoginMap.get(username).Firsttimelogin = flag;
    }

    public static String getCredentialsType(String username) {
        String type = LoginMap.get(username).Type;
        return type;
    }

    // SETTERS FOR THE MAPS 
    public static void setStudentKeyValue(String username, Student obj) {
        StudentMap.put(username, obj);
    }

    public static void setStaffKeyValue(String username, CareerCenter obj) {
        StaffMap.put(username, obj);
    }

    //should only need this 
    public static void setCompanyKeyValue(String username, CompanyRepresentative obj) {
        RepresentativeMap.put(username, obj);
    }

    public static void setInternshipKeyValue(String username, Internship obj) {
        InternshipMap.put(username, obj);
    }

    public static void setApplicationKeyValue(String username, Application obj) {
        ApplicationMap.put(username, obj);
    }
    
    public static void setWithdrawalKeyValue(String username, WithdrawalRequest obj) {
        WithdrawalMap.put(username, obj);
    }

    public static String getCompanyStatus(String username) {
        CompanyCSVData data = RepresentativeMap.get(username);
        return data.Status;
    }






}
