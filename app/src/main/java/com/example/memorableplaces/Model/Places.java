package com.example.memorableplaces.Model;

import android.content.Context;

import com.example.memorableplaces.Data.PlaceDatabase;
import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.ArrayList;

public class Places implements Serializable {
    ArrayList<Place> _places;
    public Places(){
        _places = new ArrayList<Place>();
    }
    public void addPlace(String address, LatLng latLng){
        _places.add(new Place(address,latLng));
    }
    public void addPlace(String address, LatLng latLng,boolean newFlag){
        _places.add(new Place(address,latLng,newFlag));
    }
    public void addPlaces(Places places){
        _places.addAll(places.getPlaces());
    }
    public ArrayList<Place> getPlaces(){
        return _places;
    }

    public void deletePlace(Place placeToDelete) {
        for (Places.Place p :_places){
            if(p == placeToDelete){
                _places.remove(p);
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