package com.sylvainletellier.moodsaver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.widget.Toast;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends FragmentActivity {

    public static final String MON_FICHIER = "monFichier";
    private PagerAdapter mPagerAdapter;

    public static final String BUNDLE_STATE_MOOD = "current Mood";
    public static final String BUNDLE_STATE_MOOD_M1 = "Mood J-1";
    public static final String BUNDLE_STATE_MOOD_M2 = "Mood J-2";
    public static final String BUNDLE_STATE_MOOD_M3 = "Mood J-3";
    public static final String BUNDLE_STATE_MOOD_M4 = "Mood J-4";
    public static final String BUNDLE_STATE_MOOD_M5 = "Mood J-5";
    public static final String BUNDLE_STATE_MOOD_M6 = "Mood J-6";
    public static final String BUNDLE_STATE_MOOD_M7 = "Mood J-7";

    private int mMoodIndex;

    public static final String BUNDLE_STATE_CURRENT_COMMENT = "Current Comment";
    public static final String BUNDLE_STATE_COMMENT_M1 = "Comment J-1";
    public static final String BUNDLE_STATE_COMMENT_M2 = "Comment J-2";
    public static final String BUNDLE_STATE_COMMENT_M3 = "Comment J-3";
    public static final String BUNDLE_STATE_COMMENT_M4 = "Comment J-4";
    public static final String BUNDLE_STATE_COMMENT_M5 = "Comment J-5";
    public static final String BUNDLE_STATE_COMMENT_M6 = "Comment J-6";
    public static final String BUNDLE_STATE_COMMENT_M7 = "Comment J-7";

    private VerticalViewPager pager;

    private SharedPreferences.Editor mPreferences;

    private PendingIntent pendingIntent;

    private boolean firstStart;
    public static final String BUNDLE_STATE_FIRST_START = "first start";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_main);

        // Création de la liste de Fragments que fera défiler le PagerAdapter
        List<MoodFragment> fragments = Arrays.asList(
                MoodFragment.newInstance(0),
                MoodFragment.newInstance(1),
                MoodFragment.newInstance(2),
                MoodFragment.newInstance(3),
                MoodFragment.newInstance(4)
        );

        this.mPagerAdapter = new MyPagerAdapter(super.getSupportFragmentManager(), fragments);

        pager = super.findViewById(R.id.viewpager);
        // Affectation de l'adapter au ViewPager
        pager.setAdapter(this.mPagerAdapter);

        Toast.makeText(this, getSharedPreferences(MON_FICHIER, Context.MODE_PRIVATE).getString(BUNDLE_STATE_COMMENT_M1, "Commentaire -1 vide"), Toast.LENGTH_SHORT).show();
        Toast.makeText(this, getSharedPreferences(MON_FICHIER, Context.MODE_PRIVATE).getString(BUNDLE_STATE_CURRENT_COMMENT, "Commentaire courrant vide"), Toast.LENGTH_SHORT).show();
        // Création de l'adapter qui s'occupera de l'affichage de la liste de Fragments

        mMoodIndex = this.getSharedPreferences(MON_FICHIER, Context.MODE_PRIVATE).getInt(BUNDLE_STATE_MOOD, 3);

        pager.setCurrentItem(mMoodIndex);

        firstStart = this.getSharedPreferences(MON_FICHIER, Context.MODE_PRIVATE).getBoolean(BUNDLE_STATE_FIRST_START, true);

        if(firstStart) {

            AlarmManager alarmMgr = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
            Intent alarm = new Intent(this, AlarmReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(this, 0, alarm, 0);

            // Set the alarm to start at approximately 00:00.
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE,0);
            // With setInexactRepeating(), you have to use one of the AlarmManager interval
            // constants--in this case, AlarmManager.INTERVAL_DAY.

            alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

            ComponentName receiver = new ComponentName(this, DeviceBootReceiver.class);
            PackageManager pm = this.getPackageManager();

            pm.setComponentEnabledSetting(receiver,
                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                    PackageManager.DONT_KILL_APP);

            Toast.makeText(this, "first set alarm", Toast.LENGTH_SHORT).show();

            mPreferences = this.getSharedPreferences(MON_FICHIER, Context.MODE_PRIVATE).edit();
            mPreferences.putBoolean(BUNDLE_STATE_FIRST_START, false).apply();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        mPreferences = this.getSharedPreferences(MON_FICHIER, MODE_PRIVATE).edit();
        int moodIndex = pager.getCurrentItem();
        mPreferences.putInt(BUNDLE_STATE_MOOD, moodIndex).apply();
    }
}
