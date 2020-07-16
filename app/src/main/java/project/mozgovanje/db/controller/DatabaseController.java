package project.mozgovanje.db.controller;

import android.content.Context;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import project.mozgovanje.db.service.LogoutService;
import project.mozgovanje.db.service.QuestionService;
import project.mozgovanje.db.service.ScoreboardService;
import project.mozgovanje.model.credentials.CreateAccountCredentials;
import project.mozgovanje.model.credentials.LoginCredentials;
import project.mozgovanje.db.service.CreateAccountService;
import project.mozgovanje.db.service.LoginService;
import project.mozgovanje.model.score.Score;
import project.mozgovanje.util.constants.Constants;
import project.mozgovanje.util.exception.FieldsEmptyException;
import project.mozgovanje.model.question.Question;
import project.mozgovanje.util.observer.PendingQuestionsRefreshListener;

import static project.mozgovanje.util.constants.Constants.FIRESTORE_PENDING_QUESTION_COLLECTION;
import static project.mozgovanje.util.constants.Constants.FIRESTORE_QUESTION_COLLECTION;

public class DatabaseController {

    private static final DatabaseController instance = new DatabaseController();

    private FirebaseFirestore database;
    private LoginService loginService;
    private LogoutService logoutService;
    private CreateAccountService createAccountService;
    private QuestionService questionService;
    private ScoreboardService scoreboardService;


    private DatabaseController() {
        database = FirebaseFirestore.getInstance();
        loginService = new LoginService(database);
        createAccountService = new CreateAccountService(database);
        questionService = new QuestionService(database);
        scoreboardService = new ScoreboardService(database);
    }

    public static DatabaseController getInstance() {
        return instance;
    }

    public void login(Context context, LoginCredentials credentials) throws FieldsEmptyException {
        loginService.login(context, credentials);
        logoutService = new LogoutService(
                loginService.getFirebaseAuth(),
                loginService.getCurrentUser()
        );
    }

    public void logout(Context context) {
        logoutService.logout(context);
    }

    public void createAccount(Context context, CreateAccountCredentials credentials) throws FieldsEmptyException {
        createAccountService.crateAccount(context, credentials);
        logoutService = new LogoutService(
                createAccountService.getFirebaseAuth(),
                createAccountService.getCurrentUser()
        );
    }

    public void createScore(Score score) {
        scoreboardService.createNew(score);
    }

    public void createPendingQuestion(Context context, Question question) {
        questionService.create(question, FIRESTORE_PENDING_QUESTION_COLLECTION, context);
    }

    public void createQuestion(Context context, Question question) {
        questionService.create(question, FIRESTORE_QUESTION_COLLECTION, context);
    }

    public void refreshQuestions() {
        questionService.refreshQuestions(FIRESTORE_QUESTION_COLLECTION);
    }

    public void refreshPendingQuestions() {
        questionService.refreshQuestions(FIRESTORE_PENDING_QUESTION_COLLECTION);
    }

    public ArrayList<Question> getQuestions() {
        return questionService.getQuestions();
    }

    public ArrayList<Question> getPendingQuestions() {
        return questionService.getPendingQuestions();
    }

    public ArrayList<Score> getZenScoreboard() {
        return scoreboardService.getZenScoreboard();
    }

    public ArrayList<Score> getTestScoreboard() {
        return scoreboardService.getTestScoreboard();
    }

    public ArrayList<Score> getGeekScoreboard() {
        return scoreboardService.getGeekScoreboard();
    }

    public void refreshScoreboards() {
        scoreboardService.refreshScoreboards();
    }

    public void deletePendingQuestion(Question question) {
        questionService.delete(question, FIRESTORE_PENDING_QUESTION_COLLECTION);
    }

    public void deleteQuestion(Question question) {
        questionService.delete(question, FIRESTORE_QUESTION_COLLECTION);
    }

    public void updatePendingQuestion(Question question){
        questionService.update(question, FIRESTORE_PENDING_QUESTION_COLLECTION);
    }

    public void updateQuestion(Question question){
        questionService.update(question, FIRESTORE_QUESTION_COLLECTION);
    }

}
