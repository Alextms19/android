package com.example.bills;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class AddRecord extends AppCompatActivity {
    Button calculateBtn;
    EditText fixedExpense;

    ArrayList<Expense> expenses = new ArrayList<>();
    ArrayList<Record> records = new ArrayList<>();
    RecyclerView recyclerView;
    LinearLayout cardView;
    ProgressBar progressBar;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);


     calculateBtn = findViewById(R.id.calculateBtn);
     recyclerView = findViewById(R.id.recyclerView);
     fixedExpense = findViewById(R.id.fixedExpense);
     cardView = findViewById(R.id.cardView);
     progressBar = findViewById(R.id.progressBarAddRecord);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        progressBar.setVisibility(View.VISIBLE);
       DatabaseReference reference= HelperFireBase.getFirebaseReference("Expenses/"+ HelperFireBase.getUserID());
        reference.addValueEventListener(new ValueEventListener(){

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot ds : snapshot.getChildren()
                ) {
                    Expense e = new Expense();
                    e.setExpenseName(ds.getKey());
                    extractRecords(ds);
                    e.setRecord(records.get(records.size()-1));
                    expenses.add(e);

                }
                recyclerView();
                progressBar.setVisibility(View.GONE);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }

    private void extractRecords(@NonNull DataSnapshot ds) {
        for (DataSnapshot dsChild : ds.getChildren()
        ) {
            float[] recordValues = new float[(int) dsChild.getChildrenCount()];
            int index = 0;
            for (DataSnapshot dsGrandChildren : dsChild.getChildren()) {

                recordValues[index++] = Float.parseFloat(Objects.requireNonNull(dsGrandChildren.getValue()).toString());
            }
            for (int i = 0; i < recordValues.length; i+=2) {

                records.add(new Record(recordValues[i], recordValues[i+1]));
            }



        }
    }

    private void openResultPage(Float message) {
        Intent intent = new Intent(this, ResultPage.class);
        intent.putExtra("key", message);

        startActivity(intent);

    }

    private void calculateConsumption(RecyclerViewAdapter recyclerViewAdapter) {
        ArrayList<RecyclerViewAdapter.RecyclerViewHolder> holders = recyclerViewAdapter.getHolders();
        float totalPrice = 0;

        try {

        for (RecyclerViewAdapter.RecyclerViewHolder holder: holders
             ) {
            float currentRecord =  Float.parseFloat(holder.expenseCurrentRecord.getText().toString());
            float lastRecord =  Float.parseFloat(holder.expenseLastRecord.getText().toString());
            float price = Float.parseFloat(holder.expensePrice.getText().toString());
            float result = (currentRecord-lastRecord) * price;
            totalPrice += result;
             addExpenseToDatabase(
                    new Expense(holder.expenseName.getText().toString(),
                            new Record(currentRecord, price)));

        }

        }catch (NumberFormatException e){
            Toast.makeText(this, "Some values were invalid", Toast.LENGTH_SHORT).show();
        }
        try {


            totalPrice = totalPrice + Float.parseFloat(fixedExpense.getText().toString().trim());
        }catch (NumberFormatException ignored){

        }
        openResultPage(totalPrice);
    }

    private void recyclerView() {
        RecyclerViewAdapter recyclerViewAdapter =
                new RecyclerViewAdapter(this, expenses);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        calculateBtn.setOnClickListener(view -> calculateConsumption(recyclerViewAdapter));
    }
    public void addExpenseToDatabase(Expense expense) {

        HelperFireBase.getFirebaseReference("Expenses/"+ HelperFireBase.getUserID()
        )
                .child(expense.getExpenseName())
                .push().setValue(expense.record)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        Toast.makeText(this, "Expense Saved", Toast.LENGTH_SHORT).show();

                    }
                    else
                        Toast.makeText(this, "There was an error.\n Try again later", Toast.LENGTH_LONG).show();
                });

    }


}