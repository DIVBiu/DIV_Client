package com.example.managementapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CreateSurvey extends AppCompatActivity {
    private static final String SERVER_URL = "http://"+GetIP.getIPAddress()+":5000/buildings/add_survey_to_building?address=%s&title=%s&question=%s&list_of_answers=%s&deadline=%s";

    private EditText editTextTitle;
    private EditText editTextQuestion;
    private DatePicker datePickerDeadline;
    private LinearLayout optionContainer;
    private Button buttonAddOption;
    private Button buttonCreateSurvey;
    private String my_email, address;

    private List<EditText> optionEditTextList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_create_survey);
        my_email = getIntent().getExtras().get("email").toString();
        address = getIntent().getExtras().get("building").toString();
        // Initialize views
        editTextTitle = findViewById(R.id.editTextTitle);
        editTextQuestion = findViewById(R.id.editTextQuestion);
        datePickerDeadline = findViewById(R.id.datePickerDeadline);
        optionContainer = findViewById(R.id.optionContainer);
        buttonAddOption = findViewById(R.id.buttonAddOption);
        buttonCreateSurvey = findViewById(R.id.buttonCreateSurvey);

        optionEditTextList = new ArrayList<>();

        // Add an initial option field
        addOptionField();

        // Add option button click listener
        buttonAddOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addOptionField();
            }
        });

        // Create survey button click listener
        buttonCreateSurvey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createSurvey();
            }
        });
    }

    private void addOptionField() {
        EditText optionEditText = new EditText(this);
        optionEditText.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        optionEditText.setHint("Enter voting option");
        optionContainer.addView(optionEditText);
        optionEditTextList.add(optionEditText);
    }

    private void createSurvey() {
        // Retrieve survey details
        String title = editTextTitle.getText().toString();
        String question = editTextQuestion.getText().toString();
        int day = datePickerDeadline.getDayOfMonth();
        int month = datePickerDeadline.getMonth();
        int year = datePickerDeadline.getYear();

        // Create a Calendar object for the deadline date
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        Date deadline = calendar.getTime();

        // Retrieve voting options
//        List<String> options = new ArrayList<>();
//        for (EditText optionEditText : optionEditTextList) {
//            String optionText = optionEditText.getText().toString();
//            if (!TextUtils.isEmpty(optionText)) {
//                String option = new String(optionText);
//                options.add(option);
//            }
//        }

        String options = "";
        for (EditText optionEditText : optionEditTextList) {
            String optionText = optionEditText.getText().toString();
            if (!TextUtils.isEmpty(optionText)) {
                String option = new String(optionText);
                if (options.equals("")){
                    options = option;
                    continue;
                }
                options = options + "$" + option;
            }
        }
        URL url = null;
        try {
            url = new URL(String.format(SERVER_URL, address,title, question,options,deadline));
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("address", address)
                .addFormDataPart("title", title)
                .addFormDataPart("question", question)
                .addFormDataPart("options", options)
                .addFormDataPart("deadline", deadline.toString())
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    Intent intent = new Intent(CreateSurvey.this, MainMenuAdmin.class);
                    intent.putExtra("email", my_email);
                    intent.putExtra("address", address);
                    startActivity(intent);
                    // Handle the response body here
                } else {
                    // Handle unsuccessful response
                    onResume();
                }
            }
        });

        // Create the survey object
        //Survey survey = new Survey(title, question, deadline, options);

        // Perform further actions with the created survey, such as saving to a database or sending to a server
    }
}

