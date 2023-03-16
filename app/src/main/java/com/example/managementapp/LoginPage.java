package com.example.managementapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginPage extends AppCompatActivity {

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
                Toast.makeText(this, "blabla", Toast.LENGTH_SHORT).show();
                //login(binding.loginUsername.getText().toString(), binding.loginPassword.getText().toString());
                Intent intent = new Intent(LoginPage.this, MainMenu.class);
                //intent.putExtra("username", user.getUsername());
                startActivity(intent);
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

}