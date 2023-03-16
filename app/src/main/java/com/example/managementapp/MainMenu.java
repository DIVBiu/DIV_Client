package com.example.managementapp;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
public class MainMenu extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);
        Button chat_btn = findViewById(R.id.chatBtn);
        Button new_problem_btn = findViewById(R.id.problemBtn);
        Button answer_survey_btn = findViewById(R.id.surveyBtn);
        chat_btn.setOnClickListener(v -> {
            Intent intent = new Intent(MainMenu.this, ChatScreen.class);
            //intent.putExtra("username", user.getUsername());
            startActivity(intent);
        });
        new_problem_btn.setOnClickListener(v -> {
            Intent intent2 = new Intent(MainMenu.this, NewProblem.class);
            startActivity(intent2);
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
