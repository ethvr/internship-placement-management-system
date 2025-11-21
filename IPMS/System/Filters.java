package IPMS.System;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import IPMS.Enums.*;
import IPMS.ObjectClasses.*;

public class Filters {

    /** 
     * @return List<Internship>
     */
    // final
    public static List<Internship> getAllInternships() {
        List<Internship> list = new ArrayList<>();
        list.addAll(SystemData.getInternshipMap().values());
        return list;
    }

    /** 
     * @param companyName
     * @return List<Internship>
     */
    // Returns a internship list filtered to a specific company
    // used for students
    // final version
    public static List<Internship> filterByCompanyName(String companyName) {

        List<Internship> results = new ArrayList<>();

        for (Internship i : SystemData.getInternshipMap().values()) {
            if (i.getCompanyName().equalsIgnoreCase(companyName)) {
                results.add(i);
            }
        }
        
        return results;
    }

    /** 
     * @param year
     * @return List<Internship>
     */
    // final
    public static List<Internship> filterByYearOfStudy(int studentYear) {
        List<Internship> results = new ArrayList<>();

        for (Internship i : SystemData.getInternshipMap().values()) {

            // student only sees visible internships
            if (!i.getVisibility()) continue;

            // year 1–2 → BASIC only
            if (studentYear <= 2) {
                if (i.getLevel() == InternshipLevel.BASIC) {
                    results.add(i);
                }
                continue;
            }

            // year 3–4 → NON-BASIC (Intermediate / Advanced)
            if (studentYear >= 3) {
                if (i.getLevel() != InternshipLevel.BASIC) {
                    results.add(i);
                }
            }
        }

        return results;
    }


    /** 
     * @param word
     * @return List<Internship>
     */
    public static List<Internship> filterByKeyword(String word) {
        List<Internship> results = new ArrayList<>();
        for (Internship i : SystemData.getInternshipMap().values()) {
            if (i.getDescription().equalsIgnoreCase(word)) {
                results.add(i);
            }
        }
        return results;
    }

    /** 
     * @param major
     * @return List<Internship>
     */
    public static List<Internship> filterByMajor(String major) {
        List<Internship> results = new ArrayList<>();
        for (Internship i : SystemData.getInternshipMap().values()) {
            if (i.getPreferredMajor().equalsIgnoreCase(major)) {
                results.add(i);
            }
        }
        return results;
    }

    /** 
     * @param level
     * @param list
     * @return List<Internship>
     */
    public static List<Internship> filterByInternshipLevel(InternshipLevel level, List<Internship> list) {
        List<Internship> results = new ArrayList<>();
        for (Internship i : list) {
            if (i.getLevel() == level) {
                results.add(i);
            }
        } 
        return results;
    }

    /** 
     * @param date
     * @return List<Internship>
     */
    // used for students
    public static List<Internship> filterByClosingDate(LocalDate date) {
        List<Internship> results = new ArrayList<>();
        for (Internship i : SystemData.getInternshipMap().values()) {
            if (i.getCloseDate().isBefore(date)) {
                results.add(i);
            }
        }
        return results;
    }

    /** 
     * @param slots
     * @return List<Internship>
     */
    // used for students
    public static List<Internship> filterBySlotsLeft(int slots) {
        List<Internship> results = new ArrayList<>();
        for (Internship i : SystemData.getInternshipMap().values()) {
            if (i.getSlots() == slots) {
                results.add(i);
            }
        }
        return results;
    }
}
