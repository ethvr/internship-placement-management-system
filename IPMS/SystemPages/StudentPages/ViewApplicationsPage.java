package IPMS.SystemPages.StudentPages;

import IPMS.SystemPages.PageUtilities.Page;
import IPMS.SystemPages.PageUtilities.PageAction;
import IPMS.SystemPages.PageUtilities.UniversalFunctions;
import IPMS.ObjectClasses.*;
import java.util.List;
import IPMS.Enums.*;
import IPMS.System.SystemData;
import java.util.ArrayList;

public class ViewApplicationsPage implements Page{

    private final Student obj;
    private List<Application> appList = new ArrayList<>();

    public ViewApplicationsPage(Student obj) {
        this.obj = obj;
    }

    @Override
    public void showMenu() {
        System.out.println("====== APPLICATIONS ======");

        String key = obj.getUserId();
        List<Application> list = SystemData.getALMstudent(key);

        if (list.isEmpty()) {
            System.out.println("You have not applied to any internships.");
        }

        int index = 1;
        for (Application a : list) {
            System.out.printf("[%d] Application ID: %s\n", index++, a.getApplicationID());
            System.out.println("    Internship ID : " + a.getInternshipId());
            System.out.println("    Status        : " + a.getStatus());
            System.out.println("    Accepted?     : " + a.getAcceptedByStudent());
            System.out.println();
            appList.add(a);
        }

        System.out.println("[1] Accept Offer");
        System.out.println("[2] Request Withdrawal");
        System.out.println("[3] Back");
        System.out.println("[4] Logout");
        System.out.print("Enter an option: ");
    }

    /** 
     * @return PageAction
     */
    @Override
    public PageAction next() {

        int opt = UniversalFunctions.readIntInRange(1, 4);

        return switch (opt) {
            case 1 -> PageAction.push(new OfferAcceptancePage(obj));
            case 2 -> PageAction.push(new WithdrawalRequestPage(obj));
            case 3 -> PageAction.pop();
            case 4 -> {
                obj.logout();
                yield PageAction.exit();
            }
            default -> PageAction.pop();
        };

    }
}
