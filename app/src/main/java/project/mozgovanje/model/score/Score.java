package project.mozgovanje.model.score;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

public class Score extends BaseObservable implements Comparable<Score> {

    private String username;
    private String inScoreboard;
    private int points;

    public Score(String username, String inScoreboard, int points) {
        this.username = username;
        this.inScoreboard = inScoreboard;
        this.points = points;
    }

    public Score() {
    }

    @Bindable
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
        notifyPropertyChanged(BR.username);

    }

    public String getInScoreboard() {
        return inScoreboard;
    }
    public void setInScoreboard(String inScoreboard) {
        this.inScoreboard = inScoreboard;
    }

    @Bindable
    public int getPoints() {
        return points;
    }
    public void setPoints(int points) {
        this.points = points;
    }

    @Override
    public int compareTo(Score comparingScore) {
        int thisScore = points;
        int otherScore = comparingScore.getPoints();
        return (Integer.compare(otherScore, thisScore));
    }
}
