package IPMS.incoming;

import IPMS.ObjectClasses.Student;
import IPMS.ObjectClasses.Internship;
import IPMS.System.SystemData;
import IPMS.SystemPages.PageUtilities.Page;
import IPMS.SystemPages.PageUtilities.PageAction;
import IPMS.SystemPages.PageUtilities.UniversalFunctions;

public class InternshipDetailsPage implements Page {

    private final Student student;
    private final Internship internship;

    public InternshipDetailsPage(Student student, Internship internship) {
        this.student = student;
        this.internship = internship;
    }

    @Override
    public void showMenu() {
        System.out.println("\n===== INTERNSHIP DETAILS =====");

        System.out.println("Internship ID : " + internship.getId());
        System.out.println("Title         : " + internship.getInternshipTitle());
        System.out.println("Company       : " + internship.getCompanyName());
        System.out.println("Level         : " + internship.getLevel());
        System.out.println("Major Needed  : " + internship.getPreferredMajor());
        System.out.println("Slots Left    : " + internship.getSlots());
        System.out.println("Open Date     : " + internship.getOpenDate());
        System.out.println("Close Date    : " + internship.getCloseDate());
        System.out.println("\nDescription:");
        System.out.println(internship.getDescription());

        System.out.println("\n[1] Apply for Internship");
        System.out.println("[2] Back");
        System.out.print("Enter an option: ");
    }

    @Override
    public PageAction next() {

        int choice = UniversalFunctions.readIntInRange(1, 2);

        switch (choice) {
            case 1 -> {
                try {
                    student.applyTo(internship.getId());
                    SystemData.saveAll();
                    System.out.println("Your application has been submitted.");
                } catch (Exception e) {
                    System.out.println("Could not apply: " + e.getMessage());
                }
                return PageAction.stay();
            }

            case 2 -> {
                return PageAction.pop();
            }

            default -> {
                return PageAction.stay();
            }
        }
    }
}
