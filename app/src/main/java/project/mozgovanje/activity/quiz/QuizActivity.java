package project.mozgovanje.activity.quiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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

// ===== B =======
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

        public ClickHandler() {
        }

        public void onBtnAnswer1(View view) {
            colorSelectedAnswer(ANSWER_A, binding.activityQuizTvAnswer1);
            quizMaster.userAnswered(ANSWER_A);
            binding.notifyChange();
        }

        public void onBtnAnswer2(View view) {
            colorSelectedAnswer(ANSWER_B, binding.activityQuizTvAnswer2);
            quizMaster.userAnswered(ANSWER_B);
            binding.notifyChange();
        }

        public void onBtnAnswer3(View view) {
            colorSelectedAnswer(ANSWER_C, binding.activityQuizTvAnswer3);
            quizMaster.userAnswered(ANSWER_C);
            binding.notifyChange();
        }

        public void onBtnAnswer4(View view) {
            colorSelectedAnswer(ANSWER_D, binding.activityQuizTvAnswer4);
            quizMaster.userAnswered(ANSWER_D);
            binding.notifyChange();
        }
        //TODO: Probaj da ovu metodu direktno ubacis u onBtn metode
        private void colorSelectedAnswer(String answerChar, Button selectedButton) {
            if (quizMaster.getCurrentQuestion().getCorrectAnswerChar().equals(answerChar)) {
                selectedButton.setBackgroundResource(R.drawable.edittext_rounded_correct);
            } else {
                selectedButton.setBackgroundResource(R.drawable.edittext_rounded_wrong);
            }
            binding.notifyChange();
        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                selectedButton.setBackgroundResource(R.drawable.edittext_rounded);
            }
        }, 5000);*/
            try {
                Thread.currentThread();
                Thread.sleep(3000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            selectedButton.setBackgroundResource(R.drawable.edittext_rounded);
            binding.notifyChange();

        }

    }




}