package IPMS.SystemPages.PageUtilities;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import IPMS.ObjectClasses.*;

public class UniversalFunctions {

    static Scanner sc = new Scanner(System.in);

    /** 
     * @param min
     * @param max
     * @return int
     */
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

    /** 
     * @return String
     */
    public static String readString() {

        sc.nextLine(); // clear leftover newline if needed
        String input = sc.nextLine().trim();

        while (input.isEmpty()) {
            System.out.println("Input cannot be empty. Try again.");
            input = sc.nextLine().trim();
        }

        return input;
    }

    public static String readStringAllowEmpty() {
        sc.nextLine(); // clear leftover newline if needed
        return sc.nextLine();   // <-- do NOT trim or force a re-entry
    }

    public static boolean isValidEmail(String email) {
        if (email == null) return false;
        email = email.trim();
        // Basic checks: contains one '@', not at start/end, no spaces
        return email.contains("@")
            && !email.startsWith("@")
            && !email.endsWith("@")
            && email.indexOf('@') == email.lastIndexOf('@')
            && !email.contains(" ");
    }

    public static String readStringEmail() {

        sc.nextLine(); // clear leftover newline if needed
        String input = sc.nextLine().trim();

        if (input.equalsIgnoreCase("exit")) {
            return input;
        }

        while (input.isEmpty() || !isValidEmail(input)) {
            System.out.print("Invalid email format. Enter your email again:");
            input = sc.nextLine().trim();
        }

        return input;
    }
 

    public static LocalDate readValidDate() {
        while (true) {

            String input = sc.nextLine();

            // 1. Validate basic YYYY-MM-DD structure
            if (!input.matches("\\d{4}-\\d{2}-\\d{2}")) {
                System.out.println("Invalid format! Use YYYY-MM-DD (e.g., 2025-03-21).\n");
                System.out.print("Please enter date again: ");
                continue;
            }

            // 2. Split into year, month, day
            String[] parts = input.split("-");
            int year  = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);
            int day   = Integer.parseInt(parts[2]);

            // 3. Year range check
            if (year < 1900 || year > 2100) {
                System.out.println("Year must be between 1900 and 2100.\n");
                System.out.print("Please enter date again: ");
                continue;
            }

            // 4. Month check
            if (month < 1 || month > 12) {
                System.out.println("Invalid month! Must be 01–12.\n");
                System.out.print("Please enter date again: ");
                continue;
            }

            // 5. Determine max valid day
            int maxDay = switch (month) {
                case 1,3,5,7,8,10,12 -> 31;
                case 4,6,9,11 -> 30;
                case 2 -> isLeapYear(year) ? 29 : 28;
                default -> 0; // unreachable
            };

            // 6. Day check
            if (day < 1 || day > maxDay) {
                System.out.println("Invalid day! Month/Year: " + month + "/" + year + " only has " + maxDay + " days.\n");
                continue;
            }

            // 7. All checks passed → create LocalDate
            return LocalDate.of(year, month, day);
        }
    }

    private static boolean isLeapYear(int year) {
        return (year % 400 == 0) || (year % 4 == 0 && year % 100 != 0);
    }



    /** 
     * @param enumType
     * @return T
     */
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

    /** 
     * @param list
     */
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