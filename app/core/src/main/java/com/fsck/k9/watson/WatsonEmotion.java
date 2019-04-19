package com.fsck.k9.watson;


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.ibm.watson.developer_cloud.service.security.IamOptions;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.ToneAnalyzer;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.SentenceAnalysis;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneOptions;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneScore;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;


public class WatsonEmotion {


    // IBM credentials
    private final static String TONE_ANALYZER_API_KEY = "4Ss3O4RCqYX4-EbP4mcdW-8qx3-P0MUhhyCB-Udw1GD2";
    private final static String TONE_ANALYZER_URL = "https://gateway-wdc.watsonplatform.net/tone-analyzer/api";
    private final static String TONE_ANALYZER_API_VERZION = "2017-09-21";

    private static final WatsonEmotion ourInstance = new WatsonEmotion();

    private ToneAnalyzer service;

    /**
     * @return Watson
     */
    public static WatsonEmotion getInstance() {
        ourInstance.authenticate();
        return ourInstance;
    }

    private WatsonEmotion() {
    }

    /**
     * Validate our IAM credentials to access Language Translation API
     */
    private void authenticate() {
        try {
            IamOptions iamOptions = new IamOptions.Builder()
                    .apiKey(TONE_ANALYZER_API_KEY)
                    .build();

            this.service = new ToneAnalyzer(TONE_ANALYZER_API_VERZION, iamOptions);
            this.service.setEndPoint(TONE_ANALYZER_URL);

        } catch (Exception e) {
            throw e;
        }
    }

    public String analyzeTone(String text) {

        this.authenticate();

        ToneOptions toneOptions = new ToneOptions.Builder()
                .text(text)
                .build();

        List<SentenceAnalysis> toneAnalysis = this.service.tone(toneOptions).execute().getSentencesTone();


        DecimalFormat df2 = new DecimalFormat("#.##");
        JsonObject result = new JsonObject();
        JsonObject scoreDetails;
        double totalScore = 0;

        for (SentenceAnalysis sentence : toneAnalysis) {
            for (ToneScore score : sentence.getTones()) {

                if (!result.has(score.getToneName())) {
                    scoreDetails = new JsonObject();
                    scoreDetails.addProperty("score", score.getScore());
                    scoreDetails.addProperty("count", 1);
                    scoreDetails.addProperty("effectiveScore", score.getScore());
                    result.add(score.getToneName(), scoreDetails);
                } else {
                    scoreDetails = result.getAsJsonObject(score.getToneName());
                    scoreDetails.addProperty("score", df2.format(scoreDetails.get("score").getAsDouble() + score.getScore()));
                    scoreDetails.addProperty("count", scoreDetails.get("count").getAsInt() + 1);
                    scoreDetails.addProperty("effectiveScore", df2.format(scoreDetails.get("score").getAsDouble() / scoreDetails.get("count").getAsDouble()));
                    result.add(score.getToneName(), scoreDetails);
                }
                totalScore += +score.getScore();
            }


        }

        for (Map.Entry<String, JsonElement> scores : result.entrySet()
        )
            scores.getValue().getAsJsonObject().addProperty("effectivePercentage", df2.format(scores.getValue().getAsJsonObject().get("score").getAsDouble() / totalScore));

        return result.toString();

    }


}