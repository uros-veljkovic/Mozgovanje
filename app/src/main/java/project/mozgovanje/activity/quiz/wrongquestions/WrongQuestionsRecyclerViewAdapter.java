package project.mozgovanje.activity.quiz.wrongquestions;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import project.mozgovanje.R;
import project.mozgovanje.activity.main.fragments.allquestions.AllQuestionsRecyclerViewAdapter;
import project.mozgovanje.databinding.WrongQuestionItemBinding;
import project.mozgovanje.model.question.Question;

public class WrongQuestionsRecyclerViewAdapter extends RecyclerView.Adapter<WrongQuestionsRecyclerViewAdapter.ViewHolder> {

    private List<Question> wrongQuestions;
    private Context context;

    public WrongQuestionsRecyclerViewAdapter(Context context, ArrayList<Question> allQuestions) {
        this.context = context;
        this.wrongQuestions = allQuestions;
    }

    @NonNull
    @Override
    public WrongQuestionsRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        WrongQuestionItemBinding itemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.wrong_question_item,
                parent,
                false);

        return new ViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull WrongQuestionsRecyclerViewAdapter.ViewHolder holder, int position) {
        Question question = wrongQuestions.get(position);

        holder.itemBinding.setQuestion(question);
    }

    @Override
    public int getItemCount() {
        return wrongQuestions.size();
    }

    public void filterList(ArrayList<Question> filteredList){
        wrongQuestions = filteredList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        WrongQuestionItemBinding itemBinding;

        public ViewHolder(@NonNull WrongQuestionItemBinding itemView) {
            super(itemView.getRoot());
            itemBinding = itemView;
        }


    }
}
