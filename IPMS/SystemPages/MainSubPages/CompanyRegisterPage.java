package IPMS.SystemPages.MainSubPages;

import IPMS.SystemPages.PageUtilities.Page;
import IPMS.SystemPages.PageUtilities.PageAction;
import IPMS.UserManagement.UserManager;
import java.util.List;

public class CompanyRegisterPage implements Page {

    @Override
    public void showMenu() {
        System.out.println("======= ACCOUNT REGISTRATION =======");

    }

    @Override
    public PageAction next() {

        while (true) {
            List<String> UserInput = UserManager.CompanyRepRegistrationInput();
            if (UserInput.isEmpty()){
                System.out.println("Exiting...");
                break;    
            }
            else {
                UserManager.CompanyRegistrationConfirmation(UserInput);
                break; 
            }

        }
        return PageAction.pop();

    }
}