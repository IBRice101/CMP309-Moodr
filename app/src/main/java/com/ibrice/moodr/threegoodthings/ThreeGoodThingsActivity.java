package com.ibrice.moodr.threegoodthings;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ibrice.moodr.R;
import com.ibrice.moodr.database.DBManager;
import com.ibrice.moodr.mainscreen.MainActivity;

import androidx.appcompat.app.AppCompatActivity;

public class ThreeGoodThingsActivity extends AppCompatActivity {

    Button btnSubmit;
    EditText edittxtThing1, edittxtThing2, edittxtThing3;

    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three_good_things);

        btnSubmit = findViewById(R.id.btnAddTGT);
        edittxtThing1 = findViewById(R.id.edittxtThing1);
        edittxtThing2 = findViewById(R.id.edittxtThing2);
        edittxtThing3 = findViewById(R.id.edittxtThing3);

        btnSubmit.setOnClickListener(v -> {
            String thing1 = edittxtThing1.getText().toString();
            String thing2 = edittxtThing2.getText().toString();
            String thing3 = edittxtThing3.getText().toString();

            // check if empty, else, insert content into database
            if (thing1.isEmpty() || thing2.isEmpty() || thing3.isEmpty()){
                Toast.makeText(this, "Please enter three good things that happened to you today", Toast.LENGTH_SHORT).show();
            } else {
                dbManager = new DBManager(this);
                dbManager.open();

                dbManager.insertTGT(thing1, thing2, thing3);

                Toast.makeText(this, "Entry added", Toast.LENGTH_SHORT).show();
                Intent returnToMain = new Intent(ThreeGoodThingsActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(returnToMain);

                dbManager.close();
            }
        });

        // TODO: Implement SharedPreferences to increment the number of days here
        // NOTE: https://developer.android.com/reference/android/content/SharedPreferences
        // NOTE: https://stackoverflow.com/questions/39179974/how-to-create-a-day-counter-for-android-application
    }
}