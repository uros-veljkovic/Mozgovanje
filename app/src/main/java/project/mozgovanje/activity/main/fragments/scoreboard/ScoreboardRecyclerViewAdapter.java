package project.mozgovanje.activity.main.fragments.scoreboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

import project.mozgovanje.R;
import project.mozgovanje.activity.main.fragments.allquestions.AllQuestionsRecyclerViewAdapter;
import project.mozgovanje.model.user.UserScore;

public class ScoreboardRecyclerViewAdapter extends RecyclerView.Adapter<ScoreboardRecyclerViewAdapter.ViewHolder> {

    private List<UserScore> scores;
    private Context context;

    public ScoreboardRecyclerViewAdapter(Context context, List<UserScore> allScores) {
        this.scores = allScores;
        this.context = context;
    }

    @NonNull
    @Override
    public ScoreboardRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.user_score_item, parent, false);
        return new ScoreboardRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserScore score = scores.get(position);

        int place = ++position;

        String usernameWithPlace = String.format("%d. %s", place, score.getUsername());
        holder.tvUsername.setText(usernameWithPlace);
        holder.tvTotalScore.setText(String.valueOf(score.getTotalAnswersCorrect()));
        setMedal(holder, position);

    }

    private void setMedal(@NonNull ViewHolder holder, int position) {
        switch (position) {
            case 1:
                holder.ivMedal.setImageDrawable(ContextCompat.getDrawable(context, R.mipmap.gold_medal));
                break;
            case 2:
                holder.ivMedal.setImageDrawable(ContextCompat.getDrawable(context, R.mipmap.silver_medal));
                break;
            case 3:
                holder.ivMedal.setImageDrawable(ContextCompat.getDrawable(context, R.mipmap.bronze_medal));
                break;
            default:
                holder.ivMedal.setImageDrawable(ContextCompat.getDrawable(context, R.mipmap.no_medal));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return scores.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView ivMedal;
        public TextView tvUsername;
        public TextView tvTotalScore;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivMedal = itemView.findViewById(R.id.fragment_scoreboard_ivMedal);
            tvUsername = itemView.findViewById(R.id.fragment_scoreboard_tvUsername);
            tvTotalScore = itemView.findViewById(R.id.fragment_scoreboard_tvTotalScore);
        }
    }

}
