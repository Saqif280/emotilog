package com.emotilog.app.emotilog;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Vasileios Makris 17202178 on 07-Nov-17.
 * This class handles the Broadcast. This receiver is invoked at the scheduled user selected time.
 * Based on:
 * https://github.com/jaisonfdo/RemindMe/blob/master/app/src/main/java/com/droidmentor/remindme/AlarmReceiver.java
 */

public class AlarmReceiver extends BroadcastReceiver {



    @Override
    public void onReceive(Context context, Intent intent) {

        if(intent.getAction()!= null && context!=null){
            if (intent.getAction().equalsIgnoreCase(Intent.ACTION_BOOT_COMPLETED)){
                NotificationData notificationData=new NotificationData(context);
                NotificationScheduler.setReminder(context, AlarmReceiver.class,
                        notificationData.get_notification_hour(),
                        notificationData.get_notification_min());
                return;
            }
        }

        NotificationScheduler.showNotification(context, AddEntryActivity.class,
                "Add a new log!", "Write about your day :)");
    }
}