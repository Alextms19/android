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

public class Register extends AppCompatActivity {
    EditText email;
    EditText password;
    EditText confirmPassword;
    Button registerBtn;
    ProgressBar progressBar;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        email = findViewById(R.id.emailRegisterPage);
        password = findViewById(R.id.passwordRegisterPage);
        confirmPassword = findViewById(R.id.confirmPassword);
        registerBtn = findViewById(R.id.btnRegisterPage);
        progressBar = findViewById(R.id.progressBar);
        mAuth = FirebaseAuth.getInstance();

       registerBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
        regiter();

           }
       });


    }

    private void regiter() {
        String emailString=email.getText().toString().trim();
        String passwordString=password.getText().toString().trim();
        String confirmPass = confirmPassword.getText().toString().trim();

        if(emailString.isEmpty()){
            email.setError("Email is required");
            email.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(emailString).matches()){
            email.setError("Provide valid email.");
            email.requestFocus();
            return;
        }
        if(passwordString.isEmpty()){
            password.setError("Password is required");
            password.requestFocus();
            return;
        }
        if(password.length() < 6){
            password.setError("password should be at least 6 characters ");
            password.requestFocus();
            return;
        }
        if(!confirmPass.equals(passwordString)){
            confirmPassword.setError("passwords don't match");
            confirmPassword.requestFocus();
            return;
        }
        email.setError(null);
        password.setError(null);
        confirmPassword.setError(null);

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(emailString, passwordString).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    User user = new User(emailString );
                    HelperFireBase.getFirebaseReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user)
                            .addOnCompleteListener(task1 -> {
                                if (task1.isSuccessful()) {
                                    Toast.makeText(Register.this, "User Registered", Toast.LENGTH_SHORT).show();
                                    openAccountExpenses();
                                } else {
                                    Toast.makeText(Register.this, "Fail to register", Toast.LENGTH_SHORT).show();
                                }
                                progressBar.setVisibility(View.GONE);
                            });
                }else {
                    Toast.makeText(Register.this, "Fail to register. Account already exist", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

    }

    public void openAccountExpenses(){
    startActivity(new Intent(this, AccountExpenses.class));
    }


}