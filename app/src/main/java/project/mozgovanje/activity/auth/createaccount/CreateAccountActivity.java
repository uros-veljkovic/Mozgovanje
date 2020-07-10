package project.mozgovanje.activity.auth.createaccount;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import project.mozgovanje.R;
import project.mozgovanje.activity.welcome.WelcomeActivity;
import project.mozgovanje.model.credentials.CreateAccountCredentials;
import project.mozgovanje.databinding.ActivityCreateAccountBinding;
import project.mozgovanje.db.controller.DatabaseController;

public class CreateAccountActivity extends AppCompatActivity {


    private ActivityCreateAccountBinding binding;
    private CreateAccountCredentials createAccountCredentials;
    private ClickHandler clickHandler;

//    private Animation buttonAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_account);

        initBinding();
    }

    private void initBinding() {
        createAccountCredentials = new CreateAccountCredentials();
        clickHandler = new ClickHandler(this);

        binding.setCredentials(createAccountCredentials);
        binding.setClickHandler(clickHandler);

    }

    public class ClickHandler {
        private Context context;

        public ClickHandler(Context context) {
            this.context = context;
        }

        public void onBtnCreateAccount(View view) {
            try {
                DatabaseController.getInstance().createAccount(context, createAccountCredentials);
            } catch (Exception e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }

/*        public void startActivityWithDelay(final Class activityClass) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(context, activityClass);
                    startActivity(intent);
                    finish();
                }
            }, 1000);
        }*/
    }
}