package IPMS.SystemPages.MainSubPages;

import IPMS.SystemPages.Page;
import IPMS.SystemPages.PageAction;
import IPMS.System.SystemDataEntities.*;
import IPMS.System.SystemData;
import IPMS.ObjectClasses.*;
import IPMS.SystemPages.StudentPages.*;
import IPMS.SystemPages.StaffPages.*;
import IPMS.SystemPages.CompanyPages.*;

public class AccountLoginPage implements Page{

    @Override
    public void showMenu() {
        System.out.println("===== LOGIN =====");
        
    }

    @Override
    public PageAction next() {
        String username = User.login(); // you already have this

        if (username.equals("NIL"))
            return PageAction.pop(); // go back to main menu

        // load relevant maps
        SystemData.loadIntoMap("internship", InternshipData.class);
        SystemData.loadIntoMap("application", ApplicationData.class);
        SystemData.loadIntoMap("withdrawal", WithdrawalData.class);

        String type = SystemData.getCredentialsType(username).toLowerCase();

        return switch (type) {
            case "student" -> PageAction.push(new StudentMainPage(username));
            case "staff"   -> PageAction.push(new StaffMainPage(username));
            case "company" -> PageAction.push(new CompanyMainPage(username));
            default        -> PageAction.pop();
        };
    }
}
