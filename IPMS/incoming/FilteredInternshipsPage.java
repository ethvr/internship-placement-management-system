package IPMS.incoming;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import IPMS.ObjectClasses.Student;
import IPMS.ObjectClasses.Internship;
import IPMS.System.SystemData;
import IPMS.SystemPages.PageUtilities.Page;
import IPMS.SystemPages.PageUtilities.PageAction;
import IPMS.SystemPages.PageUtilities.UniversalFunctions;
import IPMS.Enums.InternshipLevel;
import IPMS.System.SystemDataEntities.InternshipData;

public class FilteredInternshipsPage implements Page {

    private final Student student;
    private final String filterType;
    private List<InternshipData> baseList;

    public FilteredInternshipsPage(Student student, List<InternshipData> list, String filterType) {
        this.student = student;
        this.baseList = list;
        this.filterType = filterType;
    }

    @Override
    public void showMenu() {
        System.out.println("\n===== FILTERED INTERNSHIPS =====");
    }

    @Override
    public PageAction next() {

        List<InternshipData> filtered = new ArrayList<>();

        switch (filterType) {

            /* ------------------ Filter by LEVEL ------------------ */
            case "level" -> {
                if (student.getYearOfStudy() <= 2) {
                    System.out.println("You may only view BASIC internships.");
                    UniversalFunctions.printInternshipList(baseList);
                    return PageAction.pop();
                }

                System.out.println("[1] Basic");
                System.out.println("[2] Intermediate");
                System.out.println("[3] Advanced");
                System.out.print("Enter choice: ");
                int level = UniversalFunctions.readIntInRange(1, 3);

                InternshipLevel chosen = switch (level) {
                    case 1 -> InternshipLevel.BASIC;
                    case 2 -> InternshipLevel.INTERMEDIATE;
                    default -> InternshipLevel.ADVANCED;
                };

                filtered = SystemData.filterByInternshipLevel(chosen, baseList);
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

                filtered = SystemData.filterByClosingDate(date);
            }

            /* ------------------ Filter by COMPANY NAME ------------------ */
            case "name" -> {
                System.out.print("Enter company name: ");
                String name = UniversalFunctions.readString();
                filtered = SystemData.filterByCompanyName(name);
            }

            /* ------------------ Filter by SLOTS ------------------ */
            case "slots" -> {
                System.out.print("Enter number of slots: ");
                int s = UniversalFunctions.readIntInRange(1, 100);
                filtered = SystemData.filterBySlotsLeft(s);
            }

            /* ------------------ Filter by KEYWORD ------------------ */
            case "words" -> {
                System.out.print("Enter keyword: ");
                String word = UniversalFunctions.readString();
                filtered = SystemData.filterByKeyword(word);
            }

            default -> {
                return PageAction.pop();
            }
        }

        // Print the results
        UniversalFunctions.printInternshipList(filtered);

        if (filtered.isEmpty()) {
            System.out.println("No internships matched this filter.");
            return PageAction.pop();
        }

        // Option to select internship
        System.out.println("[1] Select Internship");
        System.out.println("[2] Back");
        System.out.print("Enter choice: ");

        int action = UniversalFunctions.readIntInRange(1, 2);

        if (action == 2) return PageAction.pop();

        // Select internship
        System.out.print("Enter internship number: ");
        int idx = UniversalFunctions.readIntInRange(1, filtered.size());

        InternshipData chosen = filtered.get(idx - 1);
        Internship real = SystemData.getInternshipValue(chosen.getUniqueID());

        if (real == null) {
            System.out.println("Error loading internship.");
            return PageAction.stay();
        }

        return PageAction.push(new InternshipDetailsPage(student, real));
    }
}
