package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AdminActivity extends AppCompatActivity {
    EditText pollName, candidates;
    Button createPoll;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        pollName = findViewById(R.id.pollName);
        candidates = findViewById(R.id.candidates);
        createPoll = findViewById(R.id.createPoll);

        dbHelper = new DBHelper(this);

        createPoll.setOnClickListener(v -> {
            String name = pollName.getText().toString();
            String candidateList = candidates.getText().toString();
            if (dbHelper.createPoll(name, candidateList)) {
                Toast.makeText(this, "Poll created successfully!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Error creating poll!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
