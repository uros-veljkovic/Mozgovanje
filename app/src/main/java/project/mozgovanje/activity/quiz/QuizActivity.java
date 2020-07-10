package project.mozgovanje.activity.quiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import project.mozgovanje.activity.quiz.result.QuizResultActivity;
import project.mozgovanje.R;
import project.mozgovanje.db.controller.DatabaseController;
import project.mozgovanje.util.observer.QuizEndEventListener;
import project.mozgovanje.databinding.ActivityQuizBinding;
import project.mozgovanje.model.quizmaster.QuizMaster;

import static project.mozgovanje.util.constants.Constants.ANSWER_A;
import static project.mozgovanje.util.constants.Constants.ANSWER_B;
import static project.mozgovanje.util.constants.Constants.ANSWER_C;
import static project.mozgovanje.util.constants.Constants.ANSWER_D;
import static project.mozgovanje.util.constants.Constants.ARRAY_LIST_CORRECT_QUESTIONS;
import static project.mozgovanje.util.constants.Constants.ARRAY_LIST_WRONG_QUESTIONS;
import static project.mozgovanje.util.constants.Constants.MODE;

// ===== B =======
public class QuizActivity extends AppCompatActivity implements QuizEndEventListener {

    private ActivityQuizBinding binding;
    private int mode;
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

        this.mode = bundle.getInt(MODE);
    }

    private void initBinding() {
        quizMaster = new QuizMaster(mode);
        clickHandler = new ClickHandler(this);
        binding.setClickHandler(clickHandler);
        binding.setQuizMaster(quizMaster);
    }

    @Override
    public void onQuizEnd() {
//TODO: Sacuvaj Score na firestore
//        DatabaseController.getInstance();

        Intent intent = new Intent(QuizActivity.this, QuizResultActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(ARRAY_LIST_WRONG_QUESTIONS, quizMaster.getWrongQuestions());
        bundle.putParcelableArrayList(ARRAY_LIST_CORRECT_QUESTIONS, quizMaster.getCorrectQuestions());
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    public class ClickHandler {

        public Context context;
        private String TAG = "ClickHandler";

        public ClickHandler(Context context) {
            this.context = context;
        }

        public void onBtnAnswer1(View view) {
            quizMaster.userAnswered(ANSWER_A);
            binding.notifyChange();

        }

        public void onBtnAnswer2(View view) {
            quizMaster.userAnswered(ANSWER_B);
            binding.notifyChange();

        }

        public void onBtnAnswer3(View view) {
            quizMaster.userAnswered(ANSWER_C);
            binding.notifyChange();

        }

        public void onBtnAnswer4(View view) {
            quizMaster.userAnswered(ANSWER_D);
            binding.notifyChange();

        }

    }

}