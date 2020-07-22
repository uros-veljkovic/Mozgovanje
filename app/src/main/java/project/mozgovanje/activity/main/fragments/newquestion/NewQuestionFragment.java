package project.mozgovanje.activity.main.fragments.newquestion;

import android.content.Context;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import project.mozgovanje.R;
import project.mozgovanje.databinding.FragmentNewQuestionBinding;
import project.mozgovanje.db.controller.RepositoryController;
import project.mozgovanje.model.question.Question;
import project.mozgovanje.util.validator.FieldValidator;


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
        binding.fragmentNewQuestionRbA.setChecked(true);
        question.setCorrectAnswerChar("a");
        clickHandler = new ClickHandler(container.getContext());

        binding.setClickHandler(clickHandler);
        binding.setQuestion(question);
    }

    public class ClickHandler {

        public Context context;

        public ClickHandler(Context context) {
            this.context = context;
            correctAnswerA(null);//Just to initialize answer a) as an starting correct answer
        }

        public void onBtnResetujPolja(View view) {
            question = new Question();
            binding.setQuestion(question);
            binding.executePendingBindings();
        }

        public void onBtnSacuvaj(View view) {
            String questionText = question.getQuestionText();
            String answerA = "a) " + question.getAnswer1();
            String answerB = "b) " + question.getAnswer2();
            String answerC = "c) " + question.getAnswer3();
            String answerD = "d) " + question.getAnswer4();
            String correctAnswer = question.getCorrectAnswerChar();
            if (!FieldValidator.validFields(questionText,
                    answerA,
                    answerB,
                    answerC,
                    answerD,
                    correctAnswer)) {
                Toast.makeText(context, "Molimo popunite prazna polja", Toast.LENGTH_SHORT).show();
                return;
            }

            Question newQuestion = new Question(questionText,
                    answerA,
                    answerB,
                    answerC,
                    answerD,
                    correctAnswer);

            RepositoryController.getInstance().createPendingQuestion(context, question);
            onBtnResetujPolja(null);
        }

        public void correctAnswerA(View view) {

            binding.fragmentNewQuestionEtAnswerA.setBackgroundResource(R.drawable.edit_text_squared_green_border);
            binding.fragmentNewQuestionEtAnswerB.setBackgroundResource(R.drawable.edit_text_squared_red_border);
            binding.fragmentNewQuestionEtAnswerC.setBackgroundResource(R.drawable.edit_text_squared_red_border);
            binding.fragmentNewQuestionEtAnswerD.setBackgroundResource(R.drawable.edit_text_squared_red_border);
            question.setCorrectAnswerChar("a");
        }

        public void correctAnswerB(View view) {
            binding.fragmentNewQuestionEtAnswerA.setBackgroundResource(R.drawable.edit_text_squared_red_border);
            binding.fragmentNewQuestionEtAnswerB.setBackgroundResource(R.drawable.edit_text_squared_green_border);
            binding.fragmentNewQuestionEtAnswerC.setBackgroundResource(R.drawable.edit_text_squared_red_border);
            binding.fragmentNewQuestionEtAnswerD.setBackgroundResource(R.drawable.edit_text_squared_red_border);
            question.setCorrectAnswerChar("b");

        }

        public void correctAnswerC(View view) {
            binding.fragmentNewQuestionEtAnswerA.setBackgroundResource(R.drawable.edit_text_squared_red_border);
            binding.fragmentNewQuestionEtAnswerB.setBackgroundResource(R.drawable.edit_text_squared_red_border);
            binding.fragmentNewQuestionEtAnswerC.setBackgroundResource(R.drawable.edit_text_squared_green_border);
            binding.fragmentNewQuestionEtAnswerD.setBackgroundResource(R.drawable.edit_text_squared_red_border);
            question.setCorrectAnswerChar("c");

        }

        public void correctAnswerD(View view) {
            binding.fragmentNewQuestionEtAnswerA.setBackgroundResource(R.drawable.edit_text_squared_red_border);
            binding.fragmentNewQuestionEtAnswerB.setBackgroundResource(R.drawable.edit_text_squared_red_border);
            binding.fragmentNewQuestionEtAnswerC.setBackgroundResource(R.drawable.edit_text_squared_red_border);
            binding.fragmentNewQuestionEtAnswerD.setBackgroundResource(R.drawable.edit_text_squared_green_border);
            question.setCorrectAnswerChar("d");

        }

    }
}