package com.sylvainletellier.moodsaver;

import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

   /* private final List<Pair<String, String>> moods = Arrays.asList(
            Pair.create("hier", "La pizza était trop bonne !!!"),
            Pair.create("avant-hier", "La météo est pourrie"),
            Pair.create("il y a 3 jours", "RAS"),
            Pair.create("il y a 4 jours", "Bon film au ciné ce soir"),
            Pair.create("il y a 5 jours",""),
            Pair.create("il y a 6 jours", "Beaucoup trop de travail "),
            Pair.create("il y a une semaine", "La voiture est encore en panne :(")
    );*/
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

            HashMap<Integer, Integer> bkgColor = new HashMap<>();
            bkgColor.put(0,itemView.getResources().getColor(color.faded_red));
            bkgColor.put(1, itemView.getResources().getColor(color.warm_grey));
            bkgColor.put(2, itemView.getResources().getColor(color.cornflower_blue_65));
            bkgColor.put(3, itemView.getResources().getColor(color.light_sage));
            bkgColor.put(4, itemView.getResources().getColor(color.banana_yellow));

            if (comments.get(getAdapterPosition()).getMoodIndex()!= -1) {
                comments.get(getAdapterPosition()).getDate();


                if (comments.get(getAdapterPosition()).getComment() != null) {
                    comment.setVisibility(View.VISIBLE);
                }


           itemView.setBackgroundColor(bkgColor.get(comments.get(getAdapterPosition()).getMoodIndex()));

            }
        }
    }

}
