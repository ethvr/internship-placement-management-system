package IPMS.SystemPages.CompanyPages;
import java.util.List;
import java.util.Scanner;

import IPMS.Enums.ApplicationStatus;
import IPMS.ObjectClasses.CompanyRepresentative;
import IPMS.System.SystemData;
import IPMS.SystemPages.PageUtilities.Page;
import IPMS.SystemPages.PageUtilities.PageAction;
import IPMS.SystemPages.PageUtilities.UniversalFunctions;
import IPMS.ObjectClasses.*;
import IPMS.UserManagement.*;

public class ManageApplicationsPage implements Page {
    private final CompanyRepresentative obj;
    private CompanyController controller;

    public ManageApplicationsPage(CompanyRepresentative obj) {
        this.obj = obj;
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

    /** 
     * @return PageAction
     */
    @Override
    public PageAction next() {
        int opt = UniversalFunctions.readIntInRange(1, 4);
        
        if (obj == null) {
            System.out.println("Error: Company representative not found.\n");
            return PageAction.pop();
        }

        switch (opt) {
            case 1 -> {
                System.out.println(SystemData.ALMinternship);
                controller.handleViewApplications(obj);
                return PageAction.push(this);
            }
            case 2 -> {
                controller.handleApproveApplication(obj);
                return PageAction.push(this);
            }
            case 3 -> {
                controller.handleRejectApplication(obj);
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