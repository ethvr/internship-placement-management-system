package IPMS.ObjectClasses;

import java.util.*;
import java.util.stream.Collectors;

import IPMS.Companypackage.CompanyApprovalStatus;
import IPMS.Enums.*;

public class CareerCenter extends User {

    private String role;
    private String staffDepartment;
    private List<CompanyRepresentative> pendingCompanies; 

    public CareerCenter(String staffId, String name, String email, String staffDepartment, String role) {
        super(staffId, name, email);
        this.role = role;
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
        companyRep.setStatus(CompanyApprovalStatus.APPROVED);
        careerCenter.removePendingCompany(companyRep);
        System.out.println("Company Representative from " + companyRep.getName() + "from" 
        + companyRep.getCompanyName() + "has been approved" );
    }

    // reject account creation of company represenattives
    public void rejectCompanyRep (CompanyRepresentative companyRep, CareerCenter careerCenter){
        companyRep.setStatus(CompanyApprovalStatus.REJECTED);
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

    public List getPendingCompanies(){
        return pendingCompanies;
    }

}
