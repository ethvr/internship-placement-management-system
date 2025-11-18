package SystemPages.CompanyPages;
import java.util.List;

import IPMS.ObjectClasses.Internship;
import IPMS.ObjectClasses.CompanyRepresentative;
import IPMS.System.SystemData;
import IPMS.SystemPages.Page;
import IPMS.SystemPages.PageAction;
import IPMS.SystemPages.UniversalFunctions;  

public class ViewCreatedInternshipsPage implements Page {
    private final String username;

    public ViewCreatedInternshipsPage(String username) {
        this.username = username;
    }

    @Override
    public void showMenu() {
        System.out.println("======= MY INTERNSHIPS =======\n");
        
        CompanyRepresentative compRep = SystemData.getCompanyValue(username);
        
        if (compRep == null) {
            System.out.println("Error: Company representative not found.\n");
            return;
        }

        List<Internship> internships = compRep.getInternshipsCreated();
        
        if (internships.isEmpty()) {
            System.out.println("You have not created any internships yet.\n");
        } else {
            System.out.println("Total Internships Created: " + internships.size() + "/5\n");
            
            for (int i = 0; i < internships.size(); i++) {
                Internship intern = internships.get(i);
                
                System.out.println("─────────────────────────────────────");
                System.out.println("Internship #" + (i + 1));
                System.out.println("─────────────────────────────────────");
                System.out.println("ID: " + intern.getId());
                System.out.println("Title: " + intern.getTitle());
                System.out.println("Description: " + intern.getDescription());
                System.out.println("Level: " + intern.getLevel());
                System.out.println("Preferred Major: " + intern.getPreferredMajor());
                System.out.println("Opening Date: " + intern.getOpenDate());
                System.out.println("Closing Date: " + intern.getCloseDate());
                System.out.println("Slots Available: " + intern.getSlots());
                System.out.println("Status: " + intern.getStatus());
                System.out.println("Visibility: " + (intern.isVisible() ? "Visible to Students" : "Hidden"));
                System.out.println("Company: " + intern.getCompanyName());
                System.out.println();
            }
        }
        
        System.out.println("[1] Back\n");
        System.out.print("Enter option: ");
    }

    @Override
    public PageAction next() {
        int opt = UniversalFunctions.readIntInRange(1, 1);
        
        return switch (opt) {
            case 1 -> PageAction.pop();
            default -> PageAction.pop();
        };
    }
}
