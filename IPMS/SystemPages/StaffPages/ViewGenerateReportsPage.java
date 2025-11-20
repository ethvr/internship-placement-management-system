package IPMS.SystemPages.StaffPages;

import IPMS.Enums.InternshipLevel;
import IPMS.Enums.InternshipStatus;
import IPMS.ObjectClasses.*;
import IPMS.System.SystemData;

import java.util.Map;

import IPMS.SystemPages.PageUtilities.Page;
import IPMS.SystemPages.PageUtilities.PageAction;
import IPMS.SystemPages.PageUtilities.UniversalFunctions;

public class ViewGenerateReportsPage implements Page {

    private final GenerateReports reports;
    private final CareerCenter staffObj;

    public ViewGenerateReportsPage(CareerCenter obj) {
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

    @Override
    public PageAction next() {
        int opt = UniversalFunctions.readIntInRange(1, 8);

        return switch (opt) {

            // Full Report
            case 1 -> {
                reports.generateFullReport();
                yield PageAction.pop();
            }

            // Report by Status
            case 2 -> {
                InternshipStatus status = null;

                while (true) {
                    System.out.print("Enter status (PENDING / APPROVED / REJECTED)  (leave blank to cancel): ");
                    String input = UniversalFunctions.readString();

                    if (input == null || input.trim().isEmpty()) {
                        System.out.println("Operation cancelled.");
                        yield PageAction.pop();
                    }

                    status = parseStatus(input);
                    if (status == null) {
                        System.out.println("Invalid status. Try again.");
                        continue;
                    }

                    break;
                }

                reports.generateReportByStatus(status);
                yield PageAction.pop();
            }

            // Report by Major
            case 3 -> {
                System.out.print("Enter major (leave blank to cancel): ");
                String major = UniversalFunctions.readString();

                if (major == null || major.trim().isEmpty()) {
                    System.out.println("Operation cancelled.");
                    yield PageAction.pop();
                }

                reports.generateReportByMajor(major.trim());
                yield PageAction.pop();
            }

            // Report by Internship Level
            case 4 -> {
                InternshipLevel level = null;

                while (true) {
                    System.out.print("Enter internship level (BASIC / INTERMEDIATE / ADVANCED) (leave blank to cancel): ");
                    String input = UniversalFunctions.readString();

                    if (input == null || input.trim().isEmpty()) {
                        System.out.println("Operation cancelled.");
                        yield PageAction.pop();
                    }

                    level = parseLevel(input);
                    if (level == null) {
                        System.out.println("Invalid internship level. Try again.");
                        continue;
                    }

                    break;
                }

                reports.generateReportByLevel(level);
                yield PageAction.pop();
            }

            // Report by Company
            case 5 -> {
                System.out.print("Enter company name (leave blank to cancel): ");
                String company = UniversalFunctions.readString();

                if (company == null || company.trim().isEmpty()) {
                    System.out.println("Operation cancelled.");
                    yield PageAction.pop();
                }

                reports.generateReportByCompany(company.trim());
                yield PageAction.pop();
            }

            // Custom report
            case 6 -> {
                // STATUS
                InternshipStatus status = null;
                while (true) {
                    System.out.print("Enter status (PENDING / APPROVED / REJECTED) (leave blank to cancel): ");
                    String s = UniversalFunctions.readString();

                    if (s == null || s.trim().isEmpty()) {
                        System.out.println("Operation cancelled.");
                        yield PageAction.pop();
                    }

                    status = parseStatus(s);
                    if (status == null) {
                        System.out.println("Invalid status. Try again.");
                        continue;
                    }
                    break;
                }

                // MAJOR
                System.out.print("Enter major (leave blank to cancel): ");
                String major = UniversalFunctions.readString();
                if (major == null || major.trim().isEmpty()) {
                    System.out.println("Operation cancelled.");
                    yield PageAction.pop();
                }

                // LEVEL
                InternshipLevel level = null;
                while (true) {
                    System.out.print("Enter internship level (BASIC / INTERMEDIATE / ADVANCED) (leave blank to cancel): ");
                    String lvl = UniversalFunctions.readString();

                    if (lvl == null || lvl.trim().isEmpty()) {
                        System.out.println("Operation cancelled.");
                        yield PageAction.pop();
                    }

                    level = parseLevel(lvl);
                    if (level == null) {
                        System.out.println("Invalid internship level. Try again.");
                        continue;
                    }
                    break;
                }

                // COMPANY
                System.out.print("Enter company name (leave blank to cancel): ");
                String company = UniversalFunctions.readString();
                if (company == null || company.trim().isEmpty()) {
                    System.out.println("Operation cancelled.");
                    yield PageAction.pop();
                }

                reports.generateCustomReport(status, major.trim(), level, company.trim());
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

    // ====== ENUM PARSERS WITH VALIDATION ======
    private InternshipStatus parseStatus(String s) {
        try {
            return InternshipStatus.valueOf(s.trim().toUpperCase());
        } catch (Exception e) {
            return null;
        }
    }

    private InternshipLevel parseLevel(String s) {
        try {
            return InternshipLevel.valueOf(s.trim().toUpperCase());
        } catch (Exception e) {
            return null;
        }
    }
}
