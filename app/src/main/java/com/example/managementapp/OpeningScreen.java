package com.example.managementapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

public class OpeningScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_opening_screen);
        Button lgnButton = findViewById(R.id.lgnButton);
        Button rstButton = findViewById(R.id.rstButton);

        lgnButton.setOnClickListener(v -> {
            Intent intent = new Intent(OpeningScreen.this, LoginPage.class);
            startActivity(intent);
        });
        rstButton.setOnClickListener(v -> {
            Intent intent = new Intent(OpeningScreen.this, Register.class);
            startActivity(intent);
        });
    }

}