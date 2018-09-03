package com.sylvainletellier.moodsaver;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MoodFragment extends Fragment {

    private ImageView mComment;
    private ImageView mHistory;
   /* private MainActivity mMainActivity;

    public void setMainActivity(MainActivity mainActivity) {

        mMainActivity = mainActivity;

    } */

    public static MoodFragment newInstance(int index) {
        MoodFragment f = new MoodFragment();
        Bundle args = new Bundle();
        args.putInt("index", index);
        f.setArguments(args);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        int layout = R.layout.fragment_good_mood;
        int mCurrentMood = 3;
        if (getArguments() != null) {
          mCurrentMood = getArguments().getInt("index",3);
        }
        switch (mCurrentMood){
            case 0 :
                layout = R.layout.fragment_very_bad_mood;
                break;
            case 1 :
                layout = R.layout.fragment_bad_mood;
                break;
            case 2 :
                layout = R.layout.fragment_normal_mood;
                break;
            case 3 :
                layout = R.layout.fragment_good_mood;
                break;
            case 4 :
                layout = R.layout.fragment_very_good_mood;
                break;
        }


        View v = inflater.inflate(layout, container, false);

        mComment = v.findViewById(R.id.comment);
        mHistory = v.findViewById(R.id.history);

        mComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Build an AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                // FIXME : Attention tu réutilise le même nom d'une variable du scope parent (variable shadowing)
                LayoutInflater ad_inflater = getLayoutInflater();
                View dialogView = ad_inflater.inflate(R.layout.alertdialog_custom_view, null);

                // Specify alert dialog is not cancelable/not ignorable
                builder.setCancelable(false);

                // Set the custom layout as alert dialog view
                builder.setView(dialogView);

                // Get the custom alert dialog view widgets reference
                Button btn_positive = dialogView.findViewById(R.id.dialog_positive_btn);
                Button btn_negative = dialogView.findViewById(R.id.dialog_negative_btn);
                final EditText et_comment = dialogView.findViewById(R.id.et_comment);

                // Create the alert dialog
                final AlertDialog dialog = builder.create();

                // Set positive/yes button click listener
                btn_positive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Dismiss the alert dialog
                        dialog.cancel();
                        String comment = et_comment.getText().toString();
                        Toast.makeText(getActivity().getApplicationContext(),
                                "Submitted comment : " + comment, Toast.LENGTH_SHORT).show();

                    }
                });

                // Set negative/no button click listener
                btn_negative.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Dismiss/cancel the alert dialog
                        //dialog.cancel();
                        dialog.dismiss();
                        Toast.makeText(getActivity().getApplicationContext(),
                                "Cancel button clicked", Toast.LENGTH_SHORT).show();
                    }
                });

                // Display the custom alert dialog on interface
                dialog.show();
            }
        });

        mHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // The user just clicked
                Intent historyActivity = new Intent(getActivity(), HistoryActivity.class);
                startActivity(historyActivity);
            }
        });


        return v;

    }

}
