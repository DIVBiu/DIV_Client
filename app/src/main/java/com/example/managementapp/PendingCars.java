package com.example.managementapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
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

public class PendingCars extends AppCompatActivity implements CarAdapter.OnItemClickListener {
    private RecyclerView recyclerView;
    private String my_email, address, car_email, tenant_name, answer;
    private CarAdapter adapter;
    private List<Car> cars;
    private Car clickedCar;
    private int car_position;
    private static final String SERVER_URL = "http://" + GetIP.getIPAddress() + ":5000/buildings/pending_approval_cars?address=%s";
    private static final String SERVER_URL2 = "http://" + GetIP.getIPAddress() + ":5000/amount_of_cars_per_building?address=%s&email=%s";
    private static final String DECLINE_APPROVE_SERVER_URL = "http://" + GetIP.getIPAddress() + ":5000/buildings/approve_car?address=%s&car_number=%s&ans=%s";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_tenants);
        recyclerView = findViewById(R.id.recyclerView);
        cars = new ArrayList<>();
        adapter = new CarAdapter(cars, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        my_email = getIntent().getExtras().get("email").toString();
        address = getIntent().getExtras().get("building").toString();
        try {
            get_cars(address);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onItemClick(int position) {
        car_position = position;
        clickedCar = cars.get(position);
        car_email = clickedCar.getOwner_email();
        tenant_name = clickedCar.getOwner_name();
        showCustomDialog();
    }

    private void showCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.car_popup, null);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();
        TextView messageTextView = dialogView.findViewById(R.id.messageTextView);
        Button approveButton = dialogView.findViewById(R.id.approveButton);
        Button declineButton = dialogView.findViewById(R.id.declineButton);
        URL url = null;
        try {
            url = new URL(String.format(SERVER_URL2, address, car_email));
        }
        catch (MalformedURLException e) {
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
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String jsonResponse = null;
                            try {
                                jsonResponse = response.body().string();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(jsonResponse);
                                answer = jsonObject.getString("answer");
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                            Gson gson = new Gson();
                            Type listType = new TypeToken<List<String>>() {
                            }.getType();
                            messageTextView.setText(tenant_name + " has already " + answer + " cars on this building, are you sure you want to approve this car?");

                        }
                    });
                } else {
                    // Handle unsuccessful response
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(PendingCars.this, "Username or password are incorrect", Toast.LENGTH_SHORT).show();
                            onResume();
                        }
                    });

                }
            }
        });
        //messageTextView.setText("This is a custom dialog message.");

        approveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                URL url = null;
                try {
                    url = new URL(String.format(DECLINE_APPROVE_SERVER_URL, address, clickedCar.getCar_number(),"1"));
                }
                catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("address", address)
                        .addFormDataPart("car_number", clickedCar.getCar_number())
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
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    cars.remove(car_position);
                                    adapter.notifyDataSetChanged();
                                    recyclerView.setVisibility(View.VISIBLE);
                                    dialog.dismiss();
                                }
                            });

                        } else {
                            // Handle unsuccessful response
                            onResume();
                        }
                    }
                });

                //dialog.dismiss();
            }
        });

        declineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                URL url = null;
                try {
                    url = new URL(String.format(DECLINE_APPROVE_SERVER_URL, address, clickedCar.getCar_number(),  "0"));
                }
                catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("address", address)
                        .addFormDataPart("car_number", clickedCar.getCar_number())
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
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    cars.remove(car_position);
                                    adapter.notifyDataSetChanged();
                                    recyclerView.setVisibility(View.VISIBLE);
                                    dialog.dismiss();
                                }
                            });
                        } else {
                            // Handle unsuccessful response
                            onResume();
                        }
                    }
                });
            }
        });


        dialog.show();
    }
    private static ArrayList<Car> convertJsonCars(String json) {
        Type listType = new TypeToken<ArrayList<Car>>() {}.getType();
        Gson gson = new Gson();
        ArrayList<Car> carsList = gson.fromJson(json, listType);
        return carsList;
    }
    public void get_cars(String address) throws MalformedURLException {
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
                            cars.addAll(convertJsonCars(jsonResponse));
                            adapter.notifyDataSetChanged();
                            recyclerView.setVisibility(View.VISIBLE);
                        }
                    });

                } else {
                    // Handle unsuccessful response
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            onResume();
                        }
                    });
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
        recyclerView.setVisibility(View.VISIBLE);
    }
}