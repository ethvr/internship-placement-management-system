package IPMS.SystemPages.StaffPages;


import IPMS.System.SystemData;
import IPMS.ObjectClasses.*;
import IPMS.SystemPages.CommonPages.*;
import IPMS.SystemPages.PageUtilities.Page;
import IPMS.SystemPages.PageUtilities.PageAction;
import IPMS.SystemPages.PageUtilities.UniversalFunctions;



public class StaffMainPage implements Page{

    private final String username;

    public StaffMainPage(String username) {
        this.username = username;
    }

    @Override
    public void showMenu() {
        System.out.println("\n======= CAREER STAFF =======");
        // request froms company representative to create acount
        System.out.println("[1] Manage all Company Representative Account Requests");
        // submission of internship from company representative
        System.out.println("[2] Manage all Internship Opportunity Submissions");
        // requests from students to wiithdraw
        System.out.println("[3] Manage all Withdrawal Requests");
        // to generate reports
        System.out.println("[4] Generate Reports");
        System.out.println("[5] Change password");
        System.out.println("[6] Logout\n");

        System.out.print("Enter an option (1-6): ");
        //System.out.println(SystemData.LoginMap.get("sng001").Password);
    }

    /** 
     * @return PageAction
     */
    @Override
    public PageAction next() {

        CareerCenter staffObj = SystemData.getStaffValue(username);

        int opt = UniversalFunctions.readIntInRange(1, 8);

        return switch (opt) {
            case 1 -> PageAction.push(new AccountApprovalRejectPage(staffObj));
            case 2 -> PageAction.push(new SharedInternshipPage(staffObj));
            case 3 -> PageAction.push(new ViewWithdrawalRequestsPage(staffObj));
            case 4 -> PageAction.push(new ViewGenerateReportsPage(staffObj));
            case 5 -> PageAction.push(new PasswordChangePage(staffObj));
            case 6 -> {
                staffObj.logout();
                yield PageAction.exit();
            }
            default -> PageAction.exit();
        };
    }
    
}
