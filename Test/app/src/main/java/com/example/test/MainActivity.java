package com.example.test;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import androidx.navigation.ui.AppBarConfiguration;

import com.example.test.databinding.ActivityMainBinding;
import com.google.gson.JsonObject;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    EditText latitude,longitude;
    TextView temperature;
    Button fetchTemperatureBtn;
    private static final String TAG = "APP";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        latitude = findViewById(R.id.latitude);
        longitude = findViewById(R.id.longitude);
        temperature = findViewById(R.id.temperature);
        fetchTemperatureBtn = findViewById(R.id.temp_button);
        fetchTemperatureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(StringUtils.isNotBlank(latitude.getText())
                        && StringUtils.isNotBlank(longitude.getText())){
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Log.i(TAG, "Checking weather for Lat {"+latitude.getText()+"} , Long {"+longitude.getText()+"} ");
                            String responseData = getWeatherData(latitude.getText().toString(),longitude.getText().toString());
                            if (StringUtils.isNotBlank(responseData)){
                                Log.i(TAG, "Response :"+responseData);
                                try {
                                    JSONObject jsonObject = new JSONObject(responseData);
                                    String temp = null,city = null,errorMessage = null;
                                    if(!jsonObject.has("message")) {
                                        temp = jsonObject.getJSONObject("main").getString("temp");
                                        city = jsonObject.getString("name");
                                    }else{
                                        errorMessage = jsonObject.getString("message");
                                    }
                                    updateUI(errorMessage, city, temp);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }
                    }).start();
                }
            }
        });


        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void updateUI(String finalErrorMessage, String finalCity, String finalTemp) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(StringUtils.isBlank(finalErrorMessage)) {
                    temperature.setText(finalCity + " - " + finalTemp);
                }else{
                    temperature.setText(finalErrorMessage);
                }
                temperature.setVisibility(View.VISIBLE);
            }
        });
    }

    private String getWeatherData(String latitude, String longitude) {
        StringBuilder url = new StringBuilder("https://api.openweathermap.org/data/2.5/weather?");
        url.append("lat="+latitude).append("&lon="+longitude).append("&appid=f9f956738d6a4fc31a66372d51e44a21")
                .append("&units=metric");
        Log.i(TAG, "getWeatherData: URL is "+url.toString());
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url.toString())
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*@Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }*/
}