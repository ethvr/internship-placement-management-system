package SystemPages.StaffPages;

import SystemPages.Page;
import SystemPages.PageAction;

public class ViewInternsipRequestPage implements Page {
    private final String username;

    public ViewInternsipRequestPage(String username) {
        this.username = username;
    }

    @Override
    public void showMenu() {
        System.out.println("ViewInternsipRequestPage (placeholder)");
    }

    @Override
    public PageAction next() {
        return PageAction.pop();
    }
}
