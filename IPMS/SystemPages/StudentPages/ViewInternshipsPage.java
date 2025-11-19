package IPMS.SystemPages.StudentPages;

import java.util.List;
import IPMS.ObjectClasses.Student;
import IPMS.System.SystemData;
import IPMS.SystemPages.Page;
import IPMS.SystemPages.PageAction;
import IPMS.SystemPages.UniversalFunctions;
import IPMS.System.SystemDataEntities.InternshipData;
import IPMS.ObjectClasses.Internship;

public class ViewInternshipsPage implements Page {

    private final Student student;

    public ViewInternshipsPage(Student student) {
        this.student = student;
    }

    @Override
    public void showMenu() {
        System.out.println("\n===== INTERNSHIPS AVAILABLE =====");

        List<InternshipData> list = SystemData.filterByYearOfStudy(student.getYearOfStudy());

        // print list
        UniversalFunctions.printInternshipList(list);

        System.out.println("[1] Filter by Internship Level");
        System.out.println("[2] Filter by Closing Date");
        System.out.println("[3] Filter by Company Name");
        System.out.println("[4] Filter by Slots Left");
        System.out.println("[5] Filter by Keyword");
        System.out.println("[6] Select Internship");
        System.out.println("[7] Back");

        System.out.print("Enter an option: ");
    }

    @Override
    public PageAction next() {

        List<InternshipData> list = SystemData.filterByYearOfStudy(student.getYearOfStudy());
        int option = UniversalFunctions.readIntInRange(1, 7);

        switch (option) {

            // open filtered list pages
            case 1:
                return PageAction.push(new FilteredInternshipsPage(student, list, "level"));

            case 2:
                return PageAction.push(new FilteredInternshipsPage(student, list, "date"));

            case 3:
                return PageAction.push(new FilteredInternshipsPage(student, list, "name"));

            case 4:
                return PageAction.push(new FilteredInternshipsPage(student, list, "slots"));

            case 5:
                return PageAction.push(new FilteredInternshipsPage(student, list, "words"));

            /* 
                NEW OPTION:
                Select an internship from the printed list.
                Opens InternshipDetailsPage.
            */
            case 6: {
                if (list.isEmpty()) {
                    System.out.println("No available internships.");
                    return PageAction.stay();
                }

                System.out.print("Enter internship number: ");
                int idx = UniversalFunctions.readIntInRange(1, list.size());

                InternshipData chosen = list.get(idx - 1);
                Internship real = SystemData.getInternshipValue(chosen.getUniqueID());

                if (real == null) {
                    System.out.println("Error loading internship.");
                    return PageAction.stay();
                }

                return PageAction.push(new InternshipDetailsPage(student, real));
            }

            case 7:
                return PageAction.pop();

            default:
                return PageAction.stay();
        }
    }
}
