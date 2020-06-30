package todelete;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import project.mozgovanje.model.Question;

public class InsertQuestions {


    private FirebaseFirestore database;
    private CollectionReference questionsCollections;

    Context context;

    public static List<Question> questions = new ArrayList<Question>();
    private static String QUESTION_TEXT = "";
    private static String ANSWER_1 = "";
    private static String ANSWER_2 = "";
    private static String ANSWER_3 = "";
    private static String ANSWER_4 = "";
    private static String CORRECT = "";
    private String TAG = "MainActivity";

    public InsertQuestions(Context context) {
        this.context = context;
        database = FirebaseFirestore.getInstance();
        questionsCollections = database.collection("Questions");
    }

    private void putIntoFirestore() {
        for (final Question question : questions) {
            questionsCollections.add(question)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d(TAG, "onSuccess: " + question.getQuestionID());
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(TAG, "onFailure: " + question.getQuestionID());
                }
            });
        }
        questionsCollections.orderBy("correctAnswerChar");
    }

    private void readFromFile() throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(context.getAssets().open("opsta_informisanost.txt")));

        String line;

        int counter = 0;
        int questionID = 1;

        while ((line = br.readLine()) != null) {
            int position = ++counter % 7;

            switch (position) {
                case 1:
                    QUESTION_TEXT = line;
                    break;
                case 2:
                    ANSWER_1 = line;
                    break;
                case 3:
                    ANSWER_2 = line;
                    break;
                case 4:
                    ANSWER_3 = line;
                    break;
                case 5:
                    ANSWER_4 = line;
                    break;
                case 6:
                    CORRECT = line;
                    Question question = new Question(questionID++,
                            QUESTION_TEXT,
                            ANSWER_1,
                            ANSWER_2,
                            ANSWER_3,
                            ANSWER_4,
                            CORRECT);
                    questions.add(question);
                    resetFields();
                    break;
                default:
                    throw new Exception("Somethign went wrong...position " + position);
            }

            if (((counter + 1) % 7) == 0) counter++;
        }
        br.close();
        printList();


    }

    private void resetFields() {
        QUESTION_TEXT = "";
        ANSWER_1 = "";
        ANSWER_2 = "";
        ANSWER_3 = "";
        ANSWER_4 = "";
        CORRECT = "";
    }

    private void printList() {

        for (Question question : questions) {
            System.out.println(question.getQuestionText());
            System.out.println(question.getAnswer1());
            System.out.println(question.getAnswer2());
            System.out.println(question.getAnswer3());
            System.out.println(question.getAnswer4());
            System.out.println(question.getCorrectAnswerChar());
            System.out.println("============================");
        }

    }

    private void printFileInfo(File myObj) {
        if (myObj.exists()) {
            System.out.println("File name: " + myObj.getName());
            System.out.println("Absolute path: " + myObj.getAbsolutePath());
            System.out.println("Writeable: " + myObj.canWrite());
            System.out.println("Readable " + myObj.canRead());
            System.out.println("File size in bytes " + myObj.length());
        } else {
            System.out.println("The file does not exist.");
        }
    }

}
