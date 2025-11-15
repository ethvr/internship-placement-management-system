import java.io.*;
import java.util.*;
import java.lang.reflect.Field;
import java.time.LocalDateTime;

    
public class SystemData {
    
    // PRIVATE HashMaps - proper encapsulation
    private static HashMap<String, Student> students = new HashMap<>();
    private static HashMap<String, CareerCenter> staff = new HashMap<>();
    private static HashMap<String, CompanyRepresentative> representatives = new HashMap<>();
    private static HashMap<String, Internship> internships = new HashMap<>();
    private static HashMap<String, Application> applications = new HashMap<>();
    private static HashMap<String, WithdrawalRequest> withdrawalRequests = new HashMap<>();
    private static HashMap<String, Credentials> LoginMap = new HashMap<>();

    // ========== PUBLIC ACCESSOR METHODS ==========
    
    // Internship methods
    public static void addInternship(Internship internship) {
        if (internship != null && internship.getId() != null) {
            internships.put(internship.getId(), internship);
        }
    }
    
    public static void removeInternship(String internshipId) {
        internships.remove(internshipId);
    }
    
    public static Internship getInternship(String internshipId) {
        return internships.get(internshipId);
    }
    
    public static Collection<Internship> getAllInternships() {
        return internships.values();
    }
    
    // Application methods
    public static void addApplication(Application application) {
        if (application != null && application.getId() != null) {
            applications.put(application.getId(), application);
        }
    }
    
    public static void removeApplication(String applicationId) {
        applications.remove(applicationId);
    }
    
    public static Application getApplication(String applicationId) {
        return applications.get(applicationId);
    }
    
    public static Collection<Application> getAllApplications() {
        return applications.values();
    }
    
    // Withdrawal methods
    public static void addWithdrawal(WithdrawalRequest withdrawal) {
        if (withdrawal != null && withdrawal.getId() != null) {
            withdrawalRequests.put(withdrawal.getId(), withdrawal);
        }
    }
    
    public static void removeWithdrawal(String withdrawalId) {
        withdrawalRequests.remove(withdrawalId);
    }
    
    public static WithdrawalRequest getWithdrawal(String withdrawalId) {
        return withdrawalRequests.get(withdrawalId);
    }
    
    public static Collection<WithdrawalRequest> getAllWithdrawals() {
        return withdrawalRequests.values();
    }
    
    // Student methods
    public static Student getStudent(String username) {
        return students.get(username);
    }
    
    public static Collection<Student> getAllStudents() {
        return students.values();
    }
    
    // Staff methods
    public static CareerCenter getStaff(String username) {
        return staff.get(username);
    }
    
    public static Collection<CareerCenter> getAllStaff() {
        return staff.values();
    }
    
    // Company Representative methods
    public static void addCompanyRep(String username, CompanyRepresentative rep) {
        if (rep != null && username != null && !username.isEmpty()) {
            representatives.put(username, rep);
        }
    }
    
    public static CompanyRepresentative getCompanyRep(String username) {
        return representatives.get(username);
    }
    
    public static Collection<CompanyRepresentative> getAllCompanyReps() {
        return representatives.values();
    }

    // ========== LOADING METHODS ==========

    /**
     * Load students from CSV and convert to Student objects
     */
    public static void loadStudents() {
        HashMap<String, StudentCSVData> tempMap = new HashMap<>();
        loadCSVIntoMap("student", StudentCSVData.class, tempMap);
        
        for (Map.Entry<String, StudentCSVData> entry : tempMap.entrySet()) {
            StudentCSVData data = entry.getValue();
            Student student = new Student(
                data.StudentID,
                data.Name,
                "password",
                data.Year,
                data.Major
            );
            students.put(entry.getKey(), student);
        }
        System.out.println("Loaded " + students.size() + " students");
    }

