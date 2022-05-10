package com.ibrice.moodr.reports;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.ibrice.moodr.databinding.ActivityReportsHabitsBinding;
import com.ibrice.moodr.reports.ui.reportsHabitsFragment.SectionsPagerAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class ReportsHabitsActivity extends AppCompatActivity {

    private ActivityReportsHabitsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityReportsHabitsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);

        // do stuff here
    }
}