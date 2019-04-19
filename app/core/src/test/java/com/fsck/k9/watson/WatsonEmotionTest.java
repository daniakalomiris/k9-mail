package com.fsck.k9.watson;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class WatsonEmotionTest {

    private WatsonEmotion watson;

    @Before
    public void setUp() throws Exception {
        watson = WatsonEmotion.getInstance();
    }

    @Test
    public void doesItDetectAnger_TRUE_test() throws Exception {

        String result = watson.analyzeTone("i am angry.i am also angry");
        assertEquals("{\"Anger\":{\"score\":\"1.95\",\"count\":2,\"effectiveScore\":\"0.98\",\"effectivePercentage\":\"1\"}}", result);
    }
    @Test
    public void doesItDetectAnger_False_test() throws Exception {
        String result = watson.analyzeTone("i am happy. i am happy");
        assertNotEquals("{\"Anger\":{\"score\":\"1.95\",\"count\":2,\"effectiveScore\":\"0.98\",\"effectivePercentage\":\"1\"}}", result);
    }

}
