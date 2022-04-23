package com.ibrice.moodr.habits;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ibrice.moodr.databinding.ActivityViewHabitsBinding;
import com.ibrice.moodr.diary.InputActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ViewHabitsActivity extends AppCompatActivity {

    private ActivityViewHabitsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityViewHabitsBinding.inflate(getLayoutInflater());
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
    }
}