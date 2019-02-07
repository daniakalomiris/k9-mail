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
    @Test
    public void detectLanguage_test() throws Exception {

        String result = watson.detectLanguage("this is an english phrase and should be detected as such");
        assertEquals("en", result);
    }
    @Test
    public void translateLanguage_test() throws Exception {

        String result = watson.translateLanguage("Este es una prueba.");
        assertEquals("This is a test.", result);
    }
}
