abstract class User {
    private String userId;
    private String name;
    private String password;
    private boolean loggedIn;
    
    public User(String userId, String name) {
        this.userId = userId;
        this.name = name;
        this.password = "password";
    }

    public boolean login(String userID, String pw) {
        return userID.equals(userID) && password.equals(pw);
    }

    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

    public void logout() {
        System.out.println(name + " logged out.");
    }
    public String getName() {
        return name;
    }   
    public String getUserId() {
        return userId;
    }
}