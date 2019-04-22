package com.fsck.k9.activity;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StatsToggleTest {

    // Tests to ensure intial toggle values are valid/correct
    @Test
    public void checkInitialToggleValueIsCorrect(){
        int statsToggleTest = 0;
        assertEquals(statsToggleTest, StatsActivityToggle.statsToggle);
    }

    @Test
    public void checkInitialToggleTextsAreCorrect(){
        String thisWeekTest = "You've received ";
        String emailTypeTest = " emails";
        String emotionLabelTest = "How are people feeling?";
        String mostFrequentTest = "You get a lot of emails from ";

        assertEquals(thisWeekTest, StatsActivityToggle.thisWeek);
        assertEquals(emailTypeTest, StatsActivityToggle.emailType);
        assertEquals(emotionLabelTest, StatsActivityToggle.emotionLabel);
        assertEquals(mostFrequentTest, StatsActivityToggle.mostFrequent);
    }
}
