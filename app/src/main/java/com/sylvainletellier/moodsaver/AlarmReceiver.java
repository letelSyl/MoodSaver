package com.sylvainletellier.moodsaver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import static com.sylvainletellier.moodsaver.MainActivity.BUNDLE_STATE_COMMENT_M1;
import static com.sylvainletellier.moodsaver.MainActivity.BUNDLE_STATE_COMMENT_M2;
import static com.sylvainletellier.moodsaver.MainActivity.BUNDLE_STATE_COMMENT_M3;
import static com.sylvainletellier.moodsaver.MainActivity.BUNDLE_STATE_COMMENT_M4;
import static com.sylvainletellier.moodsaver.MainActivity.BUNDLE_STATE_COMMENT_M5;
import static com.sylvainletellier.moodsaver.MainActivity.BUNDLE_STATE_COMMENT_M6;
import static com.sylvainletellier.moodsaver.MainActivity.BUNDLE_STATE_COMMENT_M7;
import static com.sylvainletellier.moodsaver.MainActivity.BUNDLE_STATE_CURRENT_COMMENT;
import static com.sylvainletellier.moodsaver.MainActivity.BUNDLE_STATE_MOOD;
import static com.sylvainletellier.moodsaver.MainActivity.BUNDLE_STATE_MOOD_M1;
import static com.sylvainletellier.moodsaver.MainActivity.BUNDLE_STATE_MOOD_M2;
import static com.sylvainletellier.moodsaver.MainActivity.BUNDLE_STATE_MOOD_M3;
import static com.sylvainletellier.moodsaver.MainActivity.BUNDLE_STATE_MOOD_M4;
import static com.sylvainletellier.moodsaver.MainActivity.BUNDLE_STATE_MOOD_M5;
import static com.sylvainletellier.moodsaver.MainActivity.BUNDLE_STATE_MOOD_M6;
import static com.sylvainletellier.moodsaver.MainActivity.BUNDLE_STATE_MOOD_M7;

public class AlarmReceiver extends BroadcastReceiver {

    public static final String BUNDLE_STATE_NBR_HISTORY = "Number of days in history";

    private  int mMoodIndex, mMoodIndexM1, mMoodIndexM2, mMoodIndexM3, mMoodIndexM4, mMoodIndexM5, mMoodIndexM6, mMoodIndexM7 ;

    private  String mCurrentComment, mCommentM1, mCommentM2, mCommentM3, mCommentM4, mCommentM5, mCommentM6, mCommentM7;

    @Override
    public void onReceive(Context context, Intent alarm) {

        fillHistory(context);

        Intent mainActivity = new Intent(context, MainActivity.class);
        context.startActivity(mainActivity);
    }

