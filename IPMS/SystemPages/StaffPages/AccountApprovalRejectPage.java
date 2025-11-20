package IPMS.SystemPages.StaffPages;

import IPMS.ObjectClasses.*;
import java.util.HashMap;
import java.util.List;
import IPMS.SystemPages.PageUtilities.Page;
import IPMS.SystemPages.PageUtilities.PageAction;
import IPMS.SystemPages.PageUtilities.UniversalFunctions;

public class AccountApprovalRejectPage implements Page{
    
    private final CareerCenter obj;
    private HashMap<Integer, CompanyRepresentative> indexMap;
    private int size;

    public AccountApprovalRejectPage(CareerCenter obj){
        this.obj = obj;
        indexMap = new HashMap<>();
    }

    @Override
    public void showMenu() {

        System.out.println("====== PENDING ACCOUNT CREATION REQUESTS ======");
        List<CompanyRepresentative> pending = CareerCenter.getPendingCompanies();
        size = pending.size();
        if (pending.isEmpty()){
            System.out.println("\n No pending account creation requests.");
        } else{
            System.out.println("\n Pending Account creation requests: ");
            int i =1;
            // for every pending request, [print company name, name, email of company rep]
            for (CompanyRepresentative c : pending){
                indexMap.put(i, c);
                System.out.printf("[%d] %s - %s - %s \n", i++,c.getCompanyName(), c.getName(), c.getEmail());
            }
        }

        System.out.printf("[%d] Cancel\n", size+1);
        System.out.print("\nSelect a company representative to approve/reject (1-" + size + "): ");
        
    }

    /** 
     * @return PageAction
     */
    @Override
    public PageAction next() {

        int opt = UniversalFunctions.readIntInRange(1, size+1);
        if (size == 0) {
            return PageAction.pop(); // No pending requests, go back
        }
        else if (opt == size + 1) {
            return PageAction.pop(); // Cancel option
        }

        CompanyRepresentative companyRep = indexMap.get(opt);

        printCompanyRep(companyRep);

        System.out.println("[1] Approve Account");
        System.out.println("[2] Reject Account");
        System.out.println("[3] Cancel\n");
        System.out.print("Enter an option (1-3): ");

        opt = UniversalFunctions.readIntInRange(1, 3);

        return switch (opt) {
            case 1 -> {
                obj.approveCompanyRep(companyRep);
                System.out.println("Account approved successfully.");
                yield PageAction.pop();
            }
            case 2 -> {
                obj.rejectCompanyRep(companyRep);
                System.out.println("Account rejected successfully.");
                yield PageAction.pop();
            }
            case 3 -> PageAction.stay();
            default -> PageAction.pop();
        };
    }

    private static void printCompanyRep(CompanyRepresentative rep) {
        if (rep == null) {
            System.out.println("No company representative found.");
            return;
        }

        System.out.printf(
            """
            \n==== COMPANY REP DETAILS ====
            %-20s: %s
            %-20s: %s
            %-20s: %s
            %-20s: %s
            %-20s: %s
            %-20s: %s
            %-20s: %s
            ==============================
            """,
            "Name", rep.getName(),
            "Email", rep.getEmail(),
            "Company Name", rep.getCompanyName(),
            "Department", rep.getDepartment(),
            "Position", rep.getPosition(),
            "Status", rep.getStatus()
        );
    }

}
