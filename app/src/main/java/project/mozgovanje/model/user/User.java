package project.mozgovanje.model.user;

public class User {

    private String userID;
    private String username;
    private String email;

    public User(String userID, String username, String email) {
        this.userID = userID;
        this.username = username;
        this.email = email;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserID() {
        return userID;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}
