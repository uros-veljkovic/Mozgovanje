package project.mozgovanje.activity.quiz.quizmaster;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import java.util.ArrayList;

import project.mozgovanje.BR;
import project.mozgovanje.util.observer.QuizEndEventListener;
import project.mozgovanje.db.controller.DatabaseController;
import project.mozgovanje.model.question.Question;

import static project.mozgovanje.util.constants.Constants.GEEK_MODE;
import static project.mozgovanje.util.constants.Constants.TEST_MODE;
import static project.mozgovanje.util.constants.Constants.ZEN_MODE;

public class QuizMaster extends BaseObservable {

    private final String TAG = "QuizMaster";

    private ArrayList<Question> allQuestions;
    private AnsweredQuestions answeredQuestions;

    private String currentQuizState;
    private int maxNumberOfQuestions;
    private int generatedIndex;
    private Question currentQuestion;
    private QuizEndEventListener quizEndEventListener;

    public QuizMaster(int mode) {
        allQuestions = new ArrayList<>();
        allQuestions = DatabaseController.getInstance().getQuestions();
        answeredQuestions = new AnsweredQuestions();

        maxNumberOfQuestions = generateUpperLimit(mode);
        newQuestion();
        update();
    }

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
            questionCorrect();
        } else {
            questionWrong();
        }
        newQuestion();
    }

    public void questionCorrect() {
        answeredQuestions.addNewIndex(generatedIndex);
        answeredQuestions.addCorrectQuestion(currentQuestion);

    }

    public void questionWrong() {
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
            return;
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
        int max = allQuestions.size() - 1; //197
        int min = 1;
        int range = max - min; //196

        generatedIndex = (int) ((Math.random() * range) + min); //197

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

    public ArrayList<Question> getWrongQuestions(){
        return answeredQuestions.getWrongQuestions();
    }

    public ArrayList<Question> getCorrectQuestions(){
        return answeredQuestions.getCorrectQuestions();
    }

}
