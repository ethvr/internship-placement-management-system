package UserManagement;

public class IdGenerator {

     private static int internshipCounter = 0;
     private static int applicationCounter = 0;
     private static int withdrawalCounter = 0; 
     private static int CompanyRepCounter = 0;

     
     public static String nextInternshipId() {
          internshipCounter++;
          return String.format("I%04d", internshipCounter);
     }
     
     public static String nextAppId() {
          applicationCounter++;
          return String.format("A%04d", applicationCounter);
     }
     
     public static String nextWithdrawalId() {
          withdrawalCounter++;
          return String.format("W%04d", withdrawalCounter);
     }

     public static String nextCompanyId() {
          CompanyRepCounter++;
          return String.format("C%04d", withdrawalCounter);
     }
     
}
