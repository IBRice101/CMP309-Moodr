package com.ibrice.moodr.notifications;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.ibrice.moodr.mainscreen.MainActivity;
import com.ibrice.moodr.settings.SettingsActivity;

public class NotificationReceiver extends BroadcastReceiver {
    @SuppressLint("UnspecifiedImmutableFlag")
    @Override
    public void onReceive(Context context, Intent intent) {
        if (SettingsActivity.notificationsOn) {
            NotificationHelper notificationHelper = new NotificationHelper(context);

            Intent notificationIntent = new Intent(context, MainActivity.class);
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            PendingIntent.getActivity(context, 0, notificationIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            notificationHelper.createNotification();
        }
    }
}
