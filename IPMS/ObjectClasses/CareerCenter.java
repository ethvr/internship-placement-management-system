package IPMS.ObjectClasses;

import java.util.*;
import java.util.stream.Collectors;

import IPMS.Companypackage.*;
import IPMS.Companypackage.CompanyApprovalStatus;
import IPMS.Enums.*;
import IPMS.ObjectClasses.Internship;
import IPMS.System.SystemData;
import IPMS.System.SystemDataEntities.WithdrawalData;

import javax.management.relation.Role;

import Enums.ApplicationStatus;
import Enums.InternshipStatus;
import Enums.WithdrawalStatus;

public class CareerCenter extends User {

    private String role;
    private String staffDepartment;
    private List<CompanyRepresentative> pendingCompanies = new ArrayList<>(); 
    static Map<String, WithdrawalRequest> withdrawalmap = SystemData.getWithdrawalMap();
    static Map<String, Application> applicationmap = SystemData.getApplicationMap();
    static Map<String, Internship> internshipmap = SystemData.getInternshipMap();


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
            System.out.println(companyRep.getCompanyName() + " has been added into pending list");
        }
    }

    //remove pending company
    public void removePendingCompany(CompanyRepresentative companyRep){
        if (pendingCompanies.contains(companyRep)){
            pendingCompanies.remove(companyRep);
        }
        else{
            System.out.println(companyRep.getCompanyName() + " is not in pending list");
        }
    }

    // approve account creation of company representativs
    public void approveCompanyRep (CompanyRepresentative companyRep, CareerCenter careerCenter){
        companyRep.setStatus(CompanyApprovalStatus.APPROVED);
        careerCenter.removePendingCompany(companyRep);
        System.out.println("Company Representative " + companyRep.getName() + " from " 
        + companyRep.getCompanyName() + " has been approved" );
    }

    // reject account creation of company represenattives
    public void rejectCompanyRep (CompanyRepresentative companyRep, CareerCenter careerCenter){
        companyRep.setStatus(CompanyApprovalStatus.REJECTED);
        careerCenter.removePendingCompany(companyRep);
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
    public void approveWithdrawal(WithdrawalRequest withdrawalRequest, SystemData data){
        if (withdrawalRequest.getStatus()== WithdrawalStatus.PENDING){
            withdrawalRequest.setStatus(WithdrawalStatus.APPROVED);
            String appplicationID = withdrawalRequest.getApplicationId();
            Application application = data.getApplicationValue(appplicationID); 
            String internshipID = application.getInternshipId();
            Internship internship = data.getInternshipValue(internshipID);

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
                    System.out.println("Withdrawal request approved.");
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

    public List getPendingCompanies(){
        return pendingCompanies;
    }

}
