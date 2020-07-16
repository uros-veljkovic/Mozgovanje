package project.mozgovanje.model.question;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.google.firebase.firestore.Exclude;

import project.mozgovanje.BR;

public class Question extends BaseObservable implements Parcelable, Comparable<Question> {

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

    public Question(String questionText, String answer1, String answer2, String answer3, String answer4, String correctAnswerChar) {
        this.questionText = questionText;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.answer4 = answer4;
        this.correctAnswerChar = correctAnswerChar;
    }

    public Question() {
    }

    protected Question(Parcel in) {
        super();
        readFromParcel(in);
    }


    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    public boolean userAnsweredCorrectly(String answer) {
        return correctAnswerChar.contains(answer);
    }

    @Bindable
    public int getQuestionID() {
        return questionID;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
        notifyPropertyChanged(BR.questionID);
    }

    @Bindable
    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
        notifyPropertyChanged(BR.questionText);
    }

    @Bindable
    public String getAnswer1() {
        return answer1;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
        notifyPropertyChanged(BR.answer1);
    }

    @Bindable
    public String getAnswer2() {
        return answer2;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
        notifyPropertyChanged(BR.answer2);
    }

    @Bindable
    public String getAnswer3() {
        return answer3;
    }

    public void setAnswer3(String answer3) {
        this.answer3 = answer3;
        notifyPropertyChanged(BR.answer3);
    }

    @Bindable
    public String getAnswer4() {
        return answer4;
    }

    public void setAnswer4(String answer4) {
        this.answer4 = answer4;
        notifyPropertyChanged(BR.answer4);

    }

    @Bindable
    public void setCorrectAnswerChar(String correctAnswerChar) {
        this.correctAnswerChar = correctAnswerChar;
        notifyPropertyChanged(BR.correctAnswerChar);
    }

    public String getCorrectAnswerChar() {
        return correctAnswerChar;
    }

    @Exclude
    public boolean isAnswerACorrect() {
        return answer1.startsWith(correctAnswerChar);
    }

    @Exclude
    public boolean isAnswerBCorrect() {
        return answer2.startsWith(correctAnswerChar);
    }

    @Exclude
    public boolean isAnswerCCorrect() {
        return answer3.startsWith(correctAnswerChar);
    }

    @Exclude
    public boolean isAnswerDCorrect() {
        return answer4.startsWith(correctAnswerChar);
    }

    @Override
    public String toString() {
        Log.d("==== Question ====", "toString: " + questionID);
        return
                questionText + "\n"
                        + "\t" + answer1 + "\n"
                        + "\t" + answer2 + "\n"
                        + "\t" + answer3 + "\n"
                        + "\t" + answer4 + "\n";
    }

    // ===== Parcelable methods =====
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(questionID);
        dest.writeString(questionText);
        dest.writeString(answer1);
        dest.writeString(answer2);
        dest.writeString(answer3);
        dest.writeString(answer4);
        dest.writeString(correctAnswerChar);
    }

    private void readFromParcel(Parcel in) {
        questionID = in.readInt();
        questionText = in.readString();
        answer1 = in.readString();
        answer2 = in.readString();
        answer3 = in.readString();
        answer4 = in.readString();
        correctAnswerChar = in.readString();
    }

    @Override
    public int compareTo(Question o) {
        int thisQuestionNumber = this.questionID;
        int otherQuestionNumber = o.getQuestionID();
        return (Integer.compare(thisQuestionNumber, otherQuestionNumber));
    }
}
