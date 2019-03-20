package com.fsck.k9.activity;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.fsck.k9.ui.R;
import com.fsck.k9.ui.RemindersActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(RobolectricTestRunner.class)
public class RemindersTest {

    private RemindersActivity activity;
    private View view;


    @Before
    public void setUp() {
//        view = mock(View.class);
//        activity = mock(RemindersActivity.class);
//        when(view.getId()).thenReturn(123);
        Intent intent = new Intent(RuntimeEnvironment.application, RemindersActivity.class);
        activity = Robolectric.buildActivity(RemindersActivity.class, intent).create().get();
    }

    @Test
    public void shouldSendReminderWhenSubmit(){
        Button submitButton = activity.findViewById(R.id.submit_reminder);
//        mockBtn = mock(submitButton.class);
//        Button smr = activity.submitReminder;
//        Button srm = mock(Button.class);
//        srm.equals()
//        activity.onClick(smr);
        EditText txtDate = activity.findViewById(R.id.in_date);
        EditText txtTime = activity.findViewById(R.id.in_time);
        EditText txtMessage = activity.findViewById(R.id.reminder_message);
//        EditText txtDate = mock(EditText.class);
//        EditText txtTime = mock(EditText.class);
//        EditText txtMessage = mock(EditText.class);
//        txtDate.setText("25-10-2018", TextView.BufferType.EDITABLE);


//        EditText txtTime = activity.findViewById(R.id.in_time);
//        txtDate.setText("20:00:00", TextView.BufferType.EDITABLE);
//        String reminderDate = "25-10-2018";
//        String reminderTime = "20:00:00";
        String reminderMessage = "test reminder";
        RemindersActivity rmSpy = spy( new RemindersActivity());
        rmSpy.submitReminder = submitButton;

//        EditText txtDatte= new EditText();
        txtDate.setText("25-10-2018", TextView.BufferType.EDITABLE);
        txtTime.setText("25-10-2018", TextView.BufferType.EDITABLE);
        txtMessage.setText(reminderMessage, TextView.BufferType.EDITABLE);
//        when(rmSpy.txtDate.getText()).thenReturn(txtDate.getText());
//        doCallRealMethod().when(rmSpy.txtDate.getText()).thenReturn("25-10-2018");

        rmSpy.txtDate = txtDate;
        rmSpy.txtTime = txtTime;
        rmSpy.txtMessage = txtMessage;

//        when(rmSpy.txtMessage.getText()).thenReturn(new Mockitot);
//        rmSpy.txtMessage = txtMessage.setText(reminderMessage, TextView.BufferType.EDITABLE);
//        when(rmSpy.sendReminder(reminderMessage)).thenReturn(true);
        Mockito.doReturn(true).when(rmSpy).sendReminder(reminderMessage);
        Mockito.doReturn(true).when(rmSpy).navigateToparent();
        rmSpy.onClick(submitButton);
        verify(rmSpy).sendReminder(reminderMessage);
    }
}
