package project.mozgovanje.db.controller;

import android.content.Context;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import project.mozgovanje.db.service.QuestionService;
import project.mozgovanje.db.service.ScoreboardService;
import project.mozgovanje.model.credentials.CreateAccountCredentials;
import project.mozgovanje.model.credentials.LoginCredentials;
import project.mozgovanje.db.service.CreateAccountService;
import project.mozgovanje.db.service.LoginService;
import project.mozgovanje.model.scoreboard.Score;
import project.mozgovanje.util.exception.FieldsEmptyException;
import project.mozgovanje.model.question.Question;

public class DatabaseController {

    private static final DatabaseController instance = new DatabaseController();

    private FirebaseFirestore database;
    private LoginService loginService;
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
    }

    public void logout(Context context){
        loginService.logout(context);
    }

    public void createAccount(Context context, CreateAccountCredentials credentials) throws FieldsEmptyException {
        createAccountService.crateAccount(context, credentials);
    }

    public ArrayList<Question> getQuestions(){
        return questionService.getQuestions();
    }

    public void createScore(Score score){
        scoreboardService.createNew(score);
    }

    public void createPendingQuestion(Context context, Question question) {
        questionService.createPendingQuestion(context, question);
    }

    public void createQuestion(Context context, Question question) {
        questionService.createQuestion(context, question);
    }
}
