package IPMS.SystemPages.StudentPages;

import IPMS.ObjectClasses.Student;
import IPMS.ObjectClasses.Internship;
import IPMS.System.SystemData;
import IPMS.SystemPages.Page;
import IPMS.SystemPages.PageAction;
import IPMS.SystemPages.UniversalFunctions;

/*
   A dedicated UI page to confirm + apply.
   All actual checking is still done in Student.applyTo().
*/
public class ApplyToInternshipPage implements Page {

    private final Student student;
    private final Internship internship;

    public ApplyToInternshipPage(Student student, Internship internship) {
        this.student = student;
        this.internship = internship;
    }

    @Override
    public void showMenu() {
        System.out.println("\n========== APPLY TO INTERNSHIP ==========");
        System.out.println("Internship ID : " + internship.getId());
        System.out.println("Title         : " + internship.getInternshipTitle());
        System.out.println("Company       : " + internship.getCompanyName());
        System.out.println("Slots Left    : " + internship.getSlots());
        System.out.println("Level         : " + internship.getLevel());
        System.out.println("Preferred Maj : " + internship.getPreferredMajor());
        System.out.println("Closing Date  : " + internship.getCloseDate());

        System.out.println("\nAre you sure you want to apply?");
        System.out.println("[1] Yes, Apply");
        System.out.println("[2] Cancel");
        System.out.print("Enter option: ");
    }

    @Override
    public PageAction next() {

        int option = UniversalFunctions.readIntInRange(1, 2);

        if (option == 2) {
            return PageAction.pop();
        }

        try {
            student.applyTo(internship.getId());
            SystemData.saveAll();
            System.out.println("Your application has been successfully submitted!");
        } catch (Exception e) {
            System.out.println("Could not apply: " + e.getMessage());
        }

        return PageAction.pop();
    }
}
