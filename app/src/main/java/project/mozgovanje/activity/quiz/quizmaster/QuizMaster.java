package project.mozgovanje.activity.quiz.quizmaster;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import project.mozgovanje.BR;
import project.mozgovanje.util.constants.Constants;
import project.mozgovanje.util.observer.QuizEndEventListener;
import project.mozgovanje.db.controller.RepositoryController;
import project.mozgovanje.model.question.Question;

import static project.mozgovanje.util.constants.Constants.GEEK_MODE;
import static project.mozgovanje.util.constants.Constants.GEEK_MODE_NUMBER_OF_QUESTIONS;
import static project.mozgovanje.util.constants.Constants.TEST_MODE;
import static project.mozgovanje.util.constants.Constants.TEST_MODE_NUMBER_OF_QUESTIONS;
import static project.mozgovanje.util.constants.Constants.ZEN_MODE;
import static project.mozgovanje.util.constants.Constants.ZEN_MODE_NUMBER_OF_QUESTIONS;

public class QuizMaster extends BaseObservable {

    private final String TAG = "QuizMaster";

    private ArrayList<Question> allQuestions;
    private AnsweredQuestions answeredQuestions;

    //TODO: ZA PROMENI QUIZMASTERA
/*    private ArrayList<Question> wrongQuestions;
    private ArrayList<Question> correctQuestions;
    private ArrayList<Integer> indexes;*/

    private String currentQuizState;
    private int maxNumberOfQuestions;

    private Random randomGenerator;
    private int generatedIndex;

    private Question currentQuestion;
    private QuizEndEventListener quizEndEventListener;

    public QuizMaster(int mode) {
        allQuestions = new ArrayList<>();
        allQuestions = RepositoryController.getInstance().getQuestions();
        answeredQuestions = new AnsweredQuestions();

        randomGenerator = new Random((new Date()).getTime());

        maxNumberOfQuestions = generateUpperLimit(mode);
        newQuestion();
        update();
    }

    //TODO: ZA PROMENI QUIZMASTERA
/*    public QuizMaster(int mode) {
        initLists();
        generateUpperLimitUpon(mode);
        generateQuestions();
        allQuestions = new ArrayList<>();
        allQuestions = RepositoryController.getInstance().getQuestions();
        answeredQuestions = new AnsweredQuestions();

        newQuestion();
        update();
    }*/

    //TODO: ZA PROMENI QUIZMASTERA
/*    private void generateUpperLimitUpon(int mode) {
        switch (mode) {
            case ZEN_MODE:
                maxNumberOfQuestions = ZEN_MODE_NUMBER_OF_QUESTIONS;
            case TEST_MODE:
                maxNumberOfQuestions = TEST_MODE_NUMBER_OF_QUESTIONS;
            case GEEK_MODE:
                maxNumberOfQuestions = GEEK_MODE_NUMBER_OF_QUESTIONS;
        }
    }*/

    //TODO: ZA PROMENI QUIZMASTERA
/*    private void initLists() {
        allQuestions = new ArrayList<>();
        correctQuestions = new ArrayList<>();
        wrongQuestions = new ArrayList<>();
        indexes = new ArrayList<>();
    }*/

    //TODO: ZA PROMENI QUIZMASTERA
/*    private void generateQuestions() {

        int max = RepositoryController.getInstance().getLastQuestionIndex() - 1; //197
        int min = 1;
        int range = max - min; //196

        generatedIndex = (int) ((Math.random() * range) + min); //197

        currentQuestion = allQuestions.get(generatedIndex);
        notifyPropertyChanged(BR.currentQuestion);
    }*/

    private int generateUpperLimit(int mode) {
        switch (mode) {
            case ZEN_MODE:
                return 20;
            case TEST_MODE:
                return 40;
            case GEEK_MODE:
                return 100;
            default:
                return 0;
        }
    }

    public void userAnswered(String answer) {
        if (currentQuestion.userAnsweredCorrectly(answer)) {
            userAnsweredCorrect();
        } else {
            userAnsweredWrong();
        }
        newQuestion();
    }

    public void userAnsweredCorrect() {
        answeredQuestions.addNewIndex(generatedIndex);
        answeredQuestions.addCorrectQuestion(currentQuestion);

    }

    public void userAnsweredWrong() {
        answeredQuestions.addNewIndex(generatedIndex);
        answeredQuestions.addWrongQuestion(currentQuestion);
    }

    public void newQuestion() {
        if (answeredQuestions.getSize() != maxNumberOfQuestions) {
            do {
                generateRandomQuestion();
            } while (answeredQuestions.containsIndex(generatedIndex));
            update();
        } else {
            endQuiz();
        }
    }

    private void update() {
        currentQuizState = getCurrentQuizState();
        notifyPropertyChanged(BR.currentQuizState);
    }

    public void setQuizEndEventListener(QuizEndEventListener listener) {
        this.quizEndEventListener = listener;
    }

    private void endQuiz() {
        if (quizEndEventListener != null) {
            quizEndEventListener.onQuizEnd();
        }
    }

    private void generateRandomQuestion() {

        generatedIndex = randomGenerator.nextInt(allQuestions.size() - 1);

        currentQuestion = allQuestions.get(generatedIndex);
        notifyPropertyChanged(BR.currentQuestion);
    }

    @Bindable
    public String getCurrentQuizState() {
        return (answeredQuestions.getSize() + 1) + "/" + maxNumberOfQuestions;
    }

    public void setCurrentQuizState(String currentQuizState) {
        this.currentQuizState = currentQuizState;
        notifyPropertyChanged(BR.currentQuizState);
    }

    @Bindable
    public Question getCurrentQuestion() {
        return currentQuestion;
    }

    public void setCurrentQuestion(Question currentQuestion) {
        this.currentQuestion = currentQuestion;
        notifyPropertyChanged(BR.currentQuestion);
    }

    public ArrayList<Question> getWrongQuestions() {
        return answeredQuestions.getWrongQuestions();
    }

    public ArrayList<Question> getCorrectQuestions() {
        return answeredQuestions.getCorrectQuestions();
    }

}
