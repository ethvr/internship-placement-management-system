package SystemPages.CompanyPages;

import SystemPages.Page;
import SystemPages.PageAction;

public class ViewListedInternsipsPage implements Page {
    private final String username;

    public ViewListedInternsipsPage(String username) {
        this.username = username;
    }

    @Override
    public void showMenu() {
        System.out.println("ViewListedInternsipsPage (placeholder)");
    }

    @Override
    public PageAction next() {
        return PageAction.pop();
    }
}
