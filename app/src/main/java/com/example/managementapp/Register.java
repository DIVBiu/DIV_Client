package com.example.managementapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Register extends AppCompatActivity {
    EditText email, full_name, password, password2;
    Button register;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        email = findViewById(R.id.email);
        full_name = findViewById(R.id.full_name);
        password = findViewById(R.id.Password);
        password2 = findViewById(R.id.Password2);
        register = findViewById(R.id.signup_btn);
        register.setOnClickListener(v -> {
            if (!password.getText().toString().equals(password2.getText().toString())) {
                Toast.makeText(Register.this, "The passwords do not match", Toast.LENGTH_SHORT).show();
            }
            else{
                try {
                    signup(email.getText().toString(), full_name.getText().toString(), password.getText().toString());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
        }
        Log.i("SignUpPage", "onCreate");
    });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("SignUpPage", "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("SignUpPage", "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("SignUpPage", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("SignUpPage", "onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("SignUpPage", "onRestart");
    }
    private static final String SERVER_URL = "http://192.168.1.108:5000/users/register?name=%s&email=%s&password=%s";
    //private static final String SERVER_URL = "http://172.20.10.2:5000/users/register?name=%s&email=%s&password=%s";
    //private static final String SERVER_URL = "http://192.168.10.191:5000/users/register?name=%s&email=%s&password=%s";
    public void signup(String email, String full_name, String password) throws IOException {
        URL url = new URL(String.format(SERVER_URL, full_name,email, password));
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("name", full_name)
                .addFormDataPart("email", email)
                .addFormDataPart("password", password)
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
                    String responseBody = response.body().string();
                    Log.d("LoginResponse", responseBody);
                    Intent intent = new Intent(Register.this, Building.class);
                    startActivity(intent);
                    // Handle the response body here
                } else {
                    // Handle unsuccessful response
                    Toast.makeText(Register.this, "Username or password are incorrect", Toast.LENGTH_SHORT).show();
                    onResume();
                }
            }
        });
    }

}
