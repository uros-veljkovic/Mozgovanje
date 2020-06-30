package project.mozgovanje.db;

import android.content.Context;
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

import project.mozgovanje.activity.auth.createaccount.CreateAccountActivity;
import project.mozgovanje.credentials.CreateAccountCredentials;
import project.mozgovanje.db.controller.DatabaseController;
import project.mozgovanje.db.keeper.collection.UserCollectionKeeper;
import project.mozgovanje.model.User;
import project.mozgovanje.model.api.UserAPI;
import project.mozgovanje.validator.FieldValidator;

public class CreateAccountManager {

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser currentUser;

    public CreateAccountManager() {
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void crateAccount(final CreateAccountCredentials credentials) throws Exception {
        if(FieldValidator.validate(credentials)){
            
            String username = credentials.getUsername();
            String email = credentials.getEmail();
            String password = credentials.getPassword();
            
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                addUserToCollection(credentials);
                            }else{
                                makeToast("Task for creating user not completed !");
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    makeToast("Failed to create user !");
                }
            });
        }else{
            makeToast("Please fill in all fields !");
        }
    }

    private void addUserToCollection(CreateAccountCredentials credentials) {
        currentUser = firebaseAuth.getCurrentUser();
        assert currentUser != null;
        final String currentUserID = currentUser.getUid();
        final String email = credentials.getEmail();
        final String username = credentials.getUsername();
        final User user = new User(currentUserID, username, email);

        UserCollectionKeeper.getInstance().getUserCollection().add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if(Objects.requireNonNull(task.getResult()).exists()){
                                    UserAPI.getInstance().setUsername(task.getResult().getString("username"));
                                    UserAPI.getInstance().setUserID(currentUserID);
                                    UserAPI.getInstance().setEmail(email);
                                }

                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                makeToast("Failed to add user to collection !");
            }
        });

    }

    private void makeToast(String message) {
        Context context = DatabaseController.getInstance().getContext();
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
