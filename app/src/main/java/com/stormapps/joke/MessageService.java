
package com.stormapps.joke;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;
import android.os.Handler;
import android.widget.Toast;

import androidx.annotation.Nullable;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class MessageService extends IntentService {

    public static final String EXTRA_MESSAGE = "MESSAGE";
    private Handler handler;

    public MessageService() {
        super("MessageService");
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        handler = new Handler();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        // this method will contains the code we want to run when the service receives anintent
        synchronized (this) {
            // synchronized() method is Java code which allows us to lock a particular
            //block of code from access by other threads
            try {
                //wait for 10 seconds t
                wait(10000);
            } catch (InterruptedException error) {
                error.printStackTrace();
            }
            //try..catch is Java syntax which allows us to perform code actions on the try
            //block , and catch error exceptions in the the catch block , hence making us able to trace
            //the line of code which has errors during debugging
        }
        //get the text from the intent
        String text = intent.getStringExtra(EXTRA_MESSAGE);
        //call showText method
        showText(text);

    }

    private void showText(final String text) {
        Log.v("DelayedMessageService", "What is the secret of comedy?:" + text);
        // the above line of code logs a piece of text so that we can see it in the logcat

        //post the Toast code to the main thread using the handler post method
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(),text,Toast.LENGTH_SHORT).show();
            }
        });
    }

    
}