package SystemPages.CompanyPages;
import java.util.Scanner;
import java.util.List;
import IPMS.Companypackage.CompanyRepresentative;
import IPMS.SystemPages.*;
import IPMS.Internship;
import IPMS.Enums.*;
import IPMS.System.SystemData;  
import IPMS.Companypackage.CompanyController;

public class ManageInternshipsPage implements Page {
    private final String username;
    private CompanyController controller;

    public ManageInternshipsPage(String username) {
        this.username = username;
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

    @Override
    public PageAction next() {
        int opt = UniversalFunctions.readIntInRange(1, 5);

        CompanyRepresentative compRep = SystemData.representatives.get(username);
        
        if (compRep == null) {
            System.out.println("Error: Company representative not found.\n");
            return PageAction.pop();
        }

        switch (opt) {
            case 1 -> {
                controller.handleCreateInternship(compRep);
                return PageAction.push(this);
            }
            case 2 -> {
                controller.handleEditInternship(compRep);
                return PageAction.push(this);
            }
            case 3 -> {
                controller.handleDeleteInternship(compRep);
                return PageAction.push(this);
            }
            case 4 -> {
                controller.handleToggleVisibility(compRep);
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