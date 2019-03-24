package com.fsck.k9.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.fsck.k9.ui.R;

public class StatsActivity extends K9Activity implements View.OnClickListener {




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setLayout(R.layout.activity_stats);


    }

    @Override
    public void onClick(View v) {

    }

    public static void launch(Activity activity){
        Intent intent = new Intent(activity, StatsActivity.class);
        activity.startActivity(intent);
    }
}
