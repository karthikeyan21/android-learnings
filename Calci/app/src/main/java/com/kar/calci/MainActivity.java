package com.kar.calci;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static String TAG = "Calci";
    TextView mainView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainView = findViewById(R.id.gigamon);
        mainView.setText("Gigamon");
        Button button = findViewById(R.id.welcomebtn);
        button.setText("Press ME !");

    }

    public void OnButtonPress(View view){
        Log.i(TAG, "OnButtonPress");
        mainView.setText("Welcome to Gigamon!");
    }
}