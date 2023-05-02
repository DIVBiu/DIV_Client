package com.example.managementapp;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class ChooseBuilding extends AppCompatActivity {
    private ListView lvBuildings;
    //List<String> buildings = new ArrayList<String>();
    private static final String SERVER_URL = "http://"+GetIP.getIPAddress()+":5000/users/get_buildings_by_user?email=%s";

    List<String> buildings = new ArrayList<String>();
    private ArrayAdapter<String> adapter;
    private String my_email;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choosebuilding);
        lvBuildings = findViewById(R.id.my_listview);
        my_email = getIntent().getExtras().get("email").toString();
        try {
            get_building(my_email);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        lvBuildings.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = adapter.getItem(position);
                Intent intent = new Intent(ChooseBuilding.this, MainMenu.class);
                intent.putExtra("building", selectedItem);
                intent.putExtra("email", my_email);
                startActivity(intent);
            }
        });

        FloatingActionButton openDialog = findViewById(R.id.addBuildingBtn);
        openDialog.setOnClickListener(v -> {
            showCustomDialog();
        });

    }

    public void get_building(String email) throws MalformedURLException {
        URL url = new URL(String.format(SERVER_URL, email));
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
                    List<String> buildingList = gson.fromJson(jsonResponse, listType);
                    ArrayList<String> list = new ArrayList<>(buildingList);
                    buildings = list;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter = new ArrayAdapter<>(ChooseBuilding.this, android.R.layout.simple_list_item_1);
                            lvBuildings.setAdapter(adapter);
                            adapter.addAll(buildings);
                        }
                    });
//                    adapter = new ArrayAdapter<>(ChooseBuilding.this, android.R.layout.simple_list_item_1);
//                    lvBuildings.setAdapter(adapter);
//                    adapter.addAll(buildings);

                } else {
                    // Handle unsuccessful response
                    Toast.makeText(ChooseBuilding.this, "Username or password are incorrect", Toast.LENGTH_SHORT).show();
                    onResume();
                }
            }
        });
    }

    void showCustomDialog() {
        final Dialog dialog = new Dialog(ChooseBuilding.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.custom_add_building);

        final EditText contact_username = dialog.findViewById(R.id.contact_username);
        //final EditText contact_name = dialog.findViewById(R.id.contact_nickname);
        //final EditText contact_server = dialog.findViewById(R.id.contact_server);
        Button add_contact_submitBtn = dialog.findViewById(R.id.add_contact_submitBtn);
        add_contact_submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address = contact_username.getText().toString();
                if (address.isEmpty()) {
                    Toast.makeText(ChooseBuilding.this, "One of the fields is empty", Toast.LENGTH_SHORT).show();
                } else {
                    URL url = null;
                    try {
                        url = new URL(String.format("http://"+ GetIP.getIPAddress()+":5000/buildings/add_tenant_to_building?email=%s&address=%s", my_email, address));
                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    }
                    RequestBody requestBody = new MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("email", my_email)
                            .addFormDataPart("address", address)
                            .build();
                    Request request = new Request.Builder()
                            .url(url)
                            .put(requestBody)
                            .build();
                    OkHttpClient client = new OkHttpClient();
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(okhttp3.Call call, IOException e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            if (response.code() == 200) {
                                get_building(my_email);
                                dialog.dismiss();
                            } else {
                                if (response.code() == 500) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(ChooseBuilding.this, "You are already a tenant in this building", Toast.LENGTH_SHORT).show();
                                            onResume();
                                        }
                                    });
                                }
                                if (response.code() == 404) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(ChooseBuilding.this, "Not a Valid Building", Toast.LENGTH_SHORT).show();
                                            onResume();
                                        }
                                    });
                                }
                            }
                        }
                    });
                }

            }
        });
        dialog.show();
    }

    protected void onResume() {
        super.onResume();

    }
}
