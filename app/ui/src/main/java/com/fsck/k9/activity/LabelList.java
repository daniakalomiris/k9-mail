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

import java.util.List;


public class LabelList extends K9Activity implements View.OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Intent iin = getIntent();
        Bundle b = iin.getExtras();
        Account account = Preferences.getPreferences(this).getDefaultAccount();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_label_list);
        ListView listView = (ListView) findViewById(R.id.label_list);
        List<String> labels = MessagingController.getInstance(this).getLabelList(account);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>
                (this, android.R.layout.simple_list_item_1, labels);




        listView.setAdapter(arrayAdapter);


    }

    @Override
    public void onClick(View view) {

    }
}
