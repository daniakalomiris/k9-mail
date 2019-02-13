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
    public void detectLanguage_test() throws Exception {

        String result = watson.detectLanguage("this is an english phrase and should be detected as such");
        assertEquals("en", result);
    }
    @Test
    public void translateLanguage_test() throws Exception {
        Watson.emailLanguage = "es";
        Watson.deviceLanguage = "en";
        String result = watson.translateLanguage("Este es una prueba.");
        assertEquals("This is a test.", result);
    }
    @Test
    public void doesDeviceLanguageEqualEmailLanguage_TRUE_test() throws Exception {
        Watson.emailLanguage = "en";
        Watson.deviceLanguage = "en";
        boolean result = watson.doesDeviceLanguageEqualEmailLanguage();
        assertEquals(true, result);
    }
    @Test
    public void doesDeviceLanguageEqualEmailLanguage_FALSE_test() throws Exception {
        Watson.emailLanguage = "en";
        Watson.deviceLanguage = "fr";
        boolean result = watson.doesDeviceLanguageEqualEmailLanguage();
        assertEquals(false, result);
    }

}
