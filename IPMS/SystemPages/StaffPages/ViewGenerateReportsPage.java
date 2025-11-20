package IPMS.SystemPages.StaffPages;

import IPMS.SystemPages.StudentPages.*;
import IPMS.SystemPages.StudentPages.*;
import IPMS.ObjectClasses.*;
import IPMS.System.SystemDataEntities.*;
import IPMS.System.SystemData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import IPMS.Enums.InternshipLevel;
import IPMS.Enums.InternshipStatus;
import IPMS.ObjectClasses.*;
import IPMS.SystemPages.CompanyPages.CompanyMainPage;
import IPMS.SystemPages.PageUtilities.Page;
import IPMS.SystemPages.PageUtilities.PageAction;
import IPMS.SystemPages.PageUtilities.UniversalFunctions;

public class ViewGenerateReportsPage implements Page{

    private GenerateReports reports;
    private CareerCenter staffObj ;
    private int opt;
    public InternshipLevel level;
    public InternshipStatus Status;

    public ViewGenerateReportsPage(CareerCenter obj){
        this.staffObj = obj;
        this.reports = new GenerateReports();
    }


    @Override
    public void showMenu() {
        System.out.println("===== GENERATE REPORTS =====");
        System.out.println("[1] Generate full report");
        System.out.println("[2] Generate report by status");
        System.out.println("[3] Generate report by Major");
        System.out.println("[4] Generate report by Internship Level");
        System.out.println("[5] Generate report by Company");
        System.out.println("[6] Generate custom report");
        System.out.println("[7] Back");
        System.out.println("[8] Logout\n");

        System.out.print("Enter an option (1-8): ");
    }

    /** 
     * @return PageAction
     */
<<<<<<< Updated upstream
    @SuppressWarnings("unchecked")
=======
>>>>>>> Stashed changes
    @Override
    public PageAction next() {
          int opt = UniversalFunctions.readIntInRange(1, 8);
            
          return switch (opt) {
                    //full report
                    case 1 -> {
                        reports.generateFullReport();
                        yield PageAction.pop();
                    }

                    //report by status
                    case 2 -> {
                        System.out.print("Enter status: ");
                        String whatstatus= UniversalFunctions.readString();
                        InternshipStatus status = parseStatus(whatstatus);
                        reports.generateReportByStatus(status);
                        yield PageAction.pop();
                    }


                    //report by major
                    case 3 -> {
                        System.out.print("Enter major: ");
                        String major = UniversalFunctions.readString();
                        reports.generateReportByMajor(major);
                        yield PageAction.pop();
                    }


                    //report by internship level
                    case 4 -> {
                        System.out.print("Enter internship level: ");
                        String whatLevel = UniversalFunctions.readString();
                        InternshipLevel lvl = parseLevel(whatLevel);
                        reports.generateReportByLevel(lvl);
                        yield PageAction.pop();
                    }

                    //report by company
                    case 5 -> {
                        System.out.print("Enter company: ");
                        String companyString = UniversalFunctions.readString();
                        reports.generateReportByCompany(companyString);
                        yield PageAction.pop();
                    }

                    //custom report
                    case 6 -> {
                        System.out.print("Enter status: ");
                        String statusStr = UniversalFunctions.readString();
                        InternshipStatus istatus = parseStatus(statusStr);

                        System.out.print("Enter major: ");
                        String Major = UniversalFunctions.readString();

                        System.out.print("Enter internship level: ");
                        String levelStr = UniversalFunctions.readString();
                        InternshipLevel ilevel = parseLevel(levelStr);

                        System.out.print("Enter company: ");
                        String company = UniversalFunctions.readString();

                        // pass parsed values to the report generator
                        reports.generateCustomReport(istatus, Major, ilevel, company);
                        yield PageAction.pop();
                    }

                    case 7 -> PageAction.pop();
                    case 8 -> {
                        staffObj.logout();
                        yield PageAction.pop();
                    }
                    default -> PageAction.pop();
        };

    }

<<<<<<< Updated upstream
    // add helper methods for parsing enums
=======
    /** 
     * @param s
     * @return InternshipStatus
     */
    // ====== ENUM PARSERS WITH VALIDATION ======
>>>>>>> Stashed changes
    private InternshipStatus parseStatus(String s) {
        if (s == null) return InternshipStatus.REJECTED;
        try {
            return InternshipStatus.valueOf(s.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            // default if input invalid
            return InternshipStatus.REJECTED;
        }
    }

    /** 
     * @param s
     * @return InternshipLevel
     */
    private InternshipLevel parseLevel(String s) {
        if (s == null) return InternshipLevel.BASIC;
        try {
            return InternshipLevel.valueOf(s.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            // default if input invalid
            return InternshipLevel.BASIC;
        }
    }
<<<<<<< Updated upstream




=======
>>>>>>> Stashed changes
}