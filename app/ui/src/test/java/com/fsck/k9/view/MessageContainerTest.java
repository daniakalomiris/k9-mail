package com.fsck.k9.view;


import android.content.Context;
import android.util.AttributeSet;

import com.fsck.k9.ui.messageview.MessageContainerView;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Test;


public class MessageContainerTest {

    private String emailHtml = "<html><head></head><body><meta name=\"viewport\" content=\"width=device-width><style type=\"text/css\">" +
            " pre.k9mail {white-space: pre-wrap; word-wrap:break-word; font-family: sans-serif; margin-top: 0px}</style>\n" +
            "      <div dir=\"ltr\">this is a test email to see whats up. what do you think?</div></body></html>";

    private String expectedBody = "this is a test email to see whats up. what do you think?";

    @Test
    public void checkCorrectBody() {

        Context context = mock(Context.class);
        AttributeSet attributeSet = mock(AttributeSet.class);

        MessageContainerView msv = new MessageContainerView(context, attributeSet);
        String actualBody = msv.getMessageText(emailHtml);

        assertEquals(expectedBody, actualBody);
    }

}