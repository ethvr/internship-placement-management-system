
package IPMS.SystemPages.CompanyPages;

import IPMS.ObjectClasses.*;
import IPMS.System.SystemData;
import IPMS.System.SystemDataEntities.*;
import IPMS.SystemPages.CommonPages.PasswordChangePage;
import IPMS.SystemPages.CommonPages.SharedInternshipPage;
import IPMS.SystemPages.PageUtilities.Page;
import IPMS.SystemPages.PageUtilities.PageAction;
import IPMS.SystemPages.PageUtilities.UniversalFunctions;
import IPMS.ObjectClasses.*;

public class CompanyMainPage implements Page{

    private final String username;

    public CompanyMainPage(String username) {
        this.username = username;
    }

    @Override
    public void showMenu() {
        // Get the company rep to display personalized greeting
        CompanyRepresentative obj = SystemData.getCompanyValue(username);
        
        System.out.println("\n======= COMPANY REPRESENTATIVE MENU =======");
        if (obj != null) {
            System.out.println("Welcome, " + obj.getName() + " from " + obj.getCompanyName());
        }
        System.out.println();
        System.out.println("[1] Manage Internships (View/Create/Edit/Delete/Toggle Visibility)");
        System.out.println("[2] Manage Applications (View/Approve/Reject)");
        System.out.println("[3] Change Password");
        System.out.println("[4] Logout\n");

        System.out.print("Enter an option (1-4): ");
    }

    /** 
     * @return PageAction
     */
    @Override
    public PageAction next() {

        CompanyRepresentative obj = SystemData.getCompanyValue(username);
        int opt = UniversalFunctions.readIntInRange(1, 5);

        return switch (opt) {
            case 1 -> PageAction.push(new SharedInternshipPage(obj));
            case 2 -> PageAction.push(new ManageApplicationsPage(obj));
            case 3 -> PageAction.push(new PasswordChangePage(obj));
            case 4 -> {
                obj.logout();
                yield PageAction.exit();
            }
            default -> PageAction.pop();
        };
    }
}