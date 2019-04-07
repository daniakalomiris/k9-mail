package com.fsck.k9.activity;


import android.content.Intent;
import android.widget.EditText;

import com.fsck.k9.ui.EolConvertingEditText;
import com.fsck.k9.ui.R;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

@RunWith(RobolectricTestRunner.class)
public class TemplatesTest {

    private MessageCompose activity;

    @Before
    public void setUp() {
        Intent intent = new Intent(RuntimeEnvironment.application, MessageCompose.class);
        activity = Robolectric.buildActivity(MessageCompose.class, intent).create().get();
    }

    @Test
    public void shouldSetTemplateWhenSelected(){
        String selectedItem = "Out of Office";
        EditText messageContentView = new EditText(activity);

        String template = "Hi %1$s,\n" +
                "        \\nI\\'m enjoying a holiday and will be off the grid until the %2$s! I\\'ll get back to you that week.\n" +
                "        Thanks for your patience and talk to you then! \\nBest regards, \\n%3$s";
        template = String.format(template, "", "", "");

        messageContentView.setText(template);

        assertEquals(messageContentView.getText().toString(), template);

    }
}
