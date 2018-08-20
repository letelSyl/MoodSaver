package com.sylvainletellier.moodsaver;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
//import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;


import java.util.Calendar;
import java.util.List;
import java.util.Vector;

public class MainActivity extends FragmentActivity {

    private PagerAdapter mPagerAdapter;

    public static final String BUNDLE_STATE_MOOD = "currentMood";
    private int mMoodIndex;

    public static final String BUNDLE_STATE_COMMENT = "Cemment";

    private ViewPager pager;

    private SharedPreferences mPreferences;


    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_main);

        // Création de la liste de Fragments que fera défiler le PagerAdapter
        List<Fragment> fragments = new Vector<>();

        // Ajout des Fragments dans la liste
        fragments.add(Fragment.instantiate(this,VeryBadMoodFragment.class.getName()));
        fragments.add(Fragment.instantiate(this,BadMoodFragment.class.getName()));
        fragments.add(Fragment.instantiate(this,NormalMoodFragment.class.getName()));
        fragments.add(Fragment.instantiate(this,GoodMoodFragment.class.getName()));
        fragments.add(Fragment.instantiate(this,VeryGoodMoodFragment.class.getName()));



        // Création de l'adapter qui s'occupera de l'affichage de la liste de
        // Fragments
        this.mPagerAdapter = new MyPagerAdapter(super.getSupportFragmentManager(), fragments);

        pager = super.findViewById(R.id.viewpager);
        // Affectation de l'adapter au ViewPager
        pager.setAdapter(this.mPagerAdapter);

        mMoodIndex = getPreferences(MODE_PRIVATE).getInt(BUNDLE_STATE_MOOD, -1);
        if (mMoodIndex != -1) {
            pager.setCurrentItem(mMoodIndex);
        }
        else{
            pager.setCurrentItem(3);
        }



        alarmMgr = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class) ;
        alarmIntent = PendingIntent.getBroadcast(this, 0, intent, 0);



        // Set the alarm to start at approximately 00:00 p.m.


        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 00);

        // With setInexactRepeating(), you have to use one of the AlarmManager interval
        // constants--in this case, AlarmManager.INTERVAL_DAY.
        alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY, alarmIntent);


    }

    @Override
    protected void onPause() {
        super.onPause();

        mPreferences = getPreferences(MODE_PRIVATE);
        int moodIndex = pager.getCurrentItem();
        mPreferences.edit().putInt(BUNDLE_STATE_MOOD, moodIndex).apply();
    }
    private class AlarmReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {

    }
}

}
