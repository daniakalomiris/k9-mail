package com.fsck.k9;

import java.util.Hashtable;
import java.util.Collections;
import java.util.Enumeration;

public class Statistics {

    private static Hashtable<String, Integer> mSenders = new Hashtable<>();
    private static Hashtable<String, Integer> mDates = new Hashtable<>();

    public void addSender(String sender) {
        if (mSenders.containsKey(sender)) {
            mSenders.put(sender, mSenders.get(sender) + 1);
        } else {
            int initialSenderCount = 1;
            mSenders.put(sender, initialSenderCount);
        }
    }

    public void addSentDate(String sentDate) {
        if (mDates.containsKey(sentDate)) {
            mDates.put(sentDate, mDates.get(sentDate) + 1);
        } else {
            int initialSentDateCount = 1;
            mDates.put(sentDate, initialSentDateCount);
        }
    }

    public String getMostFrequentSender() {
        int maxSender = Collections.max(mSenders.values());

        String mostFrequentSender = null;
        Enumeration senders;
        String key;
        senders = mSenders.keys();
        while(senders.hasMoreElements()) {
            key = (String) senders.nextElement();
            if (mSenders.get(key) == maxSender) {
                mostFrequentSender = key;
            }
        }
        return mostFrequentSender;
    }

    public String getMostFrequentDate() {
        int maxDate = Collections.max(mDates.values());

        String mostFrequentDate = null;
        Enumeration dates;
        String key;
        dates = mDates.keys();
        while(dates.hasMoreElements()) {
            key = (String) dates.nextElement();
            if (mDates.get(key) == maxDate) {
                mostFrequentDate = key;
            }
        }
        return mostFrequentDate;
    }
}
