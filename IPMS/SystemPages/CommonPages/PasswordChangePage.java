package IPMS.SystemPages.CommonPages;

import IPMS.ObjectClasses.*;
import IPMS.SystemPages.PageUtilities.Page;
import IPMS.SystemPages.PageUtilities.PageAction;

public class PasswordChangePage implements Page {

    private final User obj;

    public PasswordChangePage(User obj) {
        this.obj = obj;
    }

    
    @Override
    public void showMenu() {
        System.out.println("======= PASSWORD CHANGE ======");

    }

    /** 
     * @return PageAction
     */
    @Override
    public PageAction next() {

        String email = obj.getEmail();
        String username;
        if (email == null || !email.contains("@")) {
            username = null;
            return PageAction.pop();
        }
        else username = email.split("@")[0];

        User.changePassword(username);

        //System.out.println(SystemData.LoginMap.get("sng001").Password + "in page");
        return PageAction.pop();
    }
}
