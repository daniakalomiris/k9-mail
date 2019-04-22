package com.fsck.k9.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.view.View.OnClickListener;

import com.fsck.k9.Account;
import com.fsck.k9.Preferences;
import com.fsck.k9.mail.Address;
import com.fsck.k9.mail.MessageRetrievalListener;
import com.fsck.k9.mailstore.LocalFolder;
import com.fsck.k9.mailstore.LocalMessage;
import com.fsck.k9.mailstore.LocalStore;
import com.fsck.k9.mailstore.LocalStoreProvider;
import com.fsck.k9.ui.R;
import com.fsck.k9.watson.AsyncEmotionWatson;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;


import org.json.JSONObject;
import org.jsoup.Jsoup;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class StatsActivity extends K9Activity implements View.OnClickListener {

    private LocalStore localStore;
    private LocalFolder localFolder;
    private Account account = Preferences.getPreferences(this).getDefaultAccount();
    private MessageRetrievalListener<LocalMessage> listener;
    private List<LocalMessage> messages;
    private HashMap<String, Integer> dayOfWeek = new HashMap<>();
    private HashMap<String, Float> pieData = new HashMap<>();
    private int messagesLastWeek = 0;
    private static Hashtable<Address, Integer> mSenders = new Hashtable<Address, Integer>();
    public static final int[] PASTEL_COLORS = {
            Color.rgb(64, 89, 128), Color.rgb(149, 165, 124), Color.rgb(217, 184, 162),
            Color.rgb(191, 134, 134), Color.rgb(179, 48, 80), Color.rgb(144, 144, 144), Color.rgb(179, 148, 180)
    };

    BarChart barChart;
    PieChart pieChart;
    ArrayList<String> days;
    ArrayList<BarEntry> barEntries;
    String allMessageContent = "";

    Button inboxStatsButton;
    Button sentStatsButton;
    Button junkStatsButton;
    Button trashStatsButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayout(R.layout.activity_stats);

        addInboxStatsButton();
        addSentStatsButton();
        addJunkStatsButton();
        addTrashStatsButton();

        try {
            LocalStoreProvider localStoreProvider = new LocalStoreProvider();
            localStore = localStoreProvider.getInstance(account);
            if(StatsActivityToggle.statsToggle == 0)
                localFolder = localStore.getFolder("INBOX");
            if(StatsActivityToggle.statsToggle == 1)
                localFolder = localStore.getFolder("Sent");
            if(StatsActivityToggle.statsToggle == 2)
                localFolder = localStore.getFolder("Junk");
            if(StatsActivityToggle.statsToggle == 3)
                localFolder = localStore.getFolder("Trash");
            //messages = localFolder.getMessages(listener);
            List<String> allMessages = localFolder.getAllMessageUids();
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
        setMostFrequentSender();

        pieChart = (PieChart) findViewById(R.id.pieChart);
        if (messages != null) {
            crunchEmotion();
            new ASyncWatsonToneAnayzer().execute(allMessageContent);
            // createPieChart();

        }
    }

// These Stats Buttons generate the appropriate Stats page and change specific text values for that page
    public void addInboxStatsButton() {
        inboxStatsButton = (Button) findViewById(R.id.buttonINBOX);
        inboxStatsButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                StatsActivityToggle.statsToggle = 0;
                StatsActivityToggle.thisWeek = "You've received ";
                StatsActivityToggle.emailType = " emails";
                StatsActivityToggle.emotionLabel = "How are people feeling?";
                StatsActivityToggle.mostFrequent = "You get a lot of emails from ";
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });
    }

    public void addSentStatsButton() {
        sentStatsButton = (Button) findViewById(R.id.buttonSENT);
        sentStatsButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                StatsActivityToggle.statsToggle = 1;
                StatsActivityToggle.thisWeek = "You've sent ";
                StatsActivityToggle.emailType = " emails";
                StatsActivityToggle.emotionLabel = "How were you feeling?";
                StatsActivityToggle.mostFrequent = "You send a lot of emails, ";
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });
    }

    public void addJunkStatsButton() {
        junkStatsButton = (Button) findViewById(R.id.buttonJUNK);
        junkStatsButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                StatsActivityToggle.statsToggle = 2;
                StatsActivityToggle.thisWeek = "You've received ";
                StatsActivityToggle.emailType = " junk mails";
                StatsActivityToggle.emotionLabel = "How is your junk feeling?";
                StatsActivityToggle.mostFrequent = "You get a lot of junk from ";
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });
    }

    public void addTrashStatsButton() {
        trashStatsButton = (Button) findViewById(R.id.buttonTRASH);
        trashStatsButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                StatsActivityToggle.statsToggle = 3;
                StatsActivityToggle.thisWeek = "You've deleted ";
                StatsActivityToggle.emailType = " emails";
                StatsActivityToggle.emotionLabel = "How is your trash feeling?";
                StatsActivityToggle.mostFrequent = "You delete a lot of emails from ";
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });
    }


    private void setMostFrequentSender() {
        TextView senderText = findViewById(R.id.senderID);

        senderText.setText(StatsActivityToggle.mostFrequent + getMostFrequentSender());
    }

    private void createPieChart() {

        TextView senderText = findViewById(R.id.emotionLabel);
        senderText.setText(StatsActivityToggle.emotionLabel);

        List<PieEntry> entries = new ArrayList<>();
        for (String key : pieData.keySet()
        ) {
            entries.add(new PieEntry(pieData.get(key), key));
        }

        PieDataSet pds = new PieDataSet(entries, "Email Tones");
        pds.setColors(PASTEL_COLORS);
        PieData data = new PieData(pds);
        data.setValueTextColor(Color.DKGRAY);
        pieChart.setData(data);
        pieChart.getDescription().setEnabled(false);
        pieChart.getLegend().setEnabled(false);
        pieChart.animateXY(3000, 3000, Easing.EaseOutCirc);

    }

    private void createDateBarChart() {
        days = new ArrayList<>();
        days.add(0, "Sun");
        days.add(1, "Mon");
        days.add(2, "Tue");
        days.add(3, "Wed");
        days.add(4, "Thu");
        days.add(5, "Fri");
        days.add(6, "Sat");
        barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(0, dayOfWeek.get("Sun")));
        barEntries.add(new BarEntry(1, dayOfWeek.get("Mon")));
        barEntries.add(new BarEntry(2, dayOfWeek.get("Tue")));
        barEntries.add(new BarEntry(3, dayOfWeek.get("Wed")));
        barEntries.add(new BarEntry(4, dayOfWeek.get("Thu")));
        barEntries.add(new BarEntry(5, dayOfWeek.get("Fri")));
        barEntries.add(new BarEntry(6, dayOfWeek.get("Sat")));


        XAxis xAxis = barChart.getXAxis();

        xAxis.setValueFormatter(new MyXAxisValueFormatter(days));

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(false);

        BarDataSet bds = new BarDataSet(barEntries, "Days");
        bds.setColors(PASTEL_COLORS);

        BarData theData = new BarData(bds);
        barChart.setTouchEnabled(true);
        barChart.getDescription().setEnabled(false);
        barChart.getLegend().setEnabled(false);
        barChart.setData(theData);
        barChart.setFitBars(true);
        barChart.animateX(3000, Easing.EaseInOutBack);

        TextView senderText = findViewById(R.id.lastWeek);

        senderText.setText(StatsActivityToggle.thisWeek + messagesLastWeek + StatsActivityToggle.emailType + " this week!");

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
            if (m.getSentDate().after(sevenDaysAgo))
                messagesLastWeek++;
        }
    }

    private void crunchEmotion() {
        for (LocalMessage m : messages)
            allMessageContent = allMessageContent + Jsoup.parse(m.getPreview()).text();
    }

    // Add each sender to hashtable
    public void addSender(Address sender) {
        if (mSenders.containsKey(sender)) {
            mSenders.put(sender, mSenders.get(sender) + 1);
        } else {
            int initialSenderCount = 1;
            mSenders.put(sender, initialSenderCount);
        }
    }

    // Get the most frequent sender from all local messages
    public String getMostFrequentSender() {
        int maxSender = Collections.max(mSenders.values());
        if (maxSender == 1)
            return "everyone";
        String mostFrequentSender = null;
        Enumeration senders;
        Address key;
        senders = mSenders.keys();
        while (senders.hasMoreElements()) {
            key = (Address) senders.nextElement();
            if (mSenders.get(key) == maxSender) {
                mostFrequentSender = key.getPersonal();
            }
        }
        return mostFrequentSender;
    }

    private void senderStats() {
        for (LocalMessage m : messages) {
            addSender(m.getFrom()[0]);
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

    private class MyXAxisValueFormatter extends ValueFormatter {
        private ArrayList<String> dayString;

        public MyXAxisValueFormatter(ArrayList<String> days) {
            dayString = days;
        }

        @Override
        public String getFormattedValue(float value) {
            return dayString.get((int) value);
        }
    }

    private class ASyncWatsonToneAnayzer extends AsyncEmotionWatson {

        @Override
        protected void onPostExecute(String anaylysedStringREsults) {
            try {

                JSONObject t = new JSONObject(anaylysedStringREsults);
                Iterator keys = t.keys();
                while (keys.hasNext()) {
                    String key = (String) keys.next();
                    pieData.put(key, Float.parseFloat(t.getJSONObject(key).get("effectivePercentage").toString()));
                }
                Log.i("tone", "onPostExecute: " + pieData.toString());
            } catch (Exception e) {
                Log.i("error", "onPostExecute: " + e.getMessage());
            }

            createPieChart();
        }
    }
}
