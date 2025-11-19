package SystemPages.StaffPages;

import SystemPages.Page;
import SystemPages.PageAction;

public class ViewAccountRequestPage implements Page {
    private final String username;

    public ViewAccountRequestPage(String username) {
        this.username = username;
    }

    @Override
    public void showMenu() {
        System.out.println("ViewAccountRequestPage (placeholder)");
    }

    @Override
    public PageAction next() {
        return PageAction.pop();
    }
}
