package project.mozgovanje.model.user;

public class UserScore implements Comparable<UserScore> {

    private String username;
    private int totalAnswersCorrect;

    public UserScore(String username, int totalAnswersCorrect) {
        this.username = username;
        this.totalAnswersCorrect = totalAnswersCorrect;
    }

    public UserScore() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getTotalAnswersCorrect() {
        return totalAnswersCorrect;
    }

    public void setTotalAnswersCorrect(int totalAnswersCorrect) {
        this.totalAnswersCorrect = totalAnswersCorrect;
    }

    @Override
    public int compareTo(UserScore comparingScore) {
        int thisScore = totalAnswersCorrect;
        int otherScore = comparingScore.getTotalAnswersCorrect();
        return (Integer.compare(otherScore, thisScore));
    }
}
