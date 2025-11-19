// main app class 
// runs main
// include login and logout functions to dictate current user 
package IPMS.System;

import java.util.*;
import IPMS.System.SystemDataEntities.*;
import IPMS.SystemPages.CommonPages.MainPage;
import IPMS.SystemPages.PageUtilities.Page;
import IPMS.SystemPages.PageUtilities.PageAction;
import IPMS.UserManagement.UserManager;
import IPMS.System.SystemData.*;
import IPMS.ObjectClasses.User;
import java.io.*;

public class SystemApp {

    public static String currentUser = null;
    static Scanner sc = new Scanner(System.in);

    /** 
     * @param args
     */
    public static void main(String[] args) {

        // things to run before the start of the code 
        UserManager.UsernameCSVGenerator();
        SystemData.loadIntoMap("password", Credentials.class);
        SystemData.loadAll("company");

        Stack<Page> nav = new Stack<>();
        nav.push(new MainPage());   // Start at the main menu

        // --- Main Loop ---
        while (!nav.isEmpty()) {

            Page current = nav.peek();   // Top of stack = current page

            current.showMenu();          // Display page UI
            PageAction action = current.next();  // Process user input

            // Handle page navigation
            switch (action.gettype()) {
                case PUSH -> nav.push(action.getnextPage());
                case POP  -> nav.pop();
                case EXIT -> nav.clear();  // Clear stack = exit program
                case STAY -> nav.peek(); //???? need for stay?
            }
        }
        System.out.print("sng001 pw: ");
        System.out.println(SystemData.LoginMap.get("sng001").Password);
        System.out.println(SystemData.RepresentativeMap);
        System.out.println(SystemData.RepresentativeCSVMap);
        
    }       

    /** 
     * @param user
     */
    public static void setCurrentUser(String user) {
        currentUser = user;
    }       

    /** 
     * @param username
     */
    //username from user login and creates object?
    // protected or public or private?
    public static void login(String username) {
        currentUser = username;
    }




    

    
}
