package IPMS.SystemPages.StaffPages;

import IPMS.SystemPages.StudentPages.*;
import IPMS.SystemPages.StudentPages.*;
import IPMS.Enums.InternshipStatus;
import IPMS.ObjectClasses.*;
import IPMS.System.SystemDataEntities.*;
import IPMS.System.SystemData;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import IPMS.ObjectClasses.*;
import IPMS.SystemPages.CompanyPages.CompanyMainPage;
import IPMS.SystemPages.PageUtilities.Page;
import IPMS.SystemPages.PageUtilities.PageAction;
import IPMS.SystemPages.PageUtilities.UniversalFunctions;
import java.util.HashMap;

public class ViewInternshipRequestPage implements Page{

    private CareerCenter staffObj ;
    private List<Internship> internshipList;
    private int opt;
    private int index;
    private HashMap<Integer, Internship> indexMap;

    public ViewInternshipRequestPage(CareerCenter obj, List<Internship> internshipList){
        this.staffObj = obj;
        this.internshipList = internshipList;
        indexMap = new HashMap<>();
    }

    @Override
    public void showMenu() {
        List<Internship> pendingList = new ArrayList<>();

        for (Internship internship : internshipList) {
            if (internship.getStatus() == InternshipStatus.PENDING) {
                pendingList.add(internship);
            }
        }
        System.out.println("\n====== PENDING INTERNSHIPS ======");


        if (pendingList.isEmpty()) {
            index = 1;
            System.out.println("\nNo pending internship requests at this time.\n");
            System.out.printf("[%d] Cancel\n", index);
            System.out.print("Select an option: ");
        } else {
            index = 1;
            for (Internship i : pendingList) {
                System.out.printf("[%d] ID: %s | Title: %s | Company: %s | Slots: %d\n",
                    index,
                    i.getInternshipId(),
                    i.getInternshipTitle(),
                    i.getCompanyName(),
                    i.getSlots()
                    
                );
                indexMap.put(index, i);
                index++;
            }
            System.out.printf("[%d] Cancel\n", index);
            System.out.print("Select an Internshgip to Approve/Reject: ");
        }
    }

    /** 
     * @return PageAction
     */
    @Override
    public PageAction next() {
        opt = UniversalFunctions.readIntInRange(1, index);

        if (opt == index) {
            return PageAction.pop(); // Cancel option
        }

        Internship selectedInternship = indexMap.get(opt);

        System.out.println("\n====== INTERNSHIP APPROVAL/REJECTION ======");

        long totalDays = ChronoUnit.DAYS.between(
            selectedInternship.getOpenDate(),
            selectedInternship.getCloseDate()
        );

        System.out.printf(
            "  ID:                %s\n" +
            "  Title:             %s\n" +
            "  Company:           %s\n" +
            "  Level:             %s\n" +
            "  Preferred Major:   %s\n" +
            "  Application Window: %d days\n" +
            "  Slots:             %d\n" +
            "  Visible:           %s\n",
            selectedInternship.getInternshipId(),
            selectedInternship.getInternshipTitle(),
            selectedInternship.getCompanyName(),
            selectedInternship.getLevel(),
            selectedInternship.getPreferredMajor(),
            totalDays,
            selectedInternship.getSlots(),
            selectedInternship.getVisibility() ? "Yes" : "No"
        );



        System.out.println("===================================");
        System.out.println("\n[1] Approve Internship");
        System.out.println("[2] Reject Internship");
        System.out.println("[3] Back");

        opt = UniversalFunctions.readIntInRange(1, 3);

        return switch (opt) {
            case 1 -> {
                staffObj.approveInternship(selectedInternship);
                System.out.printf("%s - %s internship has been approved\n",
                selectedInternship.getInternshipId(),
                selectedInternship.getInternshipTitle());
                yield PageAction.pop();
            }
            case 2 -> {
                staffObj.rejectInternship(selectedInternship);
                System.out.printf("%s - %s internship has been rejected\n",
                selectedInternship.getInternshipId(),
                selectedInternship.getInternshipTitle());
                yield PageAction.pop();
            }
            default -> PageAction.pop();
        };
    }

}