package project.mozgovanje.model.api;

import com.google.firebase.auth.FirebaseUser;

public class UserAPI {

    private static UserAPI instance;

    private String username;
    private String email;
    private String userID;

    private UserAPI() {
        this.userID = "";
        this.email = "";
        this.username = "";
    }

    public static UserAPI getInstance() {
        if (instance == null)
            instance = new UserAPI();
        return instance;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }


}
