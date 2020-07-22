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
import project.mozgovanje.util.exception.FieldsEmptyException;
import project.mozgovanje.model.question.Question;

import static project.mozgovanje.util.constants.Constants.FIRESTORE_PENDING_QUESTION_COLLECTION;
import static project.mozgovanje.util.constants.Constants.FIRESTORE_QUESTION_COLLECTION;

public class RepositoryController {

    private static final RepositoryController instance = new RepositoryController();

    private FirebaseFirestore database;
    private LoginService loginService;
    private CreateAccountService createAccountService;
    private LogoutService logoutService;
    private QuestionService questionService;
    private ScoreboardService scoreboardService;

    private RepositoryController() {
        database = FirebaseFirestore.getInstance();
        questionService = new QuestionService(database);
        scoreboardService = new ScoreboardService(database);
    }

    public static RepositoryController getInstance() {
        return instance;
    }

    public void login(Context context, LoginCredentials credentials) throws FieldsEmptyException {
        loginService = new LoginService(database); //dodato
        loginService.login(context, credentials);
        logoutService = new LogoutService(
                loginService.getFirebaseAuth(),
                loginService.getCurrentUser()
        );
    }

    public void createAccount(Context context, CreateAccountCredentials credentials) throws FieldsEmptyException {
        createAccountService = new CreateAccountService(database); //dodato
        createAccountService.crateAccount(context, credentials);
        logoutService = new LogoutService(
                createAccountService.getFirebaseAuth(),
                createAccountService.getCurrentUser()
        );
    }

    public void logout(Context context) {
        logoutService.logout(context);
    }

    public void createScore(Score score) {
        scoreboardService.create(score);
    }

    public void createPendingQuestion(Context context, Question question) {
        questionService.create(question, FIRESTORE_PENDING_QUESTION_COLLECTION, context);
    }

    public void createQuestion(Context context, Question question) {
        questionService.create(question, FIRESTORE_QUESTION_COLLECTION, context);
    }

    public void updatePendingQuestion(Question question){
        questionService.update(question, FIRESTORE_PENDING_QUESTION_COLLECTION);
    }

    public void updateQuestion(Question question){
        questionService.update(question, FIRESTORE_QUESTION_COLLECTION);
    }

    public void deletePendingQuestion(Question question) {
        questionService.delete(question, FIRESTORE_PENDING_QUESTION_COLLECTION);
    }

    public void deleteQuestion(Question question) {
        questionService.delete(question, FIRESTORE_QUESTION_COLLECTION);
    }

    //For the case of refreshing all questions in AllQuestionsFramgment.class
    public void refreshQuestions() {
        questionService.reloadQuestions(FIRESTORE_QUESTION_COLLECTION);
    }

    public void refreshPendingQuestions() {
        questionService.reloadQuestions(FIRESTORE_PENDING_QUESTION_COLLECTION);
    }

    public void refreshScoreboards() {
        if(scoreboardService == null){
            scoreboardService = new ScoreboardService(database);
            return;
        }
        scoreboardService.refreshAll();
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

    public void refreshScoreboardForMode(int quizMode) {
        scoreboardService.refreshScoreboardForMode(quizMode);
    }

    public int getLastQuestionIndex() {
        return questionService.getLastQuestionIndex();
    }
}
