package com.fsck.k9;

import android.app.Activity;
import android.view.LayoutInflater;
import android.widget.Button;

import com.fsck.k9.activity.MessageList;
import com.fsck.k9.ui.R;
import com.fsck.k9.ui.messageview.MessageContainerView;
import com.fsck.k9.ui.messageview.MessageTopView;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class MessageTopViewTest {

    private MessageTopView messageTopView;

    @Before
    public void setup() throws Exception {
        Activity activity = Robolectric.buildActivity(MessageList.class).create().get();
        messageTopView = (MessageTopView) LayoutInflater.from(activity).inflate(R.layout.message, null);
    }

    @Test
    public void shouldNotBeNull() {
        assertNotNull(messageTopView);
    }

    @Test
    public void ttsButtonVisibleInitially() {
        Button button = messageTopView.findViewById(R.id.tts);
        int ttsButtonVisibility = button.getVisibility();
        assertEquals(ttsButtonVisibility, Button.VISIBLE);
    }

    @Test
    public void stopTtsButtonInvisibleInitially() {
        Button button = messageTopView.findViewById(R.id.tts_stop);
        int ttsButtonVisibility = button.getVisibility();
        assertEquals(ttsButtonVisibility, Button.GONE);
    }

    @Test
    public void stopTtsButtonVisibleWhenClicked() {
        MessageContainerView mcv = mock(MessageContainerView.class);

        when(mcv.getJustTheText()).thenReturn("This is a string to be read");

        Button startButton = messageTopView.findViewById(R.id.tts);
        Button stopButton = messageTopView.findViewById(R.id.tts_stop);
        startButton.performClick();
        assertEquals(startButton.getVisibility(), Button.GONE);
        assertEquals(stopButton.getVisibility(), Button.VISIBLE);
    }

    @Test
    public void ttsButtonVisibleWhenStopButtonClicked() {
        MessageContainerView mcv = mock(MessageContainerView.class);

        when(mcv.getJustTheText()).thenReturn("This is a string to be read");

        Button startButton = messageTopView.findViewById(R.id.tts);
        Button stopButton = messageTopView.findViewById(R.id.tts_stop);
        startButton.performClick();
        stopButton.performClick();
        assertEquals(startButton.getVisibility(), Button.VISIBLE);
        assertEquals(stopButton.getVisibility(), Button.GONE);
    }
    //cant test other methods because private
}
