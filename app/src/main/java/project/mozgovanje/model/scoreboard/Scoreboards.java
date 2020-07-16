package project.mozgovanje.model.scoreboard;

import java.util.ArrayList;
import java.util.Collections;

import project.mozgovanje.model.score.Score;

public class Scoreboards {

    private ArrayList<Score> zenScoreboard;
    private ArrayList<Score> testScoreboard;
    private ArrayList<Score> geekScoreboard;

    public Scoreboards() {
        zenScoreboard = new ArrayList<>();
        testScoreboard = new ArrayList<>();
        geekScoreboard = new ArrayList<>();
    }

    public Scoreboards(ArrayList<Score> zenScoreboard,
                       ArrayList<Score> testScoreboard,
                       ArrayList<Score> geekScoreboard) {
        this.zenScoreboard = zenScoreboard;
        this.testScoreboard = testScoreboard;
        this.geekScoreboard = geekScoreboard;
        sortScoreboards();
    }

    private void sortScoreboards() {
        Collections.sort(zenScoreboard);
        Collections.sort(testScoreboard);
        Collections.sort(geekScoreboard);
    }

    public ArrayList<Score> getZenScoreboard() {
        return zenScoreboard;
    }

    public void setZenScoreboard(ArrayList<Score> zenScoreboard) {
        this.zenScoreboard = zenScoreboard;
    }

    public ArrayList<Score> getTestScoreboard() {
        return testScoreboard;
    }

    public void setTestScoreboard(ArrayList<Score> testScoreboard) {
        this.testScoreboard = testScoreboard;
    }

    public ArrayList<Score> getGeekScoreboard() {
        return geekScoreboard;
    }

    public void setGeekScoreboard(ArrayList<Score> geekScoreboard) {
        this.geekScoreboard = geekScoreboard;
    }
}
