package com.fsck.k9.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.fsck.k9.Account;
import com.fsck.k9.Preferences;
import com.fsck.k9.controller.MessagingController;
import com.fsck.k9.mailstore.LocalStore;
import com.fsck.k9.mailstore.LocalStoreProvider;
import com.fsck.k9.ui.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class LabelList extends K9Activity implements View.OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Intent iin = getIntent();
        Bundle b = iin.getExtras();
        Account account = Preferences.getPreferences(this).getDefaultAccount();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_label_list);
        ListView listView = (ListView) findViewById(R.id.label_list);
        HashMap<String, Integer> labelsAndCount = MessagingController.getInstance(this).getLabelList(account);

        ArrayList<String> labelsWithCount = new ArrayList<>();

        for(HashMap.Entry<String, Integer> entry : labelsAndCount.entrySet()) {
            labelsWithCount.add(entry.getKey() + " - " + entry.getValue().toString());
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>
                (this, android.R.layout.simple_list_item_1, labelsWithCount);

        listView.setAdapter(arrayAdapter);
    }

    @Override
    public void onClick(View view) {

    }
}
