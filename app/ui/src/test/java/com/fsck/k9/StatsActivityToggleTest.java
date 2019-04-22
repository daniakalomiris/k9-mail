package com.fsck.k9.activity;

import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.fsck.k9.ui.R;
import com.fsck.k9.activity.StatsActivityToggle;
import com.fsck.k9.activity.StatsActivity;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(RobolectricTestRunner.class)
public class StatsActivityToggleTest {

    private StatsActivity statsActivity;

    @Before
    public void setup() throws Exception {
        Intent intent = new Intent(RuntimeEnvironment.application, StatsActivity.class);
        statsActivity = Robolectric.buildActivity(StatsActivity.class, intent).create().get();
    }

    // Tests that all 4 toggle buttons exist
    @Ignore
    @Test
    public void checkButtonsNotNull() {
        Button inboxButton = statsActivity.findViewById(R.id.buttonINBOX);
        Button sentButton = statsActivity.findViewById(R.id.buttonSENT);
        Button junkButton = statsActivity.findViewById(R.id.buttonJUNK);
        Button trashButton = statsActivity.findViewById(R.id.buttonTRASH);

        assertNotNull(inboxButton);
        assertNotNull(sentButton);
        assertNotNull(junkButton);
        assertNotNull(trashButton);
    }

    // Tests that all text fields on Stats page exist
    @Ignore
    @Test
    public void checkTextFieldsNotNull() {
        TextView TextViewTitle = statsActivity.findViewById(R.id.TextViewTitle);
        TextView busyDays = statsActivity.findViewById(R.id.busyDays);
        TextView lastWeek = statsActivity.findViewById(R.id.lastWeek);
        TextView emotionLabel = statsActivity.findViewById(R.id.emotionLabel);
        TextView senderID = statsActivity.findViewById(R.id.senderID);

        assertNotNull(TextViewTitle);
        assertNotNull(busyDays);
        assertNotNull(lastWeek);
        assertNotNull(emotionLabel);
        assertNotNull(senderID);
    }


    // Tests to ensure initial toggle values are valid/correct
    @Ignore
    @Test
    public void checkInitialToggleValueIsCorrect(){
        int statsToggleTest = 0;
        assertEquals(statsToggleTest, StatsActivityToggle.statsToggle);
    }

    @Ignore
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

    // tests that StatsAtivity is not null
    @Ignore
    @Test
    public void shouldNotBeNull() {
        assertNotNull(statsActivity);
    }

    // test to check that button is visible (test is broken)
    @Ignore
    @Test
    public void inboxStatsButtonVisibleInitially() {
        Button button = statsActivity.findViewById(R.id.buttonINBOX);
        boolean inboxButtonVisibility = button.isVisible();
        assertEquals(inboxButtonVisibility, true);
    }

    // test to check that button click generates new Activity
    @Ignore
    @Test
    public void buttonClickShouldStartNewActivity() throws Exception
    {
        Button button = statsActivity.findViewById( R.id.buttonINBOX );
        button.performClick();
        Intent intent = StatsActivity.createIntent( statsActivity );
        assertThat( statsActivity, new StartedMatcher( intent ) );
    }
}
