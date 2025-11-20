package IPMS.SystemPages.MainSubPages;

import IPMS.System.SystemDataEntities.*;
import IPMS.System.SystemData;
import IPMS.ObjectClasses.*;
import IPMS.SystemPages.StudentPages.*;
import IPMS.SystemPages.StaffPages.*;
import IPMS.SystemPages.CompanyPages.*;
import IPMS.SystemPages.PageUtilities.Page;
import IPMS.SystemPages.PageUtilities.PageAction;

public class AccountLoginPage implements Page{

    @Override
    public void showMenu() {
        System.out.println("===== LOGIN =====");
        
    }

    /** 
     * @return PageAction
     */
    @Override
    public PageAction next() {
        String username = User.login(); // you already have this

        System.out.println(username);

        if (username.equals("NIL"))
            return PageAction.pop(); // go back to main menu

        String type = SystemData.getCredentialsType(username).toLowerCase();

        return switch (type) {
            case "student" -> PageAction.push(new StudentMainPage(username));
            case "staff"   -> PageAction.push(new StaffMainPage(username));
            case "company" -> PageAction.push(new CompanyMainPage(username));
            default        -> PageAction.pop();
        };
    }
}
