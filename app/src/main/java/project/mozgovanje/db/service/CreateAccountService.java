package project.mozgovanje.db.service;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

import project.mozgovanje.activity.welcome.WelcomeActivity;
import project.mozgovanje.util.exception.FieldsEmptyException;
import project.mozgovanje.model.credentials.CreateAccountCredentials;
import project.mozgovanje.model.user.User;
import project.mozgovanje.model.api.UserAPI;
import project.mozgovanje.util.validator.FieldValidator;

public class CreateAccountService {

    public static final String TAG = "===== CreateAccountService =====";

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseFirestore database;
    private FirebaseUser currentUser;

    public CreateAccountService(FirebaseFirestore db) {
        this.database = db;
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void crateAccount(final Context context, final CreateAccountCredentials credentials) throws FieldsEmptyException {
        FieldValidator.validate(credentials);

        String email = credentials.getEmail();
        String password = credentials.getPassword();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(context, "Uspesno kreiranje profila !", Toast.LENGTH_SHORT).show();
                            initUserAPI(credentials);
                            addUserToCollection(credentials);
                            startWelcomeActivity(context);
                        } else {
                            Toast.makeText(context, "Neuspesno kreiranje profila...", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: " + e.getMessage());
            }
        });
    }

    private void initUserAPI(CreateAccountCredentials credentials) {
        currentUser = firebaseAuth.getCurrentUser();

        UserAPI.getInstance().setUsername(credentials.getUsername());
        UserAPI.getInstance().setUserID(currentUser.getUid());
        UserAPI.getInstance().setEmail(credentials.getEmail());
    }

    private void addUserToCollection(CreateAccountCredentials credentials) {
        final String currentUserID = currentUser.getUid();
        final String email = credentials.getEmail();
        final String username = credentials.getUsername();

        final User user = new User(currentUserID, username, email);

        database.collection("Users").add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (Objects.requireNonNull(task.getResult()).exists()) {
                                    Log.d(TAG, "onComplete: " + UserAPI.getInstance().getEmail());
                                    Log.d(TAG, "onComplete: " + UserAPI.getInstance().getUsername());
                                    Log.d(TAG, "onComplete: " + UserAPI.getInstance().getUserID());
                                }
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: Failed to add user to collection !");
            }
        });
    }

    private void startWelcomeActivity(Context context) {
        context.startActivity(new Intent(context, WelcomeActivity.class));
        ((Activity) context).finish();
    }


}