    /**
     * Load career center staff from CSV and convert to CareerCenter objects
     */
    public static void loadStaff() {
        HashMap<String, StaffCSVData> tempMap = new HashMap<>();
        loadCSVIntoMap("staff", StaffCSVData.class, tempMap);
        
        for (Map.Entry<String, StaffCSVData> entry : tempMap.entrySet()) {
            StaffCSVData data = entry.getValue();
            CareerCenter staffMember = new CareerCenter(
                data.StaffID,
                data.Name,
                data.Department
            );
            staff.put(entry.getKey(), staffMember);
        }
        System.out.println("Loaded " + staff.size() + " staff members");
    }

    /**
     * Load company representatives from CSV and convert to CompanyRepresentative objects
     */
    public static void loadCompanyRepresentatives() {
        HashMap<String, CompanyCSVData> tempMap = new HashMap<>();
        loadCSVIntoMap("company", CompanyCSVData.class, tempMap);
        
        for (Map.Entry<String, CompanyCSVData> entry : tempMap.entrySet()) {
            CompanyCSVData data = entry.getValue();
            CompanyRepresentative rep = new CompanyRepresentative(
                data.Email,
                data.Name,
                data.CompanyName,
                data.Department,
                data.Position
            );
            rep.setApproved(data.Status.equalsIgnoreCase("Approved"));
            representatives.put(entry.getKey(), rep);
        }
        System.out.println("Loaded " + representatives.size() + " company representatives");
    }

    /**
     * Load internships from CSV and convert to Internship objects
     */
    public static void loadInternships() {
        HashMap<String, InternshipData> tempMap = new HashMap<>();
        loadCSVIntoMap("internship", InternshipData.class, tempMap);
        
        for (Map.Entry<String, InternshipData> entry : tempMap.entrySet()) {
            InternshipData data = entry.getValue();
            
            String repUsername = data.CompanyRepInCharge;
            CompanyRepresentative rep = representatives.get(repUsername);
            
            if (rep == null) {
                System.out.println("Warning: Company rep not found for internship " + data.UniqueID);
                continue;
            }
            
            InternshipLevel level;
            try {
                level = InternshipLevel.valueOf(data.InternshipLevel.toUpperCase());
            } catch (Exception e) {
                System.out.println("Warning: Invalid internship level for " + data.UniqueID);
                level = InternshipLevel.BASIC;
            }
            
            Internship internship = new Internship(
                data.InternshipTitle,
                data.Description,
                level,
                data.PrefferedMajors,
                String.valueOf(data.OpeningDate),
                String.valueOf(data.ClosingDate),
                data.CompanyName,
                rep,
                data.NumberofSlots
            );
            
            internship.setId(data.UniqueID);
            internship.setStatus(InternshipStatus.valueOf(data.Status));//////////////////////////////////////////////////
            internship.setVisible(data.Visibility.equalsIgnoreCase("true") || data.Visibility.equalsIgnoreCase("visible"));
            
            internships.put(data.UniqueID, internship);
        }
        System.out.println("Loaded " + internships.size() + " internships");
    }

    /**
     * Load applications from CSV and convert to Application objects
     */
    public static void loadApplications() {
        HashMap<String, ApplicationData> tempMap = new HashMap<>();
        loadCSVIntoMap("application", ApplicationData.class, tempMap);
        
        for (Map.Entry<String, ApplicationData> entry : tempMap.entrySet()) {
            ApplicationData data = entry.getValue();
            
            Application app = new Application(
                data.StudentID,
                data.InternshipID,
                data.Status
            );
            
            app.setId(data.UniqueID);
            app.setAcceptedByStudent(data.AcceptedByStudent);
            
            applications.put(data.UniqueID, app);
        }
        System.out.println("Loaded " + applications.size() + " applications");
    }

    /**
     * Load withdrawal requests from CSV and convert to WithdrawalRequest objects
     */
    public static void loadWithdrawals() {
        HashMap<String, WithdrawalData> tempMap = new HashMap<>();
        loadCSVIntoMap("withdrawal", WithdrawalData.class, tempMap);
        
        for (Map.Entry<String, WithdrawalData> entry : tempMap.entrySet()) {
            WithdrawalData data = entry.getValue();
            
            WithdrawalRequest wr = new WithdrawalRequest(
                data.ApplicationID,
                data.StudentID,
                data.Status
            );
            
            wr.setId(data.UniqueID);
            if (data.RequestTime != null) {
                wr.setRequestTime(data.RequestTime);
            }
            if (data.Remarks != null && !data.Remarks.isEmpty()) {
                wr.setRemarks(data.Remarks);
            }
            
            withdrawalRequests.put(data.UniqueID, wr);
        }
        System.out.println("Loaded " + withdrawalRequests.size() + " withdrawal requests");
    }

