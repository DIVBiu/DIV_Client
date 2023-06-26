package com.example.managementapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ParkingScreen extends AppCompatActivity {

    private static final int IMAGE_CAPTURE_CODE = 100;
    private static final int IMAGE_PICK_CODE = 200;
    private Bitmap photoBitmap;
    private Response resp;
    private EditText carNumberEditText;
    private ImageView carNumberImageView;
    private Button selectImageButton, takePhotoButton, submitButton;
    private static final String SERVER_URL = "http://" + GetIP.getIPAddress() + ":5000/cars/new_car";
    private String address, my_email, encodedImage;
    private static final int PICK_IMAGE_REQUEST = 1;
    //    private static final int REQUEST_IMAGE_CAPTURE = 2;
//    private static final int CAMERA_REQUEST = 100;
    private static final int PERMISSION_CODE = 1000;
    String cameraPermission[];
    Uri image_uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_parking_screen);
        carNumberEditText = findViewById(R.id.carNumberEditText);
        carNumberImageView = findViewById(R.id.carNumberImageView);
        selectImageButton = findViewById(R.id.selectImageButton);
        takePhotoButton = findViewById(R.id.takePhotoButton);
        cameraPermission = new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};
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
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, IMAGE_PICK_CODE);
            }
        });

        takePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                        String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permission, PERMISSION_CODE);
                    }
                    else {
                        openCamera();
                    }

                }
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement submitting data to the Flask server
                String carNumber = carNumberEditText.getText().toString();
//                Bitmap carNumberImage = ((BitmapDrawable) carNumberImageView.getDrawable()).getBitmap();
                try {
                    photoBitmap = ((BitmapDrawable) carNumberImageView.getDrawable()).getBitmap();
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    photoBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);
                } catch (Exception e) {
                    photoBitmap = null;
                    encodedImage = "";
                }
                try {
                    sendDataToServer(carNumber, encodedImage);
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void openCamera(){
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE,"New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION,"From the Camera");
        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(cameraIntent,IMAGE_CAPTURE_CODE);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamera();
                } else {
                    Toast.makeText(this, "permission denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_CAPTURE_CODE && resultCode == RESULT_OK) {
            // Image captured from camera
            carNumberImageView.setImageURI(image_uri);
        } else if (requestCode == IMAGE_PICK_CODE && resultCode == RESULT_OK && data != null) {
            // Image selected from gallery
            Uri selectedImageUri = data.getData();
            carNumberImageView.setImageURI(selectedImageUri);
        }
    }


    // onActivityResult and other required methods for handling image selection and camera capture go here.
    private void sendDataToServer(String carNumber, String encodedImage) throws MalformedURLException {
        if (carNumber.equals("") && encodedImage.equals("")) {
            Toast.makeText(ParkingScreen.this, "Please enter a car number or a photo of a car number", Toast.LENGTH_LONG).show();
            return;
        }
        int flag = 0;
        String sending_number;
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
                    resp = response;
                    if (resp.code() == 200) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ParkingScreen.this, "Your car number has been submitted and waiting for an admin approval", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                    else if(resp.code() == 201){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ParkingScreen.this, "Your car number has been added", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                    // Handle the response body here
                } else {
                    resp = response;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ParkingScreen.this, "This car has already been added", Toast.LENGTH_LONG).show();
                        }
                    });

                }
            }
        });
        if (resp != null) {
            carNumberEditText.setText("");
            carNumberImageView.setImageBitmap(null);
        }
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
