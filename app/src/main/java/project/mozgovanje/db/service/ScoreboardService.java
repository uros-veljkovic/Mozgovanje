package project.mozgovanje.db.service;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

import project.mozgovanje.model.score.Score;

import static project.mozgovanje.util.constants.Constants.FIRESTORE_GEEK_SCOREBOARD_COLLECTION;
import static project.mozgovanje.util.constants.Constants.FIRESTORE_TEST_SCOREBOARD_COLLECTION;
import static project.mozgovanje.util.constants.Constants.FIRESTORE_ZEN_SCOREBOARD_COLLECTION;
import static project.mozgovanje.util.constants.Constants.GEEK_MODE;
import static project.mozgovanje.util.constants.Constants.TEST_MODE;
import static project.mozgovanje.util.constants.Constants.ZEN_MODE;

public class ScoreboardService {

    private String TAG = "ScoreboardService";
    private FirebaseFirestore database;

    private ArrayList<Score> geekScoreboard;
    private ArrayList<Score> zenScoreboard;
    private ArrayList<Score> testScoreboard;

    public ScoreboardService(FirebaseFirestore database) {
        this.database = database;
        refreshAll();
    }

    public void refreshAll() {
        refreshScoreboardForMode(ZEN_MODE);
        refreshScoreboardForMode(GEEK_MODE);
        refreshScoreboardForMode(TEST_MODE);
/*        geekScoreboard = new ArrayList<>();
        zenScoreboard = new ArrayList<>();
        testScoreboard = new ArrayList<>();
        loadAll();*/
    }

    public void refreshScoreboardForMode(int quizMode) {
        switch (quizMode){
            case ZEN_MODE :
                zenScoreboard = new ArrayList<>();
                load(zenScoreboard, FIRESTORE_ZEN_SCOREBOARD_COLLECTION);
                break;
            case TEST_MODE :
                testScoreboard = new ArrayList<>();
                load(testScoreboard, FIRESTORE_TEST_SCOREBOARD_COLLECTION);
                break;
            case GEEK_MODE :
                geekScoreboard = new ArrayList<>();
                load(geekScoreboard, FIRESTORE_GEEK_SCOREBOARD_COLLECTION);
                break;
            default:
                Log.d(TAG, "refreshScoreboardForMode: Something went wrong...");
                break;
        }
    }

/*    private void loadAll() {
        loadOne(geekScoreboard, FIRESTORE_GEEK_SCOREBOARD_COLLECTION);
        loadOne(testScoreboard, FIRESTORE_TEST_SCOREBOARD_COLLECTION);
        loadOne(zenScoreboard, FIRESTORE_ZEN_SCOREBOARD_COLLECTION);
    }*/

    private void load(final ArrayList<Score> scoreboard, final String scoreboardName) {
        database.collection(scoreboardName).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "Scoreboard " + scoreboardName + "SUCCESSFULLY loaded from firestore");
                    for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                        Score score = document.toObject(Score.class);
                        scoreboard.add(score);
                    }
                } else {
                    Log.d(TAG, "Task for loading " + scoreboardName + " FAILED !");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: " + scoreboardName + "\n" + e.getMessage());
            }
        });
    }

    public void create(final Score score) {
        database.collection(score.getInScoreboard())
                .add(score)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "Score added to " + score.getInScoreboard() + " with ID: " + documentReference.getId());

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding score to " + score.getInScoreboard(), e);
                    }
                });
    }

    public ArrayList<Score> getGeekScoreboard() {
        return geekScoreboard;
    }

    public ArrayList<Score> getTestScoreboard() {
        return testScoreboard;
    }

    public ArrayList<Score> getZenScoreboard() {
        return zenScoreboard;
    }

}
