package project.mozgovanje;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreateAccount extends AppCompatActivity implements View.OnClickListener {


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
        switch (v.getId()){
            case R.id.activity_create_account_btnCreateAccount:
                onBtnCreateAccount();
                break;
            default:
                break;
        }
    }

    private void onBtnCreateAccount() {

    }
}