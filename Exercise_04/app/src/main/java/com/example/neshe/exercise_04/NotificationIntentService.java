package com.example.neshe.exercise_04;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.os.ResultReceiver;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Handler;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class NotificationIntentService extends IntentService {
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_NOTIFICATION = "com.example.neshe.exercise_04.action.ACTION_NOTIFICATION";
    private static AlarmManager alarmMgr;

    public NotificationIntentService() {
        super("MyIntentService");
    }

    private final String[] stringsArray = {
            "'You know you're in love when you can't fall asleep because reality is finally better than your dreams.'― Dr. Seuss",
            "'Be yourself; everyone else is already taken.'― Oscar Wilde",
            "'Two things are infinite: the universe and human stupidity; and I'm not sure about the universe.'― Albert Einstein",
            "'You only live once, but if you do it right, once is enough.'― Mae West",
            "'Live as if you were to die tomorrow. Learn as if you were to live forever.'― Mahatma Gandhi",
            "'Without music, life would be a mistake.'― Friedrich Nietzsche"
    };

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionNotification(Context context, int interval) {
        Intent intent = new Intent(context, NotificationIntentService.class);
        intent.setAction(ACTION_NOTIFICATION);
        context.startService(intent);
        alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        alarmMgr.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(),
                TimeUnit.MINUTES.toMillis(interval), PendingIntent.getService(context,0,intent, 0));
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_NOTIFICATION.equals(action)) {
                handleActionNotification();
            }
        }
    }

    /**
     * Handle action Notification in the provided background thread with the provided parameters.
     */
    private void handleActionNotification() {
        String result = getQuote(stringsArray);
//        Notification myNotification = new Notification.Builder(this)
//                .setAutoCancel(true)
//                .setDefaults(Notification.DEFAULT_ALL)
//                .setWhen(System.currentTimeMillis())
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setContentText(result)
//                .build();
//
//        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
//        manager.notify(0, myNotification);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("My notification")
                .setContentText(result);

        NotificationManager notificationManager = (NotificationManager)this
                .getSystemService(Context.NOTIFICATION_SERVICE);
        int id = 1;
        notificationManager.notify(id, builder.build());
    }

    // Returns a random string from a given array of Strings.
    private String getQuote(String[] i_StringArray) {
        Random random = new Random();
        int randomNumber = random.nextInt(i_StringArray.length + 1);     // a random number indicating some index in the array.
        String randomString = i_StringArray[randomNumber];  // a string corresponding to the random index chosen.

        return randomString;
    }
}
