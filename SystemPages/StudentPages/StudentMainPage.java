package IPMS.SystemPages.StudentPages;

import java.util.Map;
import IPMS.SystemPages.Page;
import IPMS.SystemPages.PageAction;
import IPMS.System.SystemDataEntities.*;
import IPMS.System.SystemData;
import IPMS.ObjectClasses.*;
import IPMS.SystemPages.UniversalFunctions;

public class StudentMainPage implements Page{
    private final String username;

    public StudentMainPage(String username) {
        this.username = username;
    }

    @Override
    public void showMenu() {
        System.out.println("======= STUDENT =======");
        System.out.println("[1] View all available Internships");
        System.out.println("[2] View your Internship Applications");
        System.out.println("[3] View your Withdrawal Applications");
        System.out.println("[4] Logout\n");

        System.out.print("Enter an option (1-4): ");;
    }

    @Override
    public PageAction next() {

        // load relevaent maps (code here)
        SystemData.loadIntoMap("student", StudentCSVData.class);
        // student map
        Map<String, StudentCSVData> studentmap = SystemData.getStudentMap();
        // get users object
        StudentCSVData data = studentmap.get(username);

        Student studentObj = new Student(
                        data.StudentID,
                        data.Name,
                        data.Year,
                        data.Major
                    );

        int opt = UniversalFunctions.readIntInRange(1, 4);

        return switch (opt) {
            case 1 -> PageAction.push(new ViewInternshipsPage(studentObj));
            case 2 -> PageAction.push(new ViewApplicationsPage(studentObj));
            case 3 -> PageAction.push(new ViewWithdrawalsPage(studentObj));
            case 4 -> {
                User.logout();
                yield PageAction.exit();
            }
            default -> PageAction.pop();
        };
    }
}

