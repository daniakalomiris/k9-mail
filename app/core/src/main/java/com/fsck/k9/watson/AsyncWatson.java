package com.fsck.k9.watson;

import android.os.AsyncTask;

public abstract class AsyncWatson extends AsyncTask<String,String,String> {

    Watson watson = Watson.getInstance();

    // calls watson's function in a separate asynchronous thread from UI so it doesn't block it
    @Override
    protected String doInBackground(String... strings) {

        String result = watson.translateLanguage(strings[0]);

        return result;
    }

    // This is the callback that is overrided in it's implementation (changes ui)
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
