package com.android.simpleasynctask;

import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.Random;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class SimpleAsyncTask extends AsyncTask<Void, Integer, String> {

    private WeakReference<TextView> mTextView;
    private WeakReference<TextView> mTextViewProgress;

    SimpleAsyncTask(TextView tv, TextView progress) {
        mTextView = new WeakReference<>(tv);
        mTextViewProgress = new WeakReference<>(progress);
    }

    @Override
    protected String doInBackground(Void... voids) {
        // Generate a random number between 0 and 10
        Random r = new Random();
        int n = r.nextInt(11);

        // Make the task take long enough that we have
        // time to rotate the phone while it is running
        int s = n * 200;

        publishProgress(s,n); //to publish Progress in the UI
        Log.d(TAG, "doInBackground: " + s);

        // Sleep for the random amount of time
        try {
            Thread.sleep(s);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Return a String result
        return "Awake at last after sleeping for " + s + " milliseconds!";
    }

    protected void onProgressUpdate(Integer... result){
        //this is what will you see in the sleeping mode (progress time)
        
        mTextViewProgress.get().setText("Waiting For Progress: " + result[0] + " millisecond");

    }

    protected void onPostExecute(String result) {
        mTextView.get().setText(result);
    }
}