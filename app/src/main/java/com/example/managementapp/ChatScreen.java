//Not working 17:46
//package com.example.managementapp;
//
//import android.annotation.SuppressLint;
//import android.content.Intent;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.google.gson.Gson;
//import com.google.gson.JsonArray;
//import com.google.gson.JsonElement;
//import com.google.gson.JsonObject;
//
//import java.io.IOException;
//import java.io.OutputStream;
//import java.lang.reflect.Type;
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.List;
//import java.util.Locale;
//import java.util.Map;
//import java.util.TimeZone;
//
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//
//
//public class ChatScreen extends AppCompatActivity {
//    private View back;
//    private View send;
//    private EditText input;
//    private String BuildingID;
//    private String my_email;
//    private TextView building_title;
//    private List<Message> messages = new ArrayList<Message>();
//    ///private static ChatAdapter chatAdapter = new ChatAdapter(messages);
//    private static ChatAdapter chatAdapter;
//    private static RecyclerView recyclerView;
//    private String content;
//    private String Full_name;
//    private static final String SERVER_URL = "http://"+GetIP.getIPAddress()+":5000/users/get_name_by_email?email=%s";
//    private static final String GET_BUILDING_URL = "http://"+GetIP.getIPAddress()+":5000/buildings/get_chat_by_building?address=%s";
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_chat_screen);
//        BuildingID = getIntent().getExtras().getString("building");
//        my_email = getIntent().getExtras().getString("email");
//        building_title = findViewById(R.id.textName);
//        building_title.setText(BuildingID);
//        recyclerView = findViewById(R.id.chatRecyclerView);
////        chatAdapter = new ChatAdapter(messages);
////        recyclerView.setAdapter(chatAdapter);
////        chatRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        get_messages(BuildingID);
//        chatAdapter = new ChatAdapter(messages);
//        recyclerView.setAdapter(chatAdapter);
//        // recyclerView.setLayoutManager(new LinearLayoutManager(this));
//    }
//    private static final SimpleDateFormat dateFormat =
//            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
//    static {
//        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
//    }
//    @NonNull
//    public static ArrayList<Message> convertJsonToMessages(String json) {
//        ArrayList<Message> m = new ArrayList<>();
//        Gson gson = new Gson();
//        JsonArray jsonArray = gson.fromJson(json, JsonArray.class);
//        //JsonArray jsonMessages = jsonArray.getAsJsonArray("messages");
//        for (JsonElement jsonElement : jsonArray) {
//            JsonObject jsonMessage = jsonElement.getAsJsonObject();
//            String content = jsonMessage.get("content").getAsString();
//            String dateString = jsonMessage.get("date").getAsString();
////            Date date;
////            try {
////                date = dateFormat.parse(dateString);
////            } catch (Exception e) {
////                date = new Date();
////            }
//            String sender = jsonMessage.get("sender").getAsString();
//            String email = jsonMessage.get("email").getAsString();
//            Message message = new Message(content, dateString, sender, email);
//            m.add(message);
//        }
//        return m;
//    }
//    protected void get_messages(String BuildingID){
//        URL url = null;
//        try {
//            url = new URL(String.format(GET_BUILDING_URL, BuildingID));
//        } catch (MalformedURLException e) {
//            throw new RuntimeException(e);
//        }
//        OkHttpClient client = new OkHttpClient();
//        Request request = new Request.Builder().url(url).build();
//        client.newCall(request).enqueue(new okhttp3.Callback() {
//            @Override
//            public void onFailure(okhttp3.Call call, IOException e) {
//                e.printStackTrace();
//            }
//
//            @Override
//            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
//                if (response.isSuccessful()) {
//                    String jsonResponse = response.body().string();
//                    messages = convertJsonToMessages(jsonResponse);
//                } else {
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            onResume();
//                        }
//                    });
//                }
//            }
//        });
//    }
//    @SuppressLint("NotifyDataSetChanged")
//    @Override
//    protected void onResume(){
//        super.onResume();
////        get_messages(BuildingID);
////        chatAdapter = new ChatAdapter(m);
////        recyclerView.setAdapter(chatAdapter);
////        recyclerView.setAdapter(chatAdapter);
////        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        //chatAdapter = new ChatAdapter(messages);
//        //chatRecyclerView.setAdapter(chatAdapter);
//        //chatRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        //chatAdapter.notifyDataSetChanged();
//        //findViewById(R.id.chatRecyclerView).setVisibility(View.VISIBLE);
//        //recyclerView.setVisibility(View.VISIBLE);
//    }
//
//
//
//}

