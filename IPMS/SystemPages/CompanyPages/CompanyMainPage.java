
package IPMS.SystemPages.CompanyPages;

import IPMS.SystemPages.Page;
import IPMS.SystemPages.PageAction;
import IPMS.ObjectClasses.*;
import IPMS.System.SystemData;
import IPMS.System.SystemDataEntities.*;
import IPMS.SystemPages.UniversalFunctions;
import IPMS.ObjectClasses.*;

public class CompanyMainPage implements Page{

    private final String username;

    public CompanyMainPage(String username) {
        this.username = username;
    }

    @Override
    public void showMenu() {
        // Get the company rep to display personalized greeting
        CompanyRepresentative compRep = SystemData.representatives.get(username);
        
        System.out.println("======= COMPANY REPRESENTATIVE MENU =======");
        if (compRep != null) {
            System.out.println("Welcome, " + compRep.getName() + " from " + compRep.getCompanyName());
        }
        System.out.println();
        System.out.println("[1] Manage Internships (Create/Edit/Delete/Toggle Visibility)");
        System.out.println("[2] View Internships Created");
        System.out.println("[3] Manage Applications (View/Approve/Reject)");
        System.out.println("[4] Change Password");
        System.out.println("[5] Logout\n");

        System.out.print("Enter an option (1-5): ");
    }

    @Override
    public PageAction next() {

        int opt = UniversalFunctions.readIntInRange(1, 5);

        return switch (opt) {
            case 1 -> PageAction.push(new ManageInternshipsPage(username));
            case 2 -> PageAction.push(new ViewCreatedInternshipsPage(username));
            case 3 -> PageAction.push(new ManageApplicationsPage(username));
            case 4 -> {
                CompanyRepresentative compRep = SystemData.representatives.get(username);
                if (compRep != null) {
                    compRep.changePassword(username);
                }
                yield PageAction.push(this);
            }
            case 5 -> {
                User.logout();
                yield PageAction.pop();
            }
            default -> PageAction.pop();
        };
    }
}