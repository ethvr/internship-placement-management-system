package IPMS.System;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import IPMS.Enums.*;
import IPMS.System.SystemDataEntities.*;
import IPMS.ObjectClasses.*;

public class Filters {

    /** 
     * @return List<Internship>
     */
    public static List<Internship> getAllInternships() {
        List<Internship> list = new ArrayList<>();
        list.addAll(SystemData.getInternshipMap().values());
        return list;
    }

    /** 
     * @param word
     * @return List<Internship>
     */
    public static List<Internship> filterByKeyword(String word) {
        List<Internship> results = new ArrayList<>();

        for (Internship i : SystemData.getInternshipMap().values()) {
            if (i.getDescription().contains(word) && i.getVisibility() == true) {
                results.add(i);
            }
        }
        return results;
    }
    /** 
     * @param companyName
     * @return List<Internship>
     */
    //Returns a internship list filtered to a specific company
    // used for students
    public static List<Internship> filterByCompanyName(String companyName) {
        List<Internship> results = new ArrayList<>();

        for (Internship i : SystemData.getInternshipMap().values()) {
            if (i.getCompanyName().equalsIgnoreCase(companyName) && i.getVisibility() == true) {
                results.add(i);
            }
        }
        return results;
    }

    /** 
     * @param year
     * @return List<Internship>
     */
    public static List<Internship> filterByYearOfStudy(int year) {
        List<Internship> results = new ArrayList<>();

        for (Internship i : SystemData.getInternshipMap().values()) {

            if (!i.getVisibility() == true) continue;

            InternshipLevel level = i.getLevel();

            if (year <= 2 && year > 0) {
                if (level == InternshipLevel.BASIC) {
                    results.add(i);
                }
            } else {
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
            if (i.getPreferredMajor().equalsIgnoreCase(major) && i.getVisibility() == true) {
                results.add(i);
            }
        }
        return results;
    }


    /** 
     * @param level
     * @return List<Internship>
     */
    // used for students
    public static List<Internship> filterByInternshipLevel(InternshipLevel level) {
        List<Internship> results = new ArrayList<>();

        for (Internship i : SystemData.getInternshipMap().values()) {
            if (i.getLevel() == level && i.getVisibility() == true) {
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
            if (i.getLevel() == level && i.getVisibility() == true) {
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
            if (i.getCloseDate().isBefore(date) && i.getVisibility() == true) {
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
            if (i.getSlots() == slots && i.getVisibility() == true) {
                results.add(i);
            }
        }
        return results;
    }
}
