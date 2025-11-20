package zzzzzzz;

import IPMS.SystemPages.StudentPages.*;
import IPMS.SystemPages.StudentPages.*;
import IPMS.ObjectClasses.*;
import IPMS.System.SystemDataEntities.*;
import IPMS.System.SystemData;
import java.util.List;
import java.util.Map;

import IPMS.SystemPages.CompanyPages.CompanyMainPage;
import IPMS.SystemPages.PageUtilities.Page;
import IPMS.SystemPages.PageUtilities.PageAction;
import IPMS.SystemPages.PageUtilities.UniversalFunctions;
import IPMS.SystemPages.StaffPages.AccountApprovalRejectPage;

public class ViewAccountRequestPage implements Page{

    private final CareerCenter staffObj;
    //final Map<String, CompanyRepresentative> companymap = SystemData.getCompanyMap();

    public ViewAccountRequestPage(CareerCenter obj){
        this.staffObj = obj;
    }

    @Override
    public void showMenu() {
        System.out.println("===== VIEW ACCOUNT CREATION REQUESTS =====");
        System.out.println("[1] View all pending account creation requests");
        /*(System.out.println("[2] Approve account creation request");
        System.out.println("[3] Reject account creation request");*/
        System.out.println("[2] Back");
        System.out.println("[3] Logout\n");

        System.out.print("Enter an option (1-3): ");
    }

    /** 
     * @return PageAction
     */
    @SuppressWarnings("unchecked")
    @Override
    public PageAction next() {
          int opt = UniversalFunctions.readIntInRange(1, 3);
            
          return switch (opt) {
                    case 1 -> {
                        /*//pending = CareerCenter.viewPendingCompanyReps();
                        //print list of pending companies
                        List<CompanyRepresentative> pending = CareerCenter.getPendingCompanies();
                        if (pending.isEmpty()){
                            System.out.println("\n No pending account creation requests.");
                        } else{
                            System.out.println("\n Pending Account creation requests: ");
                            int i =1;
                            // for every pending request, [print company name, name, email of company rep]
                            for (CompanyRepresentative c : pending){
                            System.out.printf("[%d] %s - %s - %s \n", i++,c.getCompanyName(), c.getName(), c.getEmail());
                            }
                        }
                        //exit printing
                        yield PageAction.pop();*/
                        yield PageAction.push(new AccountApprovalRejectPage(staffObj));
                    }
                     
                    case 2 -> {
                        //approve account
                        CompanyRepresentative compRep = null;
                        while (true) {
                            System.out.print("Enter email to approve account creation (type EXIT to cancel): ");
                            String emailString = UniversalFunctions.readStringEmail();
                            if (emailString.equalsIgnoreCase("exit")) {
                                System.out.println("Operation cancelled.");
                                yield PageAction.pop();
                            }
                            String username = emailString.substring(0, emailString.indexOf("@"));
                            compRep = SystemData.getCompanyValue(username);
                            if (compRep == null) {
                                System.out.println("No pending account found for that email. Please try again.");
                                continue;
                            }
                            staffObj.approveCompanyRep(compRep);
                            System.out.printf("%s account has been approved%n", compRep.getEmail());
                            yield PageAction.pop();
                        }
                    }

                    case 3 -> {
                        //reject account
                        CompanyRepresentative compRep = null;
                         while (true) {
                            System.out.print("Enter email to reject account creation (type EXIT to cancel): ");
                            String emailString = UniversalFunctions.readStringEmail();
                            if (emailString.equalsIgnoreCase(emailString)) {
                                System.out.println("Operation cancelled.");
                                yield PageAction.pop();
                            }
                            String username = emailString.substring(0, emailString.indexOf("@"));
                            compRep = SystemData.getCompanyValue(username);
                            if (compRep == null) {
                                System.out.println("No pending account found for that email. Please try again.");
                                continue;
                            }
                            staffObj.rejectCompanyRep(compRep);
                            System.out.printf("%s account has been rejected%n", compRep.getEmail());
                            yield PageAction.pop();
                        }

                    }
                    case 4 -> PageAction.pop();
                    case 5 -> {
                        staffObj.logout();
                        yield PageAction.pop();
                    }
                    default -> PageAction.pop();
        };


    }


}