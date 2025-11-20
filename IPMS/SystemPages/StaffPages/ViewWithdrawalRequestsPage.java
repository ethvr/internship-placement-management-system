package IPMS.SystemPages.StaffPages;

import IPMS.SystemPages.StudentPages.*;
import IPMS.SystemPages.StudentPages.*;
import IPMS.ObjectClasses.*;
import IPMS.System.SystemDataEntities.*;
import IPMS.System.SystemData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import IPMS.ObjectClasses.*;
import IPMS.SystemPages.CompanyPages.CompanyMainPage;
import IPMS.SystemPages.PageUtilities.Page;
import IPMS.SystemPages.PageUtilities.PageAction;
import IPMS.SystemPages.PageUtilities.UniversalFunctions;

public class ViewWithdrawalRequestsPage implements Page{

    private CareerCenter staffObj ;
    private int opt;
    final Map<String, WithdrawalRequest> withdrawalmap = SystemData.getWithdrawalMap();

    public ViewWithdrawalRequestsPage( CareerCenter obj){
        this.staffObj = obj;
    }


    @Override
    public void showMenu() {
        System.out.println("===== VIEW WITHDRAWAL REQUESTS =====");
        System.out.println("[1] View all pending withdrawal requests");
        System.out.println("[2] Approve withdrawal");
        System.out.println("[3] Reject withdrawal");
        System.out.println("[4] Back");
        System.out.println("[5] Logout\n");

        System.out.print("Enter an option (1-5): ");
    }

    /** 
     * @return PageAction
     */
    @SuppressWarnings("unchecked")
    @Override
    public PageAction next() {
          int opt = UniversalFunctions.readIntInRange(1, 5);
            
          return switch (opt) {
                    case 1 -> {
                        //print list of pending withdrawal requests 
                        if (withdrawalmap.isEmpty()){
                            System.out.println("\n No pending withdrawal requests.");
                        } else{
                            System.out.println("\n Pending withdrawal requests: ");
                            int x =1; 
                            // for every pending request, [print wtihdrawal id]
                            for (Map.Entry<String, WithdrawalRequest> entry : withdrawalmap.entrySet()){
                                WithdrawalRequest w = entry.getValue();
                                System.out.printf("[%d] %s \n", x++, w.getId());
                            }
                        }
                        //exit printing
                        yield PageAction.pop();
                    }
                     
                    case 2 -> {
                        //approve withdrawal
                        WithdrawalRequest withdrawal = null;

                        while (true) {
                            System.out.print("Enter withdrawal ID to approve (leave blank to cancel): ");
                            String withdrawalString = UniversalFunctions.readStringAllowEmpty();

                            // Cancel operation
                            if (withdrawalString == null || withdrawalString.trim().isEmpty()) {
                                System.out.println("Operation cancelled.");
                                yield PageAction.pop();
                            }

                            withdrawal = withdrawalmap.get(withdrawalString.trim());
                            if (withdrawal == null) {
                                System.out.println("No withdrawal found for that ID. Please try again.");
                                continue;
                            }

                            break;
                        }

                        staffObj.approveWithdrawal(withdrawal);
                        System.out.printf("%s withdrawal for student %s has been approved\n",
                                withdrawal.getId(), withdrawal.getStudentId());

                        yield PageAction.pop();
                                        }

                    case 3 -> {
                        //reject withdrawal
                       WithdrawalRequest withdrawal = null;

                        while (true) {
                            System.out.print("Enter withdrawal ID to reject (leave blank to cancel): ");
                            String withdrawalString = UniversalFunctions.readStringAllowEmpty();

                            // Cancel operation
                            if (withdrawalString == null || withdrawalString.trim().isEmpty()) {
                                System.out.println("Operation cancelled.");
                                yield PageAction.pop();
                            }

                            withdrawal = withdrawalmap.get(withdrawalString.trim());
                            if (withdrawal == null) {
                                System.out.println("No withdrawal found for that ID. Please try again.");
                                continue;
                            }

                            break;
                        }

                        staffObj.rejectWithdrawal(withdrawal);
                        System.out.printf("%s withdrawal for student %s has been rejected\n",
                                withdrawal.getId(), withdrawal.getStudentId());

                        yield PageAction.pop();
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
