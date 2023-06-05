package com.example.managementapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ProblemStatus extends AppCompatActivity {
    private String address,my_email;
    private ListView lvProblems;
    List<Problem> problems = new ArrayList<Problem>();
    private ArrayAdapter<Problem> adapter;
    private static final String SERVER_URL = "http://"+GetIP.getIPAddress()+":5000/get_problems?address=%s&email=%s";


    private static ArrayList<Problem> convertJsonToProblems(String json) {
        Type listType = new TypeToken<ArrayList<Problem>>() {}.getType();
        Gson gson = new Gson();
        ArrayList<Problem> problemList = gson.fromJson(json, listType);
        return problemList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem_status);
        address = getIntent().getExtras().getString("building");
        my_email = getIntent().getExtras().getString("email");
        lvProblems = findViewById(R.id.my_problem_listview);
        FloatingActionButton openD = findViewById(R.id.add_button);
        openD.setOnClickListener(v -> {
            Intent intent = new Intent(ProblemStatus.this, NewProblem.class);
            intent.putExtra("building", address);
            intent.putExtra("email", my_email);
            startActivity(intent);
        });
        try {
            get_problems(my_email, address);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public void get_problems(String email, String address) throws MalformedURLException {
        URL url = new URL(String.format(SERVER_URL,address, email));
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
                    problems = convertJsonToProblems(jsonResponse);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter = new ArrayAdapter<>(ProblemStatus.this, android.R.layout.simple_list_item_1);
                            lvProblems.setAdapter(adapter);
                            adapter.addAll(problems);
                        }
                    });
//                    adapter = new ArrayAdapter<>(ChooseBuilding.this, android.R.layout.simple_list_item_1);
//                    lvBuildings.setAdapter(adapter);
//                    adapter.addAll(buildings);

                } else {
                    // Handle unsuccessful response
                    //Toast.makeText(ProblemStatus.this, "Username or password are incorrect", Toast.LENGTH_SHORT).show();
                    //onResume();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("ProblemStatus", "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("ProblemStatus", "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("ProblemStatus", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("ProblemStatus", "onStop");
    }
}
