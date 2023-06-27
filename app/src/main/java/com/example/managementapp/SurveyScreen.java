package com.example.managementapp;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SurveyScreen extends AppCompatActivity {
    private ArrayAdapter<String> adapter;
    List<String> l = new ArrayList<String>();
    private ListView lvSurvey;
    private String survey_title, email, building;
    private TextView title;
    private SurveyItem my_survey;
    private static final String SERVER_URL = "http://" + GetIP.getIPAddress() + ":5000/buildings/get_survey_by_title?title=%s";
    private static final String POST_SERVER_URL = "http://" + GetIP.getIPAddress() + ":5000/buildings/update_survey?email=%s&title=%s&choice=%s";
    Button survey_btn;

    public static SurveyItem fromJson(String json) {
        Gson gson = new Gson();
        SurveyItem surveyItem = gson.fromJson(json, SurveyItem.class);
        return surveyItem;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_survey);
        title = findViewById(R.id.title_textview);
        survey_btn = findViewById(R.id.survey_btn);
        survey_title = getIntent().getExtras().get("title").toString();
        email = getIntent().getExtras().get("email").toString();
        building = getIntent().getExtras().get("building").toString();
        get_survey(survey_title);
        lvSurvey = findViewById(R.id.survey_listview);
        lvSurvey.setChoiceMode(lvSurvey.CHOICE_MODE_SINGLE);

        survey_btn.setOnClickListener(t -> {
            showCustomDialog();
        });
    }

    void showCustomDialog() {
        final Dialog dialog = new Dialog(SurveyScreen.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.confirm_submittion);
        Button cancel = dialog.findViewById(R.id.cancel_btn);
        Button confirm_survey = dialog.findViewById(R.id.confirm_survey);

        confirm_survey.setOnClickListener(t -> {
                int selectedItemPosition = lvSurvey.getCheckedItemPosition();

                if (selectedItemPosition != ListView.INVALID_POSITION) {
                    String selectedItemValue = (String) lvSurvey.getItemAtPosition(selectedItemPosition);

                    URL url = null;
                    try {
                        url = new URL(String.format(POST_SERVER_URL, email, survey_title, String.valueOf(selectedItemPosition)));
                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    }
                    RequestBody requestBody = new MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("email", email)
                            .addFormDataPart("title", survey_title)
                            .addFormDataPart("choice", String.valueOf(selectedItemPosition))
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
                            if (response.code()==200) {
                                Intent intent = new Intent(SurveyScreen.this, MainMenu.class);
                                intent.putExtra("email", email);
                                intent.putExtra("building", building);
                                startActivity(intent);
                                // Handle the response body here
                            } else if(response.code() == 201) {
                                Intent intent = new Intent(SurveyScreen.this, MainMenuAdmin.class);
                                intent.putExtra("email", email);
                                intent.putExtra("building", building);
                                startActivity(intent);
                            }
                        }
                    });
                }
                // Do something with the selected item value
                else {
                    // No item was selected
                }
                }
        );
        cancel.setOnClickListener(v -> {
            dialog.dismiss();
            onResume();
        });
        dialog.show();
    }

    protected void get_survey(String survey_title){
        URL url = null;
        try {
            url = new URL(String.format(SERVER_URL, survey_title));
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String jsonResponse = response.body().string();
                    my_survey = fromJson(jsonResponse);
                    title.setText(my_survey.getQuestion());
                    l = my_survey.getList_of_answers();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter = new ArrayAdapter<>(SurveyScreen.this, android.R.layout.simple_list_item_single_choice, l);
                            lvSurvey.setAdapter(adapter);
                        }
                    });

                } else {
                    // Handle unsuccessful response
                    //onResume();
                }
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.i("SurveyScreen", "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("SurveyScreen", "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("SurveyScreen", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("SurveyScreen", "onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("SurveyScreen", "onRestart");
    }

}
