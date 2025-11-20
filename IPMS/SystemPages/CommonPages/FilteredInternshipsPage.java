package IPMS.SystemPages.CommonPages;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import IPMS.System.*;
import IPMS.Enums.InternshipLevel;
import IPMS.ObjectClasses.*;
import IPMS.SystemPages.MainSubPages.CompanyRegisterPage;
import IPMS.SystemPages.PageUtilities.Page;
import IPMS.SystemPages.PageUtilities.PageAction;
import IPMS.SystemPages.PageUtilities.UniversalFunctions;
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
    private PageAction ForStudent(User Obj) {

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
        System.out.println("Enter [1] to return: ");
            int opt = UniversalFunctions.readIntInRange(1, 1);
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
                if (YearOfStudy <= 2 && YearOfStudy > 0) {
                    System.out.println("You can only view BASIC Internships");
                    UniversalFunctions.printInternshipList(list);
                    yield ForStudent(obj);
                }
                else {
                    UniversalFunctions.printInternshipList(list);
                    yield ForStudent(obj);
                }

            }
            case "level" -> {
                if (YearOfStudy <= 2 && YearOfStudy > 0) {
                    System.out.println("You can only view BASIC Internships");
                    UniversalFunctions.printInternshipList(list);
                    yield ForStudent(obj);
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
                    yield ForStudent(obj);
                }
            }

            /* ------------------ Filter by DATE ------------------ */
            case "date" -> {
                LocalDate date;
                while (true) {
                    try {
                        System.out.print("Enter year: ");
                        int y = UniversalFunctions.readIntInRange(2024, 9999);

                        System.out.print("Enter month: ");
                        int m = UniversalFunctions.readIntInRange(1, 12);

                        System.out.print("Enter day: ");
                        int d = UniversalFunctions.readIntInRange(1, 31);

                        date = LocalDate.of(y, m, d);
                        break;
                    } catch (DateTimeException e) {
                        System.out.println("Invalid date. Try again.\n");
                    }
                }
                InternshipList2 = Filters.filterByClosingDate(date);
                UniversalFunctions.printInternshipList(InternshipList2);
                yield ForStudent(obj);
            }

            /* ------------------ Filter by COMPANY NAME ------------------ */
            case "name" -> {
                System.out.print("Enter company name: ");
                String name = UniversalFunctions.readString();
                InternshipList2 = Filters.filterByCompanyName(name);
                UniversalFunctions.printInternshipList(InternshipList2);
                yield ForStudent(obj);
                
            }

            /* ------------------ Filter by SLOTS ------------------ */
            case "slots" -> {
                System.out.print("Enter the number of slots left: ");
                int slots = UniversalFunctions.readIntInRange(1, 10);
                InternshipList2 = Filters.filterBySlotsLeft(slots);
                UniversalFunctions.printInternshipList(InternshipList2);
                yield ForStudent(obj);
            }

            /* ------------------ Filter by KEYWORD ------------------ */
            case "words" -> {
                System.out.print("Enter keyword: ");
                String word = UniversalFunctions.readString();
                InternshipList2 = Filters.filterByKeyword(word);
                UniversalFunctions.printInternshipList(InternshipList2);
                yield ForStudent(obj);
            }

            default -> {
                yield PageAction.pop();
            }
        };

    }
}
