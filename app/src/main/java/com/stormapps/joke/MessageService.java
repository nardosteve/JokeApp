
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
    //USE A CONSTANT TO PASS A A MESSAGE FROM THE MAIN ACTTIVITY TO THE SERVICE
    public static final String EXTRA_MESSAGE = "MESSAGE";
    /*Declare a private NOTIFICATION_ID which will be used to identify a notification.
    * If we send another notification with the same ID, it will replace
    the current notification.
    * This is useful if you want to update an existing notification with new information.
    **/
    public static final int NOTIFICATION_ID = 1;
    public MessageService() {
        super("MessageService");
//required constructor
    }
    @Override
    protected void onHandleIntent(Intent intent) {
//this method contains the code you want to run when the service receives an intent
        synchronized (this) {
            try {
//wait for 10 seconds
                wait(10000);
            } catch (InterruptedException error) {
                error.printStackTrace();
            }
        }
//get the text from the intent
        String text = intent.getStringExtra(EXTRA_MESSAGE);
//call showText method
        showText(text);
    }
    private void showText(final String text) {
        Log.v("DelayedMessageService", "What is the secret of comedy?? " + text);
        Intent intent = new Intent(this, MainActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(intent);

        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new Notification.Builder(this)

                .setSmallIcon(R.mipmap.ic_joke_round)
                //set the title as your application name
                .setContentTitle(getString(R.string.app_name))
                //set the content text
                .setContentText(text)
                //make the notification disappear when clicked
                .setAutoCancel(true)
                //give it a maximum priority to allow peeking
                .setPriority(Notification.PRIORITY_MAX)
                //set it to vibrate to get a large heads-up notification
                .setDefaults(Notification.DEFAULT_VIBRATE)
                //open main activity on clicking the notification
                .setContentIntent(pendingIntent)
                .build();

        //display the notification using the Android notification service
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        //Issue the notification
        notificationManager.notify(NOTIFICATION_ID, notification);
    }
}