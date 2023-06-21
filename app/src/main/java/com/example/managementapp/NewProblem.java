package com.example.managementapp;

import android.Manifest;
import android.app.Dialog;
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
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
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
import java.io.IOException;
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
    private static final int PICK_IMAGE_REQUEST = 1;

    private static final int REQUEST_PERMISSION_CAMERA = 2;
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 100;
    private ImageView carNumberImage;
    private Button selectImageButton,takePhotoButton;
    private Bitmap photoBitmap;
    private Spinner mySpinner;
    private Button submit;
    private EditText description;
    private int chosen_problem ;
    private String content,my_email,address, Date, encodedImage;

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
        carNumberImage = findViewById(R.id.carNumberImageV);
        if (ContextCompat.checkSelfPermission(NewProblem.this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(NewProblem.this, new String[]{
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
            }
        });
        takePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePictureIntent, PICK_IMAGE_REQUEST);
            }
        });
        submit.setOnClickListener(v -> {
            try {
                //photoBitmap = ((BitmapDrawable) carNumberImage.getDrawable()).getBitmap();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                photoBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);
            }
            catch (Exception e){
                photoBitmap = null;
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                photoBitmap = (Bitmap) extras.get("data");
                // Get the URI of the selected image
                Uri imageUri = data.getData();

                // Use the image URI to load the selected image
                carNumberImage.setImageURI(imageUri);
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

