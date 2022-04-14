package com.ibrice.moodr.diary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.ibrice.moodr.R;

public class InputActivity extends AppCompatActivity {

    String mood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        // handlers for on click of each image button
        ImageButton imgbtnSad = findViewById(R.id.imgbtnSad);
        imgbtnSad.setOnClickListener(v -> {
            mood = "Sad";
            Toast.makeText(this, "You're feeling " + mood, Toast.LENGTH_SHORT).show();
            // do backend stuff here
        });
        ImageButton imgbtnDown = findViewById(R.id.imgbtnDown);
        imgbtnDown.setOnClickListener(v -> {
            mood = "Down";
            Toast.makeText(this, "You're feeling " + mood, Toast.LENGTH_SHORT).show();
            // do backend stuff here
        });
        ImageButton imgbtnOkay = findViewById(R.id.imgbtnOkay);
        imgbtnOkay.setOnClickListener(v -> {
            mood = "Okay";
            Toast.makeText(this, "You're feeling " + mood, Toast.LENGTH_SHORT).show();
            // do backend stuff here
        });
        ImageButton imgbtnGood = findViewById(R.id.imgbtnGood);
        imgbtnGood.setOnClickListener(v -> {
            mood = "Good";
            Toast.makeText(this, "You're feeling " + mood, Toast.LENGTH_SHORT).show();
            // do backend stuff here
        });
        ImageButton imgbtnHappy = findViewById(R.id.imgbtnHappy);
        imgbtnHappy.setOnClickListener(v -> {
            mood = "Happy";
            Toast.makeText(this, "You're feeling " + mood, Toast.LENGTH_SHORT).show();
            // do backend stuff here
        });

        Button btnSubmit = findViewById(R.id.btnSubmit);
        EditText edittxtDiary = findViewById(R.id.edittxtDiary);
        btnSubmit.setOnClickListener(v -> {
            String diary = edittxtDiary.getText().toString();
            if (diary.isEmpty()) {
                Toast.makeText(this,
                        "Please enter your diary entry...", Toast.LENGTH_SHORT).show();
            } else {
                // submit to database here
            }
        });
    }
}