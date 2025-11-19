package IPMS.ObjectClasses;

import java.util.*;
import java.util.stream.Collectors;

//import Companypackage.*;
//import Companypackage.CompanyApprovalStatus;
import IPMS.Enums.*;
import IPMS.ObjectClasses.Internship;
import IPMS.System.SystemData;
import IPMS.System.SystemDataEntities.WithdrawalData;

import javax.management.relation.Role;

public class CareerCenter extends User {

    private String role;
    private String staffDepartment;
    public static List<CompanyRepresentative> pendingCompanies; 
    static Map<String, WithdrawalRequest> withdrawalmap = SystemData.getWithdrawalMap();
    static Map<String, Application> applicationmap = SystemData.getApplicationMap();
    static Map<String, Internship> internshipmap = SystemData.getInternshipMap();


    public CareerCenter(String staffId, String name, String email, String staffDepartment, String role) {
        super(staffId, name, email);
        this.role = role;
        this.staffDepartment = staffDepartment;
        pendingCompanies = new ArrayList<>();
        pendingCompanies
    }

    
    //view pending company
    // public void viewPendingCompanyReps(CareerCenter careerCenter) {
    //     System.out.println("\n=== Pending Company Representative Requests ===\n");
        
    //     List<CompanyRepresentative> pendingReps = careerCenter.getPendingCompanies();
        
    //     if (pendingReps.isEmpty()) {
    //         System.out.println("No pending requests.");
    //         return;
    //     }

    //     for (int i = 0; i < pendingReps.size(); i++) {
    //         CompanyRepresentative rep = pendingReps.get(i);
    //         System.out.println((i + 1) + ". " + rep.getName());
    //         System.out.println("   Email: " + rep.getUserId());
    //         System.out.println("   Company: " + rep.getCompanyName());
    //         System.out.println("   Department: " + rep.getDepartment());
    //         System.out.println("   Position: " + rep.getPosition());
    //         System.out.println();
    //     }
    // }

    //add pending company
    public void addPendingCompany(CompanyRepresentative companyRep){
        if (!pendingCompanies.contains(companyRep)){
            pendingCompanies.add(companyRep);
        }
        else{
            System.out.println(companyRep.getCompanyName() + " has been added into pending list");
        }
    }

    //remove pending company
    public void removePendingCompany(CompanyRepresentative companyRep){
        boolean s = SystemData.removeUnapprovedRep(companyRep);
        if (!s) {
            System.out.println("Company Representative not found");
        }
    }

    // approve account creation of company representativs
    public void approveCompanyRep (CompanyRepresentative companyRep) {
        companyRep.setStatus(CompanyApprovalStatus.APPROVED);
        removePendingCompany(companyRep);
        System.out.println("Company Representative " + companyRep.getName() + " from " 
        + companyRep.getCompanyName() + " has been approved" );
    }

    // reject account creation of company represenattives
    public void rejectCompanyRep (CompanyRepresentative companyRep){
        companyRep.setStatus(CompanyApprovalStatus.REJECTED);
        removePendingCompany(companyRep);
        System.out.println("Company Representative " + companyRep.getName() + " from " 
        + companyRep.getCompanyName() + " has been rejected " );
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
    public void approveWithdrawal(WithdrawalRequest withdrawalRequest){
        if (withdrawalRequest.getStatus()== WithdrawalStatus.PENDING){
            withdrawalRequest.setStatus(WithdrawalStatus.APPROVED);

            // pull application obj from map
            String appplicationID = withdrawalRequest.getApplicationId();
            Application application = SystemData.getApplicationValue(appplicationID); 
            // pull internship obj from map
            String internshipID = application.getInternshipId();
            Internship internship = SystemData.getInternshipValue(internshipID);


            // for (Application withdrawer : applicationmap.values()){
            //     if (applicationmap.equals(appplicationID)){
            //         withdrawer = 
            //     }
            // }
            
            //check if application is valid
            if (application != null){       
                //if intern slot is confirmed, remove student
                if (application.getStatus() == ApplicationStatus.SUCCESSFUL){
                    application.setStatus(ApplicationStatus.WITHDRAWN);
                    System.out.println("Withdrawal request is approved.");
                    // add slot to internship
                    internship.addSlots();

            }
            else{
                System.out.println("Withdrawal request is not pending.");
            }

            }
        }
    }

    //reject student withdrawal before or after confirmation
    public void rejectWithdrawal(WithdrawalRequest withdrawalRequest){
        if (withdrawalRequest.getStatus()== WithdrawalStatus.PENDING){
            withdrawalRequest.setStatus(WithdrawalStatus.REJECTED);
            System.out.println("Withdrawal request rejected.");
            }
        else{
            System.out.println("Withdrawal request is not pending.");
        }
    }

    //getters and setters
    public String getRole() {
        return role;
    }



    public String getStaffDepartment() {
        return staffDepartment;
    }

    public void setStaffDepartment (String staffDepartment){
        this.staffDepartment = staffDepartment;
    }

    public static List getPendingCompanies(){
        return pendingCompanies;
    }

}
