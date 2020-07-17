package project.mozgovanje.activity.auth.createaccount;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import java.util.regex.Pattern;

import project.mozgovanje.R;
import project.mozgovanje.activity.auth.login.LoginActivity;
import project.mozgovanje.model.credentials.CreateAccountCredentials;
import project.mozgovanje.databinding.ActivityCreateAccountBinding;
import project.mozgovanje.db.controller.DatabaseController;
import project.mozgovanje.util.constants.Constants;
import project.mozgovanje.util.exception.FieldsEmptyException;

public class CreateAccountActivity extends AppCompatActivity {

    private ActivityCreateAccountBinding binding;
    private CreateAccountCredentials createAccountCredentials;
    private ClickHandler clickHandler;
    private boolean validEmail = false;
    private boolean validPassword = false;
    private boolean validConfirmPassword = false;
    private boolean validUsername = false;
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    "(?=.*[a-z])" +         //at least 1 lower case letter
                    "(?=.*[A-Z])" +         //at least 1 upper case letter
//                    "(?=.*[a-zA-Z])" +      //any letter
//                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{6,}" +               //at least 6 characters
                    "$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_account);

        init();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(CreateAccountActivity.this, LoginActivity.class));
    }

    private void init() {
        initBinding();
        observeCredentialFields();
    }

    private void initBinding() {
        createAccountCredentials = new CreateAccountCredentials();
        clickHandler = new ClickHandler(this);
        binding.setCredentials(createAccountCredentials);
        binding.setClickHandler(clickHandler);
    }

    private void observeCredentialFields() {
        binding.activityCreateAccountEtUsername.addTextChangedListener(usernameWatcher);
        binding.activityCreateAccountEtEmail.addTextChangedListener(emailWatcher);
        binding.activityCreateAccountEtPassword.addTextChangedListener(passwordWatcher);
        binding.activityCreateAccountEtConfirmPassword.addTextChangedListener(confirmPasswordWatcher);
    }

    public class ClickHandler {
        private Context context;

        public ClickHandler(Context context) {
            this.context = context;
        }

        public void onBtnCreateAccount(View view) {
            try {
                DatabaseController.getInstance().createAccount(context, createAccountCredentials);
            } catch (FieldsEmptyException e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private TextWatcher emailWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence email, int start, int count, int after) {
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.activityCreateAccountTilEmail.setError(Constants.ERROR_EMAIL);
                validEmail = false;
                binding.activityCreateAccountBtnCreateAccount.setEnabled(false);
            } else {
                binding.activityCreateAccountTilEmail.setError(null);
                validEmail = true;
                if (validEmail && validPassword && validConfirmPassword && validUsername)
                    binding.activityCreateAccountBtnCreateAccount.setEnabled(true);
            }
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    private TextWatcher passwordWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence password, int start, int before, int count) {

            if (!PASSWORD_PATTERN.matcher(password).matches()) {
                binding.activityCreateAccountTilPassword.setError(Constants.ERROR_PASSWORD);
                validPassword = false;
                binding.activityCreateAccountBtnCreateAccount.setEnabled(false);
            } else {
                validPassword = true;
                binding.activityCreateAccountTilPassword.setError(null);
                if (validEmail && validPassword && validConfirmPassword && validUsername)
                    binding.activityCreateAccountBtnCreateAccount.setEnabled(true);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    private TextWatcher confirmPasswordWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (!createAccountCredentials.getPassword().equals(s.toString())) {

                binding.activityCreateAccountTilConfirmPassword.setError(Constants.ERROR_CONFIRM_PASSWORD);
                validConfirmPassword = false;
                binding.activityCreateAccountBtnCreateAccount.setEnabled(false);
            } else {
                validConfirmPassword = true;
                binding.activityCreateAccountTilConfirmPassword.setError(null);
                if (validEmail && validPassword && validConfirmPassword && validUsername)
                    binding.activityCreateAccountBtnCreateAccount.setEnabled(true);
            }


        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    private TextWatcher usernameWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() < 4) {
                binding.activityCreateAccountTilUsername.setError(Constants.ERROR_USERNAME_TOO_WEEK);
                validUsername = false;
                binding.activityCreateAccountBtnCreateAccount.setEnabled(false);
            } else if (s.length() > 15) {
                binding.activityCreateAccountTilUsername.setError(Constants.ERROR_USERNAME_TOO_STRONG);
                validUsername = false;
                binding.activityCreateAccountBtnCreateAccount.setEnabled(false);
            } else {
                binding.activityCreateAccountTilConfirmPassword.setError(null);
                if (validEmail && validPassword && validConfirmPassword && validUsername)
                    binding.activityCreateAccountBtnCreateAccount.setEnabled(true);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}