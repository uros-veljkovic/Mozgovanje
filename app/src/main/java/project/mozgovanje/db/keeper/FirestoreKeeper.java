package project.mozgovanje.db.keeper;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirestoreKeeper {

    private static FirestoreKeeper instance;
    private FirebaseFirestore database;

    private FirestoreKeeper() {
        database = FirebaseFirestore.getInstance();
    }

    public static FirestoreKeeper getInstance() {
        if (instance == null) {
            instance = new FirestoreKeeper();
        }
        return instance;
    }

    public CollectionReference getUsercollection() {
        return database.collection("Users");
    }

    public FirebaseFirestore getDatabase() {
        return database;
    }
}
