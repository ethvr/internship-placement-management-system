import java.util.*;
import java.util.stream.Collectors;

public class CareerCenter extends User {

    private String staffDepartment;
    private List<CompanyRepresentative> pendingCompanies; 

    public CareerCenter(String staffId, String name, String staffDepartment) {
        super(staffId, name);
        this.staffDepartment = staffDepartment;
    }

    //add pending company
    public void addPendingCompany(CompanyRepresentative companyRep){
        if (!pendingCompanies.contains(companyRep)){
            pendingCompanies.add(companyRep);
        }
        else{
            System.out.println(companyRep.getCompanyName() + "has been added into pending list");
        }
    }

    //remove pending company
    public void removePendingCompany(CompanyRepresentative companyRep){
        if (pendingCompanies.contains(companyRep)){
            pendingCompanies.remove(companyRep);
        }
        else{
            System.out.println(companyRep.getCompanyName() + "is not in pending list");
        }
    }

    // approve account creation of company representativs
    public void approveCompanyRep (CompanyRepresentative companyRep, CareerCenter careerCenter){
        companyRep.setApproved(true);
        careerCenter.removePendingCompany(companyRep);
        System.out.println("Company Representative from " + companyRep.getName() + "from" 
        + companyRep.getCompanyName() + "has been approved" );
    }

    // reject account creation of company represenattives
    public void rejectCompanyRep (CompanyRepresentative companyRep, CareerCenter careerCenter){
        companyRep.setApproved(false);
        careerCenter.removePendingCompany(companyRep);
        System.out.println("Company Representative from " + companyRep.getName() + "from" 
        + companyRep.getCompanyName() + "has been rejected" );
    }

    // approve  internships submitted by company
    public void approveInternship(Internship internship){
        if (internship.getStatus() == InternshipStatus.PENDING){
            internship.setStatus(InternshipStatus.APPROVED);
            internship.setVisible(true);
        }
        else{
            System.out.println("Internship is not pending.");
        }
    }

    // reject  internships submitted by company
    public void rejectInternship(Internship internship){
        if (internship.getStatus()==InternshipStatus.PENDING){
            internship.setStatus(InternshipStatus.REJECTED);
        }
        else{
            System.out.println("Internship is not pending");
        }
    }


    // approve student withdrawal before or after confirmation
    public void approveWithdrawal(WithdrawalRequest withdrawalRequest, SystemData data){
        if (withdrawalRequest.getStatus()== WithdrawalStatus.PENDING){
            
            withdrawalRequest.setStatus(WithdrawalStatus.APPROVED);
            String appplicationID = withdrawalRequest.getApplicationId();
            Application application = data.applications.get(appplicationID);
            
            //check if application is valid
            if (application != null){       
                //if intern slot is confirmed, remove student
                if (application.getStatus() == ApplicationStatus.SUCCESSFUL){
                    // clear slot 
                    // update slot -1
                    // clear student

                application.setStatus(ApplicationStatus.WITHDRAWN);
                System.out.println("Withdrawal request approved.");
            }
            else{
                System.out.println("Withdrawal request is not pending.");
            }

            }
        }
    }

    //reject student withdrawal before or after confirmation
    public void rejectWithdrawal(WithdrawalRequest withdrawalRequest, SystemData data){
        if (withdrawalRequest.getStatus()== WithdrawalStatus.PENDING){
            withdrawalRequest.setStatus(WithdrawalStatus.REJECTED);
            System.out.println("Withdrawal request rejected.");
            }
        else{
            System.out.println("Withdrawal request is not pending.");
        }
    }

    // generate comprehensive reports regarding internship opportunities created

    public void generateFullReport(SystemData data) {
        System.out.println("\n========================================");
        System.out.println("    COMPREHENSIVE INTERNSHIP REPORT");
        System.out.println("========================================\n");
        
        if (data.internships.isEmpty()) {
            System.out.println("No internship opportunities found.");
            return;
        }

        int count = 1;
        for (Internship internship : data.internships.values()) {
            System.out.println(count + ". " + internship.getTitle());
            System.out.println("   Company: " + internship.getCompanyName());
            System.out.println("   Level: " + internship.getLevel());
            System.out.println("   Major: " + internship.getPreferredMajor() );
            System.out.println("   Status: " + internship.getStatus());
            System.out.println("   Slots: " + internship.getSlots() + " left ");
            System.out.println("   Applications: " + internship.getApplications(data).size());
            System.out.println("   Visible: " + (internship.isVisibleTo(null) ? "Yes" : "No"));
            System.out.println("   Date: " + internship.getOpenDate() + " to " + 
                             internship.getCloseDate());
            System.out.println();
            count++;
        }
    }

    //generate report by status

    public void generateReportByStatus(SystemData data, InternshipStatus status) {
        System.out.println("\n=== Internships with Status: " + status + " ===\n");
        
        List<Internship> filtered = data.internships.values().stream()
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


    //getters and setters
    public String getStaffDepartment() {
        return staffDepartment;
    }

    public void setStaffDepartment (String staffDepartment){
        this.staffDepartment = staffDepartment;
    }

    public List getPendingCompanies(){
        return pendingCompanies;
    }

}
