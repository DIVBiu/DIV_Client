package com.example.managementapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ChatScreen extends AppCompatActivity {
    private View back;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_screen);
        back = findViewById(R.id.imageBack);
        back.setOnClickListener(v -> {
            Intent intent_2 = new Intent(ChatScreen.this, MainMenu.class);
            startActivity(intent_2);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("MainMenu", "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("MainMenu", "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("MainMenu", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("MainMenu", "onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("MainMenu", "onRestart");
    }

}
