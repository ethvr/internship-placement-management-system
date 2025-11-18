<<<<<<< HEAD
package IPMS.SystemPages;

import java.util.List;
import java.util.Scanner;

import IPMS.System.SystemDataEntities.InternshipData;

public class UniversalFunctions {

    static Scanner sc = new Scanner(System.in);

    public static int readIntInRange(int min, int max) {
        while (true) {
            if (!sc.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                sc.next(); // discard invalid input
                continue;
            }

            int option = sc.nextInt();
            if (option >= min && option <= max) {
                return option;
            }

            System.out.println("Invalid option, try again.");
        }
    }

    public static String readString() {

        sc.nextLine(); // clear leftover newline if needed
        String input = sc.nextLine().trim();

        while (input.isEmpty()) {
            System.out.println("Input cannot be empty. Try again.");
            input = sc.nextLine().trim();
        }

        return input;
    }


    public static <T extends Enum<T>> T readEnum(Class<T> enumType) {
        while (true) {
            String input = sc.next().trim().toUpperCase();

            try {
                return Enum.valueOf(enumType, input);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid choice. Options are:");
                for (T constant : enumType.getEnumConstants()) {
                    System.out.println(" - " + constant.name());
                }
            }
        }
    }

    public static void printInternshipList(List<InternshipData> list) {

        if (list == null || list.isEmpty()) {
            System.out.println("\nNo internships found.\n");
            return;
        }

        System.out.println("\n======= AVAILABLE INTERNSHIPS =======\n");

        int index = 1;
        for (InternshipData i : list) {

            System.out.printf("[%d] %s (%s)\n",
                index++,
                i.getInternshipTitle()//,
                //i.getInternshipLevel()
            );

            System.out.printf("    Company         : %s\n", i.getCompanyName());
            System.out.printf("    Preferred Major : %s\n", i.getPrefferedMajors());
            System.out.printf("    Slots Available : %d\n", i.getNumberofSlots());
            System.out.printf("    Closing Date    : %d\n", i.getClosingDate());
            System.out.printf("    Description     : %s\n", i.getDescription());

            System.out.println(); // blank line between entries
        }
    }




=======
package IPMS.SystemPages;

import java.util.List;
import java.util.Scanner;

import IPMS.System.SystemDataEntities.InternshipData;

public class UniversalFunctions {

    static Scanner sc = new Scanner(System.in);

    public static int readIntInRange(int min, int max) {
        while (true) {
            if (!sc.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                sc.next(); // discard invalid input
                continue;
            }

            int option = sc.nextInt();
            if (option >= min && option <= max) {
                return option;
            }

            System.out.println("Invalid option, try again.");
        }
    }

    public static String readString() {

        sc.nextLine(); // clear leftover newline if needed
        String input = sc.nextLine().trim();

        while (input.isEmpty()) {
            System.out.println("Input cannot be empty. Try again.");
            input = sc.nextLine().trim();
        }

        return input;
    }


    public static <T extends Enum<T>> T readEnum(Class<T> enumType) {
        while (true) {
            String input = sc.next().trim().toUpperCase();

            try {
                return Enum.valueOf(enumType, input);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid choice. Options are:");
                for (T constant : enumType.getEnumConstants()) {
                    System.out.println(" - " + constant.name());
                }
            }
        }
    }

    public static void printInternshipList(List<InternshipData> list) {

        if (list == null || list.isEmpty()) {
            System.out.println("\nNo internships found.\n");
            return;
        }

        System.out.println("\n======= AVAILABLE INTERNSHIPS =======\n");

        int index = 1;
        for (InternshipData i : list) {

            System.out.printf("[%d] %s (%s)\n",
                index++,
                i.getInternshipTitle()//,
                //i.getInternshipLevel()
            );

            System.out.printf("    Company         : %s\n", i.getCompanyName());
            System.out.printf("    Preferred Major : %s\n", i.getPrefferedMajors());
            System.out.printf("    Slots Available : %d\n", i.getNumberofSlots());
            System.out.printf("    Closing Date    : %d\n", i.getClosingDate());
            System.out.printf("    Description     : %s\n", i.getDescription());

            System.out.println(); // blank line between entries
        }
    }




>>>>>>> 071a7f7e66cc371b2eb40ec6247ad244aad11744
}