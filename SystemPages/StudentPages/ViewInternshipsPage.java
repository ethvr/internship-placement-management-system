package SystemPages.StudentPages;

import java.util.List;
import java.util.Map;
import SystemPages.Page;
import SystemPages.PageAction;
import System.SystemDataEntities.*;
import System.SystemData;
import ObjectClasses.*;
import SystemPages.UniversalFunctions;


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
                return PageAction.push(new FilteredInternshipsPage(StudentObj, InternshipList, "level"));
            }
            case 2 -> {
                return PageAction.push(new FilteredInternshipsPage(StudentObj, InternshipList, "date"));
            }
            case 3 -> {
                return PageAction.push(new FilteredInternshipsPage(StudentObj, InternshipList, "name"));
            }
            case 4 -> {
                return PageAction.push(new FilteredInternshipsPage(StudentObj, InternshipList, "slots"));
            }
            case 5 -> {
                return PageAction.push(new FilteredInternshipsPage(StudentObj, InternshipList, "words"));
            }
            case 6 -> {
                return PageAction.pop();
            }
            default -> {
                return PageAction.pop();
            }
        }

    }



}


