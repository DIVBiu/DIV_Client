package com.example.managementapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

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

public class ProblemList extends AppCompatActivity implements RecyclerViewInterface {
    private RecyclerView recyclerView;
    private List<String> categoryOptions;
    private String my_email, address, car_email, tenant_name, answer;
    private ProblemAdapter adapter;
    private List<Problem> problems;
    private Problem clickedProblem;
    private int position;
    private static final String SERVER_URL = "http://" + GetIP.getIPAddress() + ":5000/get_problems_by_building?address=%s";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_problem_list);
        recyclerView = findViewById(R.id.recyclerView);
        problems = new ArrayList<>();
        adapter = new ProblemAdapter(this, problems, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        my_email = getIntent().getExtras().get("email").toString();
        address = getIntent().getExtras().get("building").toString();

        categoryOptions = new ArrayList<>();
        categoryOptions.add("All");
        categoryOptions.add("Electricity");
        categoryOptions.add("Plumbing");
        categoryOptions.add("Infrastructure");
        categoryOptions.add("Construction");
        categoryOptions.add("Other");

        try {
            get_problems(address);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        Spinner categorySpinner = findViewById(R.id.categorySpinner);
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categoryOptions);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);

        // Set up the spinner item selection listener
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCategory = categoryOptions.get(position);
                adapter.getFilter().filter(selectedCategory);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    @Override
    public void onItemClick(int position) {
        this.position = position;
        clickedProblem = problems.get(position);
        car_email = clickedProblem.getProblem_creator_email();
        Intent intent = new Intent(ProblemList.this, ProblemDetails.class);
        intent.putExtra("type", clickedProblem.getType());
        intent.putExtra("id", clickedProblem.getId());
        intent.putExtra("address", address);
        intent.putExtra("email", my_email);
        intent.putExtra("opening_date", clickedProblem.getOpening_date());
        intent.putExtra("description", clickedProblem.getDescription());
        intent.putExtra("status", clickedProblem.getStatus());
        intent.putExtra("image", clickedProblem.getImage());
        //intent.putStringArrayListExtra("buildings", buildings);
        startActivity(intent);
    }

    private static ArrayList<Problem> convertJsonProblems(String json) {
        Type listType = new TypeToken<ArrayList<Problem>>() {
        }.getType();
        Gson gson = new Gson();
        ArrayList<Problem> list = gson.fromJson(json, listType);
        return list;
    }

            public void get_problems(String address) throws MalformedURLException {
                URL url = new URL(String.format(SERVER_URL, address));
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
                            Gson gson = new Gson();
                            Type listType = new TypeToken<List<String>>() {
                            }.getType();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    problems.clear();
                                    problems.addAll(convertJsonProblems(jsonResponse));
                                    adapter.notifyDataSetChanged();
                                    recyclerView.setVisibility(View.VISIBLE);
                                }
                            });

                        } else {
                            // Handle unsuccessful response
                            //Toast.makeText(ProblemList.this, "Problem Contacting the server", Toast.LENGTH_SHORT).show();
                            //onResume();
                        }
                    }
                });
            }

//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        adapter.notifyDataSetChanged();
//        recyclerView.setVisibility(View.VISIBLE);
//    }
}