package SystemPages.CompanyPages;

import SystemPages.Page;
import SystemPages.PageAction;
import SystemDataEntities.*;
import SystemData;
import ObjectClasses.*;
import SystemPages.UniversalFunctions;
import ObjectClasses.*;

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
        System.out.println("[1] Create Internship Opportunity");
        System.out.println("[2] View My Internship Opportunities");
        System.out.println("[3] Edit Internship Opportunity");
        System.out.println("[4] Delete Internship Opportunity");
        System.out.println("[5] Toggle Internship Visibility");
        System.out.println("[6] View Pending Internships (Awaiting Staff Approval)");
        System.out.println("[7] View Applications for My Internships");
        System.out.println("[8] Approve/Reject Student Application");
        System.out.println("[9] Change Password");
        System.out.println("[10] Logout\n");

        System.out.print("Enter an option (1-10): ");
    }

    @Override
    public PageAction next() {

        int opt = UniversalFunctions.readIntInRange(1, 10);

        return switch (opt) {
            case 1 -> PageAction.push(new CreateInternshipPage(username));
            case 2 -> PageAction.push(new ViewMyInternshipsPage(username));
            case 3 -> PageAction.push(new EditInternshipPage(username));
            case 4 -> PageAction.push(new DeleteInternshipPage(username));
            case 5 -> PageAction.push(new ToggleVisibilityPage(username));
            case 6 -> PageAction.push(new ViewPendingInternshipsPage(username));
            case 7 -> PageAction.push(new ViewApplicationsPage(username));
            case 8 -> PageAction.push(new ReviewApplicationsPage(username));
            case 9 -> {
                CompanyRepresentative compRep = SystemData.representatives.get(username);
                if (compRep != null) {
                    compRep.changePassword();
                }
                yield PageAction.push(this); // Stay on same page after password change
            }
            case 10 -> {
                User.logout();
                yield PageAction.pop();
            }
            default -> PageAction.pop();
        };
    }
}