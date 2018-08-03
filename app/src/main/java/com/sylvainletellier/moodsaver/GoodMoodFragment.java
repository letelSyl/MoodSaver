package com.sylvainletellier.moodsaver;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

public class GoodMoodFragment extends Fragment {

    private ImageView mComment;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_good_mood, container, false);

        mComment = v.findViewById(R.id.good_comment);

        mComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity().getApplicationContext(),"test",Toast.LENGTH_SHORT).show();
            }
        });

        // Inflate the layout for this fragment
        return v;




    }
}
