package project.mozgovanje.model;

public class Question {

    private int questionID;
    private String questionText;
    private String answer1;
    private String answer2;
    private String answer3;
    private String answer4;
    private String correctAnswerChar;

    public Question(int questionID, String questionText, String answer1, String answer2, String answer3, String answer4, String correctAnswerChar) {
        this.questionID = questionID;
        this.questionText = questionText;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.answer4 = answer4;
        this.correctAnswerChar = correctAnswerChar;
    }

    public Question() {
    }

    public int getQuestionID() {
        return questionID;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getAnswer1() {
        return answer1;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    public String getAnswer3() {
        return answer3;
    }

    public void setAnswer3(String answer3) {
        this.answer3 = answer3;
    }

    public String getAnswer4() {
        return answer4;
    }

    public void setAnswer4(String answer4) {
        this.answer4 = answer4;
    }

    public String getCorrectAnswerChar() {
        return correctAnswerChar;
    }

    public void setCorrectAnswerChar(String correctAnswerChar) {
        this.correctAnswerChar = correctAnswerChar;
    }
}
