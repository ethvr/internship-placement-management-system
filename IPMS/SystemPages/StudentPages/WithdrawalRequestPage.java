package IPMS.SystemPages.StudentPages;

import IPMS.Enums.*;
import IPMS.ObjectClasses.*;
import IPMS.System.SystemData;
import IPMS.SystemPages.PageUtilities.Page;
import IPMS.SystemPages.PageUtilities.PageAction;
import IPMS.SystemPages.PageUtilities.UniversalFunctions;
import java.util.HashMap;
import java.util.List;

public class WithdrawalRequestPage implements Page{

    private final Student obj;
    private final HashMap<Integer, String> indexMap;
    private int index;

    public WithdrawalRequestPage(Student obj) {
        this.obj = obj;
        this.indexMap = new HashMap<>();
    }

    @Override
    public void showMenu() {

        indexMap.clear();

        index = 1;

        System.out.println("====== WITHDRAWAL REQUEST ======\n");

        List<Application> list = obj.getAllMyApplications();
        for (Application a : list) {

            String internshipId = a.getInternshipId();
            Internship internship = SystemData.getInternshipValue(internshipId);

            indexMap.put(index, a.getApplicationID());  // store index for selection

            System.out.printf("[%d] %s - %s\n", 
                index, 
                internship.getCompanyName(), 
                internship.getInternshipTitle()
            );

            index++;
        }

        System.out.printf("[%d] Back\n", index);
        System.out.printf("[%d] Logout\n", index + 1);

        System.out.printf("\n Choose an option (1-%d): ", index + 1);



    }

    /** 
     * @return PageAction
     */
    @Override
    public PageAction next() {

        int opt = UniversalFunctions.readIntInRange(1, index + 1);

        if (opt == index) {
            return PageAction.pop();   // back
        } 
            
        if (opt == index + 1) {        // logout
            obj.logout();
            return PageAction.exit();
        }

        String appID = indexMap.get(opt);

        List<WithdrawalRequest> existingRequests = SystemData.getWLMstudent(obj.getUserId());

        boolean alreadyExists = existingRequests.stream()
            .anyMatch(w -> w.getApplicationId().equals(appID));

        if (alreadyExists) {
            System.out.println("\n⚠ You have already submitted a withdrawal request for this application.");
            System.out.println("   You cannot submit another request.\n");
            return PageAction.pop();
        }

        Application app = SystemData.getApplicationValue(appID);
        if (app.getStatus() == ApplicationStatus.WITHDRAWN 
            || app.getStatus() == ApplicationStatus.PENDING) {

            System.out.println("\n This application is already in withdrawal status.");
            System.out.println("   You cannot withdraw again.\n");
            return PageAction.pop();
        }

        System.out.print("Enter your reason for withdrawal: ");
        String remarks = UniversalFunctions.readString();

        obj.requestWithdrawal(appID, remarks);

        System.out.println("\nYour withdrawal request has been submitted successfully!\n");
        return PageAction.pop();
    }
}
