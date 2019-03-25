package com.fsck.k9.activity;

import android.content.Intent;

import com.fsck.k9.mail.Address;
import com.fsck.k9.activity.StatsActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(RobolectricTestRunner.class)
public class StatsTest {

    StatsActivity activity;

    /*@Before
    public void setUp() {
        Intent intent = new Intent(RuntimeEnvironment.application, StatsActivity.class);
        activity = Robolectric.buildActivity(StatsActivity.class, intent).create().get();
    }

    @Test
    public void onCreateLogicTest() {
        StatsActivity mockStats = mock(StatsActivity.class);

        Address sender1 = new Address("sender1@mail.com");
        Address sender2 = new Address("sender2@mail.com");
        Address sender3 = new Address("sender3@mail.com");

        // add senders to hashtable
        mockStats.addSender(sender1);
        mockStats.addSender(sender2);
        mockStats.addSender(sender3);
        mockStats.addSender(sender2);

        verify(mockStats).addSender(any(Address.class));

        // check that most frequent sender is sender2
        when(mockStats.getMostFrequentSender()).thenReturn(sender2.getAddress());
    }*/
}
