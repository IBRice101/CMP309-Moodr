package com.ibrice.moodr.diary;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ibrice.moodr.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalendarActivity extends AppCompatActivity {

    TextView txtDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        txtDate = findViewById(R.id.txtDate);
        setDate(txtDate);

        FloatingActionButton fab = findViewById(R.id.fabAdd);
        fab.setOnClickListener(view ->  {
            Intent fabIntent = new Intent(this, InputActivity.class);
            fabIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(fabIntent);
        });

    }

    // set today's date
    @SuppressLint("SetTextI18n")
    public void setDate(TextView view) {
        Date today = Calendar.getInstance().getTime();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format
                = new SimpleDateFormat("EEE, d MMM yyyy");
        String date = format.format(today);
        view.setText("Today's date is" + " " + date);
    }
}