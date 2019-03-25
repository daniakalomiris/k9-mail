package com.fsck.k9.activity;

import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.fsck.k9.ui.R;
import com.fsck.k9.activity.StatsActivity;

import com.google.common.math.Stats;
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
public class StatsActivityTest {

    private StatsActivity activity;

    /*@Before
    public void setup() throws Exception {
        Intent intent = new Intent(RuntimeEnvironment.application, Stats.class);
        activity = Robolectric.buildActivity(StatsActivity.class, intent).create().get();
    }

    @Test
    public void shouldNotBeNull() {
        assertNotNull(activity);
    }*/

}
