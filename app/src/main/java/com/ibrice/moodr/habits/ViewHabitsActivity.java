package com.ibrice.moodr.habits;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ibrice.moodr.R;
import com.ibrice.moodr.database.DBHelper;
import com.ibrice.moodr.database.DBManager;
import com.ibrice.moodr.databinding.ActivityViewHabitsBinding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ViewHabitsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        com.ibrice.moodr.databinding.ActivityViewHabitsBinding binding = ActivityViewHabitsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = binding.toolbarLayout;
        toolBarLayout.setTitle(getTitle());

        FloatingActionButton fab = binding.fabAddHabit;
        fab.setOnClickListener(view ->  {
            Intent fabIntent = new Intent(this, CreateHabitsActivity.class);
            fabIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(fabIntent);
        });

        // open db
        DBManager dbManager = new DBManager(this);
        dbManager.open();

        // fetch quote from db
        Cursor cursorHabits = dbManager.fetchHabits();

        // if list view is empty, display this message
        ListView listhabits = findViewById(R.id.listHabits);
        listhabits.setEmptyView(findViewById(R.id.txtHabitsEmpty));

        // get data and assign to fields
        final String[] from = new String[] {
                DBHelper._ID, DBHelper.HABITS_TITLE, DBHelper.HABITS_TIME, DBHelper.HABITS_DESCRIPTION
        };
        final int[] to = new int[] {
                R.id.txtHabitsID, R.id.txtHabitsTitle, R.id.txtHabitsTime, R.id.txtHabitsDescription
        };

        // set adapter to grab habits
        SimpleCursorAdapter adapterHabits = new SimpleCursorAdapter(this, R.layout.activity_view_habits_text, cursorHabits, from, to, 0);
        adapterHabits.notifyDataSetChanged();

        // add habit to list view
        listhabits.setAdapter(adapterHabits);
    }
}