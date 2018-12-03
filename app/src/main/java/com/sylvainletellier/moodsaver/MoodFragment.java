package com.sylvainletellier.moodsaver;


import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import static android.content.Context.MODE_PRIVATE;
import static com.sylvainletellier.moodsaver.MainActivity.BUNDLE_STATE_CURRENT_COMMENT;

public class MoodFragment extends Fragment {

    private ImageView mComment;
    private ImageView mHistory;
    private int layout;
    private int mCurrentMood;


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


        if (getArguments() != null) {
            mCurrentMood = getArguments().getInt("index", 3);
        }
        switch (mCurrentMood) {
            case 0:
                layout = R.layout.fragment_very_bad_mood;
                break;
            case 1:
                layout = R.layout.fragment_bad_mood;
                break;
            case 2:
                layout = R.layout.fragment_normal_mood;
                break;
            case 3:
                layout = R.layout.fragment_good_mood;
                break;
            case 4:
                layout = R.layout.fragment_very_good_mood;
                break;
            default:
                layout = R.layout.fragment_good_mood;
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

                LayoutInflater adInflater = getLayoutInflater();
                View dialogView = adInflater.inflate(R.layout.alertdialog_custom_view, null);

                // Specify alert dialog is not cancelable/not ignorable
                builder.setCancelable(false);

                // Set the custom layout as alert dialog view
                builder.setView(dialogView);

                // Get the custom alert dialog view widgets reference
                Button btnPositive = dialogView.findViewById(R.id.dialog_positive_btn);
                Button btnNegative = dialogView.findViewById(R.id.dialog_negative_btn);
                final EditText etComment = dialogView.findViewById(R.id.et_comment);

                // Create the alert dialog
                final AlertDialog dialog = builder.create();

                // Set positive/yes button click listener
                btnPositive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Dismiss the alert dialog
                        dialog.cancel();
                        String comment = etComment.getText().toString();
                        Toast.makeText(getActivity().getApplicationContext(),
                                "Submitted comment : " + comment, Toast.LENGTH_SHORT).show();
                        SharedPreferences preferences = getActivity().getBaseContext().getSharedPreferences("monFichier", MODE_PRIVATE);

                        preferences.edit().putString(BUNDLE_STATE_CURRENT_COMMENT, comment).apply();
                    }
                });

                // Set negative/no button click listener
                btnNegative.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Dismiss/cancel the alert dialog
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

