package com.example.memorableplaces.Data.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.memorableplaces.Data.Table.Place;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface PlaceDao {
    @Query("SELECT * FROM places")
    List<Place> getPlaceList();
    @Query("SELECT * FROM places WHERE Address = :address LIMIT 1")
    Place getPlace(String address);

    @Insert
    public void insertPlace(Place place);
    @Insert
    public void insertPlaces(ArrayList<Place> place);
    @Update
    public void updatePlace(Place place);
    @Delete
    public void deletePlace(Place place);
}
