package SystemPages.MainSubPages;

import IPMS.SystemPages.Page;
import IPMS.SystemPages.PageAction;
import IPMS.UserManagement.UserManager;
import java.util.List;

public class CompanyRegisterPage implements Page {

    @Override
    public void showMenu() {
        System.out.println("======= ACCOUNT REGISTRATION =======");

    }

    @Override
    public PageAction next() {
        int optionBack = 1;

        while (optionBack == 1) {
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