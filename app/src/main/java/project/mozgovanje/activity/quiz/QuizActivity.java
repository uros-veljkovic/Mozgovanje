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
import project.mozgovanje.db.controller.DatabaseController;
import project.mozgovanje.model.api.UserAPI;
import project.mozgovanje.model.score.Score;
import project.mozgovanje.util.observer.QuizEndEventListener;
import project.mozgovanje.databinding.ActivityQuizBinding;
import project.mozgovanje.model.quizmaster.QuizMaster;

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
        quizMaster = new QuizMaster(quizMode);
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
        DatabaseController.getInstance().createScore(score);
    }

    public class ClickHandler {

        private Button currentButton;
        private String currentAnswer;
        Drawable greenBackground = ResourcesCompat.getDrawable(getResources(), R.drawable.edit_text_rounded_green, null);
        Drawable redBackground = ResourcesCompat.getDrawable(getResources(), R.drawable.edit_text_rounded_red, null);

        public ClickHandler() {

        }

        public void onBtnAnswer1(View view) {
            currentAnswer = ANSWER_A;
            colorSelectedAnswer(ANSWER_A, view);
            binding.activityQuizBtnNextQuestion.setVisibility(VISIBLE);
        }

        public void onBtnAnswer2(View view) {
            currentAnswer = ANSWER_B;
            colorSelectedAnswer(ANSWER_B, view);
            binding.activityQuizBtnNextQuestion.setVisibility(VISIBLE);
        }

        public void onBtnAnswer3(View view) {
            currentAnswer = ANSWER_C;
            colorSelectedAnswer(ANSWER_C, view);
            binding.activityQuizBtnNextQuestion.setVisibility(VISIBLE);
        }

        public void onBtnAnswer4(View view) {
            currentAnswer = ANSWER_D;
            colorSelectedAnswer(ANSWER_D, view);
            binding.activityQuizBtnNextQuestion.setVisibility(VISIBLE);
        }


        private void colorSelectedAnswer(String answerChar, View view) {
            currentButton = (Button) view;
            if (quizMaster.getCurrentQuestion().getCorrectAnswerChar().contains(answerChar)) {
                currentButton.setBackground(greenBackground);
            } else {
                currentButton.setBackground(redBackground);
            }
            currentButton.refreshDrawableState();
        }

        public void onBtnNextQuestion(View view){
            currentButton.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.edit_text_rounded_blue_border, null));
            currentButton.refreshDrawableState();
            binding.activityQuizBtnNextQuestion.setVisibility(INVISIBLE);
            quizMaster.userAnswered(currentAnswer);
            binding.executePendingBindings();
        }

/*        private void changeQuestion(String answer, View view) {
*//*            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    view.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.edit_text_rounded_blue_border, null));

                    view.refreshDrawableState();
                }
            }, 3000);*//*
            try {
                Thread.currentThread().sleep(3000);
                view.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.edit_text_rounded_blue_border, null));
                view.refreshDrawableState();
                quizMaster.userAnswered(answer);
                binding.executePendingBindings();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }*/


    }


}