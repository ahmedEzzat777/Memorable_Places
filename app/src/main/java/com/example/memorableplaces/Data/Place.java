package com.example.memorableplaces.Data;

import android.content.Context;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.memorableplaces.Model.Places;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

@Entity(tableName = "places")
public class Place {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "address")
    public String address;
    @ColumnInfo(name = "latitude")
    public double latitude;
    @ColumnInfo(name = "longitude")
    public double longitude;

    public Place(int id,String address,double latitude,double longitude){
        this.id =id;
        this.address=address;
        this.latitude=latitude;
        this.longitude=longitude;
    }
    @Ignore
    public Place(String address,double latitude,double longitude){
        this.address=address;
        this.latitude=latitude;
        this.longitude=longitude;
    }

    public static void addPlaces(Context context, Places places){
        PlaceDatabase db= PlaceDatabase.getInstance(context);

        ArrayList<Place> placeList = new ArrayList<Place>();
        ArrayList<Places.Place> placesModel = places.getPlaces();
        for (Places.Place i :placesModel) {
            Place place = new Place(i.Address,i.Lat,i.Long);
            placeList.add(place);
        }

        db.placeDao().insertPlaces(placeList);
    }

    public static void deletePlace(Context context, Places.Place place){
        PlaceDatabase db= PlaceDatabase.getInstance(context);

        Place dbPlace = db.placeDao().getPlace(place.Address);

        db.placeDao().deletePlace(dbPlace);
    }


    public static ArrayList<Places.Place> getPlaces(Context context){
        PlaceDatabase db= PlaceDatabase.getInstance(context);

        ArrayList<Places.Place> placeList = new ArrayList<Places.Place>();
        Places places = new Places();
        ArrayList<Place> dbPlaces = (ArrayList<Place>) db.placeDao().getPlaceList();
        for (Place i :dbPlaces) {
            places.addPlace(i.address,new LatLng(i.latitude,i.longitude),false);
            placeList = places.getPlaces();
        }

        return placeList;
    }
}
