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

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

public class StatsActivity extends K9Activity implements View.OnClickListener {

    private LocalStore localStore;
    private LocalFolder localFolder;
    private Account account = Preferences.getPreferences(this).getDefaultAccount();
    private MessageRetrievalListener<LocalMessage> listener;
    private List<LocalMessage> messages;
    private HashMap<String, Integer> dayOfWeek = new HashMap<>();
    private int messagesLastWeek = 0;

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

    @Override
    public void onClick(View v) {
    }

    public static void launch(Activity activity){
        Intent intent = new Intent(activity, StatsActivity.class);
        activity.startActivity(intent);
    }
}
