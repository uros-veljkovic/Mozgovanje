package project.mozgovanje.activity.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import project.mozgovanje.R;
import project.mozgovanje.activity.main.fragments.FragmentAllQuestions;
import project.mozgovanje.activity.main.fragments.FragmentHome;
import project.mozgovanje.activity.main.fragments.FragmentScoreboard;
import project.mozgovanje.activity.main.fragments.FragmentUser;
import project.mozgovanje.model.api.UserAPI;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initActivityElements();
    }

    private void initActivityElements() {
        BottomNavigationView bottomNav = findViewById(R.id.activity_main_bottom_navigation);
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
                    selectedFragment = new FragmentScoreboard();
                    break;
                case R.id.activity_main_nav_all_questions:
                    selectedFragment = new FragmentAllQuestions();
                    break;
                case R.id.activity_main_nav_user:
                    selectedFragment = new FragmentUser();
                    break;
                default:
                    break;
            }

            assert selectedFragment != null;
            getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_fragment_container,
                    selectedFragment).commit();


            return true;
        }
    };


}