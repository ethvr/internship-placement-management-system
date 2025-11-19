package SystemPages.CompanyPages;

import SystemPages.Page;
import SystemPages.PageAction;

public class ViewPendingInternshipsPage implements Page {
    private final String username;

    public ViewPendingInternshipsPage(String username) {
        this.username = username;
    }

    @Override
    public void showMenu() {
        System.out.println("ViewPendingInternshipsPage (placeholder)");
    }

    @Override
    public PageAction next() {
        return PageAction.pop();
    }
}
