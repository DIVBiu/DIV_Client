package com.example.managementapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class SurveyAnswerOrResult extends AppCompatActivity {
    private String my_email;
    private String address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_survey_answer_or_result);
        Button answer_survey = findViewById(R.id.answerbtn);
        Button result_survey = findViewById(R.id.resultbtn);
        my_email = getIntent().getExtras().get("email").toString();
        address = getIntent().getExtras().get("building").toString();
        answer_survey.setOnClickListener(c -> {
            Intent intent = new Intent(SurveyAnswerOrResult.this, ChooseSurvey.class);
            intent.putExtra("building", address);
            intent.putExtra("email", my_email);
            startActivity(intent);
        });
        result_survey.setOnClickListener(c -> {
            Intent intent = new Intent(SurveyAnswerOrResult.this, SurveyResults.class);
            intent.putExtra("building", address);
            intent.putExtra("email", my_email);
            startActivity(intent);
        });
    }
}