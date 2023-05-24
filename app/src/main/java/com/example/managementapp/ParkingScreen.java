package com.example.managementapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ParkingScreen extends AppCompatActivity {
    private Bitmap bitmap;
    private EditText carNumberEditText;
    private ImageView carNumberImageView;
    private Button selectImageButton, takePhotoButton, submitButton;
    private static final String SERVER_URL = "http://" + GetIP.getIPAddress() + ":5000/cars/new_car";
    private String address, my_email;
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 2;
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_screen);

        carNumberEditText = findViewById(R.id.carNumberEditText);
        carNumberImageView = findViewById(R.id.carNumberImageView);
        selectImageButton = findViewById(R.id.selectImageButton);
        takePhotoButton = findViewById(R.id.takePhotoButton);
        my_email = getIntent().getExtras().get("email").toString();
        address = getIntent().getExtras().get("building").toString();
        submitButton = findViewById(R.id.submitButton);
        if (ContextCompat.checkSelfPermission(ParkingScreen.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ParkingScreen.this, new String[]{
                    Manifest.permission.CAMERA
            }, 100);
        }
        selectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement image selection from gallery
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");

                // Start the gallery app and wait for a result
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
//                Intent intent = new Intent();
//                intent.setType("image/*");
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });

        takePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePictureIntent, CAMERA_PERMISSION_REQUEST_CODE);
            }
        });


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement submitting data to the Flask server
                String carNumber = carNumberEditText.getText().toString();
                Bitmap carNumberImage = ((BitmapDrawable) carNumberImageView.getDrawable()).getBitmap();
                try {
                    sendDataToServer(carNumber, carNumberImage);
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    // Handle the result of the camera app
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 100) {
//            bitmap = (Bitmap) data.getExtras().get("data");
//            carNumberImageView.setImageBitmap(bitmap);
//        }
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            // Get the URI of the selected image
            Uri imageUri = data.getData();

            // Use the image URI to load the selected image
            carNumberImageView.setImageURI(imageUri);
        }
    }

    // onActivityResult and other required methods for handling image selection and camera capture go here.
    private void sendDataToServer(String carNumber, Bitmap carNumberImage) throws MalformedURLException {

        int flag = 0;
        String sending_number;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        carNumberImage.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        String encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);
        if (!carNumber.equals("")) {
            flag = 0;
            sending_number = carNumber;
        } else {
            flag = 1;
            sending_number = encodedImage;
        }
        //URL url = new URL(String.format(SERVER_URL, my_email, address, flag, sending_number));
        URL url = new URL(String.format(SERVER_URL));
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("email", my_email)
                .addFormDataPart("address", address)
                .addFormDataPart("flag", String.valueOf(flag))
                .addFormDataPart("car_number", sending_number)
                .build();
        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {

                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    Log.d("YESSSSS", responseBody);

                    // Handle the response body here
                } else {
                    // Handle unsuccessful response
                    //Toast.makeText(ParkingScreen.this, "Username or password are incorrect", Toast.LENGTH_SHORT).show();
                    //onResume();
                }
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.i("ParkingScreen", "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("ParkingScreen", "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("ParkingScreen", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("ParkingScreen", "onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("MainMenu", "onRestart");
    }


}
