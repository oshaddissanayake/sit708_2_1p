package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Spinner fromUnitSpinner, toUnitSpinner;
    private EditText inputValue;
    private TextView resultView;
    private Button convertButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fromUnitSpinner = findViewById(R.id.fromUnit);
        toUnitSpinner = findViewById(R.id.toUnit);
        inputValue = findViewById(R.id.inputValue);
        resultView = findViewById(R.id.resultView);
        convertButton = findViewById(R.id.convertButton);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.units_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        fromUnitSpinner.setAdapter(adapter);
        toUnitSpinner.setAdapter(adapter);

        fromUnitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("DEBUG", "From Spinner selected: " + parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        toUnitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("DEBUG", "To Spinner selected: " + parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("DEBUG", "Convert button clicked");
                convertUnits();
            }
        });
    }

    private void convertUnits() {
        String fromUnit = fromUnitSpinner.getSelectedItem().toString();
        String toUnit = toUnitSpinner.getSelectedItem().toString();
        String inputText = inputValue.getText().toString();

        if (inputText.isEmpty()) {
            Toast.makeText(this, "Please enter a value", Toast.LENGTH_SHORT).show();
            return;
        }

        double input;
        try {
            input = Double.parseDouble(inputText);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid number input", Toast.LENGTH_SHORT).show();
            return;
        }

        if (fromUnit.equals(toUnit)) {
            Toast.makeText(this, "Source and Destination units are the same", Toast.LENGTH_SHORT).show();
            return;
        }

        double result = 0;

        // Length Conversions
        if (fromUnit.equals("Inch") && toUnit.equals("Centimeter")) result = input * 2.54;
        else if (fromUnit.equals("Centimeter") && toUnit.equals("Inch")) result = input / 2.54;
        else if (fromUnit.equals("Foot") && toUnit.equals("Centimeter")) result = input * 30.48;
        else if (fromUnit.equals("Centimeter") && toUnit.equals("Foot")) result = input / 30.48;
        else if (fromUnit.equals("Yard") && toUnit.equals("Meter")) result = input * 0.9144;
        else if (fromUnit.equals("Meter") && toUnit.equals("Yard")) result = input / 0.9144;
        else if (fromUnit.equals("Mile") && toUnit.equals("Kilometer")) result = input * 1.60934;
        else if (fromUnit.equals("Kilometer") && toUnit.equals("Mile")) result = input / 1.60934;

            // Weight Conversions
        else if (fromUnit.equals("Pound") && toUnit.equals("Kilogram")) result = input * 0.453592;
        else if (fromUnit.equals("Kilogram") && toUnit.equals("Pound")) result = input / 0.453592;
        else if (fromUnit.equals("Ounce") && toUnit.equals("Gram")) result = input * 28.3495;
        else if (fromUnit.equals("Gram") && toUnit.equals("Ounce")) result = input / 28.3495;
        else if (fromUnit.equals("Ton") && toUnit.equals("Kilogram")) result = input * 907.185;
        else if (fromUnit.equals("Kilogram") && toUnit.equals("Ton")) result = input / 907.185;

            // Temperature Conversions
        else if (fromUnit.equals("Celsius") && toUnit.equals("Fahrenheit")) result = (input * 1.8) + 32;
        else if (fromUnit.equals("Fahrenheit") && toUnit.equals("Celsius")) result = (input - 32) / 1.8;
        else if (fromUnit.equals("Celsius") && toUnit.equals("Kelvin")) result = input + 273.15;
        else if (fromUnit.equals("Kelvin") && toUnit.equals("Celsius")) result = input - 273.15;
        else if (fromUnit.equals("Fahrenheit") && toUnit.equals("Kelvin")) result = ((input - 32) / 1.8) + 273.15;
        else if (fromUnit.equals("Kelvin") && toUnit.equals("Fahrenheit")) result = ((input - 273.15) * 1.8) + 32;
        else {
            Toast.makeText(this, "Invalid conversion", Toast.LENGTH_SHORT).show();
            return;
        }

        resultView.setText(String.format("%.2f %s", result, toUnit));
    }
}