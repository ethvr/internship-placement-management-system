package IPMS.incoming;

import IPMS.ObjectClasses.Student;
import IPMS.ObjectClasses.Internship;
import IPMS.System.SystemData;
import IPMS.SystemPages.PageUtilities.Page;
import IPMS.SystemPages.PageUtilities.PageAction;
import IPMS.SystemPages.PageUtilities.UniversalFunctions;

public class ApplyByIdPage implements Page {

    private final Student student;

    public ApplyByIdPage(Student student) {
        this.student = student;
    }

    @Override
    public void showMenu() {
        System.out.println("\n===== APPLY BY INTERNSHIP ID =====");
        System.out.print("Enter Internship ID (or type 0 to cancel): ");
    }

    @Override
    public PageAction next() {

        String input = UniversalFunctions.readString();

        if (input.equals("0")) {
            return PageAction.pop();
        }

        Internship internship = SystemData.getInternshipValue(input);

        if (internship == null) {
            System.out.println("Invalid Internship ID.");
            return PageAction.stay();
        }

        // Show brief info
        System.out.println("\nFound Internship:");
        System.out.println("Title       : " + internship.getInternshipTitle());
        System.out.println("Company     : " + internship.getCompanyName());
        System.out.println("Level       : " + internship.getLevel());
        System.out.println("Slots       : " + internship.getSlots());
        System.out.println("Closing Date: " + internship.getCloseDate());
        System.out.println();

        System.out.println("Apply for this internship?");
        System.out.println("[1] Yes");
        System.out.println("[2] No");
        int confirm = UniversalFunctions.readIntInRange(1, 2);

        if (confirm == 2) {
            return PageAction.pop();
        }

        /* Student.applyTo() already has all core checks:
            - eligibility by major
            - eligibility by year
            - visibility
            - active application limit
            - slot availability
        */
        try {
            student.applyTo(input);
            SystemData.saveAll();
            System.out.println("Application submitted successfully!");
        }
        catch (Exception e) {
            System.out.println("Could not apply: " + e.getMessage());
        }

        return PageAction.stay();
    }
}
