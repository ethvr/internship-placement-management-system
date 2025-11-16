package IPMS.ObjectClasses;

import java.util.*;
import java.util.stream.Collectors;

import IPMS.Enums.InternshipLevel;
import IPMS.Enums.InternshipStatus;
import IPMS.System.SystemData;
import IPMS.System.SystemDataEntities;
import IPMS.System.SystemDataEntities.InternshipData;
import IPMS.ObjectClasses.Internship;


public class GenerateReports{

    static Map <String, SystemDataEntities.InternshipData> internshipmap = SystemData.getInternshipMap();
    static Map <String, SystemDataEntities.ApplicationData> applicationmap = SystemData.getApplicationMap();
    static List<Internship> InternshipList = new ArrayList<>();
    static List<Application> ApplicationList = new ArrayList<>();
    static Map <String, Application> ApplicationMap = new HashMap<>();



    // generate reports with applicants?
    public void printApplicants(Internship internship) {

        ApplicationList.clear();

        String internshipID = internship.getId();

        for (SystemDataEntities.ApplicationData data : applicationmap.values()) {
            if (data.getInternshipID().equalsIgnoreCase(internshipID)) {
            Application application = new Application (data.getStudentID(), data.getInternshipID(), 
                                                       data.getStatus(), data.getAcceptedByStudent());

            ApplicationList.add(application);
            }

        }

        int count = 1;
        System.out.println("-------------------------------------------------------------");
        System.out.printf("%-5s %-15s %-18s %-20s\n",
                "#", "STUDENT ID", "APPLICATION ID", "STATUS");
        System.out.println("-------------------------------------------------------------");

        for (Application app : ApplicationList) {
           
                System.out.printf("%-5d %-15s %-18s %-20s\n",
                    count,
                    app.getStudentId(),
                    app.getId(),
                    app.getStatus()   // enum prints nicely
                );
                count++;
            
        }

        if (count == 1) {
            System.out.println("No applicants found for this internship.");
        }

        System.out.println("-------------------------------------------------------------");

    }
    
    // generate comprehensive reports regarding internship opportunities created
    public void generateFullReport() {

        InternshipList.clear();

        for (InternshipData data : internshipmap.values()) {

            Internship internship = new Internship (data.getInternshipTitle(), data.getDescription(), 
            data.getInternshipLevel(), data.getPrefferedMajors(), data.getOpeningDate(), data.getClosingDate(), 
            data.getCompanyName(), data.getCompanyRepInCharge(), data.getNumberofSlots());

            InternshipList.add(internship);

        }
        System.out.println("\n========================================");
        System.out.println("    COMPREHENSIVE INTERNSHIP REPORT");
        System.out.println("========================================\n");
        
        if (internshipmap.isEmpty()) {
            System.out.println("No internship opportunities found.");
            return;
        }

        int count = 1;
        for (Internship internship : InternshipList) {

            System.out.println(count + ". " + internship.getTitle());
            System.out.println("   • Company    : " + internship.getCompanyName());
            System.out.println("   • Level      : " + internship.getLevel());
            System.out.println("   • Major      : " + internship.getPreferredMajor());
            System.out.println("   • Status     : " + internship.getStatus());
            System.out.println("   • Slots      : " + internship.getSlots() + " left");
            System.out.println("   • Visible    : " + (internship.isVisibleTo(null) ? "Yes" : "No"));
            System.out.println("   • Dates      : " + internship.getOpenDate()
                                            + " → " + internship.getCloseDate());
            printApplicants(internship);
            System.out.println("   • Description:");
            System.out.println("       " + internship.getDescription());
            System.out.println("------------------------------------------------------------");

            count++;
        }

    }

    //generate report by status

    public void generateReportByStatus(InternshipStatus status) {

        InternshipList.clear();

        // Load internships from your data map
        for (InternshipData data : internshipmap.values()) {

            Internship internship = new Internship(
                data.getInternshipTitle(),
                data.getDescription(),
                data.getInternshipLevel(),
                data.getPrefferedMajors(),
                data.getOpeningDate(),
                data.getClosingDate(),
                data.getCompanyName(),
                data.getCompanyRepInCharge(),
                data.getNumberofSlots()
            );

            InternshipList.add(internship);
        }

        System.out.println("\n=== Internships with Status: " + status + " ===\n");

        List<Internship> filtered = InternshipList.stream()
            .filter(i -> i.getStatus() == status)
            .collect(Collectors.toList());

        if (filtered.isEmpty()) {
            System.out.println("No internships found with status: " + status);
            return;
        }

        for (Internship internship : filtered) {
            System.out.println("- " + internship.getTitle() + " (" +
                            internship.getCompanyName() + ")");
        }
    }


