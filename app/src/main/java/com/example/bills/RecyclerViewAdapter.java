package com.example.bills;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {
    Context context;
    ArrayList<Expense> expense;

    public ArrayList<RecyclerViewHolder> getHolders() {
        return holders;
    }

    public void setHolders(RecyclerViewHolder holder) {
        this.holders.add(holder);
    }

    private ArrayList<RecyclerViewHolder> holders = new ArrayList<>();

    public RecyclerViewAdapter(Context context, ArrayList<Expense> expenses) {
        this.context=context;
        this.expense= expenses;

    }



    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view =inflater.inflate(R.layout.card_design, parent, false);
        RecyclerViewHolder r = new RecyclerViewHolder(view);

        return r;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        Expense e = expense.get(position);
        holder.expensePrice.setText(String.valueOf(e.record.getRecordPrice()));
        holder.expenseLastRecord.setText(String.valueOf(e.record.getLastRecord()));
        holder.expenseName.setText(e.getExpenseName());
        setHolders(holder);


    }

    @Override
    public int getItemCount() {
        return expense.size();
    }


    public static class RecyclerViewHolder  extends RecyclerView.ViewHolder{
        TextView expenseName;
        EditText expensePrice;
        EditText expenseLastRecord;
        EditText expenseCurrentRecord;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            expenseName = itemView.findViewById(R.id.expenseName);
            expensePrice = itemView.findViewById(R.id.price);
            expenseLastRecord = itemView.findViewById(R.id.lastRecord);
            expenseCurrentRecord = itemView.findViewById(R.id.currentRecord);

        }

    }
}
