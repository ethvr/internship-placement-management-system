package IPMS.SystemPages.StaffPages;

import IPMS.Enums.WithdrawalStatus;
import IPMS.ObjectClasses.*;
import IPMS.System.SystemData;
import IPMS.SystemPages.PageUtilities.Page;
import IPMS.SystemPages.PageUtilities.PageAction;
import IPMS.SystemPages.PageUtilities.UniversalFunctions;
import java.util.HashMap;
import java.util.List;

public class ViewWithdrawalRequestsPage implements Page{

    private CareerCenter staffObj ;
    private int opt;
    private HashMap<Integer, WithdrawalRequest> indexMap;

    public ViewWithdrawalRequestsPage(CareerCenter obj){
        this.staffObj = obj;
        indexMap = new HashMap<>();
    }


    @Override
    public void showMenu() {
        System.out.println("\n====== WITHDRAWAL REQUESTS ======");
        List<WithdrawalRequest> withdrawalList = SystemData.getAllWithdrawalList();
        if (withdrawalList.isEmpty()) {
            System.out.println("\nNo pending withdrawal requests at this time.\n");
            System.out.printf("[1] Cancel\n");
            System.out.print("Select an option: ");
            return;
        }
        indexMap = UniversalFunctions.printWithdrawalrequest(withdrawalList);

        System.out.printf("[%d] Cancel", indexMap.size() + 1);
        System.out.print("\nSelect an Withdrawal Request or Cancel: ");
    }

    /** 
     * @return PageAction
     */
    @SuppressWarnings("unchecked")
    @Override
    public PageAction next() {
        int opt = UniversalFunctions.readIntInRange(1, indexMap.size() + 1);

        if (opt == indexMap.size() + 1) {
            return PageAction.pop(); // Cancel option
        }

        WithdrawalRequest selectedWithdrawal = indexMap.get(opt);

        System.out.println("\n====== SELECTED WITHDRAWAL REQUEST DETAILS ======");

        if (!(selectedWithdrawal.getStatus() == WithdrawalStatus.PENDING)) {
            System.out.println("\nThis withdrawal request has already been processed.");
            return PageAction.stay();
        }

        System.out.printf("[%s] %n",
                selectedWithdrawal.getApplicationId()
            );

        System.out.printf(
            "  %-20s : %s%n" +
            "  %-20s : %s%n" +
            "  %-20s : %s%n" +
            "  %-20s : %s%n" +
            "  %-20s : %s%n",
            "Withdrawal ID",  selectedWithdrawal.getId(),
            "Student ID",     selectedWithdrawal.getStudentId(),
            "Requested Date", selectedWithdrawal.getRequestDate(),
            "Status",         selectedWithdrawal.getStatus(),
            "Remarks",        selectedWithdrawal.getRemarks()
        );

            System.out.println();

        System.out.println("[1] Approve Withdrawal Request");
        System.out.println("[2] Reject Withdrawal Request");
        System.out.println("[3] Back to Withdrawal Requests Menu");
        System.out.print("select an option: ");

        opt = UniversalFunctions.readIntInRange(1, 3);

        return switch (opt) {
            case 1 -> {
                staffObj.approveWithdrawal(selectedWithdrawal);
                System.out.printf("%s withdrawal for student %s has been approved\n",
                selectedWithdrawal.getId(), selectedWithdrawal.getStudentId());
                yield PageAction.pop();
            }
            case 2 -> {
                staffObj.rejectWithdrawal(selectedWithdrawal);
                System.out.printf("%s withdrawal for student %s has been rejected\n",
                selectedWithdrawal.getId(), selectedWithdrawal.getStudentId());
                yield PageAction.pop();
            }
            case 3 -> PageAction.stay();
            default -> PageAction.pop();
        };
    }

}
