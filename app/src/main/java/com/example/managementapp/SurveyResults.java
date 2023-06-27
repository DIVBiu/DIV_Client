package com.example.managementapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SurveyResults extends AppCompatActivity {
    private String my_email;
    private String address;
    private ListView lvSurveys;
    private SurveyAdapter adapter;
    private ArrayList<Survey> surveys = new ArrayList<>();
    String Date;
    private static final String SERVER_URL = "http://" + GetIP.getIPAddress() + ":5000/buildings/get_answered_surveys_by_building?email=%s&address=%s&client_date=%s";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_survey_results);
        lvSurveys = findViewById(R.id.my_listview);
        lvSurveys.setClickable(true);
        adapter = new SurveyAdapter(this, surveys);
        lvSurveys.setAdapter(adapter);
        my_email = getIntent().getExtras().get("email").toString();
        address = getIntent().getExtras().get("building").toString();
        try {
            get_survey_result(my_email, address);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        lvSurveys.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                             @Override
                                             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                 Survey selectedItem = adapter.getItem(position);
                                                 Intent intent = new Intent(SurveyResults.this, ResultScreen.class);
                                                 intent.putExtra("title", selectedItem.getTitle());
                                                 intent.putExtra("email", my_email);
                                                 intent.putExtra("building", address);
                                                 startActivity(intent);
                                             }
                                         }
        );
    }

    public void get_survey_result(String email, String building_id) throws MalformedURLException {
        LocalDate currentDate = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            currentDate = LocalDate.now();
        }
        Date = currentDate.toString();
        URL url = null;
        try {
            url = new URL(String.format(SERVER_URL, email, building_id, Date));
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

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            surveys.clear();
                            surveys.addAll(convertJsonToSurveys(jsonResponse));
                            adapter.notifyDataSetChanged();
                            lvSurveys.setVisibility(View.VISIBLE);
                        }
                    });


                } else {
                    // Handle unsuccessful response
                    Toast.makeText(com.example.managementapp.SurveyResults.this, "Username or password are incorrect", Toast.LENGTH_SHORT).show();
                    onResume();
                }
            }
        });
    }

    private static ArrayList<Survey> convertJsonToSurveys(String json) {
        Type listType = new TypeToken<ArrayList<Survey>>() {
        }.getType();
        Gson gson = new Gson();
        ArrayList<Survey> surveysList = gson.fromJson(json, listType);
        return surveysList;
    }
}