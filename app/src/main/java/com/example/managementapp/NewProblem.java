package com.example.managementapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class NewProblem extends AppCompatActivity {
    private Spinner mySpinner;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_problem);

//        mySpinner = findViewById(R.id.my_spinner);
//
//        String[] options = {"Option 1", "Option 2", "Option 3"};
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        mySpinner.setAdapter(adapter);
        mySpinner = findViewById(R.id.my_spinner);

        List<MyObject> options = new ArrayList<>();
        options.add(new MyObject("Option 1"));
        options.add(new MyObject("Option 2"));
        options.add(new MyObject("Option 3"));

        ArrayAdapter<MyObject> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mySpinner.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("SignUpPage", "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("SignUpPage", "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("SignUpPage", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("SignUpPage", "onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("SignUpPage", "onRestart");
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

