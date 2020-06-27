package project.mozgovanje;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
    }

    private void onBtnCreateAccount() {

    }
}