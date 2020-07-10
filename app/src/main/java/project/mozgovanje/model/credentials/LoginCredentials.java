package project.mozgovanje.model.credentials;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;
import androidx.databinding.library.baseAdapters.BR;

public class LoginCredentials extends BaseObservable {

    private String email;
    private String password;

    public LoginCredentials(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public LoginCredentials() {
        this.email = "";
        this.password = "";
    }

    @Bindable
    public String getEmail() {
        return email;
    }

    @Bindable
    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
        notifyPropertyChanged(BR.email);
    }

    public void setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(BR.password);
    }

/*    @Bindable
    public TextWatcher getPasswordTextWatcher() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Do nothing.
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setPassword(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Do nothing.
            }
        };
    }

    @Bindable
    public TextWatcher getEmailTextWatcher() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Do nothing.
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setPassword(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Do nothing.
            }
        };
    }*/



}
