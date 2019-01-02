package com.sylvainletellier.moodsaver;

import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends FragmentActivity {

    public static final String MON_FICHIER = "monFichier";

    private PagerAdapter mPagerAdapter;

    private VerticalViewPager pager;

    private PreferencesUtil preferences;

    private boolean firstStart;

    private SoundPool mSoundPool;
    private int sound0, sound1, sound2, sound3, sound4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_main);

        // Creating the list of Fragments that will scroll the PagerAdapter
        List<MoodFragment> fragments = Arrays.asList(
                MoodFragment.newInstance(0),
                MoodFragment.newInstance(1),
                MoodFragment.newInstance(2),
                MoodFragment.newInstance(3),
                MoodFragment.newInstance(4)
        );

        soundPoolGenerator();

        preferences = new PreferencesUtil(this);

        this.mPagerAdapter = new MyPagerAdapter(super.getSupportFragmentManager(), fragments);

        pager = super.findViewById(R.id.viewpager);
        // Assignment to adapt it to ViewPager
        pager.setAdapter(this.mPagerAdapter);

        pager.setCurrentItem(preferences.getMoodState(PreferencesUtil.BUNDLE_STATE_MOOD).getMood());

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            int index;
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                this.index = position;
                int[]sounds = {sound0, sound1, sound2, sound3, sound4};
                mSoundPool.autoPause();
                mSoundPool.play(sounds[this.index], 1.0f, 1.0f, 0, 0, 1);
                preferences.setMoodState(PreferencesUtil.BUNDLE_STATE_MOOD, new PreferencesUtil.MoodState(this.index,PreferencesUtil.BUNDLE_STATE_MOOD.second));
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });

        firstStart = preferences.getFirstStart();

        if(firstStart) {

            AlarmUtil.setAlarm(this);

            ComponentName receiver = new ComponentName(this, DeviceBootReceiver.class);
            PackageManager pm = this.getPackageManager();

            pm.setComponentEnabledSetting(receiver,
                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                    PackageManager.DONT_KILL_APP);

            Toast.makeText(this, "first set alarm", Toast.LENGTH_SHORT).show();

            pager.setCurrentItem(3);

            preferences.setFirstStart(false);
        }
    }

    public void soundPoolGenerator(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();

            mSoundPool = new SoundPool.Builder()
                    .setMaxStreams(5)
                    .setAudioAttributes(audioAttributes)
                    .build();
        } else {
            mSoundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        }

        sound0 = mSoundPool.load(this,R.raw.sound0,1 );
        sound1 = mSoundPool.load(this,R.raw.sound1,1 );
        sound2 = mSoundPool.load(this,R.raw.sound2,1 );
        sound3 = mSoundPool.load(this,R.raw.sound3,1 );
        sound4 = mSoundPool.load(this,R.raw.sound4,1 );
    }
}
