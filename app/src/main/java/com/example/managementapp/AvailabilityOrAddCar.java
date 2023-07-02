package com.example.managementapp;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AvailabilityOrAddCar extends AppCompatActivity {
    private String my_email;
    private static final String SERVER_URL = "http://" + GetIP.getIPAddress() + ":5000/buildings/get_parking_slots?address=%s";
    private TextView slots;
    private String address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_availability_or_add_car);
        Button availability = findViewById(R.id.availabilitybtn);
        Button add_new_car = findViewById(R.id.addbtn);
        slots = findViewById(R.id.slots);
        my_email = getIntent().getExtras().get("email").toString();
        address = getIntent().getExtras().get("building").toString();
        try {
            get_available_spot();
        } catch (MalformedURLException e) {
            Log.i("SHIT", "SHIT SHIT SHIT");

            throw new RuntimeException(e);
        }
        availability.setOnClickListener(c -> {
            Intent intent = new Intent(AvailabilityOrAddCar.this, CarAvailability.class);
            intent.putExtra("building", address);
            intent.putExtra("email", my_email);
            startActivity(intent);
        });
        add_new_car.setOnClickListener(c -> {
            Intent intent = new Intent(AvailabilityOrAddCar.this, ParkingScreen.class);
            intent.putExtra("building", address);
            intent.putExtra("email", my_email);
            startActivity(intent);
        });
    }

    private void get_available_spot() throws MalformedURLException {
        URL url = new URL(String.format(SERVER_URL, address));
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String jsonResponse = response.body().string();
                    JsonObject jsonObject = new Gson().fromJson(jsonResponse, JsonObject.class);
                    String amount = jsonObject.get("amount").getAsString();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            slots.setText(amount);
                        }
                    });

                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(AvailabilityOrAddCar.this, "Problem with server", Toast.LENGTH_SHORT).show();
                        }
                    });
                    // Handle unsuccessful response

                }
            }
        });
    }
}