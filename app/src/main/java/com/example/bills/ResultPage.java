package com.example.bills;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;
import java.util.Locale;

public class ResultPage extends AppCompatActivity {
    TextView result ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_page);

        Bundle extras = getIntent().getExtras();
        result = findViewById(R.id.textView5);


        if (extras != null) {

            Float value = extras.getFloat("key");
            result.setText(NumberFormat.getCurrencyInstance(new Locale("ro", "Ro")).format(value));
        }
    }


}