package IPMS.SystemPages.StudentPages;

import java.util.List;
import java.util.ArrayList;

import IPMS.ObjectClasses.Student;
import IPMS.ObjectClasses.WithdrawalRequest;
import IPMS.System.SystemData;
import IPMS.SystemPages.PageUtilities.Page;
import IPMS.SystemPages.PageUtilities.PageAction;

public class ViewWithdrawalsPage implements Page {

    private final Student student;

    public ViewWithdrawalsPage(Student student) {
        this.student = student;
    }

    @Override
    public void showMenu() {
        System.out.println("\n===== YOUR WITHDRAWAL REQUESTS =====");

        List<WithdrawalRequest> list = getMyWithdrawalRequests();

        if (list.isEmpty()) {
            System.out.println("You have not submitted any withdrawal requests.");
            System.out.println("\nPress Enter to go back...");
            try { System.in.read(); } catch (Exception e) {}
            return;
        }

        int index = 1;
        for (WithdrawalRequest wr : list) {
            System.out.printf("[%d] Request ID      : %s\n", index++, wr.getId());
            System.out.println("    Application ID : " + wr.getApplicationId());
            System.out.println("    Status         : " + wr.getStatus());
            System.out.println("    Date Requested : " + wr.getRequestTime());
            System.out.println("    Remarks        : " + wr.getRemarks());
            System.out.println();
        }

        System.out.println("Press Enter to go back...");
        try { System.in.read(); } catch (Exception e) {}
    }

    @Override
    public PageAction next() {
        return PageAction.pop();
    }

    /* 
        Returns all withdrawal requests made by this student.
        Very light helper.
    */
    private List<WithdrawalRequest> getMyWithdrawalRequests() {
        List<WithdrawalRequest> result = new ArrayList<>();

        for (WithdrawalRequest wr : SystemData.getWithdrawalMap().values()) {
            if (wr.getStudentId().equals(student.getUserId())) {
                result.add(wr);
            }
        }

        return result;
    }
}
