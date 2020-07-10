package project.mozgovanje.model.credentials;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import project.mozgovanje.BR;

public class CreateAccountCredentials extends BaseObservable {
    private String username;
    private String email;
    private String password;
    private String confirmPassword;

    public CreateAccountCredentials(String username, String email, String password, String confirmPassword) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public CreateAccountCredentials() {
        this.username = "";
        this.email = "";
        this.password = "";
        this.confirmPassword = "";
    }

    @Bindable
    public String getUsername() {
        return username;
    }
    @Bindable
    public String getEmail() {
        return email;
    }
    @Bindable
    public String getPassword() {
        return password;
    }
    @Bindable
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setUsername(String username) {
        this.username = username;
        notifyPropertyChanged(BR.username);
    }

    public void setEmail(String email) {
        this.email = email;
        notifyPropertyChanged(BR.email);
    }

    public void setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(BR.password);
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        notifyPropertyChanged(BR.confirmPassword);
    }
}
