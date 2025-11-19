package IPMS.SystemPages.StudentPages;

import IPMS.ObjectClasses.Student;
import IPMS.SystemPages.CommonPages.PasswordChangePage;
import IPMS.SystemPages.PageUtilities.Page;
import IPMS.SystemPages.PageUtilities.PageAction;
import IPMS.SystemPages.StudentPages.*;
import IPMS.UniversalFunctions;

public class StudentMainPage implements Page {

    private Student student;

    public StudentMainPage(Student student) {
        this.student = student;
    }

    @Override
    public PageAction run() {
        System.out.println("\n===== STUDENT MENU =====");
        System.out.println("[1] View Available Internships");
        System.out.println("[2] View Your Applications");
        System.out.println("[3] View Your Withdrawal Requests");
        System.out.println("[4] Apply for Internship by ID");
        System.out.println("[5] Change Password");
        System.out.println("[6] Logout");

        int choice = UniversalFunctions.getIntInput("Enter choice: ");

        switch (choice) {
            case 1:
                return PageAction.push(new ViewInternshipsPage(student));

            case 2:
                return PageAction.push(new ViewApplicationsPage(student));

            case 3:
                return PageAction.push(new ViewWithdrawalsPage(student));

            case 4:
                return PageAction.push(new ApplyByIdPage(student));

            case 5:
                return PageAction.push(new PasswordChangePage(student));

            case 6:
                System.out.println("Logging out...");
                return PageAction.pop();

            default:
                System.out.println("Invalid choice.");
                return PageAction.stay();
        }
    }
}
