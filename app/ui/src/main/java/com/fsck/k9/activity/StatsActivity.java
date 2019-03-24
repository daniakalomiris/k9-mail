package com.fsck.k9.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.fsck.k9.Account;
import com.fsck.k9.Preferences;
import com.fsck.k9.mail.MessageRetrievalListener;
import com.fsck.k9.mail.MessagingException;
import com.fsck.k9.mailstore.LocalMessage;
import com.fsck.k9.ui.R;
import com.fsck.k9.mailstore.LocalStoreProvider;
import com.fsck.k9.mailstore.LocalStore;
import com.fsck.k9.mailstore.LocalFolder;
import com.fsck.k9.DI;

import java.util.List;

public class StatsActivity extends K9Activity implements View.OnClickListener {

    String serverId;
    String userId;
    LocalStore localStore;
    LocalFolder localFolder;
    List<LocalMessage> messages;
    private Account account = Preferences.getPreferences(this).getDefaultAccount();
    MessageRetrievalListener<LocalMessage> listener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setLayout(R.layout.activity_stats);
        String localStorageProvider = account.getLocalStorageProviderId();
        String inbox = account.getInboxFolder();
        LocalStoreProvider localStoreProvider = new LocalStoreProvider();
        int i = 0;
        try {
            localStore = localStoreProvider.getInstance(account);
            //localStore.searchForMessages();
            localFolder = localStore.getFolder("INBOX");
            i = localFolder.getMessageCount();
            messages = localFolder.getMessages(listener);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Inbox Message Count: " + i);

        System.out.println("All Inbox Messages in Local Storage");
        for (LocalMessage message : messages) {
            System.out.println(message);
        }
    }

    @Override
    public void onClick(View v) {
    }

    public static void launch(Activity activity){
        Intent intent = new Intent(activity, StatsActivity.class);
        activity.startActivity(intent);
    }
}
