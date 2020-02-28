package com.example.memorableplaces.Data;

import android.content.Context;

import com.example.memorableplaces.Model.Places;
import com.google.android.gms.maps.model.LatLng;

public class PlacesDbController{
    private Places m_places;
    private Context m_context;

    public PlacesDbController(Context context,Places places){
        m_context = context;
        m_places = places;
    }

    public void loadPlaces(){
        //_places.clear();
        m_places.getPlaces().addAll(Place.getPlaces(m_context));
    }
    public void postPlaces(){
        Places newPlaces = new Places();
        for (Places.Place p :m_places.getPlaces()){
            if(p.Modified){
                newPlaces.addPlace(p.Address,new LatLng(p.Lat,p.Long));
                p.Modified = false;
            }
        }
        Place.addPlaces(m_context,newPlaces);
    }
    public void deletePlace(Places.Place placeToDelete){
        Places newPlaces = new Places();
        for (Places.Place p :m_places.getPlaces()){
            if(p == placeToDelete){
                Place.deletePlace(m_context,p);
                break;
            }
        }
    }
}
