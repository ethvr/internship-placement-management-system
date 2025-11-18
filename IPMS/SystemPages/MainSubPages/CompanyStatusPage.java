<<<<<<< HEAD
package IPMS.SystemPages.MainSubPages;

import IPMS.SystemPages.Page;
import IPMS.SystemPages.PageAction;
import IPMS.SystemPages.UniversalFunctions;
import IPMS.UserManagement.UserManager;

public class CompanyStatusPage implements Page{

    @Override
    public void showMenu() {
        System.out.println("======= STATUS CHECKER =======");

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
            case 2 -> PageAction.exit();
            default -> PageAction.pop();
        };
    }

}

=======
package IPMS.SystemPages.MainSubPages;

import IPMS.SystemPages.Page;
import IPMS.SystemPages.PageAction;
import IPMS.SystemPages.UniversalFunctions;
import IPMS.UserManagement.UserManager;

public class CompanyStatusPage implements Page{

    @Override
    public void showMenu() {
        System.out.println("======= STATUS CHECKER =======");

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
            case 2 -> PageAction.exit();
            default -> PageAction.pop();
        };
    }

}

>>>>>>> 071a7f7e66cc371b2eb40ec6247ad244aad11744
