package com.example.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.EventLogTags;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreen extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        FirebaseApp.initializeApp(this);

        mAuth = FirebaseAuth.getInstance();

        new Handler().postDelayed(() -> {
            FirebaseUser currentUser = mAuth.getCurrentUser();
            if (currentUser != null) {
                // Пользователь вошел, переходим к основному экрану
                startActivity(new Intent(SplashScreen.this, ThreatListActivity.class));
            } else {
                // Пользователь не вошел, переходим к экрану входа
                startActivity(new Intent(SplashScreen.this, SignIn.class));
            }
            finish();
        }, 2000); // Задержка 2 секунды
    }
}