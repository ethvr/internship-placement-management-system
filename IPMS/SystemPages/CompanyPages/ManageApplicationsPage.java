package IPMS.SystemPages.CompanyPages;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import IPMS.SystemPages.PageUtilities.Page;
import IPMS.SystemPages.PageUtilities.PageAction;
import IPMS.SystemPages.PageUtilities.UniversalFunctions;
import IPMS.ObjectClasses.*;
import IPMS.System.Filters;
import IPMS.UserManagement.*;
import java.util.ArrayList;

public class ManageApplicationsPage implements Page {
    private final CompanyRepresentative obj;
    private List<Internship> filteredList;
    private HashMap <Integer, Internship> IindexMap;
    private HashMap <Integer, Application> AindexMap;
    private int lastindex;
    private CompanyController controller;

    public ManageApplicationsPage(CompanyRepresentative obj) {
        this.obj = obj;
        this.filteredList = new ArrayList<>();
        this.IindexMap = new HashMap<>(); 
        this.controller = new CompanyController(new Scanner(System.in));
    }

    public ManageApplicationsPage(CompanyRepresentative obj, List<Internship> filteredList) {
        this.obj = obj;
        this.filteredList = filteredList;
        this.IindexMap = new HashMap<>(); 
        this.controller = new CompanyController(new Scanner(System.in));
    }

    @Override
    public void showMenu() {

        filteredList = Filters.filterByCompanyName(obj.getCompanyName());

        IindexMap = UniversalFunctions.printInternshipListwithIndex(filteredList);

        lastindex = Collections.max(IindexMap.keySet());

        System.out.printf("[%d] Back", lastindex + 1);
        System.out.printf("\nSelect an Internship to View Applications or go back (1-%d): ", lastindex + 1);

    }

    /** 
     * @return PageAction
     */
    @Override
    public PageAction next() {
        int opt = UniversalFunctions.readIntInRange(1, lastindex + 1);

        

        if (opt == lastindex + 1) {
            return PageAction.pop();
        }
        else {
            Internship selectedInternship = IindexMap.get(opt);
            AindexMap = obj.viewApplications(selectedInternship);
            return controller.handleApplication(AindexMap, obj);
        }

    }
}