package project.mozgovanje.activity.auth.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.firebase.auth.FirebaseAuth;

import project.mozgovanje.R;
import project.mozgovanje.activity.auth.createaccount.CreateAccountActivity;
import project.mozgovanje.databinding.ActivityLoginBinding;
import project.mozgovanje.db.controller.RepositoryController;
import project.mozgovanje.util.exception.FieldsEmptyException;
import project.mozgovanje.model.credentials.LoginCredentials;

public class LoginActivity extends AppCompatActivity {

    private String TAG = "LoginActivity";

    private ActivityLoginBinding binding;
    private LoginCredentials credentials;
    private ClickHandler clickHandler;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        initBinding();
    }


    private void initBinding() {

        clickHandler = new ClickHandler(this);
        credentials = new LoginCredentials();

        binding.setCredentials(credentials);
        binding.setClickHandler(clickHandler);

    }

    public class ClickHandler {

        public Context context;

        public ClickHandler(Context context) {
            this.context = context;
        }

        public void onBtnCreateAccount(View view) {
            startActivity(new Intent(context, CreateAccountActivity.class));
            finish();
        }

        public void onBtnLogin(View view) {
            try {
                RepositoryController.getInstance().login(context, credentials);
            } catch (FieldsEmptyException e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

    }
}