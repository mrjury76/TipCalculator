package com.example.tipcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
/** @noinspection ALL*/

public class MainActivity extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView totalOutput, tipOutput, tipMeter, tipPercent; //initializing all of the views
        EditText input;
        SeekBar slider;
        Button _15, _20, _25;

        input = findViewById(R.id.inputEditText); //wireframing all of the views
        slider = findViewById(R.id.seekBar);
        tipPercent = findViewById(R.id.tipPercentTextView);
        tipMeter = findViewById(R.id.tipMeterTextView);
        tipOutput = findViewById(R.id.tipOutputTextView);
        totalOutput = findViewById(R.id.totalOutputTextView);
        _15 = findViewById(R.id.percent15Button);
        _20 = findViewById(R.id.percent20Button);
        _25 = findViewById(R.id.percent25Button);
        tipMeter.setText(R.string.acceptable);  //setting the tip meter to already say acceptable and be orange
        tipMeter.setTextColor(getResources().getColor(R.color.orange));

        /**
         * This is a text changed listener, to watch for when the user changes their input value
         * so the program can extract the inputs and call the update totals method
         * to immediatly output the totals.
         */
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {  //takes values from the app and passes them to the update totals method.
                String baseAmount = input.getText().toString();
                String tipPercentage = String.valueOf(slider.getProgress());

                updateTotals(baseAmount, tipPercentage, totalOutput, tipOutput);
            }
        });

        /**
         * Seek bar change listener that listens for any changes to the slider bar, and then takes
         * the slider value and displays a color coded tip message, while also calling the
         * update totals method to keep all outputs updated.
         */
        slider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                double tipPercentage = slider.getProgress();  //getting slider progress
                String tipPercentageString = String.valueOf(slider.getProgress());
                String baseAmountString = input.getText().toString();

                @SuppressLint("DefaultLocale") String result = String.format("%.2f%%", tipPercentage);
                tipPercent.setText(result);

                if (tipPercentage < 10) {  //if else block to determine which level to put the input
                    tipMeter.setText(R.string.poor);
                    tipMeter.setTextColor(getResources().getColor(R.color.red));
                } else if (tipPercentage <= 15) {
                    tipMeter.setText(R.string.acceptable);
                    tipMeter.setTextColor(getResources().getColor(R.color.orange));
                } else if (tipPercentage <= 20) {
                    tipMeter.setText(R.string.good);
                    tipMeter.setTextColor(getResources().getColor(R.color.lightGreen));
                } else {
                    tipMeter.setText(R.string.amazing);
                    tipMeter.setTextColor(getResources().getColor(R.color.darkGreen));
                }
                updateTotals(baseAmountString, tipPercentageString, totalOutput, tipOutput);
            }

            public void onStartTrackingTouch (SeekBar seekBar){}

            public void onStopTrackingTouch (SeekBar seekBar){}
        });

        /**
         * adding 3 buttons to automatically set the slider percentage to a specific state, as well
         * as updating totals.
         * 15%, 20%, and 25% for the most popular tipping options.
         */
        _15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slider.setProgress(15);
                String tipPercentage = String.valueOf(slider.getProgress());
                String baseAmount = input.getText().toString();
                updateTotals(baseAmount, tipPercentage, totalOutput,tipOutput);
            }
        });

        _20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slider.setProgress(20);
                String tipPercentage = String.valueOf(slider.getProgress());
                String baseAmount = input.getText().toString();
                updateTotals(baseAmount, tipPercentage, totalOutput,tipOutput);
            }
        });

        _25.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slider.setProgress(25);
                String tipPercentage = String.valueOf(slider.getProgress());
                String baseAmount = input.getText().toString();
                updateTotals(baseAmount, tipPercentage, totalOutput,tipOutput);
            }
        });

    }

    /**
     * update totals method takes 4 inputs, 2 input, and 2 output variables. the 2 inputs are the
     * base amount for the calculation, and the second is the tip percentage for the calculation.
     * Then the 2 output variables return the tip as a percentage, and the total amount combined.
     * @param baseAmountString
     * @param tipPercentageString
     * @param totalOutput
     * @param tipOutput
     */
    @SuppressLint("DefaultLocale")
    public void updateTotals(String baseAmountString, String tipPercentageString, TextView totalOutput, TextView tipOutput){
        double tipAmount, totalAmount;
        String tipResult, totalResult;

        try {
            double baseAmount = Double.parseDouble(baseAmountString); //parsing the inputs into doubles
            double tipPercentage = Double.parseDouble(tipPercentageString);

            tipAmount = baseAmount * (tipPercentage / 100); // Calculate tip amount
            totalAmount = baseAmount + tipAmount; // Calculate total amount

            if (tipPercentage == 0) {  //if the tip percentage is 0, prevents divide by 0 errors
                tipResult = "$0.00";
            } else tipResult = String.format("$%.2f", tipAmount); // Format output

            totalResult = String.format("$%.2f", totalAmount);  //assigns the total amount to the output string

            tipOutput.setText(tipResult); // Set the output text boxes
            totalOutput.setText(totalResult);

        } catch (NumberFormatException e) {  //catches unexpected inputs
            Toast.makeText(MainActivity.this, "Invalid Text", Toast.LENGTH_SHORT).show();
            tipOutput.setText(R.string.zero); // Set the output text to 0
            totalOutput.setText(R.string.zero);
        }
    }
}