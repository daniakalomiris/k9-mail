package com.fsck.k9.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.fsck.k9.Account;
import com.fsck.k9.Preferences;
import com.fsck.k9.mail.Address;
import com.fsck.k9.mail.MessageRetrievalListener;
import com.fsck.k9.mailstore.LocalMessage;
import com.fsck.k9.ui.R;
import com.fsck.k9.mailstore.LocalStoreProvider;
import com.fsck.k9.mailstore.LocalStore;
import com.fsck.k9.mailstore.LocalFolder;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

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


    BarChart barChart;
    ArrayList<String> days;
    ArrayList<BarEntry> barEntries;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setLayout(R.layout.activity_stats);

        try {
            LocalStoreProvider localStoreProvider = new LocalStoreProvider();
            localStore = localStoreProvider.getInstance(account);
            localFolder = localStore.getFolder("INBOX");
            //messages = localFolder.getMessages(listener);
            List<String> allMessages =localFolder.getAllMessageUids();
            messages = localFolder.getMessagesByUids(allMessages);

        } catch (Exception e) {
            e.printStackTrace();
        }
        barChart = (BarChart) findViewById(R.id.barGraph);
        if (messages != null) {
            crunchData();
            createDateBarChart();
        }
        senderStats();
        SetMostFrequentSender();
    }

    private void SetMostFrequentSender() {
        TextView senderText = findViewById(R.id.senderID);

        senderText.setText(getMostFrequentSender());
    }

    private void createDateBarChart() {
        days = new ArrayList<>();
        days.add(0,"Sun");
        days.add(1,"Mon");
        days.add(2,"Tue");
        days.add(3,"Wed");
        days.add(4,"Thu");
        days.add(5,"Fri");
        days.add(6,"Sat");
        barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(0,dayOfWeek.get("Sun")));
        barEntries.add(new BarEntry(1,dayOfWeek.get("Mon")));
        barEntries.add(new BarEntry(2,dayOfWeek.get("Tue")));
        barEntries.add(new BarEntry(3,dayOfWeek.get("Wed")));
        barEntries.add(new BarEntry(4,dayOfWeek.get("Thu")));
        barEntries.add(new BarEntry(5,dayOfWeek.get("Fri")));
        barEntries.add(new BarEntry(6,dayOfWeek.get("Sat")));


        XAxis xAxis = barChart.getXAxis();

        xAxis.setValueFormatter(new MyXAxisValueFormatter(days));

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(false);

        BarDataSet bds = new BarDataSet(barEntries, "Days");
        bds.setColors(ColorTemplate.PASTEL_COLORS);

        BarData theData = new BarData(bds);
        barChart.setTouchEnabled(false);
        barChart.getDescription().setEnabled(false);
        barChart.getLegend().setEnabled(false);
        barChart.setData(theData);
        barChart.setFitBars(true);

        TextView senderText = findViewById(R.id.lastWeek);

        senderText.setText("In the last week you received "+messagesLastWeek+" emails!");

    }

    private void crunchData() {
        dayOfWeek.put("Sun", 0);
        dayOfWeek.put("Mon", 0);
        dayOfWeek.put("Tue", 0);
        dayOfWeek.put("Wed", 0);
        dayOfWeek.put("Thu", 0);
        dayOfWeek.put("Fri", 0);
        dayOfWeek.put("Sat", 0);
        for (LocalMessage m : messages) {
            // Calculate day frequency
            String day = (m.getSentDate()).toString().substring(0, 3);

            dayOfWeek.put(day, dayOfWeek.get(day) + 1);

            Calendar cal = new GregorianCalendar();
            cal.add(Calendar.DAY_OF_MONTH, -7);
            Date sevenDaysAgo = cal.getTime();

            // Messages sent within the last week
            if (m.getSentDate().before(sevenDaysAgo))
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
    private String getMostFrequentSender() {
        int maxSender = Collections.max(mSenders.values());

        Address[] mostFrequentSender = null;
        Enumeration senders;
        Address[] key;
        senders = mSenders.keys();
        while (senders.hasMoreElements()) {
            key = (Address[]) senders.nextElement();
            if (mSenders.get(key) == maxSender) {
                mostFrequentSender = key;
            }
            Log.i("key", "getMostFrequentSender: "+mSenders.get(key));
        }
        return null;
       // return mostFrequentSender[0].getAddress();
    }

    private void senderStats() {
        for (LocalMessage m : messages) {
            addSender(m.getSender());
        }
        getMostFrequentSender();
    }


    @Override
    public void onClick(View v) {
    }

    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, StatsActivity.class);
        activity.startActivity(intent);
    }

    private class MyXAxisValueFormatter   extends ValueFormatter
    {
        private ArrayList<String> dayString;

        public MyXAxisValueFormatter (ArrayList<String> days) {
            dayString = days;
        }

        @Override
        public String getFormattedValue(float value) {
            Log.i("value", "getFormattedValue: "+value);
            return dayString.get((int) value);
        }


    }


}
