package project.mozgovanje.activity.quiz.quizmaster;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import project.mozgovanje.BR;
import project.mozgovanje.util.observer.QuizEventListener;
import project.mozgovanje.db.controller.RepositoryController;
import project.mozgovanje.model.question.Question;

import static project.mozgovanje.util.constants.Constants.GEEK_MODE;
import static project.mozgovanje.util.constants.Constants.TEST_MODE;
import static project.mozgovanje.util.constants.Constants.ZEN_MODE;

public class QuizMaster extends BaseObservable {

    private final String TAG = "QuizMaster";


    private AnsweredQuestions answeredQuestions;
    private ScoreManager scoreManager;
    private ArrayList<Question> allQuestions;

    private int currentQuestionIndex;
    private int lastQuestionIndex;

    private Random randomGenerator;
    private int generatedIndex;

    private Question currentQuestion;
    private QuizEventListener quizEventListener;

    public QuizMaster(int mode) {
        allQuestions = new ArrayList<>();
        allQuestions = RepositoryController.getInstance().getQuestions();
        answeredQuestions = new AnsweredQuestions();
        scoreManager = new ScoreManager();

        randomGenerator = new Random((new Date()).getTime());
        currentQuestionIndex = 0;
        generateLastQuestionIndex(mode);
        generateQuestion();
        //update();
    }

    private void generateLastQuestionIndex(int mode) {
        switch (mode) {
            case ZEN_MODE:
                lastQuestionIndex = 20;
                break;
            case TEST_MODE:
                lastQuestionIndex = 40;
                break;
            case GEEK_MODE:
                lastQuestionIndex = 100;
                break;
            default:
                lastQuestionIndex = 0;
                break;
        }
    }

    public void userAnswered(String answer) {
        if (currentQuestion.userAnsweredCorrectly(answer)) {
            userAnsweredCorrect();
        } else {
            userAnsweredWrong();
        }
        generateQuestion();
    }

    public void userAnsweredCorrect() {
        scoreManager.correct();
        answeredQuestions.addNewIndex(generatedIndex);
        answeredQuestions.addCorrectQuestion(currentQuestion);

    }

    public void userAnsweredWrong() {
        scoreManager.wrong();
        answeredQuestions.addNewIndex(generatedIndex);
        answeredQuestions.addWrongQuestion(currentQuestion);
    }

    public void generateQuestion() {
        if (++currentQuestionIndex <= lastQuestionIndex) {
            do {
                generateRandomQuestion();
            } while (answeredQuestions.containsIndex(generatedIndex));
            notifyPropertyChanged(BR.currentQuestionIndex);
        } else {
            endQuiz();
        }
    }

    public void setQuizEventListener(QuizEventListener listener) {
        this.quizEventListener = listener;
    }

    private void endQuiz() {
        if (quizEventListener != null) {
            quizEventListener.onQuizEnd();
        }
    }

    private void generateRandomQuestion() {

        generatedIndex = randomGenerator.nextInt(allQuestions.size() - 1);

        currentQuestion = allQuestions.get(generatedIndex);
        notifyPropertyChanged(BR.currentQuestion);
    }

    @Bindable
    public Question getCurrentQuestion() {
        return currentQuestion;
    }
    public void setCurrentQuestion(Question currentQuestion) {
        this.currentQuestion = currentQuestion;
        notifyPropertyChanged(BR.currentQuestion);
    }

    @Bindable
    public int getCurrentQuestionIndex() {
        return currentQuestionIndex;
    }
    public void setCurrentQuestionIndex(int currentQuestionIndex) {
        this.currentQuestionIndex = currentQuestionIndex;
        notifyPropertyChanged(BR.currentQuestionIndex);
    }

    @Bindable
    public int getLastQuestionIndex() {
        return lastQuestionIndex;
    }
    public void setLastQuestionIndex(int lastQuestionIndex) {
        this.lastQuestionIndex = lastQuestionIndex;
        notifyPropertyChanged(BR.lastQuestionIndex);

    }

    public ArrayList<Question> getWrongQuestions() {
        return answeredQuestions.getWrongQuestions();
    }
    public ArrayList<Question> getCorrectQuestions() {
        return answeredQuestions.getCorrectQuestions();
    }

    public int getTotalPoints() {
//        return getCorrectQuestions().size();
        return scoreManager.getTotalPoints();
    }

    public ScoreManager getScoreManager() {
        return scoreManager;
    }

}
