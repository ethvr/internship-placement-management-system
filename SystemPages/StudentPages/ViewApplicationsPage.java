package SystemPages.StudentPages;

import SystemPages.Page;
import SystemPages.PageAction;
import ObjectClasses.Student;

public class ViewApplicationsPage implements Page {
    private final Student student;

    public ViewApplicationsPage(Student student) {
        this.student = student;
    }

    @Override
    public void showMenu() {
        System.out.println("ViewApplicationsPage (placeholder)");
    }

    @Override
    public PageAction next() {
        return PageAction.pop();
    }
}
