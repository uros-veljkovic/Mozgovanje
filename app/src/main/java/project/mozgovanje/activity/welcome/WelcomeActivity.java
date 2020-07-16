package project.mozgovanje.activity.welcome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import pl.droidsonroids.gif.GifImageView;
import project.mozgovanje.R;
import project.mozgovanje.activity.admin.AdminActivity;
import project.mozgovanje.activity.main.MainActivity;
import project.mozgovanje.model.api.UserAPI;
import project.mozgovanje.util.constants.Constants;

public class WelcomeActivity extends AppCompatActivity {

    private GifImageView giv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        showGIF();
        launchMainActivity();
    }

    private void showGIF() {
        giv = findViewById(R.id.activity_welcome_gifMozgovanje);
        giv.setVisibility(View.VISIBLE);
    }

    private void launchMainActivity() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (UserAPI.getInstance().getEmail().equals(Constants.ADMIN_EMAIL)) {
                    startActivity(new Intent(WelcomeActivity.this, AdminActivity.class));
                } else {
                    startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                }
            }
        }, 5000);
    }

}