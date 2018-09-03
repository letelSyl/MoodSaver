package com.sylvainletellier.moodsaver;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends FragmentActivity {

    private PagerAdapter mPagerAdapter;

    public static final String BUNDLE_STATE_MOOD = "currentMood";
    private int mMoodIndex;

    public static final String BUNDLE_STATE_COMMENT = "Comment";

    private ViewPager pager;

    private SharedPreferences mPreferences;


    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;


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

       /*-- // Ajout des Fragments dans la liste
        // FIXME : il est possible d'utilister le constructeur des fragments directement. Il est aussi possible d'utiliser Arrays.asList()
        fragments.add(Fragment.instantiate(this,VeryBadMoodFragment.class.getName()));
        fragments.add(Fragment.instantiate(this,BadMoodFragment.class.getName()));
        fragments.add(Fragment.instantiate(this,NormalMoodFragment.class.getName()));
        MoodFragment instantiate = new MoodFragment();
       //instantiate.setMainActivity(this);
        fragments.add(instantiate);
        fragments.add(Fragment.instantiate(this,VeryGoodMoodFragment.class.getName())); ---*/



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



      /*--------------------------------------------------------------------------
        alarmMgr = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class) ;
        alarmIntent = PendingIntent.getBroadcast(this, 0, intent, 0);


        // Set the alarm to start at approximately 00:00 p.m.


        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        // FIXME : 00 est interpreté comme un octet au lieu d'un integer (changed 00 to 0)
        calendar.set(Calendar.HOUR_OF_DAY, 0);

        // With setInexactRepeating(), you have to use one of the AlarmManager interval
        // constants--in this case, AlarmManager.INTERVAL_DAY.
        alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY, alarmIntent);
    -----------------------------------------------------------------------------------------------*/

    }

    @Override
    protected void onPause() {
        super.onPause();

        mPreferences = getPreferences(MODE_PRIVATE);
        int moodIndex = pager.getCurrentItem();
        mPreferences.edit().putInt(BUNDLE_STATE_MOOD, moodIndex).apply();
    }
   /*--------------------------------------------------------------------
    private class AlarmReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {

        }
    }
    ----------------------------------------------------------------------*/


}
