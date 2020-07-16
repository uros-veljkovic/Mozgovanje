package project.mozgovanje.activity.main.fragments.allquestions;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import project.mozgovanje.R;
import project.mozgovanje.databinding.QuestionItemBinding;
import project.mozgovanje.databinding.UserScoreItemBinding;
import project.mozgovanje.model.question.Question;

public class AllQuestionsRecyclerViewAdapter extends RecyclerView.Adapter<AllQuestionsRecyclerViewAdapter.ViewHolder> {

    private List<Question> allQuestions;

    public AllQuestionsRecyclerViewAdapter(ArrayList<Question> allQuestions) {
        this.allQuestions = allQuestions;
    }

    @NonNull
    @Override
    public AllQuestionsRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        QuestionItemBinding binding = QuestionItemBinding.inflate(inflater, parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AllQuestionsRecyclerViewAdapter.ViewHolder holder, int position) {

        Question question = allQuestions.get(position);
        holder.bindQuestion(question);

    }

    @Override
    public int getItemCount() {
        return allQuestions.size();
    }

    public void filterList(ArrayList<Question> filteredList) {
        allQuestions = filteredList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private QuestionItemBinding binding;


        public ViewHolder(QuestionItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }


        public void bindQuestion(Question question) {
            binding.setQuestion(question);
        }
    }
}
