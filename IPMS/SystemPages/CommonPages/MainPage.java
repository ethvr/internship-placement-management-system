package IPMS.SystemPages.CommonPages;

import IPMS.SystemPages.MainSubPages.*;
import IPMS.SystemPages.PageUtilities.Page;
import IPMS.SystemPages.PageUtilities.PageAction;
import IPMS.SystemPages.PageUtilities.UniversalFunctions;
import IPMS.System.*;

public class MainPage implements Page {

    @Override
    public void showMenu() {
        System.out.println("======= WELCOME =======");
        System.out.println("[1] Login");
        System.out.println("[2] Create Account (Company Rep)");
        System.out.println("[3] Check Company Rep Status");
        System.out.println("[4] Exit\n");
        System.out.print("Enter an option (1-4): ");
    }

    /** 
     * @return PageAction
     */
    @Override
    public PageAction next() {
        int opt = UniversalFunctions.readIntInRange(1, 4);

        return switch (opt) {
            case 1 -> PageAction.push(new AccountLoginPage());
            case 2 -> PageAction.push(new CompanyRegisterPage());
            case 3 -> PageAction.push(new CompanyStatusPage());
            case 4 -> {
                yield PageAction.exit();
            }
            default -> PageAction.push(this);
        };
    }
}

