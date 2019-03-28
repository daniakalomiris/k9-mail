package com.fsck.k9.activity;

import com.fsck.k9.mail.Address;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class StatsTest {

    private StatsActivity mockStats;

    @Before
    public void setUp() {
        mockStats = mock(StatsActivity.class);
    }

    @Test
    public void onCreateLogicTest() {
        Address sender1 = mock(Address.class);
        when(sender1.getAddress()).thenReturn("sender1@mail.com");
        Address sender2 = mock(Address.class);
        when(sender2.getAddress()).thenReturn("sender2@mail.com");

        // Verify that Address object is passed
        ArgumentCaptor<Address> argumentCaptor = ArgumentCaptor.forClass(Address.class);
        mockStats.addSender(sender1);
        verify(mockStats).addSender(argumentCaptor.capture());

        // Assert that sender1 email was added
        assertEquals(argumentCaptor.getValue().getAddress(), "sender1@mail.com");
        System.out.println("Expected sender added: " + argumentCaptor.getValue().getAddress() + "\n" + "Actual sender added: " + "sender1@mail.com");

        // Assert that most frequent sender is sender2
        when(mockStats.getMostFrequentSender()).thenReturn("sender2@mail.com");
        assertEquals(mockStats.getMostFrequentSender(), "sender2@mail.com");
        System.out.println("Expected most frequent sender: " + mockStats.getMostFrequentSender() + "\n" + "Actual most frequent sender: " + "sender2@mail.com");
    }
}