    /**
     * Load credentials/passwords
     */
    public static void loadCredentials() {
        loadCSVIntoMap("password", Credentials.class, LoginMap);
        System.out.println("Loaded " + LoginMap.size() + " credentials");
    }

    /**
     * Load ALL data - call this at application startup
     */
    public static void loadAllData() {
        System.out.println("=== Loading all data from CSV ===");
        loadCredentials();
        loadStudents();
        loadStaff();
        loadCompanyRepresentatives();
        loadInternships();
        loadApplications();
        loadWithdrawals();
        System.out.println("=== All data loaded successfully ===");
    }

    /**
     * Generic CSV loader - loads into temporary data objects
     */
    private static <T> void loadCSVIntoMap(String filename, Class<T> clazz, HashMap<String,T> map) {
        File PasswordFolder = new File("data/PasswordCSVFolder");
        File OtherFolder = new File("data/OtherCSVFolder");
        File PeopleFolder = new File("data/PeopleCSVFolder");

        File folder = switch (filename.toLowerCase()) {
            case "student", "staff", "company" -> PeopleFolder;
            case "internship", "application", "withdrawal" -> OtherFolder;
            case "password" -> PasswordFolder;
            default -> throw new IllegalArgumentException("invalid");
        };

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

                Map<String,Integer> hIndex = new HashMap<>();
                for (int i = 0; i < headers.length; i++) hIndex.put(headers[i], i);

                String line;
                while ((line = br.readLine()) != null) {
                    if (line.isEmpty()) continue;
                    String[] parts = line.split(",", -1);

                    T obj = clazz.getDeclaredConstructor().newInstance();

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
                                value = raw;
                            }

                            field.set(obj, value);
                        } catch (NoSuchFieldException ignore) {
                        }
                    }

                    String key;
                    if (hIndex.containsKey("Username")) {
                        key = parts[hIndex.get("Username")];
                    } 
                    else if (hIndex.containsKey("UniqueID")) {
                        Field f = clazz.getDeclaredField("UniqueID");
                        f.setAccessible(true);
                        key = String.valueOf(f.get(obj));
                    } 
                    else if (hIndex.containsKey("Email")) {
                        Field f = clazz.getDeclaredField("Email");
                        f.setAccessible(true);
                        String email = (String) f.get(obj);
                        key = (email == null) ? "" : email.split("@")[0];
                    } 
                    else {
                        key = parts[0];
                    }

                    if (key == null || key.isEmpty()) {
                        throw new IllegalStateException("Empty key when loading " + filename);
                    }
                    map.put(key, obj);
                }
            }
        } catch (Exception e) {
            System.out.println("Load Error for " + filename + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ========== SAVING METHODS ==========

    /**
     * Write all data back to CSV files
     */
    public static void saveAllData() {
        System.out.println("=== Saving all data to CSV ===");
        saveStudents();
        saveStaff();
        saveInternships();
        saveApplications();
        saveWithdrawals();
        saveCredentials();
        System.out.println("=== All data saved successfully ===");
    }

    private static void saveStudents() {
        HashMap<String, StudentCSVData> tempMap = new HashMap<>();
        for (Map.Entry<String, Student> entry : students.entrySet()) {
            Student s = entry.getValue();
            StudentCSVData data = new StudentCSVData(
                s.getUserId(),
                s.getName(),
                s.getMajor(),
                s.getYearOfStudy(),
                entry.getKey() + "@student.ntu.edu.sg"
            );
            tempMap.put(entry.getKey(), data);
        }
        writeBackCSV("student", tempMap);
    }

    private static void saveStaff() {
        HashMap<String, StaffCSVData> tempMap = new HashMap<>();
        for (Map.Entry<String, CareerCenter> entry : staff.entrySet()) {
            CareerCenter cc = entry.getValue();
            StaffCSVData data = new StaffCSVData(
                cc.getUserId(),
                cc.getName(),
                "CareerCenter",
                cc.getStaffDepartment(),
                entry.getKey() + "@ntu.edu.sg"
            );
            tempMap.put(entry.getKey(), data);
        }
        writeBackCSV("staff", tempMap);
    }

    private static void saveInternships() {
        HashMap<String, InternshipData> tempMap = new HashMap<>();
        for (Map.Entry<String, Internship> entry : internships.entrySet()) {
            Internship i = entry.getValue();
            InternshipData data = new InternshipData(
                i.getId(),
                i.getTitle(),
                i.getDescription(),
                i.getLevel().toString(),
                i.getPreferredMajor(),
                Integer.parseInt(i.getOpenDate()),
                Integer.parseInt(i.getCloseDate()),
                i.getStatus().toString(),//////////////////////////////////////////////////
                i.getCompanyName(),
                i.getCompRep().getUserId(),
                i.getSlots()
            );
            data.Visibility = String.valueOf(i.isVisible());
            tempMap.put(entry.getKey(), data);
        }
        writeBackCSV("internship", tempMap);
    }

    private static void saveApplications() {
        HashMap<String, ApplicationData> tempMap = new HashMap<>();
        for (Map.Entry<String, Application> entry : applications.entrySet()) {
            Application app = entry.getValue();
            ApplicationData data = new ApplicationData(
                app.getId(),
                app.getStudentId(),
                app.getInternshipId(),
                app.getStatus(),
                app.isAcceptedByStudent()
            );
            tempMap.put(entry.getKey(), data);
        }
        writeBackCSV("application", tempMap);
    }

    private static void saveWithdrawals() {
        HashMap<String, WithdrawalData> tempMap = new HashMap<>();
        for (Map.Entry<String, WithdrawalRequest> entry : withdrawalRequests.entrySet()) {
            WithdrawalRequest wr = entry.getValue();
            WithdrawalData data = new WithdrawalData(
                wr.getId(),
                wr.getApplicationId(),
                wr.getStudentId(),
                wr.getStatus(),
                wr.getRequestTime(),
                wr.getRemarks()
            );
            tempMap.put(entry.getKey(), data);
        }
        writeBackCSV("withdrawal", tempMap);
    }

    private static void saveCredentials() {
        writeBackCSV("password", LoginMap);
    }

    private static <T> void writeBackCSV(String filename, Map<String,T> map) {
        File PasswordFolder = new File("\"data/PasswordCSVFolder\"");
        File OtherFolder = new File("data/OtherCSVFolder");
        File PeopleFolder = new File("data/PeopleCSVFolder");

        File folder = switch (filename.toLowerCase()) {
            case "student", "staff", "company" -> PeopleFolder;
            case "internship", "application", "withdrawal" -> OtherFolder;
            case "password" -> PasswordFolder;
            default -> throw new IllegalArgumentException("invalid");
        };

        try {
            File targetFile = null;
            for (File f : Objects.requireNonNull(folder.listFiles())) {
                if (f.getName().toLowerCase().contains(filename.toLowerCase())) {
                    targetFile = f; break;
                }
            }
            if (targetFile == null) throw new FileNotFoundException("CSV not found for: " + filename);

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
                                    cell = ((LocalDateTime) v).toString();
                                } else if (v instanceof Enum<?>) {
                                    cell = ((Enum<?>) v).name();
                                } else {
                                    cell = String.valueOf(v);
                                }
                            } catch (NoSuchFieldException ignore) {
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

    // ========== CREDENTIAL MANAGEMENT ==========
    
    public static Credentials getCredentials(String username) {
        return LoginMap.get(username);
    }

    public static boolean checkUsername(String username) {
        return LoginMap.containsKey(username);
    }

    public static void setPassword(String Password, String username) {
        if (LoginMap.containsKey(username)) {
            LoginMap.get(username).Password = Password;
        }
    }

    public static void setFirsttimelogin(boolean flag, String username) {
        if (LoginMap.containsKey(username)) {
            LoginMap.get(username).Firsttimelogin = flag;
        }
    }

    public static String getCredentialsType(String username) {
        Credentials c = LoginMap.get(username);
        return c != null ? c.Type : null;
    }

    public static Map<String, Credentials> getCredentialsMap() {
        return Collections.unmodifiableMap(LoginMap);
    }

    // ========== INNER CLASSES FOR CSV DATA ==========
    
    static class Credentials {
        String Password;
        boolean Firsttimelogin;
        String Type;
        
        Credentials() {}
        
        Credentials(String password, boolean firsttimelogin, String type) {
            this.Password = password;
            this.Firsttimelogin = firsttimelogin;
            this.Type = type;
        }
    }

    static class StudentCSVData {
        String StudentID;
        String Name;
        String Major;
        int Year;
        String Email;
        
        StudentCSVData() {}
        
        StudentCSVData(String studentID, String name, String major, int year, String email) {
            this.StudentID = studentID;
            this.Name = name;
            this.Major = major;
            this.Year = year;
            this.Email = email;
        }
    }

    static class StaffCSVData {
        String StaffID;
        String Name;
        String Role;
        String Department;
        String Email;
        
        StaffCSVData() {}
        
        StaffCSVData(String staffID, String name, String role, String department, String email) {
            this.StaffID = staffID;
            this.Name = name;
            this.Role = role;
            this.Department = department;
            this.Email = email;
        }
    }

    static class CompanyCSVData {
        String CompanyRepID;
        String Name;
        String CompanyName;
        String Department;
        String Position;
        String Email;
        String Status;
        
        CompanyCSVData() {}
        
        CompanyCSVData(String companyRepID, String name, String companyName, String department, 
                      String position, String email, String status) {
            this.CompanyRepID = companyRepID;
            this.Name = name;
            this.CompanyName = companyName;
            this.Department = department;
            this.Position = position;
            this.Email = email;
            this.Status = status;
        }
    }

    static class InternshipData {
        String UniqueID;
        String InternshipTitle;
        String Description;
        String InternshipLevel;
        String PrefferedMajors;
        int OpeningDate;
        int ClosingDate;
        String Status;
        String CompanyName;
        String CompanyRepInCharge;
        int NumberofSlots;
        String Visibility;
        
        InternshipData() {}
        
        InternshipData(String uniqueID, String internshipTitle, String description, 
                      String internshipLevel, String prefferedMajors, int openingDate, 
                      int closingDate, String status, String companyName, 
                      String companyRepInCharge, int numberofSlots) {
            this.UniqueID = uniqueID;
            this.InternshipTitle = internshipTitle;
            this.Description = description;
            this.InternshipLevel = internshipLevel;
            this.PrefferedMajors = prefferedMajors;
            this.OpeningDate = openingDate;
            this.ClosingDate = closingDate;
            this.Status = status;
            this.CompanyName = companyName;
            this.CompanyRepInCharge = companyRepInCharge;
            this.NumberofSlots = numberofSlots;
        }
    }

    static class ApplicationData {
        String UniqueID;
        String StudentID;
        String InternshipID;
        ApplicationStatus Status;
        boolean AcceptedByStudent;
        
        ApplicationData() {}
        
        ApplicationData(String uniqueID, String studentID, String internshipID, 
                       ApplicationStatus status, boolean acceptedByStudent) {
            this.UniqueID = uniqueID;
            this.StudentID = studentID;
            this.InternshipID = internshipID;
            this.Status = status;
            this.AcceptedByStudent = acceptedByStudent;
        }
    }

    static class WithdrawalData {
        String UniqueID;
        String ApplicationID;
        String StudentID;
        WithdrawalStatus Status;
        LocalDateTime RequestTime;
        String Remarks;
        
        WithdrawalData() {}
        
        WithdrawalData(String uniqueID, String applicationID, String studentID, 
                      WithdrawalStatus status, LocalDateTime requestTime, String remarks) {
            this.UniqueID = uniqueID;
            this.ApplicationID = applicationID;
            this.StudentID = studentID;
            this.Status = status;
            this.RequestTime = requestTime;
            this.Remarks = remarks;
        }
    }
}

