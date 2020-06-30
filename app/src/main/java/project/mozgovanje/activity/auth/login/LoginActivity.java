package project.mozgovanje.activity.auth.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import project.mozgovanje.R;
import project.mozgovanje.activity.auth.createaccount.CreateAccountActivity;
import project.mozgovanje.activity.welcome.WelcomeActivity;
import project.mozgovanje.credentials.LoginCredentials;
import project.mozgovanje.db.controller.DatabaseController;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etEmail;
    private EditText etPassword;
    private Button btnLogin;
    private Button btnCreateAccoutn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initActivityElements();
    }

    private void initActivityElements() {
        etEmail = findViewById(R.id.activity_login_etEmail);
        etPassword = findViewById(R.id.activity_login_etPassword);
        btnLogin = findViewById(R.id.activity_login_btnLogin);
        btnCreateAccoutn = findViewById(R.id.activity_login_btnCreateAccount);

        btnLogin.setOnClickListener(this);
        btnCreateAccoutn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_login_btnLogin:
                onBtnLogin();
                break;
            case R.id.activity_login_btnCreateAccount:
                onBtnCreateAccount();
                break;
            default:
                break;
        }

    }

    private void onBtnLogin() {

        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        LoginCredentials loginCredentials = new LoginCredentials(email, password);

        DatabaseController.getInstance().setContext(this);
        DatabaseController.getInstance().login(loginCredentials);

        Intent intent = new Intent(LoginActivity.this, WelcomeActivity.class);
        startActivity(intent);
    }

    private void onBtnCreateAccount() {
        Intent intent = new Intent(LoginActivity.this, CreateAccountActivity.class);
        startActivity(intent);
    }
}