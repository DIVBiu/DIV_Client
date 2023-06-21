package com.example.managementapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ChooseSurvey extends AppCompatActivity{

    private ListView lvSurveys;
    //List<String> buildings = new ArrayList<String>();
    private static final String SERVER_URL = "http://" + GetIP.getIPAddress() + ":5000/buildings/get_surveys_by_building?email=%s&address=%s&client_date=%s";

    private ArrayList<Survey> surveys = new ArrayList<>();
    private SurveyAdapter adapter;
    private String my_email;
    private String address;
    String Date;


    private static ArrayList<Survey> convertJsonToSurveys(String json) {
        Type listType = new TypeToken<ArrayList<Survey>>() {}.getType();
        Gson gson = new Gson();
        ArrayList<Survey> surveysList = gson.fromJson(json, listType);
        return surveysList;
    }

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_choose_survey);
        lvSurveys = findViewById(R.id.my_listview);
        lvSurveys.setClickable(true);
        adapter = new SurveyAdapter(this,surveys);
        lvSurveys.setAdapter(adapter);
        my_email = getIntent().getExtras().get("email").toString();
        address = getIntent().getExtras().get("building").toString();
        try {
            get_survey(my_email, address);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        lvSurveys.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Survey selectedItem = adapter.getItem(position);
                Intent intent = new Intent(ChooseSurvey.this, SurveyScreen.class);
                intent.putExtra("title", selectedItem.getTitle());
                intent.putExtra("email", my_email);
                intent.putExtra("building", address);
                startActivity(intent);
            }
        }
        );


    }

    public void get_survey(String email, String building_id) throws MalformedURLException {
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
//                            adapter = new ArrayAdapter<>(ChooseSurvey.this, android.R.layout.simple_list_item_1);
                            //adapter.addAll(surveys);
                            adapter.notifyDataSetChanged();
                            lvSurveys.setVisibility(View.VISIBLE);
                        }
                    });
//                    adapter = new ArrayAdapter<>(ChooseSurvey2.this, android.R.layout.simple_list_item_1);
//                    lvSurveys.setAdapter(adapter);
                    //adapter.addAll(surveys);

                } else {
                    // Handle unsuccessful response
                    Toast.makeText(com.example.managementapp.ChooseSurvey.this, "Username or password are incorrect", Toast.LENGTH_SHORT).show();
                    onResume();
                }
            }
        });
    }

    public static Map<String, String> jsonToMap(String json) {
        Gson gson = new Gson();
        Type type = Map.class.getTypeParameters()[1];
        Map<String, String> map = gson.fromJson(json, type);

        return map;
    }



    protected void onResume() {
        super.onResume();

    }
}

