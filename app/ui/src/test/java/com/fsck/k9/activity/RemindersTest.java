package com.fsck.k9.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.fsck.k9.ui.R;
import com.fsck.k9.ui.RemindersActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;


@RunWith(RobolectricTestRunner.class)
public class RemindersTest {

    private RemindersActivity activity;
    private View view;


    @Before
    public void setUp() {
        Intent intent = new Intent(RuntimeEnvironment.application, RemindersActivity.class);
        activity = Robolectric.buildActivity(RemindersActivity.class, intent).create().get();
    }

    @Test
    public void shouldSendReminderWhenSubmit(){
        Button submitButton = activity.findViewById(R.id.submit_reminder);
        EditText txtDate = activity.findViewById(R.id.in_date);
        EditText txtTime = activity.findViewById(R.id.in_time);
        EditText txtMessage = activity.findViewById(R.id.reminder_message);

        String reminderMessage = "test reminder";
        RemindersActivity rmSpy = spy( new RemindersActivity());
        rmSpy.submitReminder = submitButton;

        txtDate.setText("25-10-2018", TextView.BufferType.EDITABLE);
        txtTime.setText("11:48", TextView.BufferType.EDITABLE);
        txtMessage.setText(reminderMessage, TextView.BufferType.EDITABLE);

        rmSpy.txtDate = txtDate;
        rmSpy.txtTime = txtTime;
        rmSpy.txtMessage = txtMessage;

        Mockito.doReturn(true).when(rmSpy).sendReminder(reminderMessage);
        Mockito.doReturn(true).when(rmSpy).navigateToparent();
        rmSpy.onClick(submitButton);
    }
}
