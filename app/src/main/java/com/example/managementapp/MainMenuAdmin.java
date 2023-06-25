package com.example.managementapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainMenuAdmin extends AppCompatActivity {
    private String BuildingID;
    private String my_email;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        BuildingID = getIntent().getExtras().getString("building");
        my_email = getIntent().getExtras().getString("email");
        setContentView(R.layout.activity_mainmenuadmin);
        TextView welcome = findViewById(R.id.welcomeAdmin);
        welcome.setText( BuildingID + " as an Admin");
        Button chat_btn = findViewById(R.id.chatBtnAdmin);
        Button new_problem_btn = findViewById(R.id.problemBtnAdmin);
        Button answer_survey_btn = findViewById(R.id.surveyBtnAdmin);
        Button parking_btn = findViewById(R.id.parkingBtnAdmin);
        Button create_survey = findViewById(R.id.createSurveyAdmin);
        Button update_problem = findViewById(R.id.updateProblemBtnAdmin);
        Button pending_cars = findViewById(R.id.pendingCars);
        Button pending_tenants = findViewById(R.id.pendingTenants);
        parking_btn.setOnClickListener(c -> {
            Intent intent = new Intent(MainMenuAdmin.this, ParkingScreen.class);
            intent.putExtra("building", BuildingID);
            intent.putExtra("email", my_email);
            startActivity(intent);
        });
        update_problem.setOnClickListener(v -> {
            Intent intent = new Intent(MainMenuAdmin.this, ProblemList.class);
            intent.putExtra("building", BuildingID);
            intent.putExtra("email", my_email);
            startActivity(intent);
        });
        chat_btn.setOnClickListener(v -> {
            Intent intent = new Intent(MainMenuAdmin.this, ChatScreen.class);
            intent.putExtra("building", BuildingID);
            intent.putExtra("email", my_email);
            startActivity(intent);
        });
        new_problem_btn.setOnClickListener(t -> {
            Intent intent2 = new Intent(MainMenuAdmin.this, ProblemStatus.class);
            intent2.putExtra("building", BuildingID);
            intent2.putExtra("email", my_email);
            startActivity(intent2);
        });
        answer_survey_btn.setOnClickListener(t -> {
            Intent intent = new Intent(MainMenuAdmin.this, SurveyAnswerOrResult.class);
            intent.putExtra("building", BuildingID);
            intent.putExtra("email", my_email);
            startActivity(intent);
        });
        pending_tenants.setOnClickListener(t -> {
            Intent intent = new Intent(MainMenuAdmin.this, PendingTenants.class);
            intent.putExtra("building", BuildingID);
            intent.putExtra("email", my_email);
            startActivity(intent);
        });
        pending_cars.setOnClickListener(t -> {
            Intent intent = new Intent(MainMenuAdmin.this, PendingCars.class);
            intent.putExtra("building", BuildingID);
            intent.putExtra("email", my_email);
            startActivity(intent);
        });
        create_survey.setOnClickListener(t -> {
            Intent intent = new Intent(MainMenuAdmin.this, CreateSurvey.class);
            intent.putExtra("building", BuildingID);
            intent.putExtra("email", my_email);
            startActivity(intent);
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.i("MainMenuAdmin", "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("MainMenuAdmin", "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("MainMenuAdmin", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("MainMenuAdmin", "onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("MainMenuAdmin", "onRestart");
    }
}
