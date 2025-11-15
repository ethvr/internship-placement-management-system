import java.util.*;

public class CareerCenter extends User {

    private String staffDepartment;
    private List<CompanyRepresentative> pendingCompanies; 

    public CareerCenter(String staffId, String name, String staffDepartment) {
        super(staffId, name);
        this.staffDepartment = staffDepartment;
        this.pendingCompanies = new ArrayList<>();
    }

    public void addPendingCompany(CompanyRepresentative companyRep){
        if (!pendingCompanies.contains(companyRep)){
            pendingCompanies.add(companyRep);
        }
        else{
            System.out.println(companyRep.getCompanyName() + " has been added into pending list");
        }
    }

    public void removePendingCompany(CompanyRepresentative companyRep){
        if (pendingCompanies.contains(companyRep)){
            pendingCompanies.remove(companyRep);
        }
        else{
            System.out.println(companyRep.getCompanyName() + " is not in pending list");
        }
    }

    public void approveCompanyRep(CompanyRepresentative companyRep){
        companyRep.setApproved(true);
        this.removePendingCompany(companyRep);
        System.out.println("Company Representative " + companyRep.getName() + " from " 
        + companyRep.getCompanyName() + " has been approved");
    }

    public void rejectCompanyRep(CompanyRepresentative companyRep){
        companyRep.setApproved(false);
        this.removePendingCompany(companyRep);
        System.out.println("Company Representative " + companyRep.getName() + " from " 
        + companyRep.getCompanyName() + " has been rejected");
    }

    public void approveInternship(Internship internship){
        if (internship.getStatus() == InternshipStatus.PENDING){
            internship.setStatus(InternshipStatus.APPROVED);
            internship.setVisible(true);
        }
        else{
            System.out.println("Internship is not pending.");
        }
    }

    public void rejectInternship(Internship internship){
        if (internship.getStatus() == InternshipStatus.PENDING){
            internship.setStatus(InternshipStatus.REJECTED);
        }
        else{
            System.out.println("Internship is not pending");
        }
    }

    public void approveWithdrawal(WithdrawalRequest withdrawalRequest){
        if (withdrawalRequest.getStatus() == WithdrawalStatus.PENDING){
            
            withdrawalRequest.setStatus(WithdrawalStatus.APPROVED);
            String applicationID = withdrawalRequest.getApplicationId();
            Application application = SystemData.getApplication(applicationID);
            
            if (application != null){       
                if (application.getStatus() == ApplicationStatus.SUCCESSFUL){
                    // Clear slot by updating internship
                    Internship internship = SystemData.getInternship(application.getInternshipId());
                    if (internship != null) {
                        // Increment slots back since student is withdrawing
                        // Note: You might want to add a method in Internship class for this
                    }
                }
                application.setStatus(ApplicationStatus.WITHDRAWN);
                System.out.println("Withdrawal request approved.");
            }
        }
        else{
            System.out.println("Withdrawal request is not pending.");
        }
    }

    public void rejectWithdrawal(WithdrawalRequest withdrawalRequest){
        if (withdrawalRequest.getStatus() == WithdrawalStatus.PENDING){
            withdrawalRequest.setStatus(WithdrawalStatus.REJECTED);
            System.out.println("Withdrawal request rejected.");
        }
        else{
            System.out.println("Withdrawal request is not pending.");
        }
    }

    public String getStaffDepartment() {
        return staffDepartment;
    }

    public void setStaffDepartment(String staffDepartment){
        this.staffDepartment = staffDepartment;
    }

    public List<CompanyRepresentative> getPendingCompanies(){
        return pendingCompanies;
    }
}