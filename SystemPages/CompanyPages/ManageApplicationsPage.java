package IPMS.SystemPages.CompanyPages;
import java.util.List;
import java.util.Scanner;
import IPMS.Companypackage.CompanyRepresentative;
import IPMS.SystemPages.*;
import IPMS.ObjectClasses.*;
import IPMS.System.SystemData;
import IPMS.Companypackage.CompanyController;

public class ManageApplicationsPage implements Page {
    private final String username;
    private CompanyController controller;

    public ManageApplicationsPage(String username) {
        this.username = username;
        this.controller = new CompanyController(new Scanner(System.in));
    }

    @Override
    public void showMenu() {
        System.out.println("======= MANAGE APPLICATIONS =======");
        System.out.println("[1] View Applications for an Internship");
        System.out.println("[2] Approve Application");
        System.out.println("[3] Reject Application");
        System.out.println("[4] Back\n");
        System.out.print("Enter an option (1-4): ");
    }

    @Override
    public PageAction next() {
        int opt = UniversalFunctions.readIntInRange(1, 4);

        CompanyRepresentative compRep = SystemData.representatives.get(username);
        
        if (compRep == null) {
            System.out.println("Error: Company representative not found.\n");
            return PageAction.pop();
        }

        switch (opt) {
            case 1 -> {
                controller.handleViewApplications(compRep);
                return PageAction.push(this);
            }
            case 2 -> {
                controller.handleApproveApplication(compRep);
                return PageAction.push(this);
            }
            case 3 -> {
                controller.handleRejectApplication(compRep);
                return PageAction.push(this);
            }
            case 4 -> {
                return PageAction.pop();
            }
            default -> {
                return PageAction.pop();
            }
        }
    }
}