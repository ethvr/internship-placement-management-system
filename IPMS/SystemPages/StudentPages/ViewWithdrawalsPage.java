package IPMS.SystemPages.StudentPages;

import IPMS.SystemPages.PageUtilities.Page;
import IPMS.SystemPages.PageUtilities.PageAction;
import IPMS.SystemPages.PageUtilities.UniversalFunctions;
import IPMS.ObjectClasses.*;
import java.util.List;
import IPMS.Enums.*;
import java.util.ArrayList;

public class ViewWithdrawalsPage implements Page{

    private final Student obj;
    private List<WithdrawalRequest> list;

    public ViewWithdrawalsPage(Student obj) {
        this.obj = obj;
        list = new ArrayList<>();
    }

    @Override
    public void showMenu() {
        System.out.println("====== YOUR WITHDRAWAL REQUESTS ======");

        list = obj.getAllMyWithdrawalRequests();

        if (list.isEmpty()) {
            System.out.println("You have not submitted any withdrawal requests.");
        }

        else {
            int index = 1;
            for (WithdrawalRequest wr : list) {
                System.out.printf("[%d] Request ID      : %s\n", index++, wr.getId());
                System.out.println("    Application ID : " + wr.getApplicationId());
                System.out.println("    Status         : " + wr.getStatus());
                System.out.println("    Date Requested : " + wr.getRequestDate());
                System.out.println("    Remarks        : " + wr.getRemarks());
                System.out.println();
            }

        }

    }
    
    /** 
     * @return PageAction
     */
    @Override
    public PageAction next() {
        
        System.out.println("[1] Back");
        System.out.println("[2] Logout");
        System.out.print("Enter an option: ");

        int opt = UniversalFunctions.readIntInRange(1, 2);

        return switch (opt) {
            case 1 -> PageAction.pop();
            case 2 -> {
                obj.logout();
                yield PageAction.exit();
            }
            
            default -> PageAction.pop();
        };
    }
}
