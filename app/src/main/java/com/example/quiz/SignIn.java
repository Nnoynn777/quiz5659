package com.example.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignIn extends AppCompatActivity {

    EditText userEmail, password;
    Button signIn;
    TextView signUp;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_in);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressbar);
        Button signUp=findViewById(R.id.reg);
        Button signIn=findViewById(R.id.create);
        signUp.setOnClickListener(v -> {
            startActivity(new Intent(SignIn.this, SignUp.class));
            finish();
        });
        userEmail=findViewById(R.id.userEmail);
        password=findViewById(R.id.password);
        //signUp=findViewById(R.id.create);
        // Обработка входа
        signIn.setOnClickListener(v -> signInUser());


    }

    private void signInUser() {
        String email = userEmail.getText().toString().trim();
        String passwordStr = password.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            userEmail.setError("Введите email");
            return;
        }

        if (TextUtils.isEmpty(passwordStr)) {
            password.setError("Введите пароль");
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, passwordStr)
                .addOnCompleteListener(this, task -> {
                    progressBar.setVisibility(View.GONE);
                    if (task.isSuccessful()) {
                        // Вход успешен
                        startActivity(new Intent(SignIn.this, ThreatListActivity.class));
                        finish();
                    } else {
                        // Ошибка входа
                        Toast.makeText(SignIn.this, "Ошибка входа: " + task.getException().getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Проверяем, вошел ли пользователь
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            startActivity(new Intent(SignIn.this, ThreatListActivity.class));
            finish();
        }
    }
}