///////////////////////////////////////////////////////////////////////////////////////////////////
//import android.content.Intent;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.managementapp.ChatAdapter;
//import com.example.managementapp.MainMenu;
//import com.example.managementapp.Message;
//import com.google.gson.Gson;
//import com.google.gson.JsonArray;
//import com.google.gson.JsonElement;
//import com.google.gson.JsonObject;
//
//import java.io.IOException;
//import java.io.OutputStream;
//import java.lang.reflect.Type;
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.List;
//import java.util.Locale;
//import java.util.Map;
//import java.util.TimeZone;
//
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//
//public class ChatScreen extends AppCompatActivity {
//    private View back;
//    private View send;
//    private EditText input;
//    private String BuildingID;
//    private String my_email;
//    private TextView building_title;
//    private RecyclerView chatRecyclerView;
//    private List<Message> messages = new ArrayList<Message>();
//    //private final ChatAdapter chatAdapter = new ChatAdapter(messages);
//    private ChatAdapter chatAdapter;
//    private String content;
//    private String Full_name;
//    private static final String SERVER_URL = "http://192.168.10.108:5000/users/get_name_by_email?email=%s";
//    private static final String GET_BUILDING_URL = "http://192.168.10.108:5000/buildings/get_chat_by_building?address=%s";
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_chat_screen);
//        BuildingID = getIntent().getExtras().getString("building");
//        //get_messages(BuildingID);
//        my_email = getIntent().getExtras().getString("email");
//        building_title = findViewById(R.id.textName);
//        building_title.setText(BuildingID);
//        back = findViewById(R.id.imageBack);
//        URL url = null;
//        try {
//            url = new URL(String.format(SERVER_URL, my_email));
//        } catch (MalformedURLException e) {
//            throw new RuntimeException(e);
//        }
//        OkHttpClient client = new OkHttpClient();
//        Request request = new Request.Builder().url(url).build();
//        client.newCall(request).enqueue(new okhttp3.Callback() {
//            @Override
//            public void onFailure(okhttp3.Call call, IOException e) {
//                e.printStackTrace();
//            }
//
//            @Override
//            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
//                if (response.isSuccessful()) {
//                    String jsonResponse = response.body().string();
//                    Full_name = jsonResponse;
//                } else {
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            onResume();
//                        }
//                    });
//                }
//            }
//        });
//        back.setOnClickListener(v -> {
//            Intent intent = new Intent(ChatScreen.this, MainMenu.class);
//            intent.putExtra("building", BuildingID);
//            startActivity(intent);
//        });
//        input = findViewById(R.id.inputMessage);
//        send = findViewById(R.id.layoutSend);
//        send.setOnClickListener(v -> {
//            content = input.getText().toString();
//            new_message(Full_name, content);
//            input.setText("");
//        });
//        chatRecyclerView = findViewById(R.id.chatRecyclerView);
////        chatAdapter = new ChatAdapter(messages);
////        chatRecyclerView.setAdapter(chatAdapter);
////        chatRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//    }
//    private static final SimpleDateFormat dateFormat =
//            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
//    static {
//        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
//    }
//    @NonNull
//    public static ArrayList<Message> convertJsonToMessages(String json) {
//        ArrayList<Message> m = new ArrayList<>();
//        Gson gson = new Gson();
//        JsonArray jsonArray = gson.fromJson(json, JsonArray.class);
//        //JsonArray jsonMessages = jsonArray.getAsJsonArray("messages");
//        for (JsonElement jsonElement : jsonArray) {
//            JsonObject jsonMessage = jsonElement.getAsJsonObject();
//            String content = jsonMessage.get("content").getAsString();
//            String dateString = jsonMessage.get("date").getAsString();
//            Date date;
//            try {
//                date = dateFormat.parse(dateString);
//            } catch (Exception e) {
//                date = new Date();
//            }
//            String sender = jsonMessage.get("sender").getAsString();
//            String email = jsonMessage.get("email").getAsString();
//            Message message = new Message(content, "2023-04-09", sender, email);
//            m.add(message);
//        }
//        return m;
//    }
//    protected void get_messages(String BuildingID){
//        URL url = null;
//        try {
//            url = new URL(String.format(GET_BUILDING_URL, BuildingID));
//        } catch (MalformedURLException e) {
//            throw new RuntimeException(e);
//        }
//        OkHttpClient client = new OkHttpClient();
//        Request request = new Request.Builder().url(url).build();
//        client.newCall(request).enqueue(new okhttp3.Callback() {
//            @Override
//            public void onFailure(okhttp3.Call call, IOException e) {
//                e.printStackTrace();
//            }
//
//            @Override
//            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
//                if (response.isSuccessful()) {
//                    String jsonResponse = response.body().string();
//                    messages = convertJsonToMessages(jsonResponse);
//                } else {
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            onResume();
//                        }
//                    });
//                }
//            }
//        });
//    }
//    @Override
//    protected void onStart() {
//        super.onStart();
//        Log.i("MainMenu", "onStart");
//    }
//
//    protected void onResume(){
//        super.onResume();
//        //get_messages(BuildingID);
//        URL url = null;
//        try {
//            url = new URL(String.format(GET_BUILDING_URL, BuildingID));
//        } catch (MalformedURLException e) {
//            throw new RuntimeException(e);
//        }
//        OkHttpClient client = new OkHttpClient();
//        Request request = new Request.Builder().url(url).build();
//        client.newCall(request).enqueue(new okhttp3.Callback() {
//            @Override
//            public void onFailure(okhttp3.Call call, IOException e) {
//                e.printStackTrace();
//            }
//
//            @Override
//            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
//                if (response.isSuccessful()) {
//                    String jsonResponse = response.body().string();
//                    messages = convertJsonToMessages(jsonResponse);
//                } else {
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            onResume();
//                        }
//                    });
//                }
//            }
//        });
//        chatAdapter = new ChatAdapter(messages);
//        chatRecyclerView.setAdapter(chatAdapter);
//        chatRecyclerView.setLayoutManager(new LinearLayoutManager(this));
////        chatAdapter.notifyDataSetChanged();
//        chatRecyclerView.setVisibility(chatRecyclerView.VISIBLE);
//    }
//
//    public void new_message(String ConnectedUsername, String content) {
//        Date now = Calendar.getInstance().getTime();
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
//        String timestamp = dateFormat.format(now);
//
//        // Construct the message JSON object
//        String json = "{"
//                + "\"sender\":\"" + Full_name + "\","
//                + "\"content\":\"" + content + "\","
//                + "\"timestamp\":\"" + timestamp + "\""
//                + "}";
//        new SendMessageTask().execute(json);
//    }
//    // Background task to send a message to the server
//    private static class SendMessageTask extends AsyncTask<String, Void, Void> {
//        @Override
//        protected Void doInBackground(String... params) {
//            try {
//                // Open a connection to the server
//                URL url = new URL(SERVER_URL);
//                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//                connection.setRequestMethod("POST");
//                connection.setRequestProperty("Content-Type", "application/json");
//                connection.setRequestProperty("Accept", "application/json");
//                connection.setDoOutput(true);
//
//                // Write the message to the connection's output stream
//                String json = params[0];
//                OutputStream outputStream = connection.getOutputStream();
//                outputStream.write(json.getBytes());
//                outputStream.flush();
//                outputStream.close();
//
//                // Check the response code for errors
//                int responseCode = connection.getResponseCode();
//                if (responseCode != HttpURLConnection.HTTP_OK) {
//                    throw new RuntimeException("Failed to send message: HTTP error code " + responseCode);
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        Log.i("MainMenu", "onPause");
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        Log.i("MainMenu", "onStop");
//    }
//
//    @Override
//    protected void onRestart() {
//        super.onRestart();
//        Log.i("MainMenu", "onRestart");
//    }
//
//}


