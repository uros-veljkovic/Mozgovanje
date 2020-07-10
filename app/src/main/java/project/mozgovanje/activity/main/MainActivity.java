package project.mozgovanje.activity.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import project.mozgovanje.R;
import project.mozgovanje.activity.main.fragments.allquestions.AllQuestionsFragment;
import project.mozgovanje.activity.main.fragments.home.FragmentHome;
import project.mozgovanje.activity.main.fragments.newquestion.NewQuestionFragment;
import project.mozgovanje.activity.main.fragments.scoreboard.ScoreboardFragment;
import project.mozgovanje.activity.main.fragments.user.FragmentUser;
import project.mozgovanje.db.controller.DatabaseController;
import project.mozgovanje.model.api.UserAPI;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initActivityElements();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.more_options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.btnLogout:
                DatabaseController.getInstance().logout(this);
                return true;
            default:
                break;
        }
        return false;
    }

    private void initActivityElements() {
        bottomNav = findViewById(R.id.activity_main_bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_fragment_container, new FragmentHome()).commit();
        Toast.makeText(MainActivity.this, "Hi " + UserAPI.getInstance().getUsername(), Toast.LENGTH_SHORT).show();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.activity_main_nav_home:
                    selectedFragment = new FragmentHome();
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
    };


}