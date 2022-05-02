package com.ibrice.moodr.diary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.ibrice.moodr.R;
import com.ibrice.moodr.database.DBManager;
import com.ibrice.moodr.mainscreen.MainActivity;

public class InputActivity extends AppCompatActivity {

    String mood;
    EditText edittxtTitle, edittxtDiary;

    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        setTitle("Add New Diary Entry");

        Button btnSubmitDiary = (Button) findViewById(R.id.btnSubmitDiary);
        edittxtTitle = findViewById(R.id.edittxtTitle);
        edittxtDiary = findViewById(R.id.edittxtDiary);

        // handlers for on click of each image button
        ImageButton imgbtnSad = findViewById(R.id.imgbtnSad);
        imgbtnSad.setOnClickListener(v -> {
            mood = "Sad";
            Toast.makeText(this, "You're feeling " + mood, Toast.LENGTH_SHORT).show();
        });

        ImageButton imgbtnDown = findViewById(R.id.imgbtnDown);
        imgbtnDown.setOnClickListener(v -> {
            mood = "Down";
            Toast.makeText(this, "You're feeling " + mood, Toast.LENGTH_SHORT).show();
        });

        ImageButton imgbtnOkay = findViewById(R.id.imgbtnOkay);
        imgbtnOkay.setOnClickListener(v -> {
            mood = "Okay";
            Toast.makeText(this, "You're feeling " + mood, Toast.LENGTH_SHORT).show();
        });

        ImageButton imgbtnGood = findViewById(R.id.imgbtnGood);
        imgbtnGood.setOnClickListener(v -> {
            mood = "Good";
            Toast.makeText(this, "You're feeling " + mood, Toast.LENGTH_SHORT).show();
        });

        ImageButton imgbtnHappy = findViewById(R.id.imgbtnHappy);
        imgbtnHappy.setOnClickListener(v -> {
            mood = "Happy";
            Toast.makeText(this, "You're feeling " + mood, Toast.LENGTH_SHORT).show();
        });

        btnSubmitDiary.setOnClickListener(v -> {
            String title = edittxtTitle.getText().toString();
            String entry = edittxtDiary.getText().toString();

            // check if fields are empty, else, insert into db
            if (mood.isEmpty()) {
                Toast.makeText(this,
                        "Please press one of the buttons above...", Toast.LENGTH_SHORT).show();
            } else if (title.isEmpty()) {
                Toast.makeText(this,
                        "Please enter a title...", Toast.LENGTH_SHORT).show();
            } else if (entry.isEmpty()){
                Toast.makeText(this,
                        "Please enter a diary entry...", Toast.LENGTH_SHORT).show();
            } else {
                dbManager = new DBManager(this);
                dbManager.open();

                dbManager.insertDiary(title, mood, entry);

                Toast.makeText(this, "Entry added!", Toast.LENGTH_SHORT).show();
                Intent returnToMain = new Intent(InputActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(returnToMain);

                dbManager.close();
            }
        });
    }
}