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
import IPMS.ObjectClasses.GenerateReports;
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
        System.out.println("[2] Generate report with applicants");
        System.out.println("[3] Generate report by status");
        System.out.println("[4] Generate report by Major");
        System.out.println("[5] Generate report by Internship Level");
        System.out.println("[6] Generate report by Company");
        System.out.println("[7] Generate custom report");
        System.out.println("[8] Back");
        System.out.println("[9] Logout\n");

        System.out.print("Enter an option (1-9): ");
    }

    /** 
     * @return PageAction
     */
    @SuppressWarnings("unchecked")
    @Override
    public PageAction next() {
          int opt = UniversalFunctions.readIntInRange(1, 9);
            
          return switch (opt) {
                    //full report
                    case 1 -> {
                        reports.generateFullReport();
                        yield PageAction.pop();
                    }
                    //print applicants
                    case 2 -> {
                        yield PageAction.pop();
                    }


                    //report by status
                    // case 3 -> {
                    //     System.out.print("Enter status: ");
                    //     String whatstatus= UniversalFunctions.readString();
                    //     if (whatstatus == "PENDING"){
                    //         InternshipStatus Status = InternshipStatus.PENDING;
                    //     }
                    //     else if (whatstatus == "FILLED"){
                    //         InternshipStatus Status = InternshipStatus.FILLED;
                    //     }
                    //     else if (whatstatus == "APPROVED"){
                    //         InternshipStatus Status = InternshipStatus.APPROVED;
                    //     }
                    //     else{
                    //         InternshipStatus Status = InternshipStatus.REJECTED;
                    //     }

                    //     reports.generateReportByStatus(Status);
                    //     yield PageAction.pop();
                    // }


                    //report by major
                    case 4 -> {
                        System.out.print("Enter major: ");
                        String major = UniversalFunctions.readString();
                        reports.generateReportByMajor(major);
                        yield PageAction.pop();
                    }


                    //report by internship level
                    // case 5 -> {
                    //     System.out.print("Enter internship level: ");
                    //     String whatLevel = UniversalFunctions.readString();
                    //     if (whatLevel == "ADVANCED"){
                    //         InternshipLevel level = InternshipLevel.ADVANCED;
                    //     }
                    //     else if (whatLevel == "INTERMEDIATE"){
                    //         InternshipLevel level = InternshipLevel.INTERMEDIATE;
                    //     }
                    //     else{
                    //          InternshipLevel level = InternshipLevel.BASIC;
                    //     }
                    //     reports.generateReportByLevel(level);
                    //     yield PageAction.pop();
                    // }

                    //report by company
                    case 6 -> {
                        System.out.print("Enter company: ");
                        String companyString = UniversalFunctions.readString();
                        reports.generateReportByCompany(companyString);
                        yield PageAction.pop();
                    }

                    //custom report
                    // case 7 -> {
                        
                    //     reports.generateCustomReport(null, null, null, null);;
                    //     yield PageAction.pop();
                    // }

                    case 8 -> PageAction.pop();
                    case 9 -> {
                        staffObj.logout();
                        yield PageAction.pop();
                    }
                    default -> PageAction.pop();
        };


    }




}