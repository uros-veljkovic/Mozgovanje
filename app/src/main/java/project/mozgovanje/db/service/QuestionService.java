package project.mozgovanje.db.service;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

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

import project.mozgovanje.model.question.Question;
import project.mozgovanje.util.constants.Constants;

import static project.mozgovanje.util.constants.Constants.FIRESTORE_PENDING_QUESTION_COLLECTION;
import static project.mozgovanje.util.constants.Constants.FIRESTORE_QUESTION_COLLECTION;

public class QuestionService {

    private String TAG = "QuestionService";

    private FirebaseFirestore database;
    private ArrayList<Question> questions;
    private ArrayList<Question> pendingQuestions;

    public QuestionService(FirebaseFirestore database) {
        this.database = database;
        readQuestions();
    }

    public void createQuestion(final Context context, Question question) {
        database.collection(FIRESTORE_QUESTION_COLLECTION)
                .add(question)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot 'Question' added with ID: " + documentReference.getId());
                        Toast.makeText(context, "Uspesno sacuvano pitanje !", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                        Toast.makeText(context, "Neuspesno sacuvano pitanje...", Toast.LENGTH_SHORT).show();

                    }
                });
    }

    public void createPendingQuestion(final Context context, Question question) {
        database.collection(FIRESTORE_PENDING_QUESTION_COLLECTION)
                .add(question)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot 'Pending question' added with ID: " + documentReference.getId());
                        Toast.makeText(context, "Hvala Vam na doprinosu ! :)", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                        Toast.makeText(context, "Greska prilikom cuvanja pitanja...", Toast.LENGTH_SHORT).show();

                    }
                });

    }

    private void readQuestions() {
        questions = new ArrayList<>();
        if (questions.isEmpty()) {
            database.collection(FIRESTORE_QUESTION_COLLECTION)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "Questions SUCCESSFULLY loaded from firestore");
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                            Question question = document.toObject(Question.class);
                            questions.add(question);
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
        } else {
            Log.d(TAG, "Number of questions : " + questions.size());
        }
    }

    private void readPendingQuestions() {
        pendingQuestions = new ArrayList<>();
        if (pendingQuestions.isEmpty()) {
            database.collection(FIRESTORE_PENDING_QUESTION_COLLECTION).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "Pending questions SUCCESSFULLY loaded from firestore");
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                            Question question = document.toObject(Question.class);
                            pendingQuestions.add(question);
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
        } else {
            Log.d(TAG, "Number of questions : " + pendingQuestions.size());
        }
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }
}
