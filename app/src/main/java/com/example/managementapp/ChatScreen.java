package com.example.managementapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.managementapp.ChatAdapter;
import com.example.managementapp.GetIP;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.time.LocalDate;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class ChatScreen extends AppCompatActivity {
    private View back;
    private View send;
    private EditText input;
    private String BuildingID;
    private String my_email;
    private TextView building_title;
    private List<Message> messages;
    private ChatAdapter chatAdapter;
    private RecyclerView recyclerView;
    private String content;
    String Date;
    private static final String GET_BUILDING_URL = "http://"+GetIP.getIPAddress()+":5000/buildings/get_chat_by_building?address=%s&email=%s";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_chat_screen);
        recyclerView = findViewById(R.id.chatRecyclerView);
        messages = new ArrayList<>();
        chatAdapter = new ChatAdapter(messages);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(chatAdapter);
        BuildingID = getIntent().getExtras().getString("building");
        my_email = getIntent().getExtras().getString("email");
        input = findViewById(R.id.inputMessage);
        content = input.getText().toString();
        building_title = findViewById(R.id.textName);
        building_title.setText("Chat Building: " + BuildingID);
        send = findViewById(R.id.layoutSend);
        send.setOnClickListener(v -> {
            content = input.getText().toString();
            try {
                new_message(content);
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
            input.setText("");
        });
        get_messages(BuildingID);
    }

    private static final SimpleDateFormat dateFormat =
            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
    static {
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    }
    @NonNull
    private static ArrayList<Message> convertJsonToMessages(String json) {
        Type listType = new TypeToken<ArrayList<Message>>() {}.getType();
        Gson gson = new Gson();
        ArrayList<Message> messagesList = gson.fromJson(json, listType);
        return messagesList;
    }
    private void get_messages(String BuildingID){
        URL url = null;
        try {
            url = new URL(String.format(GET_BUILDING_URL, BuildingID, my_email));
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                if (response.isSuccessful()) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            messages.clear();
                            String jsonResponse = null;
                            try {
                                jsonResponse = response.body().string();
                            } catch (IOException e) {
                                Log.i("failure1","bad");
                                throw new RuntimeException(e);
                            }

                            messages.addAll(convertJsonToMessages(jsonResponse));
                            chatAdapter.notifyDataSetChanged();
                            recyclerView.setVisibility(View.VISIBLE);
                        }
                    });

                } else {
                    System.out.println("connection failed in get message");
                }
            }
        });
    }

    public void new_message(String content) throws MalformedURLException {

        String url = "http://" +GetIP.getIPAddress() +":5000/buildings/add_message_to_chat?email=%s&address=%s&content=%s&date=%s";  // replace with your server URL

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDate currentDate = LocalDate.now();
            Date = currentDate.toString();
        }

        // Create a request to add the message to the server
        URL url1 = new URL(String.format(url, my_email,BuildingID, content, Date));
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("email", my_email)
                .addFormDataPart("address", BuildingID)
                .addFormDataPart("content", content)
                .addFormDataPart("date", Date)
                .build();
        Request request = new Request.Builder()
                .url(url1)
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
                if (response.isSuccessful()) {
                    messages.clear();
                    URL url = null;
                    try {
                        url = new URL(String.format(GET_BUILDING_URL, BuildingID, my_email));
                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    }
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url(url).build();
                    client.newCall(request).enqueue(new okhttp3.Callback() {
                        @Override
                        public void onFailure(okhttp3.Call call, IOException e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                            if (response.isSuccessful()) {
                                String jsonResponse = response.body().string();
                                //onResume();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        messages.addAll(convertJsonToMessages(jsonResponse));
                                        chatAdapter.notifyDataSetChanged();
                                        recyclerView.setVisibility(View.VISIBLE);
                                    }
                                });

                            } else {
                                System.out.println("connection failed in the new msg");
                            }
                        }
                    });

                } else {
                    // Handle unsuccessful response
                    Log.d("ChatScreen", "BADDDDDD");
                    //onResume();
                }
                //onResume();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("ChatScreen", "onResume");
        chatAdapter.notifyDataSetChanged();
        recyclerView.setVisibility(View.VISIBLE);
    }
}