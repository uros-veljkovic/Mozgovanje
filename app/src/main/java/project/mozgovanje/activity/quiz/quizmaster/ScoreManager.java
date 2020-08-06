package project.mozgovanje.activity.quiz.quizmaster;

import android.telephony.SmsManager;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import project.mozgovanje.BR;

public class ScoreManager extends BaseObservable {

    private int increment;
    private int totalPoints;

    public ScoreManager() {
        this.increment = 1;
        this.totalPoints = 0;
    }

    public void correct() {
        totalPoints += increment++;

        notifyPropertyChanged(BR.totalPoints);
        notifyPropertyChanged(BR.increment);
    }

    public void wrong() {
        increment = 1;
        notifyPropertyChanged(BR.increment);
    }

    @Bindable
    public int getTotalPoints() {
        return totalPoints;
    }

    @Bindable
    public int getIncrement() {
        return increment;
    }

    public void setIncrement(int increment) {
        this.increment = increment;
        notifyPropertyChanged(BR.increment);
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
        notifyPropertyChanged(BR.totalPoints);
    }
}
