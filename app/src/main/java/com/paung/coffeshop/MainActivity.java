package com.paung.coffeshop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.math.BigInteger;

public class MainActivity extends AppCompatActivity {
    TextView textViewNumber;
    Button buttonPlus,buttonMin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewNumber = findViewById(R.id.quantity_text_view);
        buttonPlus = findViewById(R.id.btn_plus);
        buttonMin = findViewById(R.id.btn_min);

    }

    @Override
    protected void onStart() {
        super.onStart();
        increment();
        decrement();
    }

    void increment(){
        final int currentValue = Integer.parseInt(textViewNumber.getText().toString());
        buttonPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewNumber.setText(String.valueOf(currentValue + 1));
            }
        });
    }

    void decrement(){
        final int currentValue = Integer.parseInt(textViewNumber.getText().toString());
        buttonMin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewNumber.setText(String.valueOf(String.valueOf(currentValue - 1)));
            }
        });
    }

}
