package com.example.managementapp;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
public class MainMenu extends AppCompatActivity{
    private String BuildingID;
    private String my_email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BuildingID = getIntent().getExtras().getString("building");
        my_email = getIntent().getExtras().getString("email");
        setContentView(R.layout.activity_mainmenu);
        TextView welcome = findViewById(R.id.welcome3);
        welcome.setText("online: " + BuildingID + " Building");
        Button chat_btn = findViewById(R.id.chatBtn);
        Button new_problem_btn = findViewById(R.id.problemBtn);
        Button answer_survey_btn = findViewById(R.id.surveyBtn);
        chat_btn.setOnClickListener(v -> {
            Intent intent = new Intent(MainMenu.this, ChatScreen.class);
            intent.putExtra("building", BuildingID);
            intent.putExtra("email", my_email);
            startActivity(intent);
        });
        new_problem_btn.setOnClickListener(t -> {
            Intent intent2 = new Intent(MainMenu.this, NewProblem.class);
            startActivity(intent2);
        });
        answer_survey_btn.setOnClickListener(t -> {
            Intent intent = new Intent(MainMenu.this, ChooseSurvey.class);
            intent.putExtra("building", BuildingID);
            intent.putExtra("email", my_email);
            startActivity(intent);
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
