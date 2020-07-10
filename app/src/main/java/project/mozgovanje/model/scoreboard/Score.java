package project.mozgovanje.model.scoreboard;

public class Score {

    //TODO: implementiraj da se posle kviza postavlja novi skor sa odredjenim modom
    private String username;
    private String mode;
    private int points;

    public Score(String username, String mode, int points) {
        this.username = username;
        this.points = points;
    }

    public Score() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
}
