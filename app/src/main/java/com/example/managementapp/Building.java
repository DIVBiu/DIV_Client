package com.example.managementapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Building extends AppCompatActivity {

    private EditText buildingEditText;
    private RadioGroup radioGroup;
    private RadioButton committeeRadioButton, tenantRadioButton;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building);

        buildingEditText = findViewById(R.id.buildingEditText);
        radioGroup = findViewById(R.id.radioGroup);
        committeeRadioButton = findViewById(R.id.committeeRadioButton);
        tenantRadioButton = findViewById(R.id.tenantRadioButton);
        submitButton = findViewById(R.id.submitButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String buildingName = buildingEditText.getText().toString();
                int selectedId = radioGroup.getCheckedRadioButtonId();
                if(selectedId == R.id.committeeRadioButton) {
                    // House Committee Manager option selected
                    Toast.makeText(Building.this, "Building: " + buildingName + "\nOption: House Committee Manager", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Building.this, MainMenu.class);
                    intent.putExtra("building", buildingName);
                    startActivity(intent);
                } else if(selectedId == R.id.tenantRadioButton) {
                    // Tenant option selected
                    Toast.makeText(Building.this, "Building: " + buildingName + "\nOption: Tenant", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Building.this, MainMenu.class);
                    intent.putExtra("building", buildingName);
                    startActivity(intent);
                } else {
                    // No option selected
                    Toast.makeText(Building.this, "Please select an option", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
