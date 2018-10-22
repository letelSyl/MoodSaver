package com.sylvainletellier.moodsaver;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;

import com.sylvainletellier.moodsaver.model.ItemHistory;

import java.util.HashMap;
import java.util.Map;

import static com.sylvainletellier.moodsaver.MainActivity.BUNDLE_STATE_COMMENT_M1;
import static com.sylvainletellier.moodsaver.MainActivity.BUNDLE_STATE_COMMENT_M2;
import static com.sylvainletellier.moodsaver.MainActivity.BUNDLE_STATE_COMMENT_M3;
import static com.sylvainletellier.moodsaver.MainActivity.BUNDLE_STATE_COMMENT_M4;
import static com.sylvainletellier.moodsaver.MainActivity.BUNDLE_STATE_COMMENT_M5;
import static com.sylvainletellier.moodsaver.MainActivity.BUNDLE_STATE_COMMENT_M6;
import static com.sylvainletellier.moodsaver.MainActivity.BUNDLE_STATE_COMMENT_M7;
import static com.sylvainletellier.moodsaver.MainActivity.BUNDLE_STATE_MOOD_M1;
import static com.sylvainletellier.moodsaver.MainActivity.BUNDLE_STATE_MOOD_M2;
import static com.sylvainletellier.moodsaver.MainActivity.BUNDLE_STATE_MOOD_M3;
import static com.sylvainletellier.moodsaver.MainActivity.BUNDLE_STATE_MOOD_M4;
import static com.sylvainletellier.moodsaver.MainActivity.BUNDLE_STATE_MOOD_M5;
import static com.sylvainletellier.moodsaver.MainActivity.BUNDLE_STATE_MOOD_M6;
import static com.sylvainletellier.moodsaver.MainActivity.BUNDLE_STATE_MOOD_M7;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

       SharedPreferences mPreferences = getPreferences(MODE_PRIVATE);

        HashMap<Integer, ItemHistory> comments = new HashMap<>();
        comments.put(0,new ItemHistory(mPreferences.getInt(BUNDLE_STATE_MOOD_M1,-1),"hier",mPreferences.getString(BUNDLE_STATE_COMMENT_M1,null )));
        comments.put(1,new ItemHistory(mPreferences.getInt(BUNDLE_STATE_MOOD_M2,-1),"hier",mPreferences.getString(BUNDLE_STATE_COMMENT_M2,null )));
        comments.put(2,new ItemHistory(mPreferences.getInt(BUNDLE_STATE_MOOD_M3,-1),"hier",mPreferences.getString(BUNDLE_STATE_COMMENT_M3,null )));
        comments.put(3,new ItemHistory(mPreferences.getInt(BUNDLE_STATE_MOOD_M4,-1),"hier",mPreferences.getString(BUNDLE_STATE_COMMENT_M4,null )));
        comments.put(4,new ItemHistory(mPreferences.getInt(BUNDLE_STATE_MOOD_M5,-1),"hier",mPreferences.getString(BUNDLE_STATE_COMMENT_M5,null )));
        comments.put(5,new ItemHistory(mPreferences.getInt(BUNDLE_STATE_MOOD_M6,-1),"hier",mPreferences.getString(BUNDLE_STATE_COMMENT_M6,null )));
        comments.put(6,new ItemHistory(mPreferences.getInt(BUNDLE_STATE_MOOD_M7,-1),"hier",mPreferences.getString(BUNDLE_STATE_COMMENT_M7,null )));

        final RecyclerView rv = (RecyclerView) findViewById(R.id.list);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new MyAdapter(comments));


    }
}
