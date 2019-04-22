package com.fsck.k9.watson;

import android.os.AsyncTask;


public abstract class AsyncEmotionWatson extends AsyncTask<String, String, String> {

    WatsonEmotion watson = WatsonEmotion.getInstance();

    // calls watson's function in a separate asynchronous thread from UI so it doesn't block it
    @Override
    protected String doInBackground(String... strings) {
        String stringToAnalyze = strings[0];

        return watson.analyzeTone(stringToAnalyze);
    }

    // This is the callback that is overrided in it's implementation (changes ui)
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
