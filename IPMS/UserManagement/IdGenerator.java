package IPMS.UserManagement;

public class IdGenerator {

     private static int internshipCounter = 0;
     private static int applicationCounter = 0;
     private static int withdrawalCounter = 0; 

     
     /** 
      * @return String
      */
     public static String nextInternshipId() {
          internshipCounter++;
          return String.format("I%04d", internshipCounter);
     }
     
     /** 
      * @return String
      */
     public static String nextAppId() {
          applicationCounter++;
          return String.format("A%04d", applicationCounter);
     }
     
     /** 
      * @return String
      */
     public static String nextWithdrawalId() {
          withdrawalCounter++;
          return String.format("W%04d", withdrawalCounter);
     }

}