    public void beforeFieldHistory(Context context){
        mMoodIndex =  context.getSharedPreferences(MainActivity.MON_FICHIER, Context.MODE_PRIVATE).getInt(BUNDLE_STATE_MOOD, 3);
        mMoodIndexM1 =  context.getSharedPreferences(MainActivity.MON_FICHIER, Context.MODE_PRIVATE).getInt(BUNDLE_STATE_MOOD_M1,-1 );
        mMoodIndexM2 =  context.getSharedPreferences(MainActivity.MON_FICHIER, Context.MODE_PRIVATE).getInt(BUNDLE_STATE_MOOD_M2,-1 );
        mMoodIndexM3 =  context.getSharedPreferences(MainActivity.MON_FICHIER, Context.MODE_PRIVATE).getInt(BUNDLE_STATE_MOOD_M3,-1 );
        mMoodIndexM4 =  context.getSharedPreferences(MainActivity.MON_FICHIER, Context.MODE_PRIVATE).getInt(BUNDLE_STATE_MOOD_M4,-1 );
        mMoodIndexM5 =  context.getSharedPreferences(MainActivity.MON_FICHIER, Context.MODE_PRIVATE).getInt(BUNDLE_STATE_MOOD_M5,-1 );
        mMoodIndexM6 =  context.getSharedPreferences(MainActivity.MON_FICHIER, Context.MODE_PRIVATE).getInt(BUNDLE_STATE_MOOD_M6,-1 );
        mMoodIndexM7 =  context.getSharedPreferences(MainActivity.MON_FICHIER, Context.MODE_PRIVATE).getInt(BUNDLE_STATE_MOOD_M7,-1 );

        mCurrentComment = context.getSharedPreferences(MainActivity.MON_FICHIER, Context.MODE_PRIVATE).getString(BUNDLE_STATE_CURRENT_COMMENT, null);
        mCommentM1 =  context.getSharedPreferences(MainActivity.MON_FICHIER, Context.MODE_PRIVATE).getString(BUNDLE_STATE_COMMENT_M1, null);
        mCommentM2 =  context.getSharedPreferences(MainActivity.MON_FICHIER, Context.MODE_PRIVATE).getString(BUNDLE_STATE_COMMENT_M2, null);
        mCommentM3 =  context.getSharedPreferences(MainActivity.MON_FICHIER, Context.MODE_PRIVATE).getString(BUNDLE_STATE_COMMENT_M3, null);
        mCommentM4 =  context.getSharedPreferences(MainActivity.MON_FICHIER, Context.MODE_PRIVATE).getString(BUNDLE_STATE_COMMENT_M4, null);
        mCommentM5 =  context.getSharedPreferences(MainActivity.MON_FICHIER, Context.MODE_PRIVATE).getString(BUNDLE_STATE_COMMENT_M5, null);
        mCommentM6 =  context.getSharedPreferences(MainActivity.MON_FICHIER, Context.MODE_PRIVATE).getString(BUNDLE_STATE_COMMENT_M6, null);
        mCommentM7 =  context.getSharedPreferences(MainActivity.MON_FICHIER, Context.MODE_PRIVATE).getString(BUNDLE_STATE_COMMENT_M7, null);

    }

    public void fillHistory(Context context){

        SharedPreferences.Editor mPreferences =  context.getSharedPreferences(MainActivity.MON_FICHIER, Context.MODE_PRIVATE).edit();
        int mNbrDaysHistory =  context.getSharedPreferences(MainActivity.MON_FICHIER, Context.MODE_PRIVATE).getInt(BUNDLE_STATE_NBR_HISTORY,1 );
        beforeFieldHistory(context);

        int[] moodIndexTable = {mMoodIndex, mMoodIndexM1, mMoodIndexM2, mMoodIndexM3, mMoodIndexM4, mMoodIndexM5, mMoodIndexM6, mMoodIndexM7};
        String[]  moodKeyTable = {BUNDLE_STATE_MOOD, BUNDLE_STATE_MOOD_M1, BUNDLE_STATE_MOOD_M2, BUNDLE_STATE_MOOD_M3, BUNDLE_STATE_MOOD_M4, BUNDLE_STATE_MOOD_M5, BUNDLE_STATE_MOOD_M6, BUNDLE_STATE_MOOD_M7};


        String[] commentTable  = {mCurrentComment, mCommentM1, mCommentM2, mCommentM3, mCommentM4, mCommentM5, mCommentM6, mCommentM7};
        String[] commentKeyTable = {BUNDLE_STATE_CURRENT_COMMENT, BUNDLE_STATE_COMMENT_M1, BUNDLE_STATE_COMMENT_M2, BUNDLE_STATE_COMMENT_M3, BUNDLE_STATE_COMMENT_M4, BUNDLE_STATE_COMMENT_M5, BUNDLE_STATE_COMMENT_M6, BUNDLE_STATE_COMMENT_M7};

        for (int i=mNbrDaysHistory; i>0; i--){

            mPreferences.putInt(moodKeyTable[i],moodIndexTable[i-1]);

            mPreferences.putString(commentKeyTable[i],commentTable[i-1]);
        }
        if (mNbrDaysHistory < 7) {
            mNbrDaysHistory = mNbrDaysHistory + 1;
            mPreferences.putInt(BUNDLE_STATE_NBR_HISTORY, mNbrDaysHistory);
        }

        mPreferences.putInt(BUNDLE_STATE_MOOD,3 );
        mPreferences.putString(BUNDLE_STATE_CURRENT_COMMENT, null);

        mPreferences.apply();
    }
}
