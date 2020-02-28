package com.example.memorableplaces.Data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
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
}
