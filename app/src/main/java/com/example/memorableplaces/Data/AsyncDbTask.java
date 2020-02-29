package com.example.memorableplaces.Data;

import android.os.AsyncTask;

//this class is just to simplify async task
public abstract class AsyncDbTask extends AsyncTask<Void,Void,Void> {

    public AsyncDbTask(){
        execute();
    }
    @Override
    protected Void doInBackground(Void... voids) {
        doInBackground();
        return null;
    }

    protected abstract void doInBackground();

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        onPostExecute();
    }

    protected abstract void onPostExecute();

}
