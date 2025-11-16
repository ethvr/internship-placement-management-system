package sc2002project.SystemPages.MainSubPages;

import sc2002project.SystemPages.Page;
import sc2002project.SystemPages.PageAction;
import sc2002project.System.SystemDataEntities.*;
import sc2002project.System.SystemData;
import sc2002project.ObjectClasses.*;
import sc2002project.SystemPages.StudentPages.*;
import sc2002project.SystemPages.StaffPages.*;
import sc2002project.SystemPages.CompanyPages.*;

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
