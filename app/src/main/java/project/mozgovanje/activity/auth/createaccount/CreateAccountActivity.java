package project.mozgovanje.activity.auth.createaccount;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import project.mozgovanje.R;
import project.mozgovanje.activity.welcome.WelcomeActivity;
import project.mozgovanje.credentials.CreateAccountCredentials;
import project.mozgovanje.db.controller.DatabaseController;

public class CreateAccountActivity extends AppCompatActivity implements View.OnClickListener {


    private EditText etUsername;
    private EditText etEmail;
    private EditText etPassword;
    private EditText etConfirmPassword;
    private Button btnCreateAccount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        initActivityElements();
    }

    private void initActivityElements() {

        etUsername = findViewById(R.id.activity_create_account_etUsername);
        etEmail = findViewById(R.id.activity_create_account_etEmail);
        etPassword = findViewById(R.id.activity_create_account_etPassword);
        etConfirmPassword = findViewById(R.id.activity_create_account_etConfirmPassword);

        btnCreateAccount = findViewById(R.id.activity_create_account_btnCreateAccount);
        btnCreateAccount.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_create_account_btnCreateAccount:
                onBtnCreateAccount();
                break;
            default:
                break;
        }
    }

    private void onBtnCreateAccount() {

        String username = etUsername.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();
        CreateAccountCredentials credentials = new CreateAccountCredentials(username, email, password, confirmPassword);

        DatabaseController.getInstance().setContext(this);
        try {
            DatabaseController.getInstance().createAccount(credentials);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        Intent intent = new Intent(CreateAccountActivity.this, WelcomeActivity.class);
        startActivity(intent);
    }
}