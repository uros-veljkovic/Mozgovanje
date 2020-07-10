package project.mozgovanje.activity.main.fragments.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import project.mozgovanje.activity.quiz.QuizActivity;
import project.mozgovanje.R;
import project.mozgovanje.databinding.FragmentHomeBinding;

import static project.mozgovanje.util.constants.Constants.GEEK_MODE;
import static project.mozgovanje.util.constants.Constants.MODE;
import static project.mozgovanje.util.constants.Constants.TEST_MODE;
import static project.mozgovanje.util.constants.Constants.ZEN_MODE;

public class FragmentHome extends Fragment {

    private FragmentHomeBinding binding;
    private ClickHandler clickHandler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        initBinding(inflater, container);

        return binding.getRoot();
    }

    private void initBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        assert container != null;
        clickHandler = new ClickHandler(container.getContext());
        binding.setClickHandler(clickHandler);
    }

    public class ClickHandler {

        public Context context;

        public ClickHandler(Context context) {
            this.context = context;
        }

        public void onBtnZenMode(View view) {
            startMode(ZEN_MODE);
        }

        public void onBtnTestMode(View view) {
            startMode(TEST_MODE);
        }

        public void onBtnGeekMode(View view) {
            startMode(GEEK_MODE);
        }

        private void startMode(int concreteMode) {
            Intent intent = new Intent(context, QuizActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt(MODE, concreteMode);
            intent.putExtras(bundle);
            startActivity(intent);
            getActivity().finish();
        }

    }

}
