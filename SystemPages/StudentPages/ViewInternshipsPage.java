<<<<<<< HEAD
package IPMS.SystemPages.StudentPages;

import java.util.List;
import java.util.Map;
import IPMS.SystemPages.Page;
import IPMS.SystemPages.PageAction;
import IPMS.System.SystemDataEntities.*;
import IPMS.System.SystemData;
import IPMS.ObjectClasses.*;
import IPMS.SystemPages.UniversalFunctions;


public class ViewInternshipsPage implements Page {
    private Student StudentObj;
    private int opt;

    public ViewInternshipsPage(Student obj) {
        StudentObj = obj;
    }

    @Override
    public void showMenu() {
        System.out.println("======= INTERNSHIPS AVAILABLE =======");
        System.out.println("Filter internships by: ");
        System.out.println("[1] Internship Levels");
        System.out.println("[2] Closing Date");
        System.out.println("[3] Company Name");
        System.out.println("[4] Number of Slots left\n");
        System.out.println("[5] Key words"); //filter by searching for keywords in description
        System.out.println("[6] Back");

        System.out.print("Enter an option (1-6): ");;
    }

    @Override
    public PageAction next() {

        String major = StudentObj.getMajor();
        int YearOfStudy = StudentObj.getYearOfStudy();
        List<InternshipData> InternshipList = SystemData.filterByYearOfStudy(YearOfStudy);
        List<InternshipData> InternshipList2;
        opt = UniversalFunctions.readIntInRange(1, 6);

        switch (opt) {
            case 1 -> {
                PageAction.push(new FilteredInternshipsPage(StudentObj, InternshipList, "level"));
            }
            case 2 -> {
                PageAction.push(new FilteredInternshipsPage(StudentObj, InternshipList, "date"));
            }
            case 3 -> {
                PageAction.push(new FilteredInternshipsPage(StudentObj, InternshipList, "name"));
            }
            case 4 -> {
                PageAction.push(new FilteredInternshipsPage(StudentObj, InternshipList, "slots"));
            }
            case 5 -> {
                PageAction.push(new FilteredInternshipsPage(StudentObj, InternshipList, "words"));
            }
            case 6 -> {
                PageAction.pop();
            }
            default -> {
                PageAction.pop();
            }
        }

}
=======
package IPMS.SystemPages.StudentPages;

import java.util.List;
import java.util.Map;
import IPMS.SystemPages.Page;
import IPMS.SystemPages.PageAction;
import IPMS.System.SystemDataEntities.*;
import IPMS.System.SystemData;
import IPMS.ObjectClasses.*;
import IPMS.SystemPages.UniversalFunctions;


public class ViewInternshipsPage implements Page {
    private Student StudentObj;
    private int opt;

    public ViewInternshipsPage(Student obj) {
        StudentObj = obj;
    }

    @Override
    public void showMenu() {
        System.out.println("======= INTERNSHIPS AVAILABLE =======");
        System.out.println("Filter internships by: ");
        System.out.println("[1] Internship Levels");
        System.out.println("[2] Closing Date");
        System.out.println("[3] Company Name");
        System.out.println("[4] Number of Slots left\n");
        System.out.println("[5] Key words"); //filter by searching for keywords in description
        System.out.println("[6] Back");

        System.out.print("Enter an option (1-6): ");;
    }

    @Override
    public PageAction next() {

        String major = StudentObj.getMajor();
        int YearOfStudy = StudentObj.getYearOfStudy();
        List<InternshipData> InternshipList = SystemData.filterByYearOfStudy(YearOfStudy);
        List<InternshipData> InternshipList2;
        opt = UniversalFunctions.readIntInRange(1, 6);

        switch (opt) {
            case 1 -> {
                PageAction.push(new FilteredInternshipsPage(StudentObj, InternshipList, "level"));
            }
            case 2 -> {
                PageAction.push(new FilteredInternshipsPage(StudentObj, InternshipList, "date"));
            }
            case 3 -> {
                PageAction.push(new FilteredInternshipsPage(StudentObj, InternshipList, "name"));
            }
            case 4 -> {
                PageAction.push(new FilteredInternshipsPage(StudentObj, InternshipList, "slots"));
            }
            case 5 -> {
                PageAction.push(new FilteredInternshipsPage(StudentObj, InternshipList, "words"));
            }
            case 6 -> {
                PageAction.pop();
            }
            default -> {
                PageAction.pop();
            }
        }

}
>>>>>>> 071a7f7e66cc371b2eb40ec6247ad244aad11744
