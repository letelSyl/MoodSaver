package com.sylvainletellier.moodsaver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;


public class AlarmReceiver extends BroadcastReceiver {



    @Override
    public void onReceive(Context context, Intent alarm) {

        PreferencesUtil preferences = new PreferencesUtil(context);


        preferences.saveMoodStateHistory();
        preferences.initializeCurrentMood();

        Toast.makeText(context, "daily mood saved, please restart application", Toast.LENGTH_SHORT).show();


    }
}
