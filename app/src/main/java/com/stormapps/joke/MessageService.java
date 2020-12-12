
package com.stormapps.joke;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.content.Context;
import android.util.Log;
import android.os.Handler;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MessageService extends IntentService {

    public static final String EXTRA_MESSAGE = "MESSAGE";
    public static final int NOTIFICATION_ID = 1;

    public MessageService() {
        super("MessageService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        synchronized (this) {

            try {
                wait(10000);
            } catch (InterruptedException error) {
                error.printStackTrace();
            }
        }

        String text = intent.getStringExtra(EXTRA_MESSAGE);
        showText(text);
    }

    private void showText(final String text) {
        Log.v("DelayedMessageService", "What is the secret of comedy?:" + text);

        //Create an explicit intent
        Intent intent = new Intent(this, MainActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(intent);

        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new Notification.Builder(this)

                .setSmallIcon(R.mipmap.ic_joke_round)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(text)
                .setAutoCancel(true)
                .setPriority(Notification.PRIORITY_MAX)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentIntent(pendingIntent)
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID,notification);
    }
}