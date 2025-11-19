package IPMS.SystemPages.StudentPages;

import IPMS.SystemPages.PageUtilities.Page;
import IPMS.SystemPages.PageUtilities.PageAction;
import IPMS.SystemPages.PageUtilities.UniversalFunctions;
import IPMS.ObjectClasses.*;
import IPMS.System.SystemData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import IPMS.Enums.*;

public class WithdrawalRequestPage implements Page{

    private final Student obj;
    private final HashMap<Integer, String> indexMap;
    private int index;

    public WithdrawalRequestPage(Student obj) {
        this.obj = obj;
        this.indexMap = new HashMap<>();
    }

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
        System.out.printf("[%d] %s\n", 
                index, 
                "Cancel" 
            );


    }

    public PageAction next() {

        System.out.printf("\nChoose a application to withdraw from (1-%d): ", index);
        int opt = UniversalFunctions.readIntInRange(1, index);

        if (opt == index) {
            return PageAction.pop();   // cancel
        } else {
            System.out.print("Enter your reason for withdrawal: ");
            String remarks = UniversalFunctions.readString();

            String appID = indexMap.get(opt);
            obj.requestWithdrawal(appID, remarks);

            return PageAction.pop();
        }
    }
}
