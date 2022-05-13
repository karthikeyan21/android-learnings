package com.kar.mycalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    TextView output;
    double total = 0, num1=0,num2=0;
    StringBuilder value = new StringBuilder();
    String TAG = "calculator";
    String lastOperation = null;
    List<Character> operationsSupported = List.of('+','-','*','/');

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        output = findViewById(R.id.output);
        output.setText(String.valueOf(total));

    }

    public void numberClicked(View view){
        Button button = (Button) view;
        Log.i(TAG, "numberClicked: "+button.getText());
        Log.i(TAG, "Total is : "+total);
        if(Objects.nonNull(lastOperation) && !lastOperation.isEmpty())
        {
            char pastInput = value.charAt(value.length()-1);
            Log.i(TAG, "numberClicked: PastInput "+pastInput);
            if(operationsSupported.contains(pastInput)) {
                num2 = Double.valueOf(((Button) view).getText().toString());
            } else {
                num2 = Double.valueOf(String.valueOf(num2) + button.getText().toString());
            }
        }else{
            if(value.length()==0) {
                num1 = Double.valueOf(((Button) view).getText().toString());
            } else {
                num1 = Double.valueOf(value + button.getText().toString());
            }
        }
        value.append(((Button) view).getText());
        output.setText(value);
    }
    public void operationClicked(View view){
        Button operation = (Button) view;
        Log.i(TAG,"Operation performed "+operation.getText());
        Log.i(TAG, "Total is : "+total);
        if(Objects.nonNull(lastOperation)) {
            switch (lastOperation) {
                case "+":
                    total = num1 + num2;
                    break;
                case "-":
                    total = num1 - num2;
                    break;
                case "*":
                    total = num1 * num2;
                    break;
                case "/":
                    total = num1 / num2;
                    break;
                default:
                    Log.i(TAG, "operationClicked: Defaujlt"+lastOperation);
            }
            num1 = total;
        }
        lastOperation = operation.getText().toString();
        value.append(((Button) view).getText());
        output.setText(value);

        switch (operation.getId()) {
            case R.id.ac:
                value.setLength(0);
                total = 0;
                output.setText(String.valueOf(total));
                lastOperation = null;
                num1=0;
                num2=0;
                break;
            case R.id.equal:
                output.setText(String.valueOf(total));
                num1 = total;
                num2=0;
                value.setLength(0);
                value.append(String.valueOf(total));
                lastOperation = null;
        }
    }
}