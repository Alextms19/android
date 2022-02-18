package com.example.bills;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    private Button registerButton;
    private Button loginButton;
    private EditText emailLogin;
    private EditText passwordLogin;
    private ProgressBar progressBar;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        emailLogin = findViewById(R.id.emailLogin);
        passwordLogin = findViewById(R.id.passwordLogin);
        progressBar = findViewById(R.id.progressBarLogin);
        registerButton = findViewById(R.id.registerButton);
        loginButton = findViewById(R.id.loginButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRegisterPage();
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                login();
            }
        });


    }

    private void login() {
        String email = emailLogin.getText().toString().trim();
        String password = passwordLogin.getText().toString().trim();

        if (email.isEmpty()) {
            emailLogin.setError("Email is required");
            emailLogin.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailLogin.setError("Provide valid email.");
            emailLogin.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            passwordLogin.setError("Password is required");
            passwordLogin.requestFocus();
            return;
        }
        if (password.length() < 6) {
            passwordLogin.setError("password should be at least 6 characters ");
            passwordLogin.requestFocus();
            return;
        }
        emailLogin.setError(null);
        passwordLogin.setError(null);
        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    openAccountExpenses();
                    Toast.makeText(Login.this, "Logged in", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(Login.this, "Login Failed. Try again!", Toast.LENGTH_SHORT).show();

                }
            }
        });
        progressBar.setVisibility(View.GONE);


    }


    private void openRegisterPage() {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }

    public void openAccountExpenses() {
        Intent intent = new Intent(this, AccountExpenses.class);
        startActivity(intent);
    }

}