package SystemPages.MainSubPages;

import SystemPages.Page;
import SystemPages.PageAction;
import UserManagement.UserManager;
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