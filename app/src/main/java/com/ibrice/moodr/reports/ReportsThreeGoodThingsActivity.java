package com.ibrice.moodr.reports;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.ibrice.moodr.databinding.ActivityReportsThreeGoodThingsBinding;
import com.ibrice.moodr.reports.ui.reportsthreegoodthingsfragment.SectionsPagerAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class ReportsThreeGoodThingsActivity extends AppCompatActivity {

    private ActivityReportsThreeGoodThingsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityReportsThreeGoodThingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);

        // do stuff here
    }
}