    //genrate report by major
    public void generateReportByMajor(SystemData data, String major) {
        System.out.println("\n=== Internships for Major: " + major + " ===\n");
        
        List<Internship> filtered = data.internships.values().stream()
            .filter(i -> i.getPreferredMajor().equalsIgnoreCase(major) || 
                        i.getPreferredMajor().equalsIgnoreCase("Any"))
            .collect(Collectors.toList());

        if (filtered.isEmpty()) {
            System.out.println("No internships found for major: " + major);
            return;
        }

        for (Internship internship : filtered) {
            System.out.println("- " + internship.getTitle() + " (" + 
                             internship.getCompanyName() + ") - Level: " + 
                             internship.getLevel());
        }
    }


    // generate report by internship level
    public void generateReportByLevel(SystemData data, InternshipLevel level) {
        System.out.println("\n=== Internships at Level: " + level + " ===\n");
        
        List<Internship> filtered = data.internships.values().stream()
            .filter(i -> i.getLevel() == level)
            .collect(Collectors.toList());

        if (filtered.isEmpty()) {
            System.out.println("No internships found at level: " + level);
            return;
        }

        for (Internship internship : filtered) {
            System.out.println("- " + internship.getTitle() + " (" + 
                             internship.getCompanyName() + ") - Major: " + 
                             internship.getPreferredMajor());
        }
    }

    // generate report by company
    public void generateReportByCompany(SystemData data, String companyName) {
        System.out.println("\n=== Internships from Company: " + companyName + " ===\n");
        
        List<Internship> filtered = data.internships.values().stream()
            .filter(i -> i.getCompanyName().equalsIgnoreCase(companyName))
            .collect(Collectors.toList());

        if (filtered.isEmpty()) {
            System.out.println("No internships found from company: " + companyName);
            return;
        }

        for (Internship internship : filtered) {
            System.out.println("- " + internship.getTitle() + " - Status: " + 
                             internship.getStatus() + " - Slots: " + 
                             internship.getSlots());
        }
    }

    public void generateCustomReport(SystemData data, InternshipStatus status, 
                                    String major, InternshipLevel level, String company) {
        System.out.println("\n=== Custom Filtered Report ===");
        System.out.println("Filters Applied:");
        if (status != null) System.out.println("- Status: " + status);
        if (major != null) System.out.println("- Major: " + major);
        if (level != null) System.out.println("- Level: " + level);
        if (company != null) System.out.println("- Company: " + company);
        System.out.println();

        List<Internship> filtered = data.internships.values().stream()
            .filter(i -> status == null || i.getStatus() == status)
            .filter(i -> major == null || i.getPreferredMajor().equalsIgnoreCase(major) || 
                        i.getPreferredMajor().equalsIgnoreCase("Any"))
            .filter(i -> level == null || i.getLevel() == level)
            .filter(i -> company == null || i.getCompanyName().equalsIgnoreCase(company))
            .collect(Collectors.toList());

        if (filtered.isEmpty()) {
            System.out.println("No internships match the specified filters.");
            return;
        }

        int count = 1;
        for (Internship internship : filtered) {
            System.out.println(count + ". " + internship.getTitle());
            System.out.println("   Company: " + internship.getCompanyName());
            System.out.println("   Level: " + internship.getLevel());
            System.out.println("   Major: " + internship.getPreferredMajor());
            System.out.println("   Status: " + internship.getStatus());
            System.out.println("   Slots: " + internship.getSlots());
            System.out.println();
            count++;
        }
    }


    public void viewPendingCompanyReps(CareerCenter careerCenter) {
        System.out.println("\n=== Pending Company Representative Requests ===\n");
        
        List<CompanyRepresentative> pendingReps = careerCenter.getPendingCompanies();
        
        if (pendingReps.isEmpty()) {
            System.out.println("No pending requests.");
            return;
        }

        for (int i = 0; i < pendingReps.size(); i++) {
            CompanyRepresentative rep = pendingReps.get(i);
            System.out.println((i + 1) + ". " + rep.getName());
            System.out.println("   Email: " + rep.getUserId());
            System.out.println("   Company: " + rep.getCompanyName());
            System.out.println("   Department: " + rep.getDepartment());
            System.out.println("   Position: " + rep.getPosition());
            System.out.println();
        }
    }
}