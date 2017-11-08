package com.emotilog.app.emotilog;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {

    String TAG= "AlarmReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {

        if(intent.getAction()!= null && context!=null){
            if (intent.getAction().equalsIgnoreCase(Intent.ACTION_BOOT_COMPLETED)){
                Log.d(TAG, "onReceive:BOOT_COMPLETED");
                LocalData localData=new LocalData(context);
                NotificationScheduler.setReminder(context, AlarmReceiver.class,
                        localData.get_hour(),
                        localData.get_min());
                return;
            }
        }

        Log.d(TAG, "onReceive: ");
        // !!!!!CHANGE TARGET CLASS!!!!!
        NotificationScheduler.showNotification(context, AddEntryActivity.class,
                "Add a new log!", "Write about your day :)");
    }
}

