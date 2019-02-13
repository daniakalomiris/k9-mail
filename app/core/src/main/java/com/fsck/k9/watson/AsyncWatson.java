package com.fsck.k9.watson;

import android.os.AsyncTask;

public abstract class AsyncWatson extends AsyncTask<String,String,String> {

    Watson watson = Watson.getInstance();

    // calls watson's function in a separate asynchronous thread from UI so it doesn't block it
    @Override
    protected String doInBackground(String... strings) {

        // This task is called in 2 separate cases, so there is an if statement to determine which code to implement

        // CASE 1: Called when you first open an e-mail. Called by: MessageTopView.ASyncWatsonShowTranslateButtonIfNeeded class. It sets the e-mail language so Translation button will know to pop up or not.
        if (!Watson.isEmailLanguageDetectedYet) {
            Watson.emailLanguage = watson.detectLanguage(strings[0]);
            return Watson.emailLanguage;
        } else {
            // CASE 2: Called after you click the Translate button. Called by: MessageContainerView.WatsonTask class. Translates e-mail.
            String result = watson.translateLanguage(strings[0]);
            return result;
        }

    }

    // This is the callback that is overrided in it's implementation (changes ui)
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
