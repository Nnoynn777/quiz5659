package com.example.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(getApplicationContext());

        mAuth = FirebaseAuth.getInstance();

        // Проверка аутентификации
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            startActivity(new Intent(this, SignIn.class));
            finish();
        }
        else {
            startActivity(new Intent(this, ThreatListActivity.class));
            finish();
        }
    }

    private void signOut() {
        mAuth.signOut();
        startActivity(new Intent(this, SignIn.class));
        finish();
    }
}