package com.sylvainletellier.moodsaver;

import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sylvainletellier.moodsaver.model.ItemHistory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.sylvainletellier.moodsaver.R.*;
import static com.sylvainletellier.moodsaver.R.color.*;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {


    private Map<Integer, ItemHistory> comments;

    public MyAdapter(HashMap<Integer, ItemHistory> comments){
        this.comments = comments;
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
       // ItemHistory itemHistory= comments.get(position);
        holder.display();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final TextView date;
        private final ImageView comment;

        //private HashMap<Integer, ItemHistory> itemHistoryMap;

        public MyViewHolder(final View itemView) {
            super(itemView);
            date = ((TextView) itemView.findViewById(id.date));
            comment = ((ImageView) itemView.findViewById(id.comment_icon));
            comment.setVisibility(View.INVISIBLE);

            comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(itemView.getContext(), comments.get(getAdapterPosition()).getComment(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        public void display() {

            DisplayMetrics metrics = new DisplayMetrics();


            Pair<Integer, Integer> pair = null;
            HashMap<Integer, Pair<Integer,Integer>> cellParameters = new HashMap<>();
            cellParameters.put(0, pair.create(itemView.getResources().getColor(color.faded_red),metrics.widthPixels*1/5));
            cellParameters.put(1, pair.create(itemView.getResources().getColor(color.warm_grey),metrics.widthPixels*2/5));
            cellParameters.put(2, pair.create(itemView.getResources().getColor(color.cornflower_blue_65),metrics.widthPixels*3/5));
            cellParameters.put(3, pair.create(itemView.getResources().getColor(color.light_sage),metrics.widthPixels*4/5));
            cellParameters.put(4, pair.create(itemView.getResources().getColor(color.banana_yellow),metrics.widthPixels));

            if (comments.get(getAdapterPosition()).getMoodIndex()!= -1) {
                comments.get(getAdapterPosition()).getDate();


                if (comments.get(getAdapterPosition()).getComment() != null) {
                    comment.setVisibility(View.VISIBLE);
                }


           itemView.setBackgroundColor(cellParameters.get(comments.get(getAdapterPosition()).getMoodIndex()).first);
           itemView.setLayoutParams(new RelativeLayout.LayoutParams(cellParameters.get(comments.get(getAdapterPosition()).getMoodIndex()).second,metrics.heightPixels*1/7));

            }
        }
    }

}
