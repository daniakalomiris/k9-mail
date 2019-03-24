package com.fsck.k9.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.fsck.k9.Account;
import com.fsck.k9.Preferences;
import com.fsck.k9.mail.Address;
import com.fsck.k9.mail.MessageRetrievalListener;
import com.fsck.k9.mailstore.LocalMessage;
import com.fsck.k9.ui.R;
import com.fsck.k9.mailstore.LocalStoreProvider;
import com.fsck.k9.mailstore.LocalStore;
import com.fsck.k9.mailstore.LocalFolder;

import java.util.*;

import java.util.List;

public class StatsActivity extends K9Activity implements View.OnClickListener {

    private LocalStore localStore;
    private LocalFolder localFolder;
    private Account account = Preferences.getPreferences(this).getDefaultAccount();
    private MessageRetrievalListener<LocalMessage> listener;
    private List<LocalMessage> messages;
    private HashMap<String, Integer> dayOfWeek = new HashMap<>();
    private int messagesLastWeek = 0;
    private static Hashtable<Address[], Integer> mSenders = new Hashtable<Address[], Integer>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setLayout(R.layout.activity_stats);
        
        try {
            LocalStoreProvider localStoreProvider = new LocalStoreProvider();
            localStore = localStoreProvider.getInstance(account);
            localFolder = localStore.getFolder("INBOX");
            messages = localFolder.getMessages(listener);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(messages != null)
            crunchData();
            senderStats();
    }

    private void crunchData() {
        for(LocalMessage m : messages) {
            // Calculate day frequency
            String day = (m.getSentDate()).toString().substring(0,3);
            if(!dayOfWeek.containsKey(day))
                dayOfWeek.put(day, 1);
            else
                dayOfWeek.put(day, dayOfWeek.get(day) + 1);

            Calendar cal = new GregorianCalendar();
            cal.add(Calendar.DAY_OF_MONTH, -7);
            Date sevenDaysAgo = cal.getTime();

            // Messages sent within the last week
            if(m.getSentDate().before(sevenDaysAgo))
                messagesLastWeek++;
        }
    }

    // Add each sender to hashtable
    private void addSender(Address[] sender) {
        if (mSenders.containsKey(sender)) {
            mSenders.put(sender, mSenders.get(sender) + 1);
        } else {
            int initialSenderCount = 1;
            mSenders.put(sender, initialSenderCount);
        }
    }

    // Get the most frequent sender from all local messages
    private Address[] getMostFrequentSender() {
        int maxSender = Collections.max(mSenders.values());

        Address[] mostFrequentSender = null;
        Enumeration senders;
        Address[] key;
        senders = mSenders.keys();
        while(senders.hasMoreElements()) {
            key = (Address[]) senders.nextElement();
            if (mSenders.get(key) == maxSender) {
                mostFrequentSender = key;
            }
        }
        return mostFrequentSender;
    }

    private void senderStats() {
        for(LocalMessage m : messages) {
            addSender(m.getSender());
        }
        getMostFrequentSender();
    }


    @Override
    public void onClick(View v) {
    }

    public static void launch(Activity activity){
        Intent intent = new Intent(activity, StatsActivity.class);
        activity.startActivity(intent);
    }
}
