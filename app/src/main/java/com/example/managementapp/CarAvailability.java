package com.example.managementapp;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
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

public class CarAvailability extends AppCompatActivity {
    private String address, my_email, number;
    private static final String SERVER_URL = "http://" + GetIP.getIPAddress() + ":5000/get_parking_view?address=%s";

    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_car_availability);
        image = findViewById(R.id.photoImageView);
        my_email = getIntent().getExtras().get("email").toString();
        address = getIntent().getExtras().get("building").toString();
        number = getIntent().getExtras().get("number").toString();
        try {
            get_content(address);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    private void get_content(String address) throws MalformedURLException {
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
                    byte[] imageBytes = Base64.decode(jsonResponse, Base64.DEFAULT);
                    // Decode the byte array into a Bitmap object
                    Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                    image.setImageBitmap(bitmap);
                    //convertJsonToImageView(jsonResponse,imageView)
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(CarAvailability.this, "Problem with answer from server", Toast.LENGTH_SHORT).show();
                        }
                    });
                    // Handle unsuccessful response
                }
            }
        });
    }
//
//    public void convertJsonToImageView(String jsonResponse, ImageView imageView) {
//        try {
//            JSONObject json = new JSONObject(jsonResponse);
//            String imageUrl = json.getString("image_url");
//
//            // Load the image into the ImageView using Picasso library
//            Picasso.get().load(imageUrl).into(imageView);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
}