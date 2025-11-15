package IPMS.SystemPages.StudentPages;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import IPMS.SystemPages.Page;
import IPMS.SystemPages.PageAction;
import IPMS.System.SystemDataEntities.*;
import IPMS.System.SystemData;
import IPMS.ObjectClasses.*;
import IPMS.SystemPages.UniversalFunctions;


public class FilteredInternshipsPage implements Page {
    private Student obj;
    private String filter;
    private List<InternshipData> list;

    public FilteredInternshipsPage(Student obj, List<InternshipData> list, String filter) {
        this.obj = obj;
        this.filter = filter;
        this.list = list;
    }

    @Override
    public void showMenu() {
        System.out.println("======= FILTERED INTERNSHIPS =======");
        
    }

    @Override
    public PageAction next() {

        int YearOfStudy = obj.getYearOfStudy();
        List<InternshipData> InternshipList2;

        return switch (filter) {
            case "level" -> {
                if (YearOfStudy <= 2) {
                    System.out.println("You can only view BASIC Internships");
                    UniversalFunctions.printInternshipList(list);
                    yield PageAction.pop();
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
                            InternshipList2 = SystemData.filterByInternshipLevel(InternshipLevel.BASIC, InternshipList);
                        }
                        case 2 -> {
                            InternshipList2 = SystemData.filterByInternshipLevel(InternshipLevel.INTERMEDIATE, InternshipList);
                        }
                        case 3 -> {
                            InternshipList2 = SystemData.filterByInternshipLevel(InternshipLevel.ADVANCED, InternshipList);
                        }
                    }
                    yield PageAction.pop();

                }
            }
            case "date" -> {
                while (true) { 
                    System.out.println("Enter a year: ");
                    int year = UniversalFunctions.readIntInRange(2025, 9999);

                    System.out.println("Enter a month: ");
                    int month = UniversalFunctions.readIntInRange(1, 12);

                    System.out.println("Enter a day: ");
                    int day = UniversalFunctions.readIntInRange(1, 31);

                    try {
                        LocalDate date = LocalDate.of(year, month, day); 
                        // If this fails, the catch block runs
                        System.out.println("Valid date entered: " + date);
                        break; // Exit the loop because date is valid
                    } catch (DateTimeException e) {
                        System.out.println("Invalid date. Please try again.\n");
                    }

                }
                InternshipList2 = SystemData.filterByClosingDate(date);
                UniversalFunctions.printInternshipList(InternshipList2);
                yield PageAction.pop();

            }
            case "name" -> {
                
                System.out.print("Enter the Company name: ");
                String name = UniversalFunctions.readString();
                InternshipList2 = SystemData.filterByCompanyName(name);
                UniversalFunctions.printInternshipList(InternshipList2);
                yield PageAction.pop();
                
            }
            case "slots" -> {
                System.out.print("Enter the Company name: ");
                int slots = UniversalFunctions.readIntInRange(1, 10);
                InternshipList2 = SystemData.filterBySlotsLeft(slots);
                UniversalFunctions.printInternshipList(InternshipList2);
                yield PageAction.pop();
            }
            case "words" -> {
                System.out.print("Enter one Keyword to search for: ");
                String word = UniversalFunctions.readString();
                InternshipList2 = SystemData.filterByKeyword(word);
                UniversalFunctions.printInternshipList(InternshipList2);
                yield PageAction.pop();
            }
            default -> {
                yield PageAction.pop();
            }
        }
    }

}







