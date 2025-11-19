package SystemPages.StaffPages;

import SystemPages.Page;
import SystemPages.PageAction;

public class ViewInternshipsPage implements Page {
    private final String username;

    public ViewInternshipsPage(String username) {
        this.username = username;
    }

    @Override
    public void showMenu() {
        System.out.println("ViewInternshipsPage (placeholder)");
    }

    @Override
    public PageAction next() {
        return PageAction.pop();
    }
}
