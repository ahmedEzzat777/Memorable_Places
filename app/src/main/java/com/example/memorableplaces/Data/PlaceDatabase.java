package com.example.memorableplaces.Data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.memorableplaces.Model.Places;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
//TODO::implement Tasks to database operations
@Database(entities = Place.class,exportSchema = false,version = 1)
public abstract class PlaceDatabase extends RoomDatabase {
    private static final String DB_NAME ="Places_db";
    private static PlaceDatabase instance;
    public abstract PlaceDao placeDao();

    public static synchronized PlaceDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),PlaceDatabase.class,DB_NAME)
                            .fallbackToDestructiveMigration().build();
        }
        return instance;
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
