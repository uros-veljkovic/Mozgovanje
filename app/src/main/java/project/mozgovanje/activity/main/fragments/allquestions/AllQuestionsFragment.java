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
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;

import project.mozgovanje.R;
import project.mozgovanje.databinding.FragmentAllQuestionsBinding;
import project.mozgovanje.db.controller.DatabaseController;
import project.mozgovanje.model.question.Question;

//TODO: Implementiraj DataBinding
public class AllQuestionsFragment extends Fragment {

    private FragmentAllQuestionsBinding binding;
    private AllQuestionsRecyclerViewAdapter adapter;

    private ArrayList<Question> allQuestions = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_all_questions, container, false);

        allQuestions = DatabaseController.getInstance().getQuestions();
        Collections.sort(allQuestions);

        binding.fragmentAllQuestionsRv.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.fragmentAllQuestionsRv.setHasFixedSize(true);
        adapter = new AllQuestionsRecyclerViewAdapter(allQuestions);

        binding.fragmentAllQuestionsRv.setAdapter(adapter);

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.fragmentAllQuestionsEtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable searchText) {
                filter(searchText.toString());
            }
        });
    }

    private void filter(String characters) {
        ArrayList<Question> filteredQuestions = new ArrayList<>();

        for (Question currentQuestion : allQuestions) {
            if (questionContainsCharacters(characters, currentQuestion)) {
                filteredQuestions.add(currentQuestion);
            }
        }

        adapter.filterList(filteredQuestions);
        adapter.notifyDataSetChanged();
    }

    private boolean questionContainsCharacters(String characters, Question question) {
        return question.getQuestionText().toLowerCase().contains(characters.toLowerCase())
                || question.getAnswer1().toLowerCase().contains(characters.toLowerCase())
                || question.getAnswer2().toLowerCase().contains(characters.toLowerCase())
                || question.getAnswer3().toLowerCase().contains(characters.toLowerCase())
                || question.getAnswer4().toLowerCase().contains(characters.toLowerCase());
    }

}
