package project.mozgovanje.activity.quiz.quizmaster;

import java.util.ArrayList;

import project.mozgovanje.model.question.Question;


public class AnsweredQuestions {

    private ArrayList<Question> wrongQuestions;
    private ArrayList<Question> correctQuestions;
    private ArrayList<Integer> indexes;

    public AnsweredQuestions(){
        wrongQuestions = new ArrayList<>();
        correctQuestions = new ArrayList<>();
        indexes = new ArrayList<>();
    }

    public ArrayList<Question> getWrongQuestions() {
        return wrongQuestions;
    }
    public void setWrongQuestions(ArrayList<Question> wrongQuestions) {
        this.wrongQuestions = wrongQuestions;
    }

    public ArrayList<Question> getCorrectQuestions() {
        return correctQuestions;
    }
    public void setCorrectQuestions(ArrayList<Question> correctQuestions) {
        this.correctQuestions = correctQuestions;
    }

    public ArrayList<Integer> getIndexes() {
        return indexes;
    }
    public void setIndexes(ArrayList<Integer> indexes) {
        this.indexes = indexes;
    }

    public int getSize(){
        return indexes.size();
    }

    public boolean containsIndex(int index){
        return indexes.contains(index);
    }

    public boolean isEmpty(){
        return indexes.isEmpty();
    }

    public void addWrongQuestion(Question question){
        wrongQuestions.add(question);
    }
    public void addCorrectQuestion(Question question){
        correctQuestions.add(question);
    }
    public void addNewIndex(int index){
        indexes.add(index);
    }




}
