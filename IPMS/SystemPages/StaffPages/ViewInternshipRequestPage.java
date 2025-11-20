package IPMS.SystemPages.StaffPages;

import IPMS.SystemPages.StudentPages.*;
import IPMS.SystemPages.StudentPages.*;
import IPMS.Enums.InternshipStatus;
import IPMS.ObjectClasses.*;
import IPMS.System.SystemDataEntities.*;
import IPMS.System.SystemData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import IPMS.ObjectClasses.*;
import IPMS.SystemPages.CompanyPages.CompanyMainPage;
import IPMS.SystemPages.PageUtilities.Page;
import IPMS.SystemPages.PageUtilities.PageAction;
import IPMS.SystemPages.PageUtilities.UniversalFunctions;

public class ViewInternshipRequestPage implements Page{

    private CareerCenter staffObj ;
    private int opt;
    //final Map<String, Internship> internshipmap = SystemData.getInternshipMap();

    public ViewInternshipRequestPage(CareerCenter obj){
        this.staffObj = obj;
    }

    @Override
    public void showMenu() {
        System.out.println("===== VIEW INTERNSHIP SUBMISSIONS =====");
        System.out.println("[1] View all pending internship requests");
        System.out.println("[2] Approve internship opportunity");
        System.out.println("[3] Reject internship opportunity");
        System.out.println("[4] Back");
        System.out.println("[5] Logout\n");

        System.out.print("Enter an option (1-5): ");
    }

    /** 
     * @return PageAction
     */
    @SuppressWarnings("unchecked")
    @Override
    public PageAction next() {
          int opt = UniversalFunctions.readIntInRange(1, 5);
            
          return switch (opt) {
                    case 1 -> {
                                    // ✅ View all pending internships across ALL companies
                        List<Internship> pendingInternships = new ArrayList<>();

                        for (Internship internship : SystemData.getInternshipMap().values()) {
                            if (internship.getStatus() == InternshipStatus.PENDING) {
                                pendingInternships.add(internship);
                            }
                        }

                        if (pendingInternships.isEmpty()) {
                            System.out.println("\nNo pending internship requests.");
                        } else {
                            System.out.println("\nPending Internship Requests:");
                            int index = 1;
                            for (Internship i : pendingInternships) {
                                System.out.printf("[%d] ID: %s | Title: %s | Company: %s | Slots: %d\n",
                                    index++,
                                    i.getInternshipId(),
                                    i.getInternshipTitle(),
                                    i.getCompanyName(),
                                    i.getSlots()
                                );
                            }
                        }
                        yield PageAction.stay();  // Stay on the page
                    }
                     
                    case 2 -> {
                        Internship internship = null;

                        while (true) {
                            System.out.print("Enter internship ID to approve (leave blank to cancel): ");
                            String internIDString = UniversalFunctions.readStringAllowEmpty();

                            // Cancel
                            if (internIDString == null || internIDString.trim().isEmpty()) {
                                System.out.println("Operation cancelled.");
                                yield PageAction.pop();
                            }

                            internship = SystemData.getInternshipValue(internIDString.trim());

                            if (internship == null) {
                                System.out.println("Invalid internship ID. Please try again.");
                                continue;
                            }

                            if (internship.getStatus() != InternshipStatus.PENDING) {
                                System.out.println("This internship request is not pending. Please choose another.");
                                continue;
                            }

                            break;
                        }

                        staffObj.approveInternship(internship);
                        System.out.printf("%s - %s internship has been approved\n",
                                internship.getInternshipId(),
                                internship.getInternshipTitle());

                        yield PageAction.pop();
                    }

                    case 3 -> {
                        //reject internship
                        Internship internship = null;

                        while (true) {
                            System.out.print("Enter internship ID to reject (leave blank to cancel): ");
                            String internIDString = UniversalFunctions.readStringAllowEmpty();

                            // Cancel
                            if (internIDString == null || internIDString.trim().isEmpty()) {
                                System.out.println("Operation cancelled.");
                                yield PageAction.pop();
                            }

                            internship = SystemData.getInternshipValue(internIDString.trim());

                            if (internship == null) {
                                System.out.println("Invalid internship ID. Please try again.");
                                continue;
                            }

                            if (internship.getStatus() != InternshipStatus.PENDING) {
                                System.out.println("This internship request is not pending. Please choose another.");
                                continue;
                            }

                            break;
                        }

                        staffObj.rejectInternship(internship);
                        System.out.printf("%s - %s internship has been rejected\n",
                                internship.getInternshipId(),
                                internship.getInternshipTitle());

                        yield PageAction.pop();
                    }


                        case 4 -> PageAction.pop(); 
                        
                        case 5 -> { staffObj.logout(); yield PageAction.pop(); } default -> PageAction.pop(); };


    }

}