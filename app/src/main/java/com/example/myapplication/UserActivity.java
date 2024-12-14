package com.example.myapplication;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class UserActivity extends AppCompatActivity {
    EditText pollId;
    Button searchPoll, castVote;
    ListView candidateList;
    DBHelper dbHelper;
    int userId;
    String selectedCandidate = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        pollId = findViewById(R.id.pollId);
        searchPoll = findViewById(R.id.searchPoll);
        castVote = findViewById(R.id.castVote);
        candidateList = findViewById(R.id.candidateList);

        dbHelper = new DBHelper(this);
        userId = getIntent().getIntExtra("userId", -1);

        searchPoll.setOnClickListener(v -> {
            String id = pollId.getText().toString();
            Cursor res = dbHelper.getPoll(id);
            if (res != null && res.getCount() > 0) {
                res.moveToFirst();
                String candidates = res.getString(2);
                String[] candidateArray = candidates.split(",");
                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_single_choice, candidateArray);
                candidateList.setAdapter(adapter);
                candidateList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                candidateList.setOnItemClickListener((parent, view, position, id1) -> selectedCandidate = candidateArray[position]);
            } else {
                Toast.makeText(this, "Poll not found!", Toast.LENGTH_SHORT).show();
            }
        });

        castVote.setOnClickListener(v -> {
            if (selectedCandidate != null) {
                String id = pollId.getText().toString();
                if (dbHelper.castVote(Integer.parseInt(id), userId, selectedCandidate)) {
                    Toast.makeText(this, "Vote cast successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Error casting vote!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Please select a candidate!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
