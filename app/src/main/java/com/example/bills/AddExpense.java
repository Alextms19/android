package com.example.bills;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddExpense extends AppCompatActivity {

    EditText expenseName;
    EditText currentValue;
    EditText expensePrice;

    Button addExpense;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);
        addExpense = findViewById(R.id.saveExpanseBtn);
        expenseName = findViewById(R.id.expanseNameET);
        expensePrice = findViewById(R.id.priceETND);
        currentValue = findViewById(R.id.currentValueETND);

        addExpense.setOnClickListener(view -> {
            Expense expense =new Expense(expenseName.getText().toString(),
                    new Record(Float.parseFloat(currentValue.getText().toString()),
                            Float.parseFloat(expensePrice.getText().toString())));

            addExpenseToDatabase(expense);
        });

    }

    public void addExpenseToDatabase(Expense expense) {

        HelperFireBase.getFirebaseReference("Expenses/"+ HelperFireBase.getUserID()
                )
                .child(expense.getExpenseName())
                .push().setValue(expense.record)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        Toast.makeText(AddExpense.this, "Expense Saved", Toast.LENGTH_SHORT).show();
                        openAccountExpenses();
                    }
                    else
                        Toast.makeText(AddExpense.this, "There was an error.\n Try again later", Toast.LENGTH_LONG).show();
                });

    }

    private void openAccountExpenses() {
        Intent intent = new Intent(this, AccountExpenses.class);
        startActivity(intent);
    }

}