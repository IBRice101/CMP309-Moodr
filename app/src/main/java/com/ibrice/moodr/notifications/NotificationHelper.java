package com.ibrice.moodr.notifications;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

import com.ibrice.moodr.R;

import androidx.core.app.NotificationCompat;

public class NotificationHelper {

    private final Context notificationContext;
    private static final String NOTIFICATION_CHANNEL_ID = "10001";

    // set notification helper argument
    public NotificationHelper(Context context) {
        notificationContext = context;
    }

    public void createNotification() {
        Intent intent = new Intent(notificationContext, NotificationReciever.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // wait for an event
        @SuppressLint("UnspecifiedImmutableFlag")
        PendingIntent resultPendingIntent = PendingIntent.getActivity(notificationContext,
                0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // set notification content
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(notificationContext, NOTIFICATION_CHANNEL_ID);
        notificationBuilder.setSmallIcon(R.drawable.ic_logo);
        notificationBuilder.setContentTitle("Come improve your mood!")
                .setContentText("Add entries to your diary, three good things, or habits")
                .setAutoCancel(false)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setContentIntent(resultPendingIntent);

        // instantiate notification manager
        NotificationManager notificationManager =
                (NotificationManager) notificationContext.getSystemService(Context.NOTIFICATION_SERVICE);

        int importance = NotificationManager.IMPORTANCE_DEFAULT;

        // set notification channel behaviour
        NotificationChannel notificationChannel =
                new NotificationChannel(NOTIFICATION_CHANNEL_ID,
                        "NOTIFICATION_CHANNEL_NAME",
                        importance);
        notificationChannel.enableLights(true);
        notificationChannel.setLightColor(androidx.appcompat.R.attr.colorPrimary);
        notificationChannel.enableVibration(true);
        notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 100});

        // create notification manager
        assert notificationManager != null;
        notificationBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
        notificationManager.createNotificationChannel(notificationChannel);

        notificationManager.notify(0, notificationBuilder.build());
    }
}
