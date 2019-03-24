package com.fsck.k9.activity;

import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.fsck.k9.ui.R;
import com.fsck.k9.ui.RemindersActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import static org.junit.Assert.assertNotNull;

@RunWith(RobolectricTestRunner.class)
public class RemindersActivityTest {

    private RemindersActivity activity;

    @Before
    public void setup() throws Exception {
        Intent intent = new Intent(RuntimeEnvironment.application, RemindersActivity.class);
        activity = Robolectric.buildActivity(RemindersActivity.class, intent).create().get();
    }

    @Test
    public void shouldNotBeNull() {
        assertNotNull(activity);
    }

    @Test
    public void shouldHaveButtons() {
        Button dateButton = activity.findViewById(R.id.btn_date);
        Button timeButton = activity.findViewById(R.id.btn_time);

        Button submitButton = activity.findViewById(R.id.submit_reminder);


        assertNotNull(dateButton);
        assertNotNull(timeButton);

        assertNotNull(submitButton);
    }

    @Test
    public void shouldHaveTextFields() {
        EditText dateText = activity.findViewById(R.id.in_date);
        EditText timeText = activity.findViewById(R.id.in_time);
        TextView messageText = activity.findViewById(R.id.message_label);

        assertNotNull(dateText);
        assertNotNull(timeText);
        assertNotNull(messageText);
    }
}