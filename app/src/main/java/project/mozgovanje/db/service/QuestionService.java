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

import static project.mozgovanje.util.constants.Constants.FIRESTORE_PENDING_QUESTION_COLLECTION;
import static project.mozgovanje.util.constants.Constants.FIRESTORE_QUESTION_COLLECTION;

public class QuestionService {

    private String TAG = "QuestionService";

    private FirebaseFirestore database;
    private ArrayList<Question> questions;
    private ArrayList<Question> pendingQuestions;
    private int nextIndex;

    public QuestionService(FirebaseFirestore database) {
        this.database = database;
        read(FIRESTORE_QUESTION_COLLECTION);
        read(FIRESTORE_PENDING_QUESTION_COLLECTION);
    }

    public void create(final Question newQuestion, String collectionName, final Context context) {
        nextIndex = questions.size() + 1;
        newQuestion.setQuestionID(nextIndex);
        Toast.makeText(context, "NEXT INDEX : " + (nextIndex + 1), Toast.LENGTH_SHORT).show();

        database.collection(collectionName)
                .add(newQuestion)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot 'Question' added with ID: " + documentReference.getId());
                        Toast.makeText(context, "Uspesno sacuvano pitanje !", Toast.LENGTH_SHORT).show();
                        questions.add(newQuestion);
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

    private void read(final String collectionName) {

        initQuestionList(collectionName);

        database.collection(collectionName).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "questions SUCCESSFULLY loaded from " + collectionName);

                    for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                        Question question = document.toObject(Question.class);
                        if (collectionName.equals(FIRESTORE_QUESTION_COLLECTION))
                            questions.add(question);
                        else
                            pendingQuestions.add(question);
                    }
                } else {
                    Log.d(TAG, "TASK : questions FAILED to load from " + collectionName);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: " + e.getMessage());
            }
        });
        //TODO: SREDI LISTENER ZA REFRESHOVANJE PENDING PITNAJA
//        listener.onRefreshEnd();

    }

    public void delete(final Question question, final String collectionName) {
        database.collection(collectionName).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                        Question currentQuestion = document.toObject(Question.class);
                        if (currentQuestion.getQuestionText().equals(question.getQuestionText())) {
                            document.getReference().delete(); //OVO JEBENO RADI
                            break;
                        }
                    }
                } else {
                    Log.d(TAG, "TASK : questions FAILED to load from " + collectionName);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: " + e.getMessage());
            }
        });
    }

    public void update(final Question updatedQuestion, final String collectionName) {
        database.collection(collectionName).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                        Question currentQuestion = document.toObject(Question.class);
                        if (currentQuestion.getQuestionID() == updatedQuestion.getQuestionID()) {
                            document.getReference().set(updatedQuestion);
                            break;
                        }
                    }

                } else {
                    Log.d(TAG, "TASK : questions FAILED to load from " + collectionName);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: " + e.getMessage());
            }
        });
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public ArrayList<Question> getPendingQuestions() {
        return pendingQuestions;
    }

    public void reloadQuestions(String collectionName) {
        read(collectionName);
    }

    private void initQuestionList(String collectionName) {
        switch (collectionName) {
            case FIRESTORE_QUESTION_COLLECTION:
                questions = new ArrayList<>();
                break;
            case FIRESTORE_PENDING_QUESTION_COLLECTION:
                pendingQuestions = new ArrayList<>();
                break;
            default:
                break;
        }
    }


//    public void setPendingQuestionsListener(PendingQuestionsRefreshListener listener) {
//        this.listener = listener;
//    }

}
