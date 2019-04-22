package com.fsck.k9;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import com.fsck.k9.activity.MessageList;
import com.fsck.k9.ui.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(RobolectricTestRunner.class)
public class MessageComposeTest {
    private View messageComposeView;

    @Before
    public void setup() throws Exception {
        Activity activity = Robolectric.buildActivity(MessageList.class).create().get();
        messageComposeView = LayoutInflater.from(activity).inflate(R.layout.message_compose,null);
    }

    @Test
    public void shouldNotBeNull() {
        assertNotNull(messageComposeView);
    }

    @Test
    public void sttButtonVisibleInitially() {
        Button button = messageComposeView.findViewById(R.id.stt);
        int ttsButtonVisibility = button.getVisibility();
        assertEquals(ttsButtonVisibility, Button.VISIBLE);
    }
}
