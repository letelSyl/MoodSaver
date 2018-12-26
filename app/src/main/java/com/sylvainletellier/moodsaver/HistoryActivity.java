package com.sylvainletellier.moodsaver;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sylvainletellier.moodsaver.model.ItemHistory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.view.View.VISIBLE;
/*
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
*/

public class HistoryActivity extends AppCompatActivity {

    private ItemHistory itemHistory;

    private HashMap<Integer, ItemHistory> comments;

    private HashMap<Integer, Pair<Integer, Integer>> cellParameters;

    private DisplayMetrics metrics;

    private PreferencesUtil2 mPreferences;

    private List<PreferencesUtil2.MoodState> MoodStates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        LinearLayout layout = findViewById(R.id.list);


        mPreferences = new PreferencesUtil2(this);
        MoodStates = mPreferences.getAllMoodState();
        display(layout);
    }

    private void beforeDisplay() {
        comments = new HashMap<>();



        comments.put(0, new ItemHistory(MoodStates.get(0).getMood(),
                "hier", MoodStates.get(0).getComment()));
        comments.put(1, new ItemHistory(MoodStates.get(1).getMood(),
                "Avant-hier", MoodStates.get(1).getComment()));
        comments.put(2, new ItemHistory(MoodStates.get(2).getMood(),
                "Il y a 3 jours", MoodStates.get(2).getComment()));
        comments.put(3, new ItemHistory(MoodStates.get(3).getMood(),
                "Il y a 4 jours", MoodStates.get(3).getComment()));
        comments.put(4, new ItemHistory(MoodStates.get(4).getMood(),
                "Il y a 5 jours", MoodStates.get(4).getComment()));
        comments.put(5, new ItemHistory(MoodStates.get(5).getMood(),
                "Il y a 6 jours",  MoodStates.get(5).getComment()));
        comments.put(6, new ItemHistory(MoodStates.get(6).getMood(),
                "Il y a 1 semaine", MoodStates.get(6).getComment()));

        cellParameters = new HashMap<>();
        metrics = new DisplayMetrics();

        WindowManager windowManager = (WindowManager) this
                .getSystemService(Context.WINDOW_SERVICE);

        windowManager.getDefaultDisplay().getMetrics(metrics);


        Pair<Integer, Integer> pair = new Pair<>(null, null);

        cellParameters.put(0, pair.create(getResources().getColor(R.color.faded_red), metrics.widthPixels / 5));
        cellParameters.put(1, pair.create(getResources().getColor(R.color.warm_grey), metrics.widthPixels * 2 / 5));
        cellParameters.put(2, pair.create(getResources().getColor(R.color.cornflower_blue_65), metrics.widthPixels * 3 / 5));
        cellParameters.put(3, pair.create(getResources().getColor(R.color.light_sage), metrics.widthPixels * 4 / 5));
        cellParameters.put(4, pair.create(getResources().getColor(R.color.banana_yellow), metrics.widthPixels));
    }

    private void display(LinearLayout layout) {
        beforeDisplay();
        for (int i = comments.size()-1; i >= 0; i--) {
            View child = getLayoutInflater().inflate(R.layout.history_cell, null);
            itemHistory = comments.get(i);

            if (comments.get(i).getMoodIndex() != -1) {



                TextView date;
                ImageView comment;
                date = (child.findViewById(R.id.date));
                comment = (child.findViewById(R.id.comment_icon));

                date.setText(itemHistory.getDate());
                child.setBackgroundColor(cellParameters.get(itemHistory.getMoodIndex()).first);
                child.setLayoutParams(new RelativeLayout.LayoutParams(cellParameters.get(itemHistory.getMoodIndex()).second, metrics.heightPixels / 7));

                if (comments.get(i).getComment() != "") {
                    final String msg = itemHistory.getComment();
                    comment.setVisibility(VISIBLE);
                    comment.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
            layout.addView(child);
        }
    }
}
