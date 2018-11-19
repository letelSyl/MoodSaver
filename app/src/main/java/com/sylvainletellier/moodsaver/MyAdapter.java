package com.sylvainletellier.moodsaver;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.sylvainletellier.moodsaver.model.ItemHistory;
import java.util.HashMap;
import java.util.Map;

import static android.view.View.INVISIBLE;
import static android.view.View.OnClickListener;
import static android.view.View.VISIBLE;
import static com.sylvainletellier.moodsaver.R.color;
import static com.sylvainletellier.moodsaver.R.id;
import static com.sylvainletellier.moodsaver.R.layout;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private HashMap<Integer, ItemHistory> comments;

    private ItemHistory itemHistory;

    private Context context;

    public MyAdapter(HashMap<Integer, ItemHistory> comments, Context context){
        this.comments = comments;
        this.context = context;
    }

    @Override
    public int getItemCount() {

        return comments.size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(layout.history_cell, parent, false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        itemHistory = comments.get(position);
        holder.display(comments);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final TextView date;
        private final ImageView comment;

        public MyViewHolder(final View itemView) {
            super(itemView);
            date = (itemView.findViewById(id.date));
            comment = (itemView.findViewById(id.comment_icon));

            comment.setVisibility(INVISIBLE);
        }

        public void display(HashMap<Integer, ItemHistory> hashMap) {

            final String msg = itemHistory.getComment();

            DisplayMetrics metrics = new DisplayMetrics();
            WindowManager windowManager = (WindowManager) MyAdapter.this.context
                    .getSystemService(Context.WINDOW_SERVICE);

            windowManager.getDefaultDisplay().getMetrics(metrics);

            Pair<Integer, Integer> pair = new Pair<>(null, null);
            HashMap<Integer, Pair<Integer,Integer>> cellParameters = new HashMap<>();
            cellParameters.put(0, pair.create(itemView.getResources().getColor(color.faded_red),metrics.widthPixels/5));
            cellParameters.put(1, pair.create(itemView.getResources().getColor(color.warm_grey),metrics.widthPixels*2/5));
            cellParameters.put(2, pair.create(itemView.getResources().getColor(color.cornflower_blue_65),metrics.widthPixels*3/5));
            cellParameters.put(3, pair.create(itemView.getResources().getColor(color.light_sage),metrics.widthPixels*4/5));
            cellParameters.put(4, pair.create(itemView.getResources().getColor(color.banana_yellow),metrics.widthPixels));

            if (hashMap.get(getAdapterPosition()).getMoodIndex()!= -1) {
                date.setText(itemHistory.getDate());
                itemView.setBackgroundColor(cellParameters.get(itemHistory.getMoodIndex()).first);
                itemView.setLayoutParams(new RelativeLayout.LayoutParams(cellParameters.get(itemHistory.getMoodIndex()).second,metrics.heightPixels/7));

                if (hashMap.get(getAdapterPosition()).getComment() != null) {
                    comment.setVisibility(VISIBLE);
                    comment.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }
    }

}
