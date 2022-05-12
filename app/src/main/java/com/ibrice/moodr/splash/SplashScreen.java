package com.ibrice.moodr.splash;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import com.ibrice.moodr.mainscreen.MainActivity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

@SuppressLint("CustomSplashScreen") // preferred implementation in Android 12 but testing API is lower
public class SplashScreen extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Handler().postDelayed(() -> {
            Intent splashIntent = new Intent(SplashScreen.this, MainActivity.class);
            startActivity(splashIntent);
            finish();
        }, 1000);
    }
}