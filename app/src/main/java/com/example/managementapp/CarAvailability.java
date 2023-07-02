package com.example.managementapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
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
    private Matrix matrix;
    private static final String SERVER_URL = "http://" + GetIP.getIPAddress() + ":5000/buildings/get_parking_image?address=%s";
    private DisplayMetrics displayMetrics;
    private int screenWidth, screenHeight;
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
        matrix = new Matrix();
        matrix.postRotate(90);
        displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;

        try {
            get_content(address);
        } catch (MalformedURLException e) {
            Log.i("SHIT", "SHIT SHIT SHIT");
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
                    JsonObject jsonObject = new Gson().fromJson(jsonResponse, JsonObject.class);
                    String s_image = jsonObject.get("image").getAsString();
                    if (!jsonResponse.equals("")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                byte[] imageBytes = Base64.decode(s_image, Base64.DEFAULT);
                                // Decode the byte array into a Bitmap object
                                Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                                //Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, screenWidth, screenHeight, true);

                                Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                                //Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, screenWidth, screenHeight, matrix, true);
                                //Bitmap scaledBitmap = Bitmap.createScaledBitmap(rotatedBitmap, screenWidth, screenHeight, true);

                                image.setImageBitmap(rotatedBitmap);
                            }
                        });
                    }

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