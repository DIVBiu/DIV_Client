package com.example.managementapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

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

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.http.Url;

public class PendingTenants extends AppCompatActivity {
    private RecyclerView recyclerView_t;
    private String my_email, address;
    private static final String SERVER_URL = "http://" + GetIP.getIPAddress() + ":5000/buildings/pending_approval_tenants?address=%s";
    private static final String APPROVE_URL = "http://" + GetIP.getIPAddress() + ":5000/buildings/approve_tenant?email=%s&address=%s&ans=%s";
    private TenantAdapter adapter;
    private List<Tenant> tenants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_pending_tenants);
        recyclerView_t = findViewById(R.id.recyclerView);
        tenants = new ArrayList<>();
        adapter = new TenantAdapter(tenants);
        recyclerView_t.setLayoutManager(new LinearLayoutManager(this));
        recyclerView_t.setAdapter(adapter);
        my_email = getIntent().getExtras().get("email").toString();
        address = getIntent().getExtras().get("building").toString();
        adapter.setButtonClickListener(new TenantAdapter.ButtonClickListener() {
            @Override
            public void onButton1Click(int position) {
                URL url = null;
                try {
                    url = new URL(String.format(APPROVE_URL, tenants.get(position).email, address, "1"));
                }
                catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("email", tenants.get(position).email)
                        .addFormDataPart("address", address)
                        .addFormDataPart("ans", "1")
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
                        if (response.isSuccessful()) {
                            get_tenants(address);
                        } else {
                            // Handle unsuccessful response
                            Toast.makeText(PendingTenants.this, "Username or password are incorrect", Toast.LENGTH_SHORT).show();
                            onResume();
                        }
                    }
                });
                tenants.remove(position);
                adapter.notifyDataSetChanged();
                recyclerView_t.setVisibility(View.VISIBLE);
            }

            @Override
            public void onButton2Click(int position) {
                URL url = null;
                try {
                    url = new URL(String.format(APPROVE_URL, tenants.get(position).email, address, "0"));
                }
                catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("email", tenants.get(position).email)
                        .addFormDataPart("address", address)
                        .addFormDataPart("ans", "0")
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
                        if (response.isSuccessful()) {
                            get_tenants(address);

                        } else {
                            // Handle unsuccessful response
                            Toast.makeText(PendingTenants.this, "Username or password are incorrect", Toast.LENGTH_SHORT).show();
                            onResume();
                        }
                    }
                });
                tenants.remove(position);
                adapter.notifyDataSetChanged();
                recyclerView_t.setVisibility(View.VISIBLE);
            }
        });

        try {
            get_tenants(address);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    private static ArrayList<Tenant> convertJsonTenants(String json) {
        Type listType = new TypeToken<ArrayList<Tenant>>() {}.getType();
        Gson gson = new Gson();
        ArrayList<Tenant> tenantsList = gson.fromJson(json, listType);
        return tenantsList;
    }

    public void get_tenants(String address) throws MalformedURLException {
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
                            tenants.addAll(convertJsonTenants(jsonResponse));
                            adapter.notifyDataSetChanged();
                            recyclerView_t.setVisibility(View.VISIBLE);
                        }
                    });

                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // Handle unsuccessful response
                            Toast.makeText(PendingTenants.this, "Username or password are incorrect", Toast.LENGTH_SHORT).show();
                            onResume();
                        }
                    });
                }
            }
        });
    }
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
        recyclerView_t.setVisibility(View.VISIBLE);
    }
}