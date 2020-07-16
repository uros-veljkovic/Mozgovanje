package project.mozgovanje.activity.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import project.mozgovanje.R;
import project.mozgovanje.activity.main.fragments.allquestions.AllQuestionsFragment;
import project.mozgovanje.activity.main.fragments.home.FragmentHome;
import project.mozgovanje.activity.main.fragments.newquestion.NewQuestionFragment;
import project.mozgovanje.activity.main.fragments.scoreboard.ScoreboardFragment;
import project.mozgovanje.activity.main.fragments.user.FragmentUser;
import project.mozgovanje.databinding.ActivityMainBinding;
import project.mozgovanje.db.controller.DatabaseController;
import project.mozgovanje.model.api.UserAPI;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ClickHandler clickHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding();
        initHomeFragment();
    }

    private void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        clickHandler = new ClickHandler(this);
        binding.setClickHandler(clickHandler);
    }

    private void initHomeFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_fragment_container, new FragmentHome()).commit();
        Toast.makeText(MainActivity.this, "Hi " + UserAPI.getInstance().getUsername(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.more_options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btnLogout:
                DatabaseController.getInstance().logout(this);
                return true;
            default:
                break;
        }
        return false;
    }


    public class ClickHandler {

        public Context context;

        public ClickHandler(Context context) {
            this.context = context;
        }

        public boolean onNavigationClick(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.activity_main_nav_home:
                    selectedFragment = new FragmentHome(); //Gotov Binding
                    break;
                case R.id.activity_main_nav_scoreboard:
                    selectedFragment = new ScoreboardFragment();
                    break;
                case R.id.activity_main_nav_new_question:
                    selectedFragment = new NewQuestionFragment();
                    break;
                case R.id.activity_main_nav_all_questions:
                    selectedFragment = new AllQuestionsFragment();
                    break;
                case R.id.activity_main_nav_user:
                    selectedFragment = new FragmentUser();
                    break;
                default:
                    break;
            }

            assert selectedFragment != null;

            //Ovde kaze zameni mi FrameLayout sa izabranim fragmentom
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.activity_main_fragment_container,
                            selectedFragment)
                    .commit();


            return true;
        }

    }

}