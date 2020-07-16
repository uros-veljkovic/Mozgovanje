package project.mozgovanje.activity.main.fragments.newquestion;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.common.io.Resources;

import java.io.File;

import project.mozgovanje.R;
import project.mozgovanje.databinding.FragmentNewQuestionBinding;
import project.mozgovanje.db.controller.DatabaseController;
import project.mozgovanje.model.question.Question;
import project.mozgovanje.util.constants.Constants;


public class NewQuestionFragment extends Fragment {

    private FragmentNewQuestionBinding binding;
    private ClickHandler clickHandler;
    private Question question;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        initBinding(inflater, container);
        return binding.getRoot();
    }

    private void initBinding(LayoutInflater inflater, ViewGroup container) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_new_question, container, false);
        assert container != null;

        question = new Question();
        question.setCorrectAnswerChar("a");
        clickHandler = new ClickHandler(container.getContext());

        binding.setClickHandler(clickHandler);
        binding.setQuestion(question);
    }

    public class ClickHandler {

        public Context context;

        public ClickHandler(Context context) {
            this.context = context;
        }

        public void onBtnResetujPolja(View view){
            question = new Question();
            binding.notifyChange();
        }

        public void onBtnSacuvaj(View view){
            String questionText = question.getQuestionText();
            String answerA = "a) " + question.getAnswer1();
            String answerB = "b) " + question.getAnswer2();
            String answerC = "c) " + question.getAnswer3();
            String answerD = "d) " + question.getAnswer4();
            String correctAnswer = question.getCorrectAnswerChar();

            Question newQuestion = new Question(questionText, answerA, answerB, answerC, answerD, correctAnswer);
            DatabaseController.getInstance().createPendingQuestion(context, question);
        }

        public void correctAnswerA(View view) {

            binding.fragmentNewQuestionEtAnswerA.setBackgroundResource(R.drawable.edit_text_green_border);
            binding.fragmentNewQuestionEtAnswerB.setBackgroundResource(R.drawable.edit_text_red_border);
            binding.fragmentNewQuestionEtAnswerC.setBackgroundResource(R.drawable.edit_text_red_border);
            binding.fragmentNewQuestionEtAnswerD.setBackgroundResource(R.drawable.edit_text_red_border);
            question.setCorrectAnswerChar("a");
        }

        public void correctAnswerB(View view) {
            binding.fragmentNewQuestionEtAnswerA.setBackgroundResource(R.drawable.edit_text_red_border);
            binding.fragmentNewQuestionEtAnswerB.setBackgroundResource(R.drawable.edit_text_green_border);
            binding.fragmentNewQuestionEtAnswerC.setBackgroundResource(R.drawable.edit_text_red_border);
            binding.fragmentNewQuestionEtAnswerD.setBackgroundResource(R.drawable.edit_text_red_border);
            question.setCorrectAnswerChar("b");

        }

        public void correctAnswerC(View view) {
            binding.fragmentNewQuestionEtAnswerA.setBackgroundResource(R.drawable.edit_text_red_border);
            binding.fragmentNewQuestionEtAnswerB.setBackgroundResource(R.drawable.edit_text_red_border);
            binding.fragmentNewQuestionEtAnswerC.setBackgroundResource(R.drawable.edit_text_green_border);
            binding.fragmentNewQuestionEtAnswerD.setBackgroundResource(R.drawable.edit_text_red_border);
            question.setCorrectAnswerChar("c");

        }

        public void correctAnswerD(View view) {
            binding.fragmentNewQuestionEtAnswerA.setBackgroundResource(R.drawable.edit_text_red_border);
            binding.fragmentNewQuestionEtAnswerB.setBackgroundResource(R.drawable.edit_text_red_border);
            binding.fragmentNewQuestionEtAnswerC.setBackgroundResource(R.drawable.edit_text_red_border);
            binding.fragmentNewQuestionEtAnswerD.setBackgroundResource(R.drawable.edit_text_green_border);
            question.setCorrectAnswerChar("d");

        }

    }
}