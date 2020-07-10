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

import java.util.ArrayList;
import java.util.Collections;

import project.mozgovanje.R;
import project.mozgovanje.db.controller.DatabaseController;
import project.mozgovanje.model.question.Question;
//TODO: Implementiraj DataBinding
public class AllQuestionsFragment extends Fragment {


    private EditText etSearch;

    private RecyclerView recyclerView;
    private AllQuestionsRecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private ArrayList<Question> allQuestions = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_questions, container, false);

        allQuestions = DatabaseController.getInstance().getQuestions();
        Collections.sort(allQuestions);

        etSearch = view.findViewById(R.id.fragment_all_questions_etSearch);
        recyclerView = view.findViewById(R.id.fragment_all_questions_rv);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(view.getContext());
        adapter = new AllQuestionsRecyclerViewAdapter(getContext(), allQuestions);

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

        for (Question currentQuestion : allQuestions){
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
