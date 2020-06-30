package project.mozgovanje.db;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import project.mozgovanje.credentials.LoginCredentials;
import project.mozgovanje.db.keeper.collection.UserCollectionKeeper;
import project.mozgovanje.model.api.UserAPI;
import project.mozgovanje.validator.FieldValidator;

public class LoginManager {

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    public LoginManager() {
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void login(LoginCredentials loginCredentials) {
        String email = loginCredentials.getEmail().trim();
        String password = loginCredentials.getPassword().trim();

        if (FieldValidator.validate(email, password)) {
            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            assert user != null;
                            final String userID = user.getUid();

                            UserCollectionKeeper.getInstance().getUserCollection().whereEqualTo("userID", userID)
                                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                        @Override
                                        public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                                            if(e != null){
                                                throw new RuntimeException(e.getMessage());
                                            }
                                            assert queryDocumentSnapshots != null;
                                            if(!queryDocumentSnapshots.isEmpty()){

                                                for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots){
                                                    UserAPI.getInstance().setUserID(userID);
                                                    UserAPI.getInstance().setUsername(snapshot.getString("username"));
                                                }

                                            }
                                        }
                                    });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    throw new RuntimeException("No user with those credentials !");
                }
            });
        } else {
            throw new RuntimeException("Please fill in all fields.");
            //TODO: Implementirati sopstvenu Exception klasu pod imenom EmptyFieldsException
        }


    }
}
