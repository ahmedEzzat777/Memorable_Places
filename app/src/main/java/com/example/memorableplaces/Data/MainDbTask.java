package com.example.memorableplaces.Data;

import android.content.Context;
import android.os.AsyncTask;

import com.example.memorableplaces.MainActivity.PlacesAdapter;
import com.example.memorableplaces.Model.Places;

public class MainDbTask extends AsyncTask<MainDbTask.Operation,Void,Void> {

    private Context m_context;
    private PlacesAdapter m_adapter;
    private Places m_places;

    public MainDbTask(Context context, Places places, PlacesAdapter adapter){
        m_places = places;
        m_adapter = adapter;
        m_context = context;
    }

    @Override
    protected Void doInBackground(Operation... op) {
        switch (op[0]){
            case Save:
                m_places.postPlaces(m_context);
                break;
            case Load:
                m_places.loadPlaces(m_context);
                m_adapter.notifyDataSetChanged();
                break;
            default:
                break;
        }

        return null;
    }

    public enum Operation{
        Save,
        Load
    }
}
