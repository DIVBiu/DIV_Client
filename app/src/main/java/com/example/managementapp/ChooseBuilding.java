package com.example.managementapp;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    private static final String SERVER_URL = "http://" + GetIP.getIPAddress() + ":5000/users/get_buildings_by_user?email=%s";
    private static final String SERVER_URL2 = "http://" + GetIP.getIPAddress() + ":5000/am_I_admin?email=%s&address=%s";
    private static final String DELETE_SERVER_URL = "http://" + GetIP.getIPAddress() + ":5000/buildings/remove_tenant_from_building?email=%s&address=%s";
    ArrayList<String> buildings;
    private BuildingAdapter adapter;

    private final ArrayList<String> buildingList = new ArrayList<>();
    private String my_email;
    private RadioGroup radio_group;
    private RadioButton radio_button_tenant, radio_button_admin;
    private boolean userSelect;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_choosebuilding);
        lvBuildings = findViewById(R.id.my_listview);
        buildings = new ArrayList<>();
        adapter = new BuildingAdapter(this, buildingList);
        lvBuildings.setAdapter(adapter);
        my_email = getIntent().getExtras().get("email").toString();
        try {
            get_building(my_email);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        lvBuildings.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = adapter.getItem(position);
                URL url = null;
                try {
                    url = new URL(String.format(DELETE_SERVER_URL, my_email, selectedItem));
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
                        if (response.code() == 200) {
                            finish();

                        }
                        else if(response.code() == 201){
                            Intent intent = new Intent(ChooseBuilding.this, ChooseNewAdmin.class);
                            intent.putExtra("building", selectedItem);
                            intent.putExtra("email", my_email);
                            startActivity(intent);
                        }
                        else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(ChooseBuilding.this, "Problem with server", Toast.LENGTH_SHORT).show();
                                    onResume();
                                }
                            });
                        }
                    }
                });
                return true; // Return true to indicate that the long click event is consumed
            }
        });
        lvBuildings.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = adapter.getItem(position);

                URL url = null;
                try {
                    url = new URL(String.format(SERVER_URL2, my_email, selectedItem));
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
                        if (response.code() == 201) {
                            Intent intent = new Intent(ChooseBuilding.this, MainMenuAdmin.class);
                            intent.putExtra("email", my_email);
                            intent.putExtra("building", selectedItem);
                            //intent.putStringArrayListExtra("buildings", buildings);
                            startActivity(intent);
                            // Handle the response body here
                        } else if(response.code() == 200){
                            Intent intent = new Intent(ChooseBuilding.this, MainMenu.class);
                            intent.putExtra("email", my_email);
                            intent.putExtra("building", selectedItem);
                            startActivity(intent);
                        }
                        else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(ChooseBuilding.this, "Username or password are incorrect", Toast.LENGTH_SHORT).show();
                                    onResume();
                                }
                            });
                        }
                    }
                });
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
                    JSONObject jsonObject;
                    try {
                        jsonObject = new JSONObject(jsonResponse);
                        JSONArray buildingsArray = jsonObject.getJSONArray("buildings");
                        buildings.clear();
                        buildingList.clear();
                        for (int i = 0; i < buildingsArray.length(); i++) {
                            String building = buildingsArray.getString(i);
                            buildings.add(building);
                        }
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                    buildingList.addAll(buildings);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                            lvBuildings.setVisibility(View.VISIBLE);

                        }
                    });
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
        dialog.setContentView(R.layout.activity_building);

        final EditText contact_username = dialog.findViewById(R.id.contact_username);
        RadioButton radioButton_tenant = dialog.findViewById(R.id.tenantRadioButton);
        RadioButton radioButton_admin = dialog.findViewById(R.id.committeeRadioButton);
        Button add_contact_submitBtn = dialog.findViewById(R.id.add_contact_submitBtn);

        add_contact_submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String address = contact_username.getText().toString();
                if (address.isEmpty()) {
                    Toast.makeText(ChooseBuilding.this, "One of the fields is empty", Toast.LENGTH_SHORT).show();
                } else {
                    URL url = null;
                    if (radioButton_tenant.isChecked()) {
                        try {
                            url = new URL(String.format("http://" + GetIP.getIPAddress() + ":5000/buildings/add_tenant_to_building?email=%s&address=%s", my_email, address));
                        } catch (MalformedURLException e) {
                            throw new RuntimeException(e);
                        }
                    } else if (radioButton_admin.isChecked()) {
                        try {
                            url = new URL(String.format("http://" + GetIP.getIPAddress() + ":5000/buildings/new_building?email=%s&address=%s", my_email, address));
                        } catch (MalformedURLException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        Toast.makeText(ChooseBuilding.this, "Please Choose Your Type", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    RequestBody requestBody = new MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("email", my_email)
                            .addFormDataPart("address", address)
                            .build();
                    assert url != null;
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
                            if (response.code() == 200 || response.code() == 201) {
                                get_building(my_email);
                                dialog.dismiss();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(ChooseBuilding.this, "Your choice has been added successfully", Toast.LENGTH_SHORT).show();
                                    }
                                });

                            } else {
                                if (response.code() == 500) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(ChooseBuilding.this, "You are already a tenant in this building or the admin needs to approve you first", Toast.LENGTH_SHORT).show();
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
