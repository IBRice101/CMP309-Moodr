package com.ibrice.moodr.habits;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.ibrice.moodr.R;
import com.ibrice.moodr.database.DBManager;

public class CreateHabitsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_habits);

        TimePicker timePicker = findViewById(R.id.timePicker);

        int hour = timePicker.getHour();
        int minute = timePicker.getMinute();

        String timePicked = hour + ":" + minute;

        Button btnSubmitHabit = findViewById(R.id.btnSubmitHabit);
        EditText edittxtHabitTitle = findViewById(R.id.edittxtHabitTitle);
        EditText edittxtHabitDescription = findViewById(R.id.edittxtHabitDescription);

        btnSubmitHabit.setOnClickListener(v -> {
            String title = edittxtHabitTitle.getText().toString();
            String description = edittxtHabitDescription.getText().toString();

            if (title.isEmpty()) {
                Toast.makeText(this,
                        "Please enter a title...", Toast.LENGTH_SHORT).show();
            } else if (description.isEmpty()){
                Toast.makeText(this,
                        "Please enter a description...", Toast.LENGTH_SHORT).show();
            } else {
                DBManager dbManager = new DBManager(this);
                dbManager.open();

                dbManager.insertHabits(title, description, timePicked);

                Toast.makeText(this, "Habit added!", Toast.LENGTH_SHORT).show();
                Intent returnToHabits = new Intent(CreateHabitsActivity.this, ViewHabitsActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(returnToHabits);

                dbManager.close();
            }
        });
    }
}