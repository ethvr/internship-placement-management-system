package IPMS.SystemPages.StudentPages;

import IPMS.ObjectClasses.Student;
import java.util.HashMap;
import java.util.List;
import IPMS.ObjectClasses.Internship;
import IPMS.SystemPages.PageUtilities.Page;
import IPMS.SystemPages.PageUtilities.PageAction;
import IPMS.SystemPages.PageUtilities.UniversalFunctions;

/*
   A dedicated UI page to confirm + apply.
   All actual checking is still done in Student.applyTo().
*/
public class ApplyToInternshipPage implements Page {

    private final Student obj;
    private final List<Internship> internshipList;
    private int index;
    private final HashMap<Integer, Internship> indexMap;

    public ApplyToInternshipPage(Student obj, List<Internship> internshipList) {
        this.obj = obj;
        this.internshipList = internshipList;
        indexMap = new HashMap<>();
    }

    @Override
    public void showMenu() {

        index = 1;

        for (Internship i : internshipList) {

            System.out.printf("[%d] %s - %s\n", 
                    index, 
                    i.getCompanyName(), 
                    i.getInternshipTitle()
                );
            indexMap.put(index, i);
            index++;
        }

        System.out.printf("[%d] Cancel", index);

    }

    /** 
     * @return PageAction
     */
    @Override
    public PageAction next() {


        System.out.print("\nSelect one Internship to apply to: ");
        int opt = UniversalFunctions.readIntInRange(1, index);

        if (opt == index) {
            return PageAction.pop();   // cancel
        } else {

            Internship internship = indexMap.get(opt);
            String internshipid = internship.getInternshipId();

            if (obj.CheckifApplicationExists(internshipid)) {
                System.out.println("You have already applied to this internship.");
                return PageAction.pop();
            }

            obj.applyTo(internshipid);
            return PageAction.pop();
        }
    }
}
