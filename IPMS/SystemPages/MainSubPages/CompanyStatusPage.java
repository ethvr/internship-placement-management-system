package IPMS.SystemPages.MainSubPages;

import IPMS.System.SystemData;
import IPMS.SystemPages.PageUtilities.Page;
import IPMS.SystemPages.PageUtilities.PageAction;
import IPMS.SystemPages.PageUtilities.UniversalFunctions;
import IPMS.UserManagement.UserManager;

public class CompanyStatusPage implements Page{

    @Override
    public void showMenu() {
        System.out.println("\n======= STATUS CHECKER =======\n");
        System.out.println("[1] Check Status");
        System.out.println("[2] Back");
        System.out.println("[3] Exit app");
        System.out.print("Enter an option: ");
    }

    @Override
    public PageAction next() {
        int opt = UniversalFunctions.readIntInRange(1, 3);

        return switch (opt) {
            case 1 -> {
                // Check status
                UserManager.CompanyStatusCheck();
                yield PageAction.stay();  // Stay on page to allow back/exit after checking
            }
            case 2 -> PageAction.pop();  // Back to main menu
            case 3 -> {
                // Save and exit
                System.out.println("\nSaving data...");
                SystemData.saveAll();
                System.out.println("Data saved. Goodbye!");
                yield PageAction.exit();
            }
            default -> PageAction.pop();
        };
    }
}