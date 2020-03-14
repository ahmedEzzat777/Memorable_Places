package com.example.memorableplaces.Data;

import android.os.AsyncTask;

import needle.Needle;
import needle.UiRelatedProgressTask;
import needle.UiRelatedTask;

public abstract class AsyncDbTask{

    public AsyncDbTask(){
        Needle.onBackgroundThread().execute(new UiRelatedTask<Void>() {
            @Override
            protected Void doWork() {
                doInBackground();
                return null;
            }

            @Override
            protected void thenDoUiRelatedWork(Void v) {
                onPostExecute();
            }
        });
    }

    protected abstract void doInBackground();


    protected abstract void onPostExecute();

}
