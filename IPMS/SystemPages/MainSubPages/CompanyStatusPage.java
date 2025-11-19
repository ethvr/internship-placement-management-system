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

    }

    @Override
    public PageAction next() {
        UserManager.CompanyStatusCheck();
        System.out.println("[1] Back");
        System.out.println("[2] Exit app");
        System.out.print("Enter an option: ");
        int opt = UniversalFunctions.readIntInRange(1, 2);

        return switch (opt) {
            case 1 -> PageAction.pop();
            case 2 -> {
                SystemData.saveAll("company");
                yield PageAction.exit();
            }
            default -> PageAction.pop();
        };
    }

}

