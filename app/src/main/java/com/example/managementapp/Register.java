package com.example.managementapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;

public class Register extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        TextView toLogin = findViewById(R.id.to_login);
        toLogin.setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginPage.class);
            startActivity(intent);
        });
        Log.i("SignUpPage", "onCreate");
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

}
