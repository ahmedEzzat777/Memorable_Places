package com.example.memorableplaces.Data;

import android.content.Context;
import android.os.AsyncTask;

import com.example.memorableplaces.MainActivity.PlacesAdapter;
import com.example.memorableplaces.Model.Places;

public class MainDbTask extends AsyncTask<MainDbTask.Operation,Void,Void> {

    private Places.Place m_placeToDelete;
    private Context m_context;
    private PlacesAdapter m_adapter;
    private Places m_places;

    public MainDbTask(Context context, Places places, PlacesAdapter adapter){
        m_places = places;
        m_adapter = adapter;
        m_context = context;
    }

    public MainDbTask(Context context, Places places, PlacesAdapter adapter,Places.Place placeToDelete){
        m_places = places;
        m_adapter = adapter;
        m_context = context;
        m_placeToDelete = placeToDelete;
    }


    @Override
    protected Void doInBackground(Operation... op) {
        switch (op[0]){
            case Save:
                new PlacesDbController(m_context,m_places).postPlaces();
                break;
            case Load:
                new PlacesDbController(m_context,m_places).loadPlaces();
                break;
            case Delete:
                new PlacesDbController(m_context,m_places).deletePlace(m_placeToDelete);
                m_places.deletePlace(m_placeToDelete);
                break;
            default:
                break;
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        m_adapter.notifyDataSetChanged();
    }

    public enum Operation{
        Save,
        Load,
        Delete
    }
}
