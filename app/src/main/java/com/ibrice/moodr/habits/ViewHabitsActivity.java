package com.ibrice.moodr.habits;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ibrice.moodr.R;
import com.ibrice.moodr.database.DBHelper;
import com.ibrice.moodr.database.DBManager;
import com.ibrice.moodr.databinding.ActivityViewHabitsBinding;
import com.ibrice.moodr.mainscreen.MainActivity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ViewHabitsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DBManager db = new DBManager(ViewHabitsActivity.this);
        db.open();

        com.ibrice.moodr.databinding.ActivityViewHabitsBinding binding = ActivityViewHabitsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = binding.toolbarLayout;
        toolBarLayout.setTitle("View Your Habits");

        FloatingActionButton fab = binding.fabAddHabit;
        fab.setOnClickListener(view ->  {
            Intent fabIntent = new Intent(this, CreateHabitsActivity.class);
            fabIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(fabIntent);
        });

        // open db
        DBManager dbManager = new DBManager(this);
        dbManager.open();

        // fetch contents from db
        Cursor cursorHabits = dbManager.fetchHabits();

        // if list view is empty, display this message
        ListView listHabits = findViewById(R.id.listHabits);
        listHabits.setEmptyView(findViewById(R.id.txtHabitsEmpty));

        // get data and assign to fields
        final String[] from = new String[] {
                DBHelper._ID, DBHelper.HABITS_TITLE, DBHelper.HABITS_TIME, DBHelper.HABITS_DESCRIPTION
        };
        final int[] to = new int[] {
                R.id.txtHabitsID, R.id.txtHabitsTitle, R.id.txtHabitsTime, R.id.txtHabitsDescription
        };

        // set adapter to grab habits
        SimpleCursorAdapter adapterHabits = new SimpleCursorAdapter(this,
                R.layout.activity_view_habits_text, cursorHabits, from, to, 0);
        adapterHabits.notifyDataSetChanged();

        // add habit to list view
        listHabits.setAdapter(adapterHabits);

        // on habit click, allow user to delete entry
        listHabits.setOnItemClickListener((parent, view, position, id) -> {
            Cursor cursor = (Cursor) parent.getItemAtPosition(position);
            String positionID = cursor.getString(0);
            final int getID = Integer.parseInt(positionID);

            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Delete Habit?");
            alert.setMessage("Are you sure you want to delete this habit?");

            alert.setPositiveButton("Yes", (dialog, which) -> {
              db.deleteHabits(getID, false);

              Toast.makeText(this, "Habit deleted", Toast.LENGTH_SHORT).show();

              this.returnHome();
            });

            alert.setNegativeButton("No", (dialog, which) -> dialog.cancel());

            alert.show();
        });
    }

    public void returnHome() {
        Intent homeIntent = new Intent(getApplicationContext(), MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
    }
}