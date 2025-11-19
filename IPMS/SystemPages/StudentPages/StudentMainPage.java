package IPMS.SystemPages.StudentPages;

import java.util.Map;

import IPMS.System.SystemData;
import IPMS.ObjectClasses.*;
import IPMS.SystemPages.CommonPages.PasswordChangePage;
import IPMS.SystemPages.CommonPages.SharedInternshipPage;
import IPMS.SystemPages.PageUtilities.Page;
import IPMS.SystemPages.PageUtilities.PageAction;
import IPMS.SystemPages.PageUtilities.UniversalFunctions;

public class StudentMainPage implements Page {

    private String username;

    public StudentMainPage(String username) {
        this.username = username;
    }

    @Override
    public void showMenu() {
        System.out.println("======= STUDENT =======");
        System.out.println("[1] View all available Internships");
        System.out.println("[2] View your Internship Applications");
        System.out.println("[3] View your Withdrawal Applications");
        System.out.println("[4] Change Password");
        System.out.println("[5] Logout\n");

        System.out.print("Enter an option (1-5): ");;
    }

    @Override 
    public PageAction next() {

        int opt = UniversalFunctions.readIntInRange(1, 5);
        Student Obj = SystemData.getStudentObj(username);

        return switch (opt) {
            case 1 -> PageAction.push(new SharedInternshipPage(Obj));
            case 2 -> PageAction.push(new ViewApplicationsPage(Obj));
            case 3 -> PageAction.push(new ViewWithdrawalsPage(Obj));
            case 4 -> PageAction.push(new PasswordChangePage(Obj));
            case 5 -> {
                Obj.logout();
                yield PageAction.exit();
            }
            default -> PageAction.pop();
        };
    }
}
