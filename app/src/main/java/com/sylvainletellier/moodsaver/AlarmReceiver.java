package com.sylvainletellier.moodsaver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

import static com.sylvainletellier.moodsaver.MainActivity.BUNDLE_STATE_CURRENT_COMMENT;
import static com.sylvainletellier.moodsaver.MainActivity.BUNDLE_STATE_MOOD;
import static com.sylvainletellier.moodsaver.MainActivity.commentKeyTable;
import static com.sylvainletellier.moodsaver.MainActivity.commentTable;
import static com.sylvainletellier.moodsaver.MainActivity.moodKeyTable;


public class AlarmReceiver extends BroadcastReceiver {



    private SharedPreferences.Editor mPreferences;
    private static int mMoodIndex, mMoodIndexM1, mMoodIndexM2, mMoodIndexM3, mMoodIndexM4, mMoodIndexM5, mMoodIndexM6, mMoodIndexM7 ;
    public static int moodIndexTable[] = {mMoodIndex, mMoodIndexM1, mMoodIndexM2, mMoodIndexM3, mMoodIndexM4, mMoodIndexM5, mMoodIndexM6, mMoodIndexM7};

    public static final String BUNDLE_STATE_NBR_HISTORY = "Number of days in history";
    private int mNbrDaysHistory ;

    @Override
    public void onReceive(Context context, Intent alarm) {

        fillHistory(context);
        // For our recurring task, we'll just display a message
        Toast.makeText(context, "I'm running", Toast.LENGTH_SHORT).show();


    }

    public void fillHistory(Context context){

        mPreferences = PreferenceManager.getDefaultSharedPreferences(context).edit();
        mNbrDaysHistory = PreferenceManager.getDefaultSharedPreferences(context).getInt(BUNDLE_STATE_NBR_HISTORY,1 );
        for (int i=mNbrDaysHistory; i>0; i--){
            moodIndexTable[i]=moodIndexTable[i-1];
            mPreferences.putInt(moodKeyTable[i],moodIndexTable[i]);
            commentTable[i] = commentTable[i-1];
            mPreferences.putString(commentKeyTable[i],commentTable[i]);

        }
        if (mNbrDaysHistory < 7) {
            mNbrDaysHistory = mNbrDaysHistory + 1;
        }
        mMoodIndex = 3;
        mPreferences.putInt(BUNDLE_STATE_MOOD,mMoodIndex );
        mPreferences.putString(BUNDLE_STATE_CURRENT_COMMENT, null);
        mPreferences.putInt(BUNDLE_STATE_NBR_HISTORY, mNbrDaysHistory);
        mPreferences.apply();



    }
}
