package project.mozgovanje.activity.quiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import project.mozgovanje.activity.quiz.result.QuizResultActivity;
import project.mozgovanje.R;
import project.mozgovanje.db.controller.RepositoryController;
import project.mozgovanje.model.api.UserAPI;
import project.mozgovanje.model.score.Score;
import project.mozgovanje.util.observer.QuizEndEventListener;
import project.mozgovanje.databinding.ActivityQuizBinding;
import project.mozgovanje.activity.quiz.quizmaster.QuizMaster;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static project.mozgovanje.util.constants.Constants.ANSWER_A;
import static project.mozgovanje.util.constants.Constants.ANSWER_B;
import static project.mozgovanje.util.constants.Constants.ANSWER_C;
import static project.mozgovanje.util.constants.Constants.ANSWER_D;
import static project.mozgovanje.util.constants.Constants.ARRAY_LIST_CORRECT_QUESTIONS;
import static project.mozgovanje.util.constants.Constants.ARRAY_LIST_WRONG_QUESTIONS;
import static project.mozgovanje.util.constants.Constants.FIRESTORE_GEEK_SCOREBOARD_COLLECTION;
import static project.mozgovanje.util.constants.Constants.FIRESTORE_TEST_SCOREBOARD_COLLECTION;
import static project.mozgovanje.util.constants.Constants.FIRESTORE_ZEN_SCOREBOARD_COLLECTION;
import static project.mozgovanje.util.constants.Constants.GEEK_MODE;
import static project.mozgovanje.util.constants.Constants.MODE;
import static project.mozgovanje.util.constants.Constants.TEST_MODE;
import static project.mozgovanje.util.constants.Constants.ZEN_MODE;

public class QuizActivity extends AppCompatActivity implements QuizEndEventListener {

    private ActivityQuizBinding binding;
    private int quizMode;
    private ClickHandler clickHandler;
    private QuizMaster quizMaster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_quiz);

        initMode();
        initBinding();
    }

    @Override
    protected void onResume() {
        super.onResume();

        quizMaster.setQuizEndEventListener(this);
    }

    private void initMode() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        assert bundle != null;

        this.quizMode = bundle.getInt(MODE);
    }

    private void initBinding() {
        quizMaster = new QuizMaster(quizMode); //TODO : PROMENI LOGIKU, QUIZ MASTER TREBA DA SKIDA JEDNO PO JEDNO PITANJE
        clickHandler = new ClickHandler();

        binding.setClickHandler(clickHandler);
        binding.setQuizMaster(quizMaster);
    }

    @Override
    public void onQuizEnd() {
        saveScore();

        Intent intent = new Intent(QuizActivity.this, QuizResultActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(ARRAY_LIST_WRONG_QUESTIONS, quizMaster.getWrongQuestions());
        bundle.putParcelableArrayList(ARRAY_LIST_CORRECT_QUESTIONS, quizMaster.getCorrectQuestions());
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    private void saveScore() {
        Score score = new Score();
        score.setUsername(UserAPI.getInstance().getUsername());
        score.setPoints(quizMaster.getCorrectQuestions().size());

        switch (quizMode) {
            case ZEN_MODE:
                score.setInScoreboard(FIRESTORE_ZEN_SCOREBOARD_COLLECTION);
                break;
            case TEST_MODE:
                score.setInScoreboard(FIRESTORE_TEST_SCOREBOARD_COLLECTION);
                break;
            case GEEK_MODE:
                score.setInScoreboard(FIRESTORE_GEEK_SCOREBOARD_COLLECTION);
                break;
            default:
                break;
        }

        RepositoryController.getInstance().createScore(score);
    }

    public class ClickHandler {

        private String currentAnswer;
        private boolean buttonEnabler;
        Drawable greenBackground = ResourcesCompat.getDrawable(getResources(), R.drawable.edit_text_rounded_green, null);
        Drawable redBackground = ResourcesCompat.getDrawable(getResources(), R.drawable.edit_text_rounded_red, null);
        Drawable whiteBackground = ResourcesCompat.getDrawable(getResources(), R.drawable.edit_text_rounded_blue_border, null);

        public ClickHandler() {
            buttonEnabler = true;
        }

        public void onBtnAnswer1(View view) {
            disableAndPaintAnswerButtons(ANSWER_A);
        }

        public void onBtnAnswer2(View view) {
            disableAndPaintAnswerButtons(ANSWER_B);
        }

        public void onBtnAnswer3(View view) {
            disableAndPaintAnswerButtons(ANSWER_C);
        }

        public void onBtnAnswer4(View view) {
            disableAndPaintAnswerButtons(ANSWER_D);
        }

        private void disableAndPaintAnswerButtons(String clickedButtonAnswerCharacter) {
            currentAnswer = clickedButtonAnswerCharacter;
            paintAllButtonsTo(redBackground);
            paintCorrectAnswerButtonToGreen();
            binding.activityQuizBtnNextQuestion.setVisibility(VISIBLE);
            enableOrDisableAnswerButtons();
        }

        private void paintCorrectAnswerButtonToGreen() {
            switch (quizMaster.getCurrentQuestion().getCorrectAnswerChar()) {
                case ANSWER_A:
                    binding.activityQuizTvAnswer1.setBackground(greenBackground);
                    binding.activityQuizTvAnswer1.refreshDrawableState();
                    break;
                case ANSWER_B:
                    binding.activityQuizTvAnswer2.setBackground(greenBackground);
                    binding.activityQuizTvAnswer2.refreshDrawableState();
                    break;
                case ANSWER_C:
                    binding.activityQuizTvAnswer3.setBackground(greenBackground);
                    binding.activityQuizTvAnswer3.refreshDrawableState();
                    break;
                case ANSWER_D:
                    binding.activityQuizTvAnswer4.setBackground(greenBackground);
                    binding.activityQuizTvAnswer4.refreshDrawableState();
                    break;
            }
        }

        private void paintAllButtonsTo(Drawable background) {
            binding.activityQuizTvAnswer1.setBackground(background);
            binding.activityQuizTvAnswer2.setBackground(background);
            binding.activityQuizTvAnswer3.setBackground(background);
            binding.activityQuizTvAnswer4.setBackground(background);

            //Need to refresh the state of buttons
            binding.activityQuizTvAnswer1.refreshDrawableState();
            binding.activityQuizTvAnswer2.refreshDrawableState();
            binding.activityQuizTvAnswer3.refreshDrawableState();
            binding.activityQuizTvAnswer4.refreshDrawableState();
        }

        public void onBtnNextQuestion(View view) {

            paintAllButtonsTo(whiteBackground);
            binding.activityQuizBtnNextQuestion.setVisibility(INVISIBLE);
            quizMaster.userAnswered(currentAnswer);
            binding.executePendingBindings();
            enableOrDisableAnswerButtons();
        }

        private void enableOrDisableAnswerButtons() {
            buttonEnabler = !buttonEnabler; //disable

            binding.activityQuizTvAnswer1.setFocusable(buttonEnabler);
            binding.activityQuizTvAnswer2.setFocusable(buttonEnabler);
            binding.activityQuizTvAnswer3.setFocusable(buttonEnabler);
            binding.activityQuizTvAnswer4.setFocusable(buttonEnabler);
        }
    }
}