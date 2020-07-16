package project.mozgovanje.db.service;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

import project.mozgovanje.activity.auth.login.LoginActivity;

public class LogoutService {

    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;

    public LogoutService(FirebaseAuth firebaseAuth, FirebaseUser currentUser) {
        this.firebaseAuth = firebaseAuth;
        this.currentUser = currentUser;
    }

    public void logout(final Context context) {
        if (firebaseAuth != null) {
            firebaseAuth.signOut();
            context.startActivity(new Intent(context, LoginActivity.class));
            Toast.makeText(context, "Uspesna odjava !", Toast.LENGTH_SHORT).show();
            ((Activity) context).finish();
        }
    }

}
