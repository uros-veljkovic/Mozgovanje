package project.mozgovanje.activity.main.fragments.scoreboard;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.Collections;

import project.mozgovanje.R;
import project.mozgovanje.databinding.FragmentScoreboardBinding;
import project.mozgovanje.db.controller.RepositoryController;
import project.mozgovanje.model.score.Score;
import project.mozgovanje.model.scoreboard.Scoreboards;

import static project.mozgovanje.util.constants.Constants.GEEK_MODE;
import static project.mozgovanje.util.constants.Constants.TEST_MODE;
import static project.mozgovanje.util.constants.Constants.ZEN_MODE;

public class ScoreboardFragment extends Fragment {

    private FragmentScoreboardBinding binding;
    private ClickHandler clickHandler;
    private ScoreboardRecyclerViewAdapter adapter;

    private Scoreboards scoreboards;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_scoreboard, container, false);
        clickHandler = new ClickHandler();
        binding.setClickHandler(clickHandler);

        loadScores();
        initRecyclerView();

        return binding.getRoot();
    }

    private void loadScores() {

        RepositoryController.getInstance().refreshScoreboards();

        scoreboards = new Scoreboards(
                RepositoryController.getInstance().getZenScoreboard(),
                RepositoryController.getInstance().getTestScoreboard(),
                RepositoryController.getInstance().getGeekScoreboard()
        );

/*        scoreboards.setGeekScoreboard(DatabaseController.getInstance().getGeekScoreboard());
        scoreboards.setTestScoreboard(DatabaseController.getInstance().getTestScoreboard());
        scoreboards.setZenScoreboard(DatabaseController.getInstance().getZenScoreboard());
        Collections.sort(scoreboards.getGeekScoreboard());
        Collections.sort(scoreboards.getTestScoreboard());
        Collections.sort(scoreboards.getZenScoreboard());*/
    }

    private void initRecyclerView() {
        adapter = new ScoreboardRecyclerViewAdapter(requireActivity(), scoreboards.getZenScoreboard());

        binding.fragmentScoreboardRv.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.fragmentScoreboardRv.setHasFixedSize(true);
        binding.fragmentScoreboardRv.setAdapter(adapter);
        clickHandler.onZenBtnClicked(null); //Inicijalno prikazi Zen scoreboard
    }

    public class ClickHandler {

        public ClickHandler() {

        }

        public void onZenBtnClicked(View view) {
            RepositoryController.getInstance().refreshScoreboardForMode(ZEN_MODE);
            Collections.sort(scoreboards.getZenScoreboard());
            set(scoreboards.getZenScoreboard());
        }


        public void onGeekBtnClicked(View view) {
            RepositoryController.getInstance().refreshScoreboardForMode(GEEK_MODE);
            Collections.sort(scoreboards.getGeekScoreboard());
            set(scoreboards.getGeekScoreboard());
        }


        public void onTestBtnClicked(View view) {
            RepositoryController.getInstance().refreshScoreboardForMode(TEST_MODE);
            Collections.sort(scoreboards.getTestScoreboard());
            set(scoreboards.getTestScoreboard());
        }

        private void set(ArrayList<Score> scoreboard) {
            adapter.setScores(scoreboard);
            binding.fragmentScoreboardRv.setAdapter(adapter);
//            binding.notifyChange();
//            adapter.notifyDataSetChanged();
        }

    }

}
