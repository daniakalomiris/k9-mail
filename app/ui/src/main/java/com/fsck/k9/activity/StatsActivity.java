package com.fsck.k9.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.fsck.k9.Account;
import com.fsck.k9.Preferences;
import com.fsck.k9.controller.MessagingController;
import com.fsck.k9.mail.MessageRetrievalListener;
import com.fsck.k9.mailstore.LocalFolder;
import com.fsck.k9.mailstore.LocalMessage;
import com.fsck.k9.mailstore.LocalStore;
import com.fsck.k9.mailstore.LocalStoreProvider;
import com.fsck.k9.ui.R;

public class StatsActivity extends K9Activity implements View.OnClickListener {

    String serverId;
    String userId;
    LocalStore localStore;
    LocalFolder localFolder;
    private Account account = Preferences.getPreferences(this).getDefaultAccount();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setLayout(R.layout.activity_stats);
        String blah = account.getLocalStorageProviderId();
        String fkljsd = account.getInboxFolder();
        LocalStoreProvider localStoreProvider = new LocalStoreProvider();
        int i = 0;
        try {
            localStore = localStoreProvider.getInstance(account);
            //localStore.searchForMessages();
            localFolder = localStore.getFolder("INBOX");
            i = localFolder.getMessageCount();
        } catch (Exception e) {
            // Print exception
        }
        System.out.println(i);


    }

    @Override
    public void onClick(View v) {

    }

    public static void launch(Activity activity){
        Intent intent = new Intent(activity, StatsActivity.class);
        activity.startActivity(intent);
    }
}
