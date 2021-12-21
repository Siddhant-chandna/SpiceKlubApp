package com.example.shopondoor.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.shopondoor.MainActivity;
import com.example.shopondoor.R;
import com.google.firebase.auth.FirebaseAuth;

public class WelcomeActivity extends AppCompatActivity {
    FirebaseAuth auth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        auth=FirebaseAuth.getInstance();
        progressBar=findViewById(R.id.welcomeProgress);
        progressBar.setVisibility(View.GONE);

        if(auth.getCurrentUser() != null){
            progressBar.setVisibility(View.VISIBLE);
            startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
            Toast.makeText(this, "Logging you In!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    public void login(View view) {
        startActivity(new Intent(WelcomeActivity.this, LogInActivity.class));
    }

    public void registration(View view) {
        startActivity(new Intent(WelcomeActivity.this, SignUpActivity.class));
    }
}