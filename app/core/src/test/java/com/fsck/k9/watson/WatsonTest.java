package com.fsck.k9.watson;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class WatsonTest {

    private Watson watson;

    @Before
    public void setUp() throws Exception {
        watson = Watson.getInstance();
    }

    @Test
    public void authentication_test() throws Exception {

        String result = watson.test("Hello");
        assertEquals("Hola", result);
    }
}
