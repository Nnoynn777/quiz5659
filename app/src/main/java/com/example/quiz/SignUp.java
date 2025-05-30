package com.example.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
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

public class SignUp extends AppCompatActivity {

    EditText userName, userEmail, userPhone, password;
    Button register;
    TextView signIn;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        mAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressbar);

        // Переход на вход
        signIn.setOnClickListener(v -> {
            startActivity(new Intent(SignUp.this, SignIn.class));
            finish();
        });

        // Обработка регистрации
        register.setOnClickListener(v -> registerUser());

        userName=findViewById(R.id.userName);
        userEmail=findViewById(R.id.userEmail);
        userPhone=findViewById(R.id.userPhone);
        password=findViewById(R.id.password);
        register=findViewById(R.id.регистрация);
        signIn=findViewById(R.id.Войтиздесь);

    }

    private void registerUser() {
        String name = userName.getText().toString().trim();
        String email = userEmail.getText().toString().trim();
        String phone = userPhone.getText().toString().trim();
        String passwordStr = password.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            userName.setError("Введите имя");
            return;
        }

        if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            userEmail.setError("Введите корректный email");
            return;
        }

        if (TextUtils.isEmpty(phone)) {
            userPhone.setError("Введите телефон");
            return;
        }

        if (TextUtils.isEmpty(passwordStr) || passwordStr.length() < 6) {
            password.setError("Пароль должен быть не менее 6 символов");
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, passwordStr)
                .addOnCompleteListener(this, task -> {
                    progressBar.setVisibility(View.GONE);
                    if (task.isSuccessful()) {
                        // Регистрация успешна
                        FirebaseUser user = mAuth.getCurrentUser();
                        saveAdditionalUserData(user.getUid(), name, email, phone);
                        startActivity(new Intent(SignUp.this, ThreatListActivity.class));
                        finish();
                    } else {
                        // Ошибка регистрации
                        Toast.makeText(SignUp.this, "Ошибка регистрации: " + task.getException().getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void saveAdditionalUserData(String userId, String name, String email, String phone) {
        // Здесь можно сохранить дополнительные данные в Firestore или Realtime Database
        // Пока просто демонстрация
        Log.d("UserData", "User ID: " + userId);
        Log.d("UserData", "Name: " + name);
        Log.d("UserData", "Email: " + email);
        Log.d("UserData", "Phone: " + phone);
    }
}