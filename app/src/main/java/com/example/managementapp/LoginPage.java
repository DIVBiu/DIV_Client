package com.example.managementapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;


import java.io.IOException;
import java.net.URL;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class LoginPage extends AppCompatActivity {
    private static final String SERVER_URL = "http://192.168.1.108:5000/users/login?email=%s&password=%s";
    //private static final String SERVER_URL = "http://172.20.10.2:5000/users/login?email=%s&password=%s";
    //private static final String SERVER_URL = "http://192.168.10.191:5000/users/login?email=%s&password=%s";
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
    public static Map<String, String> jsonToMap (String json) {
        Gson gson = new Gson();
        Type type = Map.class.getTypeParameters()[1];
        Map<String, String> map = gson.fromJson(json, type);

        return map;
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
                    //String responseBody = response.body().string();
                    //Map<String,String> answer = jsonToMap(responseBody);
                    Intent intent = new Intent(LoginPage.this, ChooseBuilding.class);
                    intent.putExtra("email", email);
                    //intent.putStringArrayListExtra("buildings", buildings);
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