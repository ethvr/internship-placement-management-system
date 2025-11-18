package IPMS.SystemPages.StaffPages;

import IPMS.SystemPages.StudentPages.*;
import IPMS.SystemPages.Page;
import IPMS.SystemPages.PageAction;
import IPMS.System.SystemDataEntities.StaffCSVData;
import IPMS.SystemPages.StudentPages.ViewInternshipsPage;
import IPMS.SystemPages.StudentPages.*;
import IPMS.ObjectClasses.Internship;
import IPMS.ObjectClasses.User;
import IPMS.System.SystemDataEntities.*;
import IPMS.System.SystemData;

import java.util.Map;

import IPMS.ObjectClasses.*;
import IPMS.SystemPages.UniversalFunctions;


public class StaffMainPage implements Page{

    private final String username;

    public StaffMainPage(String username) {
        this.username = username;
    }

    @Override
    public void showMenu() {
        System.out.println("======= CAREER STAFF =======");
        // request froms company representative to create acount
        System.out.println("[1] View all Company Representative Account Requests");
        // submission of internship from company representative
        System.out.println("[2] View all Internship Opportunity Submissions");
        // requests from students to wiithdraw
        System.out.println("[3] View all Withdrawal Requests");
        // to generate reports
        System.out.println("[4] Generate Reports");
        // filtered list of submissions
        System.out.println("[5] View all available Internships");
        System.out.println("[6] Back");
        System.out.println("[7] Logout\n");

        System.out.print("Enter an option (1-5): ");
    }

    @Override
    public PageAction next() {

        //load staff map
        //SystemData.loadIntoMap("staff", StaffCSVData.class);

        final Map<String, CareerCenter> staffmap = SystemData.getStaffMap();
        CareerCenter staffObj = staffmap.get(username);

        int opt = UniversalFunctions.readIntInRange(1, 5);

        return switch (opt) {
            case 1 -> PageAction.push(new ViewAccountRequestPage(staffObj));
            case 2 -> PageAction.push(new ViewInternshipRequestPage(staffObj));
            case 3 -> PageAction.push(new ViewWithdrawalsPage(staffObj));
            case 4 -> PageAction.push(new ViewGenerateReportsPage(staffObj));
            case 5 -> PageAction.push(new ViewInternshipsPage(staffObj));
            case 6 -> PageAction.pop();
            case 7 -> {
                User.logout();
                yield PageAction.pop();
            }
            default -> PageAction.pop();
        };
    }
    
}
