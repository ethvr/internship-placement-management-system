package IPMS.SystemPages.PageUtilities;

import java.util.List;
import java.util.Scanner;
import IPMS.ObjectClasses.*;

public class UniversalFunctions {

    static Scanner sc = new Scanner(System.in);

    public static int readIntInRange(int min, int max) {
        while (true) {
            try {
                if (!sc.hasNextInt()) {
                    System.out.println("Invalid input. Please enter a number.");
                    if (sc.hasNext()) {
                        sc.next(); // discard invalid input
                    } else {
                        return min; // Return default on EOF
                    }
                    continue;
                }

                int option = sc.nextInt();
                if (option >= min && option <= max) {
                    return option;
                }

                System.out.println("Invalid option, try again.");
            } catch (Exception e) {
                return min; // Return default on exception
            }
        }
    }

    public static String readString() {

        try {
            sc.nextLine(); // clear leftover newline if needed
            if (!sc.hasNextLine()) {
                return ""; // Return empty on EOF
            }
            String input = sc.nextLine().trim();

            while (input.isEmpty()) {
                System.out.println("Input cannot be empty. Try again.");
                if (!sc.hasNextLine()) {
                    return ""; // Return empty on EOF
                }
                input = sc.nextLine().trim();
            }

            return input;
        } catch (Exception e) {
            return ""; // Return empty on exception
        }
    }


    public static <T extends Enum<T>> T readEnum(Class<T> enumType) {
        while (true) {
            try {
                if (!sc.hasNext()) {
                    return enumType.getEnumConstants()[0]; // Return first enum on EOF
                }
                String input = sc.next().trim().toUpperCase();

                try {
                    return Enum.valueOf(enumType, input);
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid choice. Options are:");
                    for (T constant : enumType.getEnumConstants()) {
                        System.out.println(" - " + constant.name());
                    }
                }
            } catch (Exception e) {
                return enumType.getEnumConstants()[0]; // Return first enum on exception
            }
        }
    }

    public static void printInternshipList(List<Internship> list) {

        if (list == null || list.isEmpty()) {
            System.out.println("\nNo internships found.\n");
            return;
        }

        System.out.println("\n======= AVAILABLE INTERNSHIPS =======\n");

        int index = 1;
        for (Internship i : list) {

            System.out.printf("[%d] %s (%s)%n",
                index++,
                i.getInternshipTitle(),
                i.getLevel()
            );

            System.out.printf("  Company         : %s%n", i.getCompanyName());
            System.out.printf("  Preferred Major : %s%n", i.getPreferredMajor());
            System.out.printf("  Slots Available : %d%n", i.getSlots());
            System.out.printf("  Closing Date    : %s%n", i.getCloseDate());
            System.out.printf("  Description     : %s%n", i.getDescription());

            System.out.println();

        }
    }




}