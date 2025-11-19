package SystemPages.StaffPages;

import SystemPages.Page;
import SystemPages.PageAction;
import System.SystemDataEntities.*;
import System.SystemData;
import ObjectClasses.*;
import SystemPages.UniversalFunctions;


public class StaffMainPage implements Page{

    private final String username;

    public StaffMainPage(String username) {
        this.username = username;
    }

    @Override
    public void showMenu() {
        System.out.println("======= CAREER STAFF =======");
        System.out.println("[1] View all Company Representative Account Request");
        System.out.println("[2] View all Internship Applications Requests");
        System.out.println("[3] View all available Internships");
        System.out.println("[4] Logout\n");

        System.out.print("Enter an option (1-4): ");
    }

    @Override
    public PageAction next() {

        int opt = UniversalFunctions.readIntInRange(1, 4);

        return switch (opt) {
            case 1 -> PageAction.push(new ViewAccountRequestPage(username));
            case 2 -> PageAction.push(new ViewInternsipRequestPage(username));
            case 3 -> PageAction.push(new ViewInternshipsPage(username));
            case 4 -> {
                User.logout();
                yield PageAction.pop();
            }
            default -> PageAction.pop();
        };
    }
    
}
