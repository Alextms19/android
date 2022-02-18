package com.example.bills;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;

public class AccountExpenses extends AppCompatActivity {
    ImageButton addRecordButton;
    ImageButton addExpenseButton;
    Button buttonLogout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_expenses);

        addRecordButton = findViewById(R.id.addRecordButton);
        addExpenseButton = findViewById(R.id.addExpenseButton);
        buttonLogout = findViewById(R.id.buttonLogout);
        if(FirebaseAuth.getInstance().getCurrentUser() != null){

            buttonLogout.setVisibility(View.VISIBLE);
        }

        addRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddRecording();
            }
        });
        addExpenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddExpense();
            }
        });
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(AccountExpenses.this, MainActivity.class));
            }
        });
    }
    public void openAddRecording(){
        Intent intent = new Intent(this, AddRecord.class);
        startActivity(intent);
    }

    public void openAddExpense() {
        Intent intent = new Intent(this, AddExpense.class);
        startActivity(intent);
    }
}