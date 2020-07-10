package project.mozgovanje.db.service;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

import project.mozgovanje.model.question.Question;
import project.mozgovanje.model.user.User;

//TODO: DELETE !!!
public class UserService {

    private String TAG = "UserService";

    private FirebaseFirestore database;
    private ArrayList<User> users;

    public UserService(FirebaseFirestore database) {
        this.database = database;
        loadUsers();
    }

    private void loadUsers() {
        users = new ArrayList<>();
        database.collection("Users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "Questions SUCCESSFULLY loaded from firestore");
                    for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                        User user = document.toObject(User.class);
                        users.add(user);
                    }
                } else {
                    Log.d(TAG, "TASK : Questions FAILED to load from firestore");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: " + e.getMessage());
            }
        });
    }

    public ArrayList<User> getUsers() {
        return users;
    }
}
