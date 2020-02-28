package com.example.memorableplaces.MainActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.memorableplaces.Data.MainDbTask;
import com.example.memorableplaces.Model.Places;
import com.example.memorableplaces.R;

import static com.example.memorableplaces.Data.MainDbTask.Operation.*;


public class MainActivity extends AppCompatActivity {

    public Places m_placesModel;
    PlacesAdapter m_placesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        m_placesModel = new Places();
        RecyclerView placesView = findViewById(R.id.placesRecycler);
        m_placesAdapter = new PlacesAdapter(MainActivity.this, m_placesModel.getPlaces());
        placesView.setAdapter(m_placesAdapter);
        placesView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        //TODO::load from db here
        new MainDbTask(this,m_placesModel, m_placesAdapter).execute(Load);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            m_placesModel.addPlaces((Places) data.getSerializableExtra("places"));
            m_placesAdapter.notifyDataSetChanged();
    }

    public void addPlaces(View view){
        new MainDbTask(this,m_placesModel, m_placesAdapter).execute(Save);
    }
    public void deletePlace(Places.Place place){ //called from recycleradapter
        new MainDbTask(this,m_placesModel, m_placesAdapter,place).execute(Delete);
    }
}
