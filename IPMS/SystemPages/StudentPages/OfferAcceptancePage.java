package IPMS.SystemPages.StudentPages;

import IPMS.SystemPages.PageUtilities.Page;
import IPMS.SystemPages.PageUtilities.PageAction;
import IPMS.SystemPages.PageUtilities.UniversalFunctions;
import IPMS.Enums.ApplicationStatus;
import IPMS.ObjectClasses.*;
import IPMS.System.SystemData;
import java.util.HashMap;
import java.util.List;

public class OfferAcceptancePage implements Page{

    private final Student obj;
    private final HashMap<Integer, String> indexMap;
    private int index;

    public OfferAcceptancePage(Student obj) {
        this.obj = obj;
        this.indexMap = new HashMap<>();
    }

    @Override
    public void showMenu() {

        indexMap.clear();

        index = 1;
        System.out.println("====== OFFER ACCEPTANCE ======");
        List<Application> list = obj.getAllMyApplications();

        for (Application a : list) {

            String internshipId = a.getInternshipId();
            Internship internship = SystemData.getInternshipValue(internshipId);

            if (a.getStatus() == ApplicationStatus.SUCCESSFUL) {

                indexMap.put(index, a.getApplicationID());

                System.out.printf("[%d] %s - %s\n", 
                    index, 
                    internship.getCompanyName(), 
                    internship.getInternshipTitle()
                );
            }          
            index++;
        }
         System.out.printf("[%d] %s\n", 
                index, 
                "Cancel" 
            );


    }

    /** 
     * @return PageAction
     */
    @Override
    public PageAction next() {
        System.out.printf("\nChoose a Application to Accept (1-%d): ", index);
        int opt = UniversalFunctions.readIntInRange(1, index);

        if (opt == index) {
            return PageAction.pop();   // cancel
        } else {

            String key = indexMap.get(opt);
            
            obj.acceptPlacement(key);

            return PageAction.pop();
        }
    }
}
