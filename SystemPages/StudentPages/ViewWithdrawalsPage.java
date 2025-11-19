package SystemPages.StudentPages;

import SystemPages.Page;
import SystemPages.PageAction;
import ObjectClasses.Student;

public class ViewWithdrawalsPage implements Page {
    private final Student student;

    public ViewWithdrawalsPage(Student student) {
        this.student = student;
    }

    @Override
    public void showMenu() {
        System.out.println("ViewWithdrawalsPage (placeholder)");
    }

    @Override
    public PageAction next() {
        return PageAction.pop();
    }
}
