package project.mozgovanje.activity.main.fragments.allquestions;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Queue;

import project.mozgovanje.R;
import project.mozgovanje.model.Question;

public class FragmentAllQuestions extends Fragment {

    private BottomNavigationView bnv;
    private EditText etSearch;
    private RecyclerView recyclerView;
    private RecyclerViewAllQuestionsAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private ArrayList<Question> demoQuestions = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_questions, container, false);

        createDemoQuestions();

        etSearch = view.findViewById(R.id.fragment_all_questions_etSearch);
        recyclerView = view.findViewById(R.id.fragment_all_questions_rv);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(view.getContext());
        adapter = new RecyclerViewAllQuestionsAdapter(demoQuestions);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        etSearch.addTextChangedListener(new TextWatcher() {
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

    private void filter(String characters) {
        ArrayList<Question> filteredQuestions = new ArrayList<>();

        for (Question currentQuestion : demoQuestions){
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

    private void createDemoQuestions() {
        demoQuestions.add(new Question(1, "Ko je ovde najjaci ?", "Ja", "Ti", "On", "Ona", "a"));
        demoQuestions.add(new Question(1, "Ko je ovde sisica ?", "Ja", "Ti", "On", "Ona", "a"));
        demoQuestions.add(new Question(1, "Ko je ovde ludak ?", "Ja", "Ti", "On", "Ona", "a"));
    }

}
