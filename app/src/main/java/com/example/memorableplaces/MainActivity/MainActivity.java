package com.example.memorableplaces.MainActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.memorableplaces.Data.AsyncDbTask;
import com.example.memorableplaces.Data.PlacesDbController;
import com.example.memorableplaces.Model.Places;
import com.example.memorableplaces.R;


public class MainActivity extends AppCompatActivity {

    private Places m_placesModel;
    private PlacesAdapter m_placesAdapter;
    private PlacesDbController m_placesDbController;
    private AsyncDbTask m_asyncDbTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        m_placesModel = new Places();
        m_placesDbController = new PlacesDbController(this,m_placesModel);
        RecyclerView placesView = findViewById(R.id.placesRecycler);
        m_placesAdapter = new PlacesAdapter(MainActivity.this, m_placesModel);
        placesView.setAdapter(m_placesAdapter);
        placesView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        m_asyncDbTask = new AsyncDbTask(){
            @Override
            protected void doInBackground() {
                m_placesDbController.loadPlaces();
            }

            @Override
            protected void onPostExecute() {
                m_placesAdapter.notifyDataSetChanged();
            }
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            Places places = (Places) data.getSerializableExtra("places");
            if(places == null){
                return;
            }
            m_placesModel.addPlaces(places);
            m_placesAdapter.notifyDataSetChanged();
    }

    public void addPlaces(View view){
        m_asyncDbTask = new AsyncDbTask(){
            @Override
            protected void doInBackground() {
                m_placesDbController.postPlaces();
            }

            @Override
            protected void onPostExecute() {
                m_placesAdapter.notifyDataSetChanged();
            }
        };
    }
    public void deletePlace(Places.Place place){ //called from recycleradapter
        final Places.Place placeToDelete = place;
        m_asyncDbTask = new AsyncDbTask(){
            @Override
            protected void doInBackground() {
                m_placesDbController.deletePlace(placeToDelete);
                m_placesModel.deletePlace(placeToDelete);
            }

            @Override
            protected void onPostExecute() {
                m_placesAdapter.notifyDataSetChanged();
            }
        };
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(m_asyncDbTask != null)
            m_asyncDbTask.cancel(true);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("places",m_placesModel);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        if(m_asyncDbTask != null) {
            try {
                m_asyncDbTask.get(); //wait till db thread finishes to restore previous instant
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        super.onRestoreInstanceState(savedInstanceState);
        //if we used m_placesModel =(Places)savedInstanceState.getSerializable(... it will change the refrence from
        //that in adapter making notify useless
        Places places = (Places)savedInstanceState.getSerializable("places");
        if(places == null){
            return;
        }
        m_placesModel.setPlaces(places.getPlaces());
        m_placesAdapter.notifyDataSetChanged();
    }
}
