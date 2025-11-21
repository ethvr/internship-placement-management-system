package IPMS.SystemPages.CommonPages;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import IPMS.System.*;
import IPMS.Enums.InternshipLevel;
import IPMS.ObjectClasses.*;
import IPMS.SystemPages.CompanyPages.ManageApplicationsPage;
import IPMS.SystemPages.CompanyPages.ManageInternshipsPage;
import IPMS.SystemPages.MainSubPages.CompanyRegisterPage;
import IPMS.SystemPages.PageUtilities.Page;
import IPMS.SystemPages.PageUtilities.PageAction;
import IPMS.SystemPages.PageUtilities.UniversalFunctions;
import IPMS.SystemPages.StaffPages.ViewInternshipRequestPage;
import IPMS.SystemPages.StudentPages.ApplyToInternshipPage;


public class FilteredInternshipsPage implements Page {
    private final User obj;
    private final String filter;
    private final List<Internship> list;

    public FilteredInternshipsPage (User obj, List<Internship> list, String filter) {
        this.obj = obj;
        this.filter = filter;
        this.list = list;
    }

    /** 
     * @param Obj
     * @return PageAction
     */
    private PageAction UserIdentifier(User obj, List<Internship> list) {

        if (list.isEmpty()) {
            System.out.println("No internships found with the applied filter(s).");
            return PageAction.pop();
        }

        if (obj instanceof Student s){
            System.out.println("[1] Proceed To Application");
            System.out.println("[2] Back");
            System.out.println("Enter an option: ");
            int opt = UniversalFunctions.readIntInRange(1, 2);
            return switch (opt) {
                case 1 -> PageAction.push(new ApplyToInternshipPage(s, list));
                case 2 -> PageAction.pop();
                default -> PageAction.pop();
            };
        }

        else if (obj instanceof CareerCenter cc) {
            System.out.println("[1] Proceed To Approve/Reject Internships");
            System.out.println("[2] Back");
            System.out.println("Enter an option: ");
            int opt = UniversalFunctions.readIntInRange(1, 2);
            return switch (opt) {
                    case 1 -> PageAction.push(new ViewInternshipRequestPage(cc, list));
                    case 2 -> PageAction.pop();
                    default -> PageAction.pop();
                };
        }
        
        else if (obj instanceof CompanyRepresentative cr) {
            System.out.println("[1] Manage Internships");
            System.out.println("[2] View Internship Applications");
            System.out.println("[3] Back");
            System.out.println("Enter an option: ");
            int opt = UniversalFunctions.readIntInRange(1, 3);
            return switch (opt) {
                    case 1 -> PageAction.push(new ManageInternshipsPage(cr));
                    case 2 -> PageAction.push(new ManageApplicationsPage(cr, list));
                    case 3 -> PageAction.pop();
                    default -> PageAction.pop();
                };
        }
        System.out.println("Enter [0] to return: ");
            int opt = UniversalFunctions.readIntInRange(0, 0);
            return switch (opt) {
                    case 1 -> PageAction.pop();
                    default -> PageAction.pop();
                };
    }

    @Override
    public void showMenu() {
        System.out.println("\n===== FILTERED INTERNSHIPS =====");
    }

    /** 
     * @return PageAction
     */
    @Override
    public PageAction next() {

        int YearOfStudy;

        if (obj instanceof Student s){YearOfStudy = s.getYearOfStudy();}
        else {YearOfStudy = 0; /* 0 for company and careerstaff */}

        List<Internship> InternshipList2 = new ArrayList<>();

        return switch (filter) {

            case "none" -> {
                System.out.println("======= NO FILTER APPLIED =======");
                if (YearOfStudy <= 2 && YearOfStudy > 0) {
                    System.out.println("You can only view BASIC Internships");
                    UniversalFunctions.printInternshipList(list);
                    yield UserIdentifier(obj, list);
                }
                else {
                    UniversalFunctions.printInternshipList(list);
                    yield UserIdentifier(obj, list);
                }

            }
            case "level" -> {
                System.out.println("======= FILTERED BY LEVEL =======");
                if (YearOfStudy <= 2 && YearOfStudy > 0) {
                    System.out.println("You can only view BASIC Internships");
                    UniversalFunctions.printInternshipList(list);
                    yield UserIdentifier(obj, list);
                }
                else {
                    //pass list as param to further filter 
                    //print list 
                    System.out.println("[1] Basic");
                    System.out.println("[2] Intermediate");
                    System.out.println("[3] Advanced");
                    System.out.print("Enter your choice: ");
                    switch (UniversalFunctions.readIntInRange(1, 3)) {
                        case 1 -> {
                            InternshipList2 = Filters.filterByInternshipLevel(InternshipLevel.BASIC, list);
                        }
                        case 2 -> {
                            InternshipList2 = Filters.filterByInternshipLevel(InternshipLevel.INTERMEDIATE, list);
                        }
                        case 3 -> {
                            InternshipList2 = Filters.filterByInternshipLevel(InternshipLevel.ADVANCED, list);
                        }
                    }
                    System.out.println();
                    UniversalFunctions.printInternshipList(InternshipList2);
                    yield UserIdentifier(obj, InternshipList2);
                }
            }

            /* ------------------ Filter by DATE ------------------ */
            case "date" -> {
                System.out.print("Enter closing date to filter by:");
                LocalDate date = UniversalFunctions.readValidDate();
                InternshipList2 = Filters.filterByClosingDate(date);
                System.out.println("======= FILTERED BY CLOSING DATE =======");
                UniversalFunctions.printInternshipList(InternshipList2);
                yield UserIdentifier(obj, InternshipList2);
            }

            /* ------------------ Filter by COMPANY NAME ------------------ */
            case "name" -> {
                System.out.print("Enter company name: ");
                String name = UniversalFunctions.readString();
                InternshipList2 = Filters.filterByCompanyName(name);
                System.out.println("======= FILTERED BY COMPANY NAME =======");
                UniversalFunctions.printInternshipList(InternshipList2);
                yield UserIdentifier(obj, InternshipList2);
                
            }

            /* ------------------ Filter by SLOTS ------------------ */
            case "slots" -> {
                System.out.print("Enter the number of slots left: ");
                int slots = UniversalFunctions.readIntInRange(1, 10);
                InternshipList2 = Filters.filterBySlotsLeft(slots);
                System.out.println("======= FILTERED BY NUMBER OF SLOTS =======");
                UniversalFunctions.printInternshipList(InternshipList2);
                yield UserIdentifier(obj, InternshipList2);
            }

            /* ------------------ Filter by KEYWORD ------------------ */
            case "words" -> {
                System.out.print("Enter keyword: ");
                String word = UniversalFunctions.readString();
                InternshipList2 = Filters.filterByKeyword(word);
                System.out.println("======= FILTERED BY KEYWORD =======");
                UniversalFunctions.printInternshipList(InternshipList2);
                yield UserIdentifier(obj, InternshipList2);
            }

            default -> {
                yield PageAction.pop();
            }
        };

    }
}
