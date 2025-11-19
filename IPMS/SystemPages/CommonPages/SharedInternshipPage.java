package IPMS.SystemPages.CommonPages;

import java.util.ArrayList;
import java.util.List;

import IPMS.ObjectClasses.*;
import IPMS.System.Filters;
import IPMS.System.SystemData;
import IPMS.SystemPages.PageUtilities.Page;
import IPMS.SystemPages.PageUtilities.PageAction;
import IPMS.SystemPages.PageUtilities.UniversalFunctions;

public class SharedInternshipPage implements Page {

    private final User Obj;

    public SharedInternshipPage (User Obj) {
        this.Obj = Obj;
    }

    @Override
    public void showMenu() {
        System.out.println("======= INTERNSHIPS VIEWER =======");

        if (InternshipExists(Obj)){
            System.out.println("Filter internships by: ");
            System.out.println("[1] No Filters");
            System.out.println("[2] Internship Level");
            System.out.println("[3] Closing Date");
            System.out.println("[4] Number of Slots");
            System.out.println("[5] Keywords");

            if (!(Obj instanceof CompanyRepresentative)) {
                System.out.println("[6] Company Name");
                System.out.println("[7] Back");
                System.out.println("[8] Logout");
            } else {
                System.out.println("[6] Back");
                System.out.println("[7] Logout");
            }

            System.out.print("Enter an option: ");
        }
        else {
            System.out.println("There are currently no Internships applicable to you");
        }
        
    }

    /** 
     * @return PageAction
     */
    @Override
    public PageAction next() {

        List<Internship> InternshipList;
        boolean isRep = Obj instanceof CompanyRepresentative;

        if (Obj instanceof Student s) {
            InternshipList = Filters.filterByYearOfStudy(s.getYearOfStudy());
        }
        else if (Obj instanceof CompanyRepresentative cr) {
            InternshipList = Filters.filterByCompanyName(cr.getCompanyName());
        }
        else InternshipList = Filters.getAllInternships();

        

        int opt = isRep
                ? UniversalFunctions.readIntInRange(1, 6)     // CompanyRep menu
                : UniversalFunctions.readIntInRange(1, 7);    // Student/Staff menu

        return switch (opt) {

            case 1 -> PageAction.push(new FilteredInternshipsPage(Obj, InternshipList, "none"));

            case 2 -> PageAction.push(new FilteredInternshipsPage(Obj, InternshipList, "level"));

            case 3 -> PageAction.push(new FilteredInternshipsPage(Obj, InternshipList, "date"));

            case 4 -> PageAction.push(new FilteredInternshipsPage(Obj, InternshipList, "slots"));

            case 5 -> PageAction.push(new FilteredInternshipsPage(Obj, InternshipList, "words"));

            case 6 -> {
                if (!isRep) {
                    // Student / Staff → COMPANY NAME filter
                    yield PageAction.push(new FilteredInternshipsPage(Obj, InternshipList, "company"));
                } else {
                    // CompanyRep → BACK
                    yield PageAction.pop();
                }
            }

            case 7 -> {
                if (!isRep) {
                    // Student / Staff → BACK
                    yield PageAction.pop();
                } else {
                    // CompanyRep → LOGOUT
                    Obj.logout();
                    yield PageAction.exit();
                }
            }

            case 8 -> {
                // Only available for non-reps
                Obj.logout();
                yield PageAction.exit();
            }
            default -> PageAction.pop();
        };
    }
    /** 
     * @param obj
     * @return boolean
     */
    // true --> internships applicable to that user exists 
    private boolean InternshipExists(User obj) {

        if (obj instanceof CompanyRepresentative cr) {
            return !SystemData.getILMcompany(cr.getCompanyRepID()).isEmpty();
        }

        return !SystemData.getInternshipMap().isEmpty();
    }



}
