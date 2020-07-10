package project.mozgovanje.activity.main.fragments.scoreboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.common.io.LineReader;

import java.util.ArrayList;
import java.util.Collections;

import project.mozgovanje.R;
import project.mozgovanje.activity.main.fragments.allquestions.AllQuestionsRecyclerViewAdapter;
import project.mozgovanje.model.question.Question;
import project.mozgovanje.model.user.UserScore;

public class ScoreboardFragment extends Fragment {


    private RecyclerView recyclerView;
    private ScoreboardRecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private ArrayList<UserScore> demoScores = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_scoreboard, container, false);

        createDemoScores();
        recyclerView = view.findViewById(R.id.fragment_scoreboard_rv);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(view.getContext());
        adapter = new ScoreboardRecyclerViewAdapter(getContext(), demoScores);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        return view;
    }

    private void createDemoScores() {
        demoScores.add(new UserScore("mikica123", 90));
        demoScores.add(new UserScore("mangulica333", 100));
        demoScores.add(new UserScore("samo_jajko", 40));
        demoScores.add(new UserScore("pajko", 30));
        demoScores.add(new UserScore("nemke_bratex", 2));
        demoScores.add(new UserScore("bot1", 7));
        demoScores.add(new UserScore("bot2", 80));
        demoScores.add(new UserScore("bot222", 100));
        demoScores.add(new UserScore("urkeev14", 88));
        demoScores.add(new UserScore("janajana", 100));

        Collections.sort(demoScores);

    }
}
