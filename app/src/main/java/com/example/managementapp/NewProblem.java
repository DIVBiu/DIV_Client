package com.example.managementapp;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NewProblem extends AppCompatActivity {
    private Spinner mySpinner;
    private Button submit;
    private EditText description;
    private int chosen_problem ;
    private String content,my_email,address, Date;

    private static final String SERVER_URL = "http://"+ GetIP.getIPAddress()+":5000/building/new_problem?address=%s&email=%s&type=%s&description=%s&date=%s";
    String[] options = {"","Electricity", "Plumbing", "infrastructure", "Construction", "Other"};
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_problem);
        submit = findViewById(R.id.confirm_problem);
        mySpinner = findViewById(R.id.spinner);
        description = findViewById(R.id.description);
        my_email = getIntent().getExtras().get("email").toString();
        address = getIntent().getExtras().get("building").toString();
        content = description.getText().toString();
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(NewProblem.this, android.R.layout.simple_spinner_item, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(adapter);
        submit.setOnClickListener(v -> {
            URL url = null;
            content = description.getText().toString();
            if (content.isEmpty() || chosen_problem == 0){
                Toast.makeText(NewProblem.this, "Please choose a problem and write a description", Toast.LENGTH_SHORT).show();
            }
            else{
                LocalDate currentDate = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    currentDate = LocalDate.now();
                }
                Date = currentDate.toString();
                try {
                    url = new URL(String.format(SERVER_URL, address,my_email,chosen_problem, content, Date));
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("address", address)
                        .addFormDataPart("email", my_email)
                        .addFormDataPart("description", content)
                        .addFormDataPart("type", String.valueOf(chosen_problem))
                        .addFormDataPart("date", Date)
                        .build();
                Request request = new Request.Builder()
                        .url(url)
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
//                            String responseBody = response.body().string();
//                            Log.d("LoginResponse", responseBody);
//                            Intent intent = new Intent(NewProblem.this, MainMenu.class);
//                            intent.putExtra("building", address);
//                            intent.putExtra("email", my_email);
//                            startActivity(intent);
                            // Handle the response body here
                        } else {
                            // Handle unsuccessful response
                            //Toast.makeText(NewProblem.this, "Username or password are incorrect", Toast.LENGTH_SHORT).show();
                            onResume();
                        }
                    }
                });
                showCustomDialog();
            }});

        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String value = parent.getItemAtPosition(position).toString();
                chosen_problem = position;
                Toast.makeText(NewProblem.this, value,Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    void showCustomDialog() {
        final Dialog dialog = new Dialog(NewProblem.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.confirm_new_problem);
        Button ok = dialog.findViewById(R.id.confirm);
        ok.setOnClickListener(v -> {
            dialog.dismiss();
            Intent intent = new Intent(NewProblem.this, MainMenu.class);
            intent.putExtra("building", address);
            intent.putExtra("email", my_email);
            startActivity(intent);
        });
        dialog.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("NewProblemPage", "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("NewProblemPage", "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("NewProblemPage", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("NewProblemPage", "onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("NewProblemPage", "onRestart");
    }
    private class MyObject {
        private String name;

        public MyObject(String name) {
            this.name = name;
        }

        public String toString() {
            return name;
        }
    }
}

