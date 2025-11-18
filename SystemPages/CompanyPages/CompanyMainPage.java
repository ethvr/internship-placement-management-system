<<<<<<< HEAD
package IPMS.SystemPages.CompanyPages;

import IPMS.SystemPages.Page;
import IPMS.SystemPages.PageAction;
import IPMS.System.SystemDataEntities.*;
import IPMS.System.SystemData;

import java.util.Map;

import IPMS.ObjectClasses.*;
import IPMS.System.SystemConverter;
import IPMS.SystemPages.UniversalFunctions;

public class CompanyMainPage implements Page{

    private final String username;


    public CompanyMainPage(String username) {
        this.username = username;
    }

    @Override
    public void showMenu() {
        System.out.println("======= COMPANY REP =======");
        System.out.println("[1] View all listed Internships opportunities");
        System.out.println("[2] View pending Internships");
        System.out.println("[3] View all Internship Applications");
        System.out.println("[4] Logout\n");

        System.out.print("Enter an option (1-4): ");
    }

    @Override
    public PageAction next() {

        Map<String, CompanyCSVData> map = SystemData.getCompanyMap();
        CompanyCSVData data = map.get(username);
        CompanyRepresentative obj = SystemConverter.toCompanyRep(data);

        int opt = UniversalFunctions.readIntInRange(1, 4);

        return switch (opt) {
            case 1 -> PageAction.push(new ViewListedInternsipsPage(obj));
            case 2 -> PageAction.push(new ViewPendingInternshipsPage(obj));
            case 3 -> PageAction.push(new ViewApplicationsPage(obj));
            case 4 -> {
                User.logout();
                yield PageAction.pop();
            }
            default -> PageAction.pop();
        };
    }
}
=======
package IPMS.SystemPages.CompanyPages;

import IPMS.Companypackage.CompanyRepresentative;
import IPMS.SystemPages.Page;
import IPMS.SystemPages.PageAction;
import IPMS.System.SystemDataEntities.*;
import IPMS.System.SystemData;

import java.util.Map;

import IPMS.ObjectClasses.*;
import IPMS.System.SystemConverter;
import IPMS.SystemPages.UniversalFunctions;

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

        // code to instantiate object ;
        CompanyRepresentative obj = SystemData.getCompanyREpObj(username);

        int opt = UniversalFunctions.readIntInRange(1, 5);

        return switch (opt) {
            case 1 -> PageAction.push(new ManageInternshipsPage(obj));
            case 2 -> PageAction.push(new ViewCreatedInternshipsPage(obj));
            case 3 -> PageAction.push(new ManageApplicationsPage(obj));
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
>>>>>>> 071a7f7e66cc371b2eb40ec6247ad244aad11744
