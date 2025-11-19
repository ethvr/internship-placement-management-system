package SystemPages.CompanyPages;

import SystemPages.Page;
import SystemPages.PageAction;

public class ViewApplicationsPage implements Page {
    private final String username;

    public ViewApplicationsPage(String username) {
        this.username = username;
    }

    @Override
    public void showMenu() {
        System.out.println("ViewApplicationsPage (company placeholder)");
    }

    @Override
    public PageAction next() {
        return PageAction.pop();
    }
}
