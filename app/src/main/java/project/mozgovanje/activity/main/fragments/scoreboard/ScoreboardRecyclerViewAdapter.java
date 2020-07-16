package project.mozgovanje.activity.main.fragments.scoreboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import project.mozgovanje.R;
import project.mozgovanje.databinding.UserScoreItemBinding;
import project.mozgovanje.model.score.Score;

public class ScoreboardRecyclerViewAdapter extends RecyclerView.Adapter<ScoreboardRecyclerViewAdapter.ViewHolder> {

    private List<Score> scores;
    private Context context;

    public ScoreboardRecyclerViewAdapter(Context context, List<Score> scores) {
        this.scores = scores;
        this.context = context;
    }

    @NonNull
    @Override
    public ScoreboardRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        UserScoreItemBinding binding = UserScoreItemBinding.inflate(inflater, parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Score score = scores.get(position);
        holder.bindScore(score, position);
    }

    @Override
    public int getItemCount() {
        return scores.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private UserScoreItemBinding binding;
        private ImageView ivMedal;


        public ViewHolder(UserScoreItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindScore(Score score, int position) {
            binding.setScore(score);
            switch (++position) {
                case 1:
                    binding.fragmentScoreboardIvMedal
                            .setImageDrawable(ContextCompat.getDrawable(context, R.mipmap.gold_medal));
                    break;
                case 2:
                    binding.fragmentScoreboardIvMedal
                            .setImageDrawable(ContextCompat.getDrawable(context, R.mipmap.silver_medal));
                    break;
                case 3:
                    binding.fragmentScoreboardIvMedal
                            .setImageDrawable(ContextCompat.getDrawable(context, R.mipmap.bronze_medal));
                    break;
                default:
                    binding.fragmentScoreboardIvMedal
                            .setImageDrawable(ContextCompat.getDrawable(context, R.mipmap.no_medal));
                    break;
            }
            binding.notifyChange();
            binding.executePendingBindings();
        }

    }

}
