package com.fsck.k9;


import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.fsck.k9.activity.MessageCompose;
import com.fsck.k9.activity.MessageList;
import com.fsck.k9.ui.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(RobolectricTestRunner.class)
public class MessageComposeTest {
    private MessageCompose messageCompose;

    @Before
    public void setup() throws Exception {
        Intent intent = new Intent(RuntimeEnvironment.application, MessageCompose.class);
        messageCompose = Robolectric.buildActivity(MessageCompose.class, intent).create().get();
    }

    @Test
    public void shouldNotBeNull() {
        assertNotNull(messageCompose);
    }

    @Test
    public void sttButtonVisibleInitially() {
        MenuItem item = messageCompose.findViewById(R.id.stt);
        boolean sttButtonVisibility = item.isVisible();
        assertEquals(sttButtonVisibility, true);
    }
}
