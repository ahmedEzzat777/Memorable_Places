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
    public void loadPlaces(Context context){
        //_places.clear();
        _places.addAll(PlaceDatabase.getPlaces(context));
    }
    public void postPlaces(Context context){
        Places newPlaces = new Places();
        for (Place p :_places){
            if(p.NewFlag){
                newPlaces.addPlace(p.Address,new LatLng(p.Lat,p.Long));
            }
        }
        PlaceDatabase.addPlaces(context,newPlaces);
    }
    public class Place implements Serializable{
        public String Address;
        public double Lat;
        public double Long;
        public boolean NewFlag;
        public Place(String address,LatLng latLng){
            Address = address;
            Lat = latLng.latitude;
            Long = latLng.longitude;
            NewFlag = true;
        }
        public Place(String address,LatLng latLng,boolean newFlag){
            Address = address;
            Lat = latLng.latitude;
            Long = latLng.longitude;
            NewFlag = newFlag;
        }

    }
}
