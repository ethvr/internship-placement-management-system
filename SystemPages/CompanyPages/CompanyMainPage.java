package SystemPages.CompanyPages;

import SystemPages.Page;
import SystemPages.PageAction;
import System.SystemDataEntities.*;
import System.SystemData;
import ObjectClasses.*;
import SystemPages.UniversalFunctions;

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

        int opt = UniversalFunctions.readIntInRange(1, 4);

        return switch (opt) {
            case 1 -> PageAction.push(new ViewListedInternsipsPage(username));
            case 2 -> PageAction.push(new ViewPendingInternshipsPage(username));
            case 3 -> PageAction.push(new ViewApplicationsPage(username));
            case 4 -> {
                User.logout();
                yield PageAction.pop();
            }
            default -> PageAction.pop();
        };
    }
}
