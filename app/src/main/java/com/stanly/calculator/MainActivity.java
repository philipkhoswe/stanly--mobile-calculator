package com.stanly.calculator;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private TextView display;
    private String currentInput = "";
    private double firstNumber = 0;
    private String operator = "";
    private boolean newInput = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display = findViewById(R.id.display);

        int[] numberButtons = {
            R.id.btn0, R.id.btn1, R.id.btn2,
            R.id.btn3, R.id.btn4, R.id.btn5,
            R.id.btn6, R.id.btn7, R.id.btn8,
            R.id.btn9
        };

        for (int id : numberButtons) {
            findViewById(id).setOnClickListener(v ->
                enterNumber(((Button) v).getText().toString())
            );
        }

        findViewById(R.id.btnAdd).setOnClickListener(v -> setOperator("+"));
        findViewById(R.id.btnSubtract).setOnClickListener(v -> setOperator("-"));
        findViewById(R.id.btnMultiply).setOnClickListener(v -> setOperator("*"));
        findViewById(R.id.btnDivide).setOnClickListener(v -> setOperator("/"));
        findViewById(R.id.btnEquals).setOnClickListener(v -> calculate());
        findViewById(R.id.btnClear).setOnClickListener(v -> clear());
        findViewById(R.id.btnDecimal).setOnClickListener(v -> enterDecimal());
        findViewById(R.id.btnDelete).setOnClickListener(v -> deleteLast());
    }

    private void enterNumber(String number) {
        if (newInput) {
            currentInput = number;
            newInput = false;
        } else {
            currentInput += number;
        }
        display.setText(currentInput);
    }

    private void enterDecimal() {
        if (newInput) {
            currentInput = "0.";
            newInput = false;
        } else if (!currentInput.contains(".")) {
            currentInput += ".";
        }
        display.setText(currentInput);
    }

    private void setOperator(String selectedOperator) {
        if (currentInput.isEmpty()) return;

        try {
            firstNumber = Double.parseDouble(currentInput);
            operator = selectedOperator;
            newInput = true;
        } catch (NumberFormatException e) {
            clear();
        }
    }

    private void calculate() {
        if (operator.isEmpty() || currentInput.isEmpty()) return;

        double secondNumber = Double.parseDouble(currentInput);
        double result;

        switch (operator) {
            case "+":
                result = firstNumber + secondNumber;
                break;
            case "-":
                result = firstNumber - secondNumber;
                break;
            case "*":
                result = firstNumber * secondNumber;
                break;
            case "/":
                if (secondNumber == 0) {
                    display.setText("Cannot divide by zero");
                    clearCalculation();
                    return;
                }
                result = firstNumber / secondNumber;
                break;
            default:
                return;
        }

        currentInput = formatResult(result);
        display.setText(currentInput);
        operator = "";
        newInput = true;
    }

    private String formatResult(double result) {
        if (result == (long) result) {
            return String.valueOf((long) result);
        }
        return String.valueOf(result);
    }

    private void deleteLast() {
        if (!currentInput.isEmpty() && !newInput) {
            currentInput = currentInput.substring(0, currentInput.length() - 1);
            display.setText(currentInput.isEmpty() ? "0" : currentInput);
        }
    }

    private void clear() {
        currentInput = "";
        firstNumber = 0;
        operator = "";
        newInput = true;
        display.setText("0");
    }

    private void clearCalculation() {
        currentInput = "";
        firstNumber = 0;
        operator = "";
        newInput = true;
    }
}