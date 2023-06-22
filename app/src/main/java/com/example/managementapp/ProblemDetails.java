package com.example.managementapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ProblemDetails extends AppCompatActivity {
    private static final String SERVER_URL = "http://" + GetIP.getIPAddress() + ":5000/buildings/update_problem?id=%s";
    private ImageView image;
    private TextView textType;
    private TextView textOpeningDate;
    private TextView textDescription;
    private Button buttonUpdateStatus;
    private String status, address, my_email, img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_problem_details);

        // Retrieve the problem details from the intent
        String id = getIntent().getStringExtra("id");
        address = getIntent().getStringExtra("address");
        my_email = getIntent().getStringExtra("email");
        img = getIntent().getStringExtra("image");
        String problemType = getIntent().getStringExtra("type");
        String openingDate = getIntent().getStringExtra("opening_date");
        String description = getIntent().getStringExtra("description");
        image = findViewById(R.id.photoSquare);
        // Initialize the views
        textType = findViewById(R.id.textType);
        textOpeningDate = findViewById(R.id.textOpeningDate);
        textDescription = findViewById(R.id.textDescription);
        buttonUpdateStatus = findViewById(R.id.buttonUpdateStatus);

        // Set the problem details in the views
        textType.setText(problemType);
        textOpeningDate.setText(openingDate);
        textDescription.setText(description);
        if(!img.equals("")) {
            byte[] decodedBytes = Base64.decode(img, Base64.DEFAULT);
            Bitmap b = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
            image.setImageBitmap(b);
        }
        else {
            image.setImageBitmap(null);
        }
        // Set the initial status
        status = getIntent().getStringExtra("status");

        // Update the button text based on the initial status
        updateButtonStatusText();

        // Set a click listener for the update status button
        buttonUpdateStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Update the status
                if (status.equals("opened")) {
                    status = "in treatment";
                } else if (status.equals("in treatment")) {
                    status = "done";
                }
                // Update the button text
                updateButtonStatusText();
                URL url = null;
                try {
                    url = new URL(String.format(SERVER_URL, id));
                }
                catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("id", id)
                        .build();
                Request request = new Request.Builder()
                        .url(url)
                        .post(requestBody)
                        .build();
                OkHttpClient client = new OkHttpClient();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.isSuccessful()) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(ProblemDetails.this, ProblemList.class);
                                    intent.putExtra("email", my_email);
                                    intent.putExtra("building", address);
                                    startActivity(intent);
                                }
                            });

                        } else {

                        }
                    }
                    @Override
                    public void onFailure(okhttp3.Call call, IOException e) {
                        e.printStackTrace();
                    }

                });
            }
        });
    }

    private void updateButtonStatusText() {
        buttonUpdateStatus.setText("Update Status: " + status);
    }
}
