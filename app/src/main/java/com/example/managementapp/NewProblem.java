package com.example.managementapp;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NewProblem extends AppCompatActivity {

//    private static final int IMAGE_CAPTURE_CODE = 100;
//    private static final int IMAGE_PICK_CODE = 200;
//    private static final int PERMISSION_CODE = 1000;

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int CAMERA_PERMISSION_REQUEST = 2;
    private static final int CAMERA_REQUEST = 3;

    private ImageView problemImage;
    private Button selectImageButton,takePhotoButton;
    private Bitmap photo;
    private Spinner mySpinner;
    private Button submit;
    private EditText description;
    private int chosen_problem ;
    private String content,my_email,address, Date, encodedImage;

    //    Uri image_uri;
    private static final String SERVER_URL = "http://"+ GetIP.getIPAddress()+":5000/building/new_problem";
    private static final String SERVER_URL2 = "http://" + GetIP.getIPAddress() + ":5000/am_I_admin?email=%s&address=%s";
    String[] options = {"","Electricity", "Plumbing", "infrastructure", "Construction", "Other"};
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_new_problem);
        submit = findViewById(R.id.confirm_problem);
        mySpinner = findViewById(R.id.spinner);
        description = findViewById(R.id.description);
        my_email = getIntent().getExtras().get("email").toString();
        address = getIntent().getExtras().get("building").toString();
        content = description.getText().toString();
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(NewProblem.this, android.R.layout.simple_spinner_item, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(adapter);
        selectImageButton = findViewById(R.id.selectImg);
        takePhotoButton = findViewById(R.id.takePhoto);
        problemImage = findViewById(R.id.carNumberImageV);
        if (ContextCompat.checkSelfPermission(NewProblem.this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(NewProblem.this, new String[]{
                    Manifest.permission.CAMERA
            }, 100);
        }
//        selectImageButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(galleryIntent, IMAGE_PICK_CODE);
//            }
//        });

        selectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImagePicker();
            }
        });


//        takePhotoButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
//                    if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
//                        String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
//                        requestPermissions(permission, PERMISSION_CODE);
//                    }
//                    else {
//                        openCamera();
//                    }
//
//                }
//
//////                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//////                startActivityForResult(takePictureIntent, CAMERA_PERMISSION_REQUEST_CODE);
//
//            }
//        });

        takePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestCameraPermission();
            }
        });


        submit.setOnClickListener(v -> {
            try {
                //photoBitmap = ((BitmapDrawable) carNumberImage.getDrawable()).getBitmap();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);
            }
            catch (Exception e){
                photo = null;
                encodedImage="";
            }
            URL url = null;
            content = description.getText().toString();
            if (content.isEmpty() || chosen_problem == 0){
                Toast.makeText(NewProblem.this, "Please choose a problem and write a description", Toast.LENGTH_SHORT).show();
            }
            else{
                LocalDate currentDate = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    currentDate = LocalDate.now();
                }
                Date = currentDate.toString();
                try {
                    url = new URL(String.format(SERVER_URL, address,my_email,chosen_problem, content, Date));
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("address", address)
                        .addFormDataPart("email", my_email)
                        .addFormDataPart("description", content)
                        .addFormDataPart("type", String.valueOf(chosen_problem))
                        .addFormDataPart("date", Date)
                        .addFormDataPart("image", encodedImage)
                        .build();
                Request request = new Request.Builder()
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
//                            String responseBody = response.body().string();
//                            Log.d("LoginResponse", responseBody);
//                            Intent intent = new Intent(NewProblem.this, MainMenu.class);
//                            intent.putExtra("building", address);
//                            intent.putExtra("email", my_email);
//                            startActivity(intent);
                            // Handle the response body here
                        } else {
                            // Handle unsuccessful response
                            //Toast.makeText(NewProblem.this, "Username or password are incorrect", Toast.LENGTH_SHORT).show();
                            onResume();
                        }
                    }
                });
                showCustomDialog();
            }});

        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String value = parent.getItemAtPosition(position).toString();
                chosen_problem = position;
                Toast.makeText(NewProblem.this, value,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == IMAGE_CAPTURE_CODE && resultCode == RESULT_OK) {
//            // Image captured from camera
//            problemImage.setImageURI(image_uri);
//        } else if (requestCode == IMAGE_PICK_CODE && resultCode == RESULT_OK && data != null) {
//            // Image selected from gallery
//            Uri selectedImageUri = data.getData();
//            problemImage.setImageURI(selectedImageUri);
//        }
//    }
//    private void openCamera(){
//        ContentValues values = new ContentValues();
//        values.put(MediaStore.Images.Media.TITLE,"New Picture");
//        values.put(MediaStore.Images.Media.DESCRIPTION,"From the Camera");
//        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
//        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
//        startActivityForResult(cameraIntent,IMAGE_CAPTURE_CODE);
//    }

    private void openImagePicker() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
    }

    private void requestCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                    CAMERA_PERMISSION_REQUEST);
        } else {
            openCamera();
        }
    }

    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(imageUri);
                photo = BitmapFactory.decodeStream(inputStream);
                problemImage.setImageBitmap(photo);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            photo = (Bitmap) data.getExtras().get("data");
            problemImage.setImageBitmap(photo);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CAMERA_PERMISSION_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    void showCustomDialog() {
        final Dialog dialog = new Dialog(NewProblem.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.confirm_new_problem);
        Button ok = dialog.findViewById(R.id.confirm);
        ok.setOnClickListener(v -> {
            dialog.dismiss();
            URL url = null;
            try {
                url = new URL(String.format(SERVER_URL2, my_email, address));
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(url).build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.code() == 201) {
                        //String responseBody = response.body().string();
                        //Map<String,String> answer = jsonToMap(responseBody);
                        Intent intent = new Intent(NewProblem.this, MainMenuAdmin.class);
                        intent.putExtra("email", my_email);
                        intent.putExtra("building", address);
                        startActivity(intent);
                        // Handle the response body here
                    } else if(response.code() == 200){
                        Intent intent = new Intent(NewProblem.this, MainMenu.class);
                        intent.putExtra("email", my_email);
                        intent.putExtra("building", address);
                        startActivity(intent);
                    }
                    else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(NewProblem.this, "There was a problem with the server", Toast.LENGTH_SHORT).show();
                                onResume();
                            }
                        });
                    }
                }
            });
        });
        dialog.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("NewProblemPage", "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("NewProblemPage", "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("NewProblemPage", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("NewProblemPage", "onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("NewProblemPage", "onRestart");
    }
    private class MyObject {
        private String name;

        public MyObject(String name) {
            this.name = name;
        }

        public String toString() {
            return name;
        }
    }
}

