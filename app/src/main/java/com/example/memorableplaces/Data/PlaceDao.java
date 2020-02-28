package com.example.memorableplaces.Data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface PlaceDao {
    @Query("SELECT * FROM places")
    List<Place> getPlaceList();
    @Insert
    public void insertPlace(Place place);
    @Insert
    public void insertPlaces(ArrayList<Place> place);
    @Update
    public void updatePlace(Place place);
    @Delete
    public void deletePlace(Place place);
}
