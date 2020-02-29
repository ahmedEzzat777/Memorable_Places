package com.example.memorableplaces.Model;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.ArrayList;

public class Places implements Serializable {
    private ArrayList<Place> m_places;
    public Places(){
        m_places = new ArrayList<>();
    }
    public void addPlace(String address, LatLng latLng){
        m_places.add(new Place(address,latLng));
    }
    public void addPlace(String address, LatLng latLng,boolean newFlag){
        m_places.add(new Place(address,latLng,newFlag));
    }
    public void addPlaces(Places places){
        m_places.addAll(places.getPlaces());
    }
    public ArrayList<Place> getPlaces(){
        return m_places;
    }
    public void setPlaces(ArrayList<Places.Place> places){
        m_places.clear();
        m_places.addAll(places);
    }
    public void deletePlace(Place placeToDelete) {
        for (Places.Place p : m_places){
            if(p == placeToDelete){
                m_places.remove(p);
                break;
            }
        }
    }

    public class Place implements Serializable{
        public String Address;
        public double Lat;
        public double Long;
        public boolean Modified;
        public Place(String address,LatLng latLng){
            Address = address;
            Lat = latLng.latitude;
            Long = latLng.longitude;
            Modified = true;
        }
        public Place(String address,LatLng latLng,boolean modified){
            Address = address;
            Lat = latLng.latitude;
            Long = latLng.longitude;
            Modified = modified;
        }

    }
}