/////////////////////////////////////////////////////////////////////////////////////////////////



//package com.example.managementapp;
//
//import android.annotation.SuppressLint;
//import android.content.Intent;
//import android.os.Build;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.RequiresApi;
//import androidx.appcompat.app.AppCompatActivity;
//
//
//import com.example.managementapp.databinding.ActivityChatScreenBinding;
//import com.google.gson.Gson;
//import com.google.gson.JsonArray;
//import com.google.gson.JsonElement;
//import com.google.gson.JsonObject;
//
//import android.app.Notification;
//import android.app.NotificationChannel;
//import android.app.NotificationManager;
//import android.app.Service;
//import android.content.Intent;
//import android.os.Build;
//import android.os.IBinder;
//
//import androidx.annotation.NonNull;
//import androidx.core.app.NotificationCompat;
//import androidx.core.app.NotificationManagerCompat;
//
//import java.io.IOException;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.List;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//public class ChatScreen extends AppCompatActivity {
//
//    //static public int id = 9;
//    private List<Message> messages = new ArrayList<Message>();
//    private ActivityChatScreenBinding binding;
//    // private MessageDao messageDao;
//    private TextView name;
//    private View back;
//    private View send;
//    private EditText input;
//    private String user_email;
//    private ChatAdapter chatAdapter;
//    private String building_address;
//    private String content;
//    private static final String GET_BUILDING_URL = "http://192.168.10.108:5000/buildings/get_chat_by_building?address=%s";
//    @RequiresApi(api = Build.VERSION_CODES.O)
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        binding = ActivityChatScreenBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//        user_email = getIntent().getExtras().getString("email");
//        building_address = getIntent().getExtras().getString("building");
//        name = findViewById(R.id.textName);
//        back = findViewById(R.id.imageBack);
////        back.setOnClickListener(v -> {
////            Intent intent = new Intent(this, MainMenu.class);
////            startActivity(intent);
////        });
//        name.setText("" + building_address + "");
//        input = findViewById(R.id.inputMessage);
//        send = findViewById(R.id.layoutSend);
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                messages.add(new Message("String content", "String date", "String sender", "String email"));
//                chatAdapter = new ChatAdapter(messages);
//                binding.chatRecyclerView.setAdapter(chatAdapter);
//            }
//        });
////        get_messages(building_address);
////        chatAdapter = new ChatAdapter(messages);
////        binding.chatRecyclerView.setAdapter(chatAdapter);

