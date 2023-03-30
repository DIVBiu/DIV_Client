package com.example.managementapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.textclassifier.TextLinks;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class LoginPage extends AppCompatActivity {
    //private static final String SERVER_URL = "http://172.20.10.2:5000/users/login?email=%s&password=%s";
    private static final String SERVER_URL = "http://192.168.10.191:5000/users/login?email=%s&password=%s";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button login_submitbtn = findViewById(R.id.login_submitBtn);
        EditText loginUsername = findViewById(R.id.login_username);
        EditText loginPassword = findViewById(R.id.login_password);
        TextView dont_have_account = findViewById(R.id.alreadyHaveAccountBtn);
        login_submitbtn.setOnClickListener(v -> {
            if (loginUsername.getText().toString().isEmpty() || loginPassword.getText().toString().isEmpty()) {
                Toast.makeText(LoginPage.this, "There is an empty field", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    login(loginUsername.getText().toString(), loginPassword.getText().toString());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
//                Toast.makeText(this, "blabla", Toast.LENGTH_SHORT).show();
//                //login(binding.loginUsername.getText().toString(), binding.loginPassword.getText().toString());
//                Intent intent = new Intent(LoginPage.this, MainMenu.class);
//                //intent.putExtra("username", user.getUsername());
//                startActivity(intent);
            }
        });
        dont_have_account.setOnClickListener(v -> {
            Intent intent = new Intent(LoginPage.this, Register.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("LoginPage", "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("LoginPage", "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("LoginPage", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("LoginPage", "onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("LoginPage", "onRestart");
    }

    public void login(String email, String password) throws IOException {
        URL url = new URL(String.format(SERVER_URL, email, password));
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    Intent intent = new Intent(LoginPage.this, MainMenu.class);
                    startActivity(intent);
                    // Handle the response body here
                } else {
                    // Handle unsuccessful response
                    Toast.makeText(LoginPage.this, "Username or password are incorrect", Toast.LENGTH_SHORT).show();
                    onResume();
                }
            }
        });
    }
}