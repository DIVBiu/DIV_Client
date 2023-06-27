package com.example.managementapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
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
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_register);
        email = findViewById(R.id.email);
        full_name = findViewById(R.id.full_name);
        password = findViewById(R.id.Password);
        password2 = findViewById(R.id.Password2);
        register = findViewById(R.id.signup_btn);
        email.setSingleLine();
        full_name.setSingleLine();
        password.setSingleLine();
        password2.setSingleLine();
        password2.setTransformationMethod(PasswordTransformationMethod.getInstance());
        password.setTransformationMethod(PasswordTransformationMethod.getInstance());
        TextView to_login = findViewById(R.id.to_login);
        register.setOnClickListener(v -> {
            if (!password.getText().toString().equals(password2.getText().toString())) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(Register.this, "The passwords do not match", Toast.LENGTH_SHORT).show();
                    }});
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
        to_login.setOnClickListener(v -> {
            Intent intent = new Intent(Register.this, LoginPage.class);
            startActivity(intent);
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
    private static final String SERVER_URL = "http://"+GetIP.getIPAddress()+":5000/users/register?name=%s&email=%s&password=%s";
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
                    Intent intent = new Intent(Register.this, ChooseBuilding.class);
                    intent.putExtra("email", email.toString());
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
