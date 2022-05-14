package com.ibrice.moodr.settings;

import android.os.Bundle;
import android.widget.Button;

import com.ibrice.moodr.R;
import com.ibrice.moodr.notifications.NotificationHelper;

import java.util.Objects;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

public class SettingsActivity extends AppCompatActivity {

    public static boolean notificationsOn = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.settings, new SettingsFragment()).commit();
        }

        Button testNotification = findViewById(R.id.btnTestNotification);

        testNotification.setOnClickListener(v -> {
            if (notificationsOn) {
                // call notification function
                NotificationHelper notificationHelper = new NotificationHelper(this);
                notificationHelper.createNotification();
            }  // else do nothing
        });
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
            // activity set in root_prefs
            setPreferencesFromResource(R.xml.root_prefs, rootKey);

            SwitchPreferenceCompat notificationSwitch = findPreference("notificationsToggle");

            if (notificationSwitch != null) {
                notificationSwitch.setOnPreferenceChangeListener((preference, newValue) -> {
                    boolean isChecked = true;
                    if (newValue instanceof Boolean)
                        isChecked = (Boolean) newValue;

                    notificationsOn = isChecked;

                    return true;
                });
            }

            // get theme switch preference toggle
            SwitchPreferenceCompat themeSwitch = findPreference("darkTheme");

            if (themeSwitch != null) {

                // when switch is toggled
                themeSwitch.setOnPreferenceChangeListener((preference, newValue) -> {
                    boolean isChecked = false; // set whether the switch is checked
                    if (newValue instanceof Boolean)
                        isChecked = (Boolean) newValue;

                    // if checked, dark theme, else, light theme
                    if (isChecked) {
                        Objects.requireNonNull(getPreferenceManager().getSharedPreferences())
                                .edit().putBoolean(getString(R.string.toggle_theme), true).apply();
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    } else {
                        Objects.requireNonNull(getPreferenceManager().getSharedPreferences())
                                .edit().putBoolean(getString(R.string.toggle_theme), false).apply();
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    }

                    return true;
                });
            }
        }
    }
}