////        send.setOnClickListener(v -> {
////            content = input.getText().toString();
////            new_message(contact_id, ConnectedUsername, content);
////            input.setText("");
////        });
//    }
//        @NonNull
//    public static ArrayList<Message> convertJsonToMessages(String json) {
//        ArrayList<Message> m = new ArrayList<>();
//        Gson gson = new Gson();
//        JsonArray jsonArray = gson.fromJson(json, JsonArray.class);
//        //JsonArray jsonMessages = jsonArray.getAsJsonArray("messages");
//        for (JsonElement jsonElement : jsonArray) {
//            JsonObject jsonMessage = jsonElement.getAsJsonObject();
//            String content = jsonMessage.get("content").getAsString();
//            String dateString = jsonMessage.get("date").getAsString();
////            Date date;
////            try {
////                date = dateFormat.parse(dateString);
////            } catch (Exception e) {
////                date = new Date();
////            }
//            String sender = jsonMessage.get("sender").getAsString();
//            String email = jsonMessage.get("email").getAsString();
//            Message message = new Message(content, dateString, sender, email);
//            m.add(message);
//        }
//        return m;
//    }
//    protected void get_messages(String BuildingID){
//        URL url = null;
//        try {
//            url = new URL(String.format(GET_BUILDING_URL, BuildingID));
//        } catch (MalformedURLException e) {
//            throw new RuntimeException(e);
//        }
//        OkHttpClient client = new OkHttpClient();
//        Request request = new Request.Builder().url(url).build();
//        client.newCall(request).enqueue(new okhttp3.Callback() {
//            @Override
//            public void onFailure(okhttp3.Call call, IOException e) {
//                e.printStackTrace();
//            }
//
//            @Override
//            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
//                if (response.isSuccessful()) {
//                    String jsonResponse = response.body().string();
//                    messages = convertJsonToMessages(jsonResponse);
//                } else {
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            onResume();
//                        }
//                    });
//                }
//            }
//        });
//    }
//    @SuppressLint("NotifyDataSetChanged")
//    @Override
//    protected void onResume(){
//        super.onResume();
//        chatAdapter.notifyDataSetChanged();
//        binding.chatRecyclerView.setVisibility(View.VISIBLE);
//    }

//    public void new_message(String contact_id,String ConnectedUsername, String content){
//        PostAPI postAPI = new PostAPI();
//        WebServiceAPI webServiceAPI = postAPI.getWebServiceAPI();
//        Call<Void> call = webServiceAPI.transfer(new Transfer(ConnectedUsername, contact_id, content));
//        call.enqueue(new Callback<Void>() {
//            @Override
//            public void onResponse(Call<Void> call, Response<Void> response) {
//                if(response.raw().code() == 201) {
//                    Call<Void> call2 = webServiceAPI.newmessage(contact_id,new Addmsg(ConnectedUsername,contact_id,content));
//                    call2.enqueue(new Callback<Void>() {
//                        @Override
//                        public void onResponse(Call<Void> call2, Response<Void> response2) {
//                            for (Message m : messages) {
//                                messageDao.delete(m);
//                            }
//                            messages.clear();
//                            Call<List<Message>> call3 = webServiceAPI.getmessages(contact_id, ConnectedUsername);
//                            call3.enqueue(new retrofit2.Callback<List<Message>>() {
//                                @Override
//                                public void onResponse(Call<List<Message>> call3, Response<List<Message>> response3) {
//                                    List<Message> messages2 = response3.body();
//                                    for (Message message : messages2) {
//                                        message.setContactID(contact_id);
//                                        messageDao.insert(message);
//                                    }
//                                    onResume();
//                                }
//
//                                @Override
//                                public void onFailure(Call<List<Message>> call3, Throwable t) {
//                                    System.out.println("connection failed");
//                                }
//                            });
//                            //onResume();
//                        }
//
//                        @Override
//                        public void onFailure(Call<Void> call, Throwable t) {
//                            Toast.makeText(ChatScreen.this, "FAILED !!!!!!!!!!!", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }
//                onResume();
//            }
//
//            @Override
//            public void onFailure(Call<Void> call, Throwable t) {
//
//            }
//        });
//    }
//}







/////////////////
//-----------------try dvir------------------------------
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

