package IPMS.SystemPages.CompanyPages;
import java.util.Scanner;

import IPMS.UserManagement.*;
import IPMS.ObjectClasses.CompanyRepresentative;
import IPMS.System.SystemData;
import IPMS.SystemPages.PageUtilities.Page;
import IPMS.SystemPages.PageUtilities.PageAction;
import IPMS.SystemPages.PageUtilities.UniversalFunctions;


public class ManageInternshipsPage implements Page {
    private final CompanyRepresentative obj;
    private CompanyController controller;

    public ManageInternshipsPage(CompanyRepresentative obj) {
        this.obj = obj;
        this.controller = new CompanyController(new Scanner(System.in));
    }

    @Override
    public void showMenu() {
        System.out.println("======= MANAGE INTERNSHIPS =======");
        System.out.println("[1] Create New Internship");
        System.out.println("[2] Edit Internship");
        System.out.println("[3] Delete Internship");
        System.out.println("[4] Toggle Visibility");
        System.out.println("[5] Back\n");
        System.out.print("Enter an option (1-5): ");
    }

    /** 
     * @return PageAction
     */
    @Override
    public PageAction next() {
        int opt = UniversalFunctions.readIntInRange(1, 5);
        
        if (obj == null) {
            System.out.println("Error: Company representative not found.\n");
            return PageAction.pop();
        }

        switch (opt) {
            case 1 -> {
                controller.handleCreateInternship(obj);
                return PageAction.push(this);
            }
            case 2 -> {
                controller.handleEditInternship(obj);
                return PageAction.push(this);
            }
            case 3 -> {
                controller.handleDeleteInternship(obj);
                return PageAction.push(this);
            }
            case 4 -> {
                controller.handleToggleVisibility(obj);
                return PageAction.push(this);
            }
            case 5 -> {
                return PageAction.pop();
            }
            default -> {
                return PageAction.pop();
            }
        }
    }
}