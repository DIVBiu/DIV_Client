package com.example.managementapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class NewProblem extends AppCompatActivity {
    private Spinner mySpinner;
    String[] options = {"Option 1", "Option 2", "Option 3"};
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_problem);

        mySpinner = findViewById(R.id.spinner);
//
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        mySpinner = findViewById(R.id.spinner2);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(NewProblem.this, android.R.layout.simple_spinner_item, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(adapter);

        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String value = parent.getItemAtPosition(position).toString();
                Toast.makeText(NewProblem.this, value,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
//
//        List<MyObject> options = new ArrayList<>();
//        options.add(new MyObject("Option 1"));
//        options.add(new MyObject("Option 2"));
//        options.add(new MyObject("Option 3"));
//
//        ArrayAdapter<MyObject> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //mySpinner.setAdapter(adapter);
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

