package IPMS.SystemPages.StudentPages;

import IPMS.ObjectClasses.Student;
import IPMS.ObjectClasses.User;
import IPMS.SystemPages.Page;
import IPMS.SystemPages.PageAction;

public class ChangePasswordPage implements Page {

    private final Student student;

    public ChangePasswordPage(Student student) {
        this.student = student;
    }

    @Override
    public void showMenu() {
        System.out.println("\n===== CHANGE PASSWORD =====");
        System.out.println("You will now be asked to enter your current password");
        System.out.println("and then set a new password according to system requirements.");
        System.out.println("Press Enter to continue...");
        try { System.in.read(); } catch (Exception e) {}
    }

    @Override
    public PageAction next() {

        // The username used in LoginMap is the prefix before '@'
        String username = extractUsername(student.getEmail());

        if (username == null) {
            System.out.println("Could not retrieve your username.");
            return PageAction.pop();
        }

        // call the system-wide password change logic
        User.changePassword(username);

        System.out.println("Returning to student menu...");
        return PageAction.pop();
    }

    /* Helper: get the username from the student's email */
    private String extractUsername(String email) {
        if (email == null || !email.contains("@")) return null;
        return email.split("@")[0];
    }
}
