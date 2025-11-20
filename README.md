INSTRUCTIONS TO RUN THE APP

1. Go into IPMS folder 
2. go into System folder
3. go into SystemData folder, under the loadIntoMap and writeBackCSV functions
4. change the file paths of the respective folders to that of your desktop
5. go back into the IPMS folder and find the Usermanagement folder
6. Find the UsernameCSVGenerator function under UserManager and change the file paths to that of the desktop
7. Go into the SystemApp file inside the System folder
8. run main



PART OF CODE TO CHANGE 

IPMS --> UserManagement --> UserManager

File outputFile  = new File("C:\\Users\\luther tang\\Desktop\\VSC files\\Java\\IPMS MAIN2\\IPMS\\PasswordCSVFolder\\usernames_and_passwords.csv");
File inputFolder = new File("C:\\Users\\luther tang\\Desktop\\VSC files\\Java\\IPMS MAIN2\\IPMS\\PeopleCSVFolder");




IPMS --> System --> SystemData ( 1 under writebackCSV func and 1 under loadIntoMap func)

File PasswordFolder = new File("C:\\Users\\luther tang\\Desktop\\VSC files\\Java\\IPMS MAIN2\\IPMS\\PasswordCSVFolder");
File OtherFolder = new File("C:\\Users\\luther tang\\Desktop\\VSC files\\Java\\IPMS MAIN2\\IPMS\\OtherCSVFolder");
File PeopleFolder = new File("C:\\Users\\luther tang\\Desktop\\VSC files\\Java\\IPMS MAIN2\\IPMS\\PeopleCSVFolder");


File PasswordFolder = new File("C:\\Users\\luther tang\\Desktop\\VSC files\\Java\\IPMS MAIN2\\IPMS\\PasswordCSVFolder");
File OtherFolder = new File("C:\\Users\\luther tang\\Desktop\\VSC files\\Java\\IPMS MAIN2\\IPMS\\OtherCSVFolder");
File PeopleFolder = new File("C:\\Users\\luther tang\\Desktop\\VSC files\\Java\\IPMS MAIN2\\IPMS\\PeopleCSVFolder");
