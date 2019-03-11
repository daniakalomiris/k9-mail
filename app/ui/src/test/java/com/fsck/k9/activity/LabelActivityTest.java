package com.fsck.k9.activity;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.widget.Button;
import android.widget.EditText;

import com.fsck.k9.ui.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(RobolectricTestRunner.class)
public class LabelActivityTest {

    private LabelPage activity;

    @Before
    public void setup() throws Exception {
        Intent intent = new Intent(RuntimeEnvironment.application, LabelPage.class);
        activity = Robolectric.buildActivity(LabelPage.class, intent).create().get();
    }

    @Test
    public void shouldNotBeNull() {
        assertNotNull(activity);
    }

    @Test
    public void shouldHaveLabelButton() {
        Button labelButton = (Button) activity.findViewById(R.id.label_button);
        int labelButtonHeight =  ((ConstraintLayout.LayoutParams) labelButton.getLayoutParams()).height;
        int labelButtonWidth = ((ConstraintLayout.LayoutParams) labelButton.getLayoutParams()).width;

        assertEquals(54, labelButtonHeight);
        assertEquals(217, labelButtonWidth);
    }

    @Test
    public void shouldReadLabelOnButtonClick() {
        Button labelButton = (Button) activity.findViewById(R.id.label_button);
        EditText editText = (EditText) activity.findViewById(R.id.label);
        editText.setText("my label");
        boolean clicked = labelButton.performClick();
        assertEquals(activity.labelString, "my label");
    }
    @Test
    public void shouldNotReadEmptyLabelOnButtonClick() {
        Button labelButton = (Button) activity.findViewById(R.id.label_button);
        EditText editText = (EditText) activity.findViewById(R.id.label);
        boolean clicked = labelButton.performClick();
        assertNotEquals(activity.labelString, "my label");
    }
}
