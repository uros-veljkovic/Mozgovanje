package project.mozgovanje.activity.quiz.wrongquestions;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Collections;

import project.mozgovanje.R;
import project.mozgovanje.activity.quiz.result.QuizResultActivity;
import project.mozgovanje.db.controller.DatabaseController;
import project.mozgovanje.util.constants.Constants;
import project.mozgovanje.databinding.ActivityWrongQuestionsBinding;
import project.mozgovanje.model.question.Question;

public class WrongQuestionsActivity extends AppCompatActivity {

    private ActivityWrongQuestionsBinding binding;
    private ArrayList<Question> wrongQuestions;
    private ArrayList<Question> correctQuestions;
    private WrongQuestionsActivityRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWrongQuestionsFromIntent();
        initBinding();
    }

    @Override
    protected void onResume() {
        super.onResume();

        binding.activityWrongQuestionsEtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.more_options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btnLogout:
                DatabaseController.getInstance().logout(this);
                return true;
            default:
                break;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(WrongQuestionsActivity.this, QuizResultActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(Constants.ARRAY_LIST_WRONG_QUESTIONS, wrongQuestions);
        bundle.putParcelableArrayList(Constants.ARRAY_LIST_CORRECT_QUESTIONS, correctQuestions);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void getWrongQuestionsFromIntent() {
        Intent intent = getIntent();
        wrongQuestions = intent.getParcelableArrayListExtra(Constants.ARRAY_LIST_WRONG_QUESTIONS);
        correctQuestions = intent.getParcelableArrayListExtra(Constants.ARRAY_LIST_CORRECT_QUESTIONS);
        Collections.sort(wrongQuestions);
    }

    private void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_wrong_questions);
        binding.activityWrongQuestionsRv.setLayoutManager(new LinearLayoutManager(this));
        binding.activityWrongQuestionsRv.setHasFixedSize(true);

        adapter = new WrongQuestionsActivityRecyclerViewAdapter(this, wrongQuestions);
        binding.activityWrongQuestionsRv.setAdapter(adapter);
    }

    private void filter(String characters) {
        ArrayList<Question> filteredQuestions = new ArrayList<>();

        for (Question currentQuestion : wrongQuestions){
            if(questionContainsCharacters(characters, currentQuestion)){
                filteredQuestions.add(currentQuestion);
            }
        }

        adapter.filterList(filteredQuestions);
    }

    private boolean questionContainsCharacters(String characters, Question question) {
        return question.getQuestionText().toLowerCase().contains(characters.toLowerCase())
                || question.getAnswer1().toLowerCase().contains(characters.toLowerCase())
                || question.getAnswer2().toLowerCase().contains(characters.toLowerCase())
                || question.getAnswer3().toLowerCase().contains(characters.toLowerCase())
                || question.getAnswer4().toLowerCase().contains(characters.toLowerCase());
    }

}