// package IPMS.OldFunctions;
// import java.util.List;

// import IPMS.System.SystemDataEntities.*;
// import IPMS.ObjectClasses.*;
// import IPMS.System.Filters;
// import IPMS.SystemPages.CommonPages.FilteredInternshipsPage;
// import IPMS.SystemPages.PageUtilities.Page;
// import IPMS.SystemPages.PageUtilities.PageAction;
// import IPMS.SystemPages.PageUtilities.UniversalFunctions;


// public class ViewInternshipsPage implements Page {
//     private final Student Obj;
//     private int opt;

//     public ViewInternshipsPage(Student obj) {
//         Obj = obj;
//     }

//     @Override
//     public void showMenu() {
//         System.out.println("======= INTERNSHIPS AVAILABLE =======");
//         System.out.println("Filter internships by: ");
//         System.out.println("[1] Internship Levels");
//         System.out.println("[2] Closing Date");
//         System.out.println("[3] Company Name");
//         System.out.println("[4] Number of Slots left\n");
//         System.out.println("[5] Key words"); //filter by searching for keywords in description
//         System.out.println("[6] Back");
//         System.out.println("[7] Logout");

//         System.out.print("Enter an option (1-7): ");;
//     }

//     @Override
//     public PageAction next() {

//         String major = Obj.getMajor();
//         int YearOfStudy = Obj.getYearOfStudy();
//         List<InternshipData> InternshipList = Filters.filterByYearOfStudy(YearOfStudy);
//         List<InternshipData> InternshipList2;
//         opt = UniversalFunctions.readIntInRange(1, 7);

//         switch (opt) {
//             case 1 -> {
//                 PageAction.push(new FilteredInternshipsPage(Obj, InternshipList, "level"));
//             }
//             case 2 -> {
//                 PageAction.push(new FilteredInternshipsPage(Obj, InternshipList, "date"));
//             }
//             case 3 -> {
//                 PageAction.push(new FilteredInternshipsPage(Obj, InternshipList, "name"));
//             }
//             case 4 -> {
//                 PageAction.push(new FilteredInternshipsPage(Obj, InternshipList, "slots"));
//             }
//             case 5 -> {
//                 PageAction.push(new FilteredInternshipsPage(Obj, InternshipList, "words"));
//             }
//             case 6 -> {
//                 PageAction.pop();
//             }
//             case 7 -> {
//                 Obj.logout();
//                 yield PageAction.exit();
//             }
//             default -> {
//                 PageAction.pop();
//             }
//         }
//     }
// }

