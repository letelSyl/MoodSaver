package com.sylvainletellier.moodsaver;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Pair;

import com.sylvainletellier.moodsaver.model.ItemHistory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.sylvainletellier.moodsaver.MainActivity.MON_FICHIER;


public class PreferencesUtil {



    public static final String BUNDLE_STATE_FIRST_START = "first start";

    public static final Pair<String, String> BUNDLE_STATE_MOOD = new Pair<>("current Mood", "Current Comment");
    public static final Pair<String, String> BUNDLE_STATE_MOOD_M1 = new Pair<>("Mood J-1", "Comment J-1");
    public static final Pair<String, String> BUNDLE_STATE_MOOD_M2 = new Pair<>("Mood J-2", "Comment J-2");
    public static final Pair<String, String> BUNDLE_STATE_MOOD_M3 = new Pair<>("Mood J-3", "Comment J-3");
    public static final Pair<String, String> BUNDLE_STATE_MOOD_M4 = new Pair<>("Mood J-4", "Comment J-4");
    public static final Pair<String, String> BUNDLE_STATE_MOOD_M5 = new Pair<>("Mood J-5", "Comment J-5");
    public static final Pair<String, String> BUNDLE_STATE_MOOD_M6 = new Pair<>("Mood J-6", "Comment J-6");
    public static final Pair<String, String> BUNDLE_STATE_MOOD_M7 = new Pair<>("Mood J-7", "Comment J-7");

    public List<Pair<String, String>> moodKeys = Arrays.asList(BUNDLE_STATE_MOOD,
            BUNDLE_STATE_MOOD_M1,
            BUNDLE_STATE_MOOD_M2,
            BUNDLE_STATE_MOOD_M3,
            BUNDLE_STATE_MOOD_M4,
            BUNDLE_STATE_MOOD_M5,
            BUNDLE_STATE_MOOD_M6,
            BUNDLE_STATE_MOOD_M7);

    public static class MoodState {
        private Integer mood;
        private String comment;

        public MoodState(Integer mood, String comment) {
            this.mood = mood;
            this.comment = comment;
        }

        public Integer getMood() {
            return mood;
        }

        public void setMood(Integer mood) {
            this.mood = mood;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }
    }

    private SharedPreferences mPreferences;

    public PreferencesUtil(Context context) {
        this.mPreferences = context.getSharedPreferences(MON_FICHIER, Context.MODE_PRIVATE);
    }

    public MoodState getMoodState(Pair<String, String> key) {
        return new MoodState(mPreferences.getInt(key.first, -1), mPreferences.getString(key.second, ""));
    }

    public List<MoodState> getAllMoodState() {

        ArrayList<MoodState> result = new ArrayList<>();

        for (Pair<String, String> moodKey : moodKeys) {
            result.add(getMoodState(moodKey));
        }

        return result;
    }

    public void saveMoodStateHistory(){

        List<MoodState> moodStateList = getAllMoodState();

        for (int i = moodKeys.size()-1; i>0; i--){

            setMoodState(moodKeys.get(i), moodStateList.get(i-1));

        }
    }

    public void initializeCurrentMood(){
        SharedPreferences.Editor edit = mPreferences.edit();

        edit.putInt(BUNDLE_STATE_MOOD.first, 3);
        edit.putString(BUNDLE_STATE_MOOD.second, "");

        edit.apply();
    }

    public void setMoodState(Pair<String, String> key, MoodState moodState) {
        SharedPreferences.Editor edit = mPreferences.edit();

        edit.putInt(key.first, moodState.getMood());
        edit.putString(key.second, moodState.getComment());

        edit.apply();
    }

    public Boolean getFirstStart() {
        return mPreferences.getBoolean(BUNDLE_STATE_FIRST_START, true);
    }

    public void setFirstStart(Boolean value) {
        mPreferences.edit().putBoolean(BUNDLE_STATE_FIRST_START, value).apply();
    }

}
