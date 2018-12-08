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

import java.util.HashMap;

import static android.view.View.VISIBLE;
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


        HashMap<Integer, ItemHistory> comments = new HashMap<>();

        comments.put(0,new ItemHistory(PreferencesUtil.get(this).getInt(BUNDLE_STATE_MOOD_M1,-1),"hier",PreferencesUtil.get(this).getString(BUNDLE_STATE_COMMENT_M1,null )));
        comments.put(1,new ItemHistory(PreferencesUtil.get(this).getInt(BUNDLE_STATE_MOOD_M2,-1),"Il y a 2 jours",PreferencesUtil.get(this).getString(BUNDLE_STATE_COMMENT_M2,null )));
        comments.put(2,new ItemHistory(PreferencesUtil.get(this).getInt(BUNDLE_STATE_MOOD_M3,-1),"Il y a 3 jours",PreferencesUtil.get(this).getString(BUNDLE_STATE_COMMENT_M3,null )));
        comments.put(3,new ItemHistory(PreferencesUtil.get(this).getInt(BUNDLE_STATE_MOOD_M4,-1),"Il y a 4 jours",PreferencesUtil.get(this).getString(BUNDLE_STATE_COMMENT_M4,null )));
        comments.put(4,new ItemHistory(PreferencesUtil.get(this).getInt(BUNDLE_STATE_MOOD_M5,-1),"Il y a 5 jours",PreferencesUtil.get(this).getString(BUNDLE_STATE_COMMENT_M5,null )));
        comments.put(5,new ItemHistory(PreferencesUtil.get(this).getInt(BUNDLE_STATE_MOOD_M6,-1),"Il y a 6 jours",PreferencesUtil.get(this).getString(BUNDLE_STATE_COMMENT_M6,null )));
        comments.put(6,new ItemHistory(PreferencesUtil.get(this).getInt(BUNDLE_STATE_MOOD_M7,-1),"Il y a 1 semaine",PreferencesUtil.get(this).getString(BUNDLE_STATE_COMMENT_M7,null )));

        LinearLayout layout = findViewById(R.id.list);
        ItemHistory itemHistory;

        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) this
                .getSystemService(Context.WINDOW_SERVICE);

        windowManager.getDefaultDisplay().getMetrics(metrics);


        Pair<Integer, Integer> pair = new Pair<>(null, null);
        HashMap<Integer, Pair<Integer,Integer>> cellParameters = new HashMap<>();
        cellParameters.put(0, pair.create(getResources().getColor(R.color.faded_red),metrics.widthPixels/5));
        cellParameters.put(1, pair.create(getResources().getColor(R.color.warm_grey),metrics.widthPixels*2/5));
        cellParameters.put(2, pair.create(getResources().getColor(R.color.cornflower_blue_65),metrics.widthPixels*3/5));
        cellParameters.put(3, pair.create(getResources().getColor(R.color.light_sage),metrics.widthPixels*4/5));
        cellParameters.put(4, pair.create(getResources().getColor(R.color.banana_yellow),metrics.widthPixels));

         for(int i = comments.size(); i>0; i--){
           View child = getLayoutInflater().inflate(R.layout.history_cell, null);


             if (comments.get(i-1).getMoodIndex()!= -1) {

               itemHistory = comments.get(i - 1);
               final String msg = itemHistory.getComment();
               TextView date;
               ImageView comment;
               date = (child.findViewById(R.id.date));
               comment = (child.findViewById(R.id.comment_icon));

               date.setText(itemHistory.getDate());
               child.setBackgroundColor(cellParameters.get(itemHistory.getMoodIndex()).first);
               child.setLayoutParams(new RelativeLayout.LayoutParams(cellParameters.get(itemHistory.getMoodIndex()).second, metrics.heightPixels / 7));

               if (comments.get(i - 1).getComment() != null) {
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
