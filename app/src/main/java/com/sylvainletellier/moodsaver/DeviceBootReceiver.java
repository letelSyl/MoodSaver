package com.sylvainletellier.moodsaver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.sylvainletellier.moodsaver.model.ItemHistory;

import java.util.Calendar;


public class DeviceBootReceiver extends BroadcastReceiver {

 /*   private AlarmManager alarmMgr;
    private  PendingIntent alarmIntent;

    public static final int BUNDLE_STATE_ALRM_INTENT = 99;*/

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {


            Intent alarm = new Intent(context, AlarmReceiver.class);
            PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, alarm, 0);

            // Set the alarm to start at approximately 00:00.
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            //00 est interpreté comme un octet au lieu d'un integer (changed 00 to 0)
            calendar.set(Calendar.HOUR_OF_DAY,0);
            calendar.set(Calendar.MINUTE,0 );

            // With setInexactRepeating(), you have to use one of the AlarmManager interval
            // constants--in this case, AlarmManager.INTERVAL_DAY.
            AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY, alarmIntent);
            Log.d("al", "set alarm ok");



        }


    }


}
