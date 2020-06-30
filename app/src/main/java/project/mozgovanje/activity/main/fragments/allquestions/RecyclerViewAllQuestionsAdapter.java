package project.mozgovanje.activity.main.fragments.allquestions;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import project.mozgovanje.R;
import project.mozgovanje.model.Question;

public class RecyclerViewAllQuestionsAdapter extends RecyclerView.Adapter<RecyclerViewAllQuestionsAdapter.ViewHolder> {

    private List<Question> allQuestions;

    public RecyclerViewAllQuestionsAdapter(ArrayList<Question> allQuestions) {
        this.allQuestions = allQuestions;
    }

    @NonNull
    @Override
    public RecyclerViewAllQuestionsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.question_item, parent, false);
        return new RecyclerViewAllQuestionsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAllQuestionsAdapter.ViewHolder holder, int position) {
        Question question = allQuestions.get(position);

        holder.tvQuestion.setText(question.getQuestionText());
        holder.tvAnswer1.setText(question.getAnswer1());
        holder.tvAnswer2.setText(question.getAnswer2());
        holder.tvAnswer3.setText(question.getAnswer3());
        holder.tvAnswer4.setText(question.getAnswer4());

    }

    @Override
    public int getItemCount() {
        return allQuestions.size();
    }

    public void filterList(ArrayList<Question> filteredList){
        allQuestions = filteredList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvQuestion;
        public TextView tvAnswer1;
        public TextView tvAnswer2;
        public TextView tvAnswer3;
        public TextView tvAnswer4;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvQuestion = itemView.findViewById(R.id.question_item_tvQuestion);
            tvAnswer1 = itemView.findViewById(R.id.question_item_tvAnswer1);
            tvAnswer2 = itemView.findViewById(R.id.question_item_tvAnswer2);
            tvAnswer3 = itemView.findViewById(R.id.question_item_tvAnswer3);
            tvAnswer4 = itemView.findViewById(R.id.question_item_tvAnswer4);
        }


    }
}
