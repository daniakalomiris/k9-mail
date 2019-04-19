package com.fsck.k9.watson;

import com.ibm.watson.developer_cloud.service.security.IamOptions;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.ToneAnalyzer;

import java.util.Locale;


public class WatsonEmotion {


    // IBM credentials
    private final static String TONE_ANALYZER_API_KEY="4Ss3O4RCqYX4-EbP4mcdW-8qx3-P0MUhhyCB-Udw1GD2";
    private final static String TONE_ANALYZER_URL="https://gateway-wdc.watsonplatform.net/tone-analyzer/api";
    private final static String TONE_ANALYZER_API_VERZION="2017-09-21";
    private static final WatsonEmotion ourInstance = new WatsonEmotion();

    private ToneAnalyzer service;

    /**
     * @return Watson
     */
    public static WatsonEmotion getInstance() {
        ourInstance.authenticate();
        return ourInstance;
    }

    private WatsonEmotion() {}

    /**
     * Validate our IAM credentials to access Language Translation API
     */
    private void authenticate(){
        try {
            IamOptions iamOptions = new IamOptions.Builder()
                    .apiKey(TONE_ANALYZER_API_KEY)
                    .build();

            this.service = new ToneAnalyzer(TONE_ANALYZER_API_VERZION, iamOptions);
            this.service.setEndPoint(TONE_ANALYZER_URL);

        }catch(Exception e){
            throw e;
        }
    }


}