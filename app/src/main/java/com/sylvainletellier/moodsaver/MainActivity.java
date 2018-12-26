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

  /*  public static final String BUNDLE_STATE_MOOD = "current Mood";
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
*/
    private VerticalViewPager pager;

    private PreferencesUtil2 preferences;

    private boolean firstStart;
    public static final String BUNDLE_STATE_FIRST_START = "first start";

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

        preferences = new PreferencesUtil2(this);

        this.mPagerAdapter = new MyPagerAdapter(super.getSupportFragmentManager(), fragments);

        pager = super.findViewById(R.id.viewpager);
        // Assignment to adapt it to ViewPager
        pager.setAdapter(this.mPagerAdapter);


      //  Toast.makeText(this, getSharedPreferences(MON_FICHIER, Context.MODE_PRIVATE).getString(BUNDLE_STATE_COMMENT_M1, "Commentaire -1 vide"), Toast.LENGTH_SHORT).show();
      //  Toast.makeText(this, getSharedPreferences(MON_FICHIER, Context.MODE_PRIVATE).getString(BUNDLE_STATE_CURRENT_COMMENT, "Commentaire courrant vide"), Toast.LENGTH_SHORT).show();
        // Creation of the adapt that will take care of the display of the list of Fragments

      //  mMoodIndex = this.getSharedPreferences(MON_FICHIER, Context.MODE_PRIVATE).getInt(BUNDLE_STATE_MOOD, 3);





        pager.setCurrentItem(preferences.getMoodState(PreferencesUtil2.BUNDLE_STATE_MOOD).getMood());



        /*-----------------------------------------
        Class qui extends ViewPager gestion listener et soundpool
         */
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

    @Override
    protected void onPause() {
        super.onPause();

    preferences.setMoodState(PreferencesUtil2.BUNDLE_STATE_MOOD, new PreferencesUtil2.MoodState(pager.getCurrentItem(),PreferencesUtil2.BUNDLE_STATE_MOOD.second));
     /*   int moodIndex = pager.getCurrentItem();
        PreferencesUtil.set(this).putInt(BUNDLE_STATE_MOOD, moodIndex).apply();*/
    }
}
