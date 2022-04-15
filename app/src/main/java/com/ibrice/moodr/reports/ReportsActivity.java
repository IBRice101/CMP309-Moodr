package com.ibrice.moodr.reports;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.ibrice.moodr.R;
import com.ibrice.moodr.threegoodthings.ThreeGoodThingsActivity;

public class ReportsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        // set on click listeners for navigation binding
        Button btnReportDiary = findViewById(R.id.btnReportDiary);
        btnReportDiary.setOnClickListener(v -> {
            Intent diaryIntent = new Intent(this, ReportsDiaryActivity.class);
            diaryIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP); // will not run if already at the top
            startActivity(diaryIntent);
        });

        Button btnHabits = findViewById(R.id.btnReportHabits);
        btnHabits.setOnClickListener(v -> {
            Intent habitsIntent = new Intent(this, ReportsHabitsActivity.class);
            habitsIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(habitsIntent);
        });

        Button btnReportTGT = findViewById(R.id.btnReportTGT);
        btnReportTGT.setOnClickListener(v -> {
            Intent threegoodthingsIntent = new Intent(this, ReportsThreeGoodThingsActivity.class);
            threegoodthingsIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(threegoodthingsIntent);
        });
    }
}