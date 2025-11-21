package IPMS.ObjectClasses;

import java.util.*;
import java.util.stream.Collectors;
import IPMS.Enums.InternshipLevel;
import IPMS.Enums.InternshipStatus;
import IPMS.System.SystemData;

public class GenerateReports{

    static Map<String, Internship> internshipmap = SystemData.getInternshipMap();
    static Map<String, Application> applicationmap = SystemData.getApplicationMap();

    /** 
     * @param status
     * @param major
     * @param level
     * @param companyName
     * @return List<Internship>
     */
    public List<Internship> loadInternships(
        InternshipStatus status,
        String major,
        InternshipLevel level,
        String companyName
        ) {
        List<Internship> list = new ArrayList<>();

        // Load all internships from the CSV data map
        for (Internship data : internshipmap.values()) {
            Internship internship = new Internship(
                data.getInternshipTitle(),
                data.getDescription(),
                data.getLevel(),
                data.getPreferredMajor(),
                data.getOpenDate(),
                data.getCloseDate(),
                data.getCompanyName(),
                data.getCompRepID(),
                data.getSlots()
            );
            list.add(internship);
        }

        // Apply filters (only if filters are not null)
        return list.stream()
            .filter(i -> status == null || i.getStatus() == status)
            .filter(i -> major == null || 
                        i.getPreferredMajor().equalsIgnoreCase(major) ||
                        i.getPreferredMajor().equalsIgnoreCase("Any"))
            .filter(i -> level == null || i.getLevel() == level)
            .filter(i -> companyName == null || 
                        i.getCompanyName().equalsIgnoreCase(companyName))
            .collect(Collectors.toList());
    }

    /** 
     * @param internship
     */
    // generate reports with applicants?
    public void printApplicants(Internship internship) {

        List<Application> ApplicationList = new ArrayList<>();

        String internshipID = internship.getInternshipId();

        for (Application data : applicationmap.values()) {
            if (data.getInternshipId().equalsIgnoreCase(internshipID)) {
            Application application = new Application (
                data.getApplicationID(),
                data.getStudentId(),
                data.getInternshipId(), 
                data.getStatus(), 
                data.getAcceptedByStudent() 
                );

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
                    app.getApplicationID(),
                    app.getStatus()   
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

        List<Internship> InternshipList = new ArrayList<>();

        for (Internship data : internshipmap.values()) {

            Internship internship = new Internship (
                data.getInternshipTitle(),
                data.getDescription(),
                data.getLevel(),
                data.getPreferredMajor(),
                data.getOpenDate(),
                data.getCloseDate(),
                data.getCompanyName(),
                data.getCompRepID(),
                data.getSlots());

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

    /** 
     * @param status
     */
    public void generateReportByStatus(InternshipStatus status) {
        List<Internship> filtered = loadInternships(status, null, null, null);

        System.out.println("\n=== Internships with Status: " + status + " ===\n");

        if (filtered.isEmpty()) {
            System.out.println("No internships found with status: " + status);
            return;
        }

        for (Internship internship : filtered) {
            System.out.println("- " + internship.getTitle() + " (" + internship.getCompanyName() + ")");
        }
    }

    /** 
     * @param major
     */
    public void generateReportByMajor(String major) {
        List<Internship> filtered = loadInternships(null, major, null, null);

        System.out.println("\n=== Internships for Major: " + major + " ===\n");

        if (filtered.isEmpty()) {
            System.out.println("No internships found for major: " + major);
            return;
        }

        for (Internship internship : filtered) {
            System.out.println("- " + internship.getTitle() + " (" + internship.getCompanyName() + ")");
        }
    }

    /** 
     * @param level
     */
    public void generateReportByLevel(InternshipLevel level) {
        List<Internship> filtered = loadInternships(null, null, level, null);

        System.out.println("\n=== Internships at Level: " + level + " ===\n");

        if (filtered.isEmpty()) {
            System.out.println("No internships found at this level: " + level);
            return;
        }

        for (Internship internship : filtered) {
            System.out.println("- " + internship.getTitle() + " (" + internship.getCompanyName() + ")");
        }
    }

    /** 
     * @param company
     */
    public void generateReportByCompany(String company) {
        List<Internship> filtered = loadInternships(null, null, null, company);

        System.out.println("\n=== Internships from Company: " + company + " ===\n");

        if (filtered.isEmpty()) {
            System.out.println("No internships found for company: " + company);
            return;
        }

        for (Internship internship : filtered) {
            System.out.println("- " + internship.getTitle() + " (Status: " + internship.getStatus() + ")");
        }
    }

    /** 
     * @param status
     * @param major
     * @param level
     * @param company
     */
    public void generateCustomReport(InternshipStatus status, String major,
                                 InternshipLevel level, String company) {

        List<Internship> filtered = loadInternships(status, major, level, company);

        System.out.println("\n=== Custom Filtered Report ===\n");

        if (filtered.isEmpty()) {
            System.out.println("No internships match these filters.");
            return;
        }

        int count = 1;
        for (Internship internship : filtered) {
            System.out.println(count++ + ". " + internship.getTitle());
        }
    }


}