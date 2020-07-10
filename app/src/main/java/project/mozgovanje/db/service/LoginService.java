package project.mozgovanje.db.service;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import project.mozgovanje.activity.auth.login.LoginActivity;
import project.mozgovanje.activity.welcome.WelcomeActivity;
import project.mozgovanje.model.credentials.LoginCredentials;
import project.mozgovanje.util.exception.FieldsEmptyException;
import project.mozgovanje.model.api.UserAPI;
import project.mozgovanje.util.validator.FieldValidator;

public class LoginService {

    public static final String TAG = "LoginManager";
    private FirebaseFirestore database;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;


    public LoginService(FirebaseFirestore db) {
        this.database = db;
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void login(final Context context, LoginCredentials loginCredentials) throws FieldsEmptyException {

        FieldValidator.validate(loginCredentials);

        String email = loginCredentials.getEmail().trim();
        String password = loginCredentials.getPassword().trim();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(context, "Uspesna prijava !", Toast.LENGTH_SHORT).show();

                            iniUserAPI();
                            startWelcomeActivity(context);
                        } else {
                            Toast.makeText(context, "Neuspesna prijava...", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void logout(final Context context){
        if(currentUser != null && firebaseAuth != null){
            firebaseAuth.signOut();
            context.startActivity(new Intent(context, LoginActivity.class));
            Toast.makeText(context, "Uspesna odjava !", Toast.LENGTH_SHORT).show();
            ((Activity)context).finish();
        }
    }

    private void startWelcomeActivity(Context context) {
        context.startActivity(new Intent(context, WelcomeActivity.class));
        ((Activity) context).finish();
    }

    private void iniUserAPI() {

        currentUser = firebaseAuth.getCurrentUser();
        assert currentUser != null;
        final String userID = currentUser.getUid();

        database.collection("Users").whereEqualTo("userID", userID)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.d(TAG, "addSnapshotListener onEvent: GRESKA PRI DODAVANJA USERA U KOLEKCIJU");
                        }
                        if (queryDocumentSnapshots != null && !queryDocumentSnapshots.isEmpty()) {
                            for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots) {
                                UserAPI.getInstance().setUserID(userID);
                                UserAPI.getInstance().setUsername(snapshot.getString("username"));
                            }
                        }
                    }
                });
    }

}
