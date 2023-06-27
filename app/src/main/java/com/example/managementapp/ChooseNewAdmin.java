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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ChooseNewAdmin extends AppCompatActivity {
    private static final String SERVER_URL = "http://" + GetIP.getIPAddress() + ":5000/buildings/get_tenants_by_building?address=%s";
    private static final String SERVER_URL2 = "http://" + GetIP.getIPAddress() + ":5000/buildings/update_admin?address=%s&new_email=%s";
    private ListView lvAdmins;
    private NewAdminAdapter adapter;
    ArrayList<String> admins;
    private final ArrayList<String> adminlist = new ArrayList<>();
    private String my_email, building;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_choose_new_admin);
        lvAdmins = findViewById(R.id.my_listview);
        admins = new ArrayList<>();
        adapter = new NewAdminAdapter(this, adminlist);
        lvAdmins.setAdapter(adapter);

        my_email = getIntent().getExtras().get("email").toString();
        building = getIntent().getExtras().get("building").toString();

        try {
            get_admins(building);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        lvAdmins.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = adapter.getItem(position);

                URL url = null;
                try {
                    url = new URL(String.format(SERVER_URL2, building, selectedItem));
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url(url).build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.isSuccessful()) {
                            Intent intent = new Intent(ChooseNewAdmin.this, ChooseBuilding.class);
                            intent.putExtra("email", my_email);
                            //intent.putStringArrayListExtra("buildings", buildings);
                            startActivity(intent);
                            // Handle the response body here
                        }
                        else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(ChooseNewAdmin.this, "Problem connecting to the server", Toast.LENGTH_SHORT).show();
                                    onResume();
                                }
                            });
                        }
                    }
                });
            }
        });

    }
    public void get_admins(String building) throws MalformedURLException {
        URL url = new URL(String.format(SERVER_URL, building));
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
                    JSONObject jsonObject;
                    try {
                        jsonObject = new JSONObject(jsonResponse);
                        JSONArray buildingsArray = jsonObject.getJSONArray("tenants");
                        admins.clear();
                        adminlist.clear();
                        for (int i = 0; i < buildingsArray.length(); i++) {
                            String building = buildingsArray.getString(i);
                            admins.add(building);
                        }
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                    adminlist.addAll(admins);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                            lvAdmins.setVisibility(View.VISIBLE);
                        }
                    });
                } else {
                    // Handle unsuccessful response
                    Toast.makeText(ChooseNewAdmin.this, "Username or password are incorrect", Toast.LENGTH_SHORT).show();
                    onResume();
                }
            }
        });
    }
}