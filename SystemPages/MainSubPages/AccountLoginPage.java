package SystemPages.MainSubPages;

import SystemPages.Page;
import SystemPages.PageAction;
import System.SystemDataEntities.*;
import System.SystemData;
import ObjectClasses.*;
import SystemPages.StudentPages.*;
import SystemPages.StaffPages.*;
import SystemPages.CompanyPages.*;

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
