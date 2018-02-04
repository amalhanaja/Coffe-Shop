package com.paung.coffeshop;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import java.math.BigInteger;
import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {
    TextView textViewNumber, total;
    Button buttonPlus,buttonMin, orderButton;
    CheckBox cream, choco;
    int quantity = 0;

    int hargaChoco = 5000;
    int creamHarga = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewNumber = findViewById(R.id.quantity_text_view);
        total = findViewById(R.id.txt_total_value);
        buttonPlus = findViewById(R.id.btn_plus);
        buttonMin = findViewById(R.id.btn_min);
        orderButton = findViewById(R.id.btn_order);
        cream = findViewById(R.id.whipped_cream_checkbox);
        choco = findViewById(R.id.chocolate_checkbox);

        setupView();
    }

    private void setupView() {
        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitOrder();
            }
        });
        buttonMin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrement();
                int price = calculatePrice(cream.isChecked(), choco.isChecked());
                total.setText("" + price);
            }
        });
        buttonPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increment();
                int price = calculatePrice(cream.isChecked(), choco.isChecked());
                total.setText("" + price);
            }
        });
        choco.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int price = calculatePrice(cream.isChecked(), choco.isChecked());
                total.setText("" + price);
            }
        });

        cream.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int price = calculatePrice(cream.isChecked(), choco.isChecked());
                total.setText("" + price);
            }
        });
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment() {
        if (quantity == 100) {
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement() {
        if (quantity == 0) {
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder() {
        // Get user's name
        EditText nameField = (EditText) findViewById(R.id.name_field);
        Editable nameEditable = nameField.getText();
        String name = nameEditable.toString();

        // Figure out if the user wants whipped cream topping
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        // Figure out if the user wants choclate topping
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        // Calculate the price
        int price = calculatePrice(hasWhippedCream, hasChocolate);

        // Display the order summary on the screen
        String message = createOrderSummary(name, price, hasWhippedCream, hasChocolate);

        // Use an intent to launch an email app.
        // Send the order summary in the email body.
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT,
                getString(R.string.order_summary_email_subject, name));
        intent.putExtra(Intent.EXTRA_TEXT, message);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * Calculates the price of the order.
     *
     * @param addWhippedCream is whether or not we should include whipped cream topping in the price
     * @param addChocolate    is whether or not we should include chocolate topping in the price
     * @return total price
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        // First calculate the price of one cup of coffee
        int hargaKopi = 10000;

        // If the user wants whipped cream, add $1 per cup
        if (addWhippedCream) {
            hargaKopi = hargaKopi + creamHarga;
        }

        // If the user wants chocolate, add $2 per cup
        if (addChocolate) {
            hargaKopi = hargaKopi + hargaChoco;
        }

        // Calculate the total order price by multiplying by the quantity
        return quantity * hargaKopi;
    }

    /**
     * Create summary of the order.
     *
     * @param name            on the order
     * @param price           of the order
     * @param addWhippedCream is whether or not to add whipped cream to the coffee
     * @param addChocolate    is whether or not to add chocolate to the coffee
     * @return text summary
     */
    private String createOrderSummary(String name, int price, boolean addWhippedCream,
                                      boolean addChocolate) {
        String priceMessage = getString(R.string.order_summary_name, name);
        priceMessage += "\n" + getString(R.string.order_summary_whipped_cream, addWhippedCream);
        priceMessage += "\n" + getString(R.string.order_summary_chocolate, addChocolate);
        priceMessage += "\n" + getString(R.string.order_summary_quantity, quantity);
        priceMessage += "\n" + getString(R.string.order_summary_price,
                NumberFormat.getCurrencyInstance().format(price));
        priceMessage += "\n" + getString(R.string.thank_you);
        return priceMessage;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int numberOfCoffees) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCoffees);
    }
}

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        inisialisasiView();
//        increment();
//        decrement();
//        pilihTopping();
//    }
//
//    private void inisialisasiView() {
//        textViewNumber = findViewById(R.id.quantity_text_view);
//        buttonPlus = (Button)findViewById(R.id.btn_plus);
//        buttonMin =  (Button)findViewById(R.id.btn_min);
//        cream = findViewById(R.id.whipped_cream_checkbox);
//        choco = findViewById(R.id.chocolate_checkbox);
//        total = findViewById(R.id.txt_total_value);
//    }
//
//    private void displayTotal(int totalVal){
//        Log.d("TAG", String.valueOf(totalVal));
//        total.setText("" + totalVal);
//    }
//
//    private void pilihTopping() {
//        choco.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                int currentTotal = Integer.parseInt(textViewNumber.getText().toString());
//                if (isChecked){
//                    displayTotal(currentTotal * hargaChoco);
//                }
//            }
//        });
//        cream.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                int currentTotal = Integer.parseInt(textViewNumber.getText().toString());
//                if (isChecked){
//                    displayTotal(currentTotal * hargaChoco);
//                }
//            }
//        });
//    }
//
//    void increment(){
//        buttonPlus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int currentValue = Integer.parseInt(textViewNumber.getText().toString());
//                textViewNumber.setText(String.valueOf(currentValue + 1));
//            }
//        });
//    }
//
//    void decrement(){
//        buttonMin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int currentValue = Integer.parseInt(textViewNumber.getText().toString());
//                textViewNumber.setText(String.valueOf(String.valueOf(currentValue - 1)));
//            }
//        });
//    }

//}
