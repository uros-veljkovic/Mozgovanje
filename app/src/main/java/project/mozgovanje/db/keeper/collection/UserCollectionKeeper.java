package project.mozgovanje.db.keeper.collection;

import com.google.firebase.firestore.CollectionReference;

import project.mozgovanje.db.keeper.FirestoreKeeper;

public class UserCollectionKeeper {

    public static UserCollectionKeeper instance;
    private CollectionReference userCollection;

    private UserCollectionKeeper() {
        userCollection = FirestoreKeeper.getInstance().getUsercollection();
    }

    public static UserCollectionKeeper getInstance() {
        if (instance == null) {
            instance = new UserCollectionKeeper();
        }
        return instance;
    }

    public CollectionReference getUserCollection(){
        return userCollection;
    }
}
