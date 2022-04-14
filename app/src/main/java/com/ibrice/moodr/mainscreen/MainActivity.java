package com.ibrice.moodr.mainscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ibrice.moodr.R;
import com.ibrice.moodr.diary.CalendarActivity;
import com.ibrice.moodr.habits.HabitsActivity;
import com.ibrice.moodr.reports.ReportsActivity;
import com.ibrice.moodr.settings.SettingsActivity;
import com.ibrice.moodr.threegoodthings.ThreeGoodThingsActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // functions that respond to onclick events for relevant buttons
    public void openDiary(View view) {
        Intent diaryIntent = new Intent(this, CalendarActivity.class);
        diaryIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP); // will not run if already at the top
        startActivity(diaryIntent);
    }

    public void openHabits(View view) {
        Intent habitsIntent = new Intent(this, HabitsActivity.class);
        habitsIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(habitsIntent);
    }

    public void openThreeGoodThings(View view) {
        Intent threegoodthingsIntent = new Intent(this, ThreeGoodThingsActivity.class);
        threegoodthingsIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(threegoodthingsIntent);
    }

    public void openReports(View view) {
        Intent reportsIntent = new Intent(this, ReportsActivity.class);
        reportsIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(reportsIntent);
    }

    public void openSettings(View view) {
        Intent settingsIntent = new Intent(this, SettingsActivity.class);
        settingsIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(settingsIntent);
    }
}