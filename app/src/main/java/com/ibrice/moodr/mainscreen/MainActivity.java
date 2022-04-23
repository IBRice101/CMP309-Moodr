package com.ibrice.moodr.mainscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import com.ibrice.moodr.R;
import com.ibrice.moodr.diary.CalendarActivity;
import com.ibrice.moodr.habits.CreateHabitsActivity;
import com.ibrice.moodr.habits.ViewHabitsActivity;
import com.ibrice.moodr.reports.ReportsActivity;
import com.ibrice.moodr.settings.SettingsActivity;
import com.ibrice.moodr.threegoodthings.ThreeGoodThingsActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set on click listeners for navigation buttons
        Button btnDiary = findViewById(R.id.btnDiary);
        btnDiary.setOnClickListener(v -> {
            Intent diaryIntent = new Intent(this, CalendarActivity.class);
            diaryIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP); // will not run if already at the top
            startActivity(diaryIntent);
        });

        Button btnHabits = findViewById(R.id.btnHabits);
        btnHabits.setOnClickListener(v -> {
            Intent habitsIntent = new Intent(this, ViewHabitsActivity.class);
            habitsIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(habitsIntent);
        });

        Button btnThreegoodthings = findViewById(R.id.btnThreeGoodThings);
        btnThreegoodthings.setOnClickListener(v -> {
            Intent threegoodthingsIntent = new Intent(this, ThreeGoodThingsActivity.class);
            threegoodthingsIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(threegoodthingsIntent);
        });

        Button btnReports = findViewById(R.id.btnReports);
        btnReports.setOnClickListener(v -> {
            Intent reportsIntent = new Intent(this, ReportsActivity.class);
            reportsIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(reportsIntent);
        });

        ImageButton btnSettings = findViewById(R.id.btnSettings);
        btnSettings.setOnClickListener(v -> {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            settingsIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(settingsIntent);
        });

    }
}