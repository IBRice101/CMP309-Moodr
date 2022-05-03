package com.ibrice.moodr.notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.ibrice.moodr.notifications.NotificationHelper;

public class NotificationReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationHelper notificationHelper = new NotificationHelper(context);
        notificationHelper.createNotification();
    }
}
