package com.android.simpleasynctask;

import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.Random;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class SimpleAsyncTask extends AsyncTask<Void, Integer, String> {

    private  static final int PROGRESS = 25;
    private WeakReference<TextView> mTextView;
    private WeakReference<TextView> mTextViewProgress;
    private WeakReference<ProgressBar> mProgressBar;

    SimpleAsyncTask(TextView tv, TextView progress, ProgressBar bar) {
        mTextView = new WeakReference<>(tv);
        mTextViewProgress = new WeakReference<>(progress);
        mProgressBar = new WeakReference<>(bar);
    }

    @Override
    protected String doInBackground(Void... voids) {
        // Generate a random number between 0 and 10
        Random r = new Random();
        int n = r.nextInt(11);

        // Make the task take long enough that we have
        // time to rotate the phone while it is running
        int s = n * 250;
        int sizaBar = s/PROGRESS;


        for (int i = 0; i < PROGRESS; i++) {
            // Sleep for the random amount of time
            try {
                Thread.sleep(sizaBar);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            publishProgress(((i + 1) * 100 / PROGRESS));
        }

        // Return a String result
        return "Awake at last after sleeping for " + s + " milliseconds!";
    }

    protected void onProgressUpdate(Integer... result){
        //this is what will you see in the sleeping mode (progress time)

        mTextViewProgress.get().setText("Waiting For Progress: " + result[0] + "");
        mProgressBar.get().setProgress(result[0]); // untuk UI update
    }

    protected void onPostExecute(String result) {
        mTextView.get().setText(result);
    }
}