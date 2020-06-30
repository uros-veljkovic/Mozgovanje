package project.mozgovanje.db.controller;

import android.content.Context;

import com.google.firebase.firestore.FirebaseFirestore;

import project.mozgovanje.credentials.CreateAccountCredentials;
import project.mozgovanje.credentials.LoginCredentials;
import project.mozgovanje.db.CreateAccountManager;
import project.mozgovanje.db.LoginManager;
import project.mozgovanje.db.keeper.FirestoreKeeper;

public class DatabaseController {

    private static DatabaseController instance;
    private Context context;
    private LoginManager loginManager;
    private CreateAccountManager createAccountManager;

    private DatabaseController() {
        loginManager = new LoginManager();
        createAccountManager = new CreateAccountManager();
    }

    public static DatabaseController getInstance() {
        if (instance == null)
            instance = new DatabaseController();
        return instance;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public void login(LoginCredentials credentials) {
        loginManager.login(credentials);
    }

    public void createAccount(CreateAccountCredentials credentials) throws Exception {
        createAccountManager.crateAccount(credentials);
    }
}
