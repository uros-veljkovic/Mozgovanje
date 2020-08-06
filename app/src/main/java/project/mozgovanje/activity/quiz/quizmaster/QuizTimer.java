package project.mozgovanje.activity.quiz.quizmaster;

import android.os.CountDownTimer;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableBoolean;

import project.mozgovanje.BR;
import project.mozgovanje.util.observer.QuizEventListener;

public class QuizTimer extends BaseObservable {

    private CountDownTimer timer;
    private QuizEventListener listener;
    private int timeLeft;
    private ObservableBoolean warningTime;

    public QuizTimer(QuizEventListener listener) {
        this.listener = listener;
        this.warningTime = new ObservableBoolean(false);
        start();
    }

    public void start() {
        timer = new CountDownTimer(20000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft = (int) (millisUntilFinished / 1000);
                notifyPropertyChanged(BR.timeLeft);
                if (timeLeft < 6){
                    warningTime.set(true);
                    notifyPropertyChanged(BR.warningTime);
                }
            }

            @Override
            public void onFinish() {
                listener.onTimeOut();
            }
        }.start();
    }

    public void stop() {
        timer.cancel();
        
    }

    @Bindable
    public int getTimeLeft() {
        return timeLeft;
    }
    public void setTimeLeft(int timeLeft) {
        this.timeLeft = timeLeft;
        notifyPropertyChanged(BR.timeLeft);
    }

    @Bindable
    public ObservableBoolean getWarningTime() {
        return warningTime;
    }

    public void setWarningTime(ObservableBoolean warningTime) {
        this.warningTime = warningTime;
        notifyPropertyChanged(BR.warningTime);
    